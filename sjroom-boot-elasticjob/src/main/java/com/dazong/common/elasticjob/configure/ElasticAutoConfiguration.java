package com.dazong.common.elasticjob.configure;

import com.dangdang.ddframe.job.lite.spring.job.util.AopTargetUtils;
import com.github.sjroom.common.startup.ApplicationStartupListener;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.dazong.common.elasticjob.properties.ElassticjobZkProperties;
import com.dazong.common.elasticjob.startup.FMJob;
import com.dazong.common.elasticjob.startup.FMJobStartupListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobCoreConfiguration.Builder;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dazong.common.elasticjob.annotation.EjTask;

/**
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 *
 * @author 贸易研发部：yanghui（think）
 * @since 2017年10月21日
 */
@Configuration
@Import(RegistryCenterConfigure.class)
public class ElasticAutoConfiguration implements ApplicationContextAware, BeanPostProcessor {

    @Autowired
    private CoordinatorRegistryCenter registryCenter;

    @Autowired(required = false)
    private DataSource dataSource;

    private ApplicationContext applicationContext;

    private FMJobStartupListener fmJobStartupListener;

    @Autowired
    private ElassticjobZkProperties elassticjobProperties;


    private JobCoreConfiguration buildJobCoreConfig(EjTask task, Object bean) {
        String jobName =
                StringUtils.isEmpty(task.jobName()) ? bean.getClass().getName() : task.jobName();
        Builder builder = JobCoreConfiguration.newBuilder(jobName, task.cron(), task.shardingTotalCount());
        builder.description(task.description())
                .jobParameter(task.jobParameter())
                .misfire(task.misfire())
                .failover(task.failover())
                .shardingItemParameters(task.shardingItemParameters());
        if (!StringUtils.isEmpty(task.properties())) {
            String[] pps = task.properties().split(";");
            for (String ps : pps) {
                String[] p = ps.split("=");
                builder.jobProperties(p[0], p[1]);
            }
        }
        JobCoreConfiguration jobCoreConfig = builder.build();
        return jobCoreConfig;
    }

    private SimpleJobConfiguration buildSimpleJobConfig(JobCoreConfiguration jobCoreConfig, Object elasticJob) {
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(jobCoreConfig,
                elasticJob.getClass().getCanonicalName());
        return simpleJobConfig;
    }

    private DataflowJobConfiguration buildDataflowConfig(JobCoreConfiguration jobCoreConfig, Object elasticJob, EjTask task) {
        DataflowJobConfiguration dataflowJobConfig = new DataflowJobConfiguration(jobCoreConfig,
                elasticJob.getClass().getCanonicalName(), task.isStreamingProcess());
        return dataflowJobConfig;
    }

    private LiteJobConfiguration buildLiteJobConfig(JobTypeConfiguration jobTypeConfig, EjTask task) {
        LiteJobConfiguration liteJobConfig = LiteJobConfiguration.newBuilder(jobTypeConfig)
                .overwrite(task.overwrite())
                .build();
        return liteJobConfig;
    }

    private void createJobScheduler(LiteJobConfiguration liteJobConfig, ElasticJob elasticJob, EjTask task) {
        if (task.jobEvent()) {
            JobEventConfiguration jobEventRdbConfig = new JobEventRdbConfiguration(dataSource);
            new SpringJobScheduler(elasticJob, this.registryCenter, liteJobConfig, jobEventRdbConfig,
                    getListener(task.jobListener())).init();
        } else {
            new SpringJobScheduler(elasticJob, this.registryCenter, liteJobConfig,
                    getListener(task.jobListener())).init();
        }
    }

    private ElasticJobListener[] getListener(Class<? extends ElasticJobListener>[] clzArr) {
        List<ElasticJobListener> listenerList = new ArrayList<ElasticJobListener>(0);
        for (Class<? extends ElasticJobListener> clz : clzArr) {
            if (clz.isInterface()) {
                continue;
            }
            ElasticJobListener listener = this.applicationContext.getBean(clz);
            if (listener != null) {
                listenerList.add(listener);
            }
        }
        return listenerList.toArray(new ElasticJobListener[]{});
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

        if (elassticjobProperties.isLazy()) {
            this.fmJobStartupListener = new FMJobStartupListener(registryCenter, dataSource);
            ApplicationStartupListener.addListener(fmJobStartupListener);
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        EjTask task = AnnotationUtils.findAnnotation(bean.getClass(), EjTask.class);
        if (task == null) {
            return bean;
        }
        if (task.jobEvent() && this.dataSource == null) {
            throw new RuntimeException("The data source does not exist！");
        }
        Object targetBean = AopTargetUtils.getTarget(bean);
        JobCoreConfiguration jobCoreConfig = buildJobCoreConfig(task, targetBean);
        JobTypeConfiguration jobTypeConfig = null;
        if (bean instanceof SimpleJob) {
            jobTypeConfig = buildSimpleJobConfig(jobCoreConfig, targetBean);
        } else if (bean instanceof DataflowJob) {
            jobTypeConfig = buildDataflowConfig(jobCoreConfig, targetBean, task);
        } else {
            throw new RuntimeException("The task type does not exist！");
        }
        LiteJobConfiguration liteJobConfig = buildLiteJobConfig(jobTypeConfig, task);
        if (elassticjobProperties.isLazy()) {
            fmJobStartupListener.addJob(new FMJob(liteJobConfig, (ElasticJob) bean, task));
        } else {
            createJobScheduler(liteJobConfig, (ElasticJob) bean, task);
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }
}
