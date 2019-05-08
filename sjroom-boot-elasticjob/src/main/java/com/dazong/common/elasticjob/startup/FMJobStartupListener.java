package com.dazong.common.elasticjob.startup;

import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.github.sjroom.common.startup.StartupListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huqichao
 * @date 2018-07-13 16:27
 */
public class FMJobStartupListener implements StartupListener {

    private static final Logger logger = LoggerFactory.getLogger(FMJobStartupListener.class);

    private List<FMJob> jobList;

    private CoordinatorRegistryCenter registryCenter;

    private DataSource dataSource;


    public FMJobStartupListener(CoordinatorRegistryCenter registryCenter, DataSource dataSource){
        this.registryCenter = registryCenter;
        this.dataSource = dataSource;

        this.jobList = new ArrayList<>();
    }

    /**
     * 应用启动完成，端口监听后执行方法
     * 当该方法抛出异常时，将会导致应用关闭
     * @param context spring context
     */
    @Override
    public void start(ApplicationContext context) {
        logger.debug("定时任务启动....");
        for (FMJob job : jobList){
            if(job.getTask().jobEvent()){
                JobEventConfiguration jobEventRdbConfig = new JobEventRdbConfiguration(dataSource);
                new SpringJobScheduler(job.getElasticJob(),this.registryCenter, job.getLiteJobConfig(),jobEventRdbConfig, getListener(job.getTask().jobListener(), context)).init();
            }else {
                new SpringJobScheduler(job.getElasticJob(),this.registryCenter, job.getLiteJobConfig(), getListener(job.getTask().jobListener(), context)).init();
            }
        }

        logger.debug("定时任务启动完成");
    }

    /**
     * 检测该监听器执行完方式是否都成功
     *
     * @param context spring context
     * @return 检测状态
     */
    @Override
    public boolean check(ApplicationContext context) {
        return true;
    }

    private ElasticJobListener[] getListener(Class<? extends ElasticJobListener>[] clzArr, ApplicationContext context){
        List<ElasticJobListener> listenerList = new ArrayList<ElasticJobListener>(0);
        for(Class<? extends ElasticJobListener> clz : clzArr){
            if(clz.isInterface()){
                continue;
            }
            ElasticJobListener listener = context.getBean(clz);
            if(listener != null){
                listenerList.add(listener);
            }
        }
        return listenerList.toArray(new ElasticJobListener[]{});
    }

    /**
     * 执行顺序，越小越前执行
     * @return 执行顺序
     */
    @Override
    public int order() {
        return 99;
    }

    public void addJob(FMJob fmJob) {
        this.jobList.add(fmJob);
    }
}
