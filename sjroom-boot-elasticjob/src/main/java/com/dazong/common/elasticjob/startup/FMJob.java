package com.dazong.common.elasticjob.startup;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dazong.common.elasticjob.annotation.EjTask;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author huqichao
 * @date 2018-07-13 16:28
 */
@Data
@AllArgsConstructor
public class FMJob {

    private LiteJobConfiguration liteJobConfig;

    private ElasticJob elasticJob;

    private EjTask task;
}
