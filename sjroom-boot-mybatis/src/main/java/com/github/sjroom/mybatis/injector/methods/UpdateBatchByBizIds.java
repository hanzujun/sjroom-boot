package com.github.sjroom.mybatis.injector.methods;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.github.sjroom.mybatis.injector.BizSqlMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 根据 ID 批量更新有值字段
 *
 * @author dream.lu
 */
@Slf4j
public class UpdateBatchByBizIds extends AbstractBizIdMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        // 业务主键信息
        TableFieldInfo bizIdField = super.getBIdField(modelClass, tableInfo);
        if (bizIdField == null) {
            log.info("Entity {} 没有 @TableBId 注解.", modelClass);
            return null;
        }
        boolean logicDelete = tableInfo.isLogicDelete();
        BizSqlMethod sqlMethod = BizSqlMethod.UPDATE_BATCH_BY_BIDS;
        final String additional = optlockVersion() + tableInfo.getLogicDeleteSql(true, false);

        // bid 更新的时候要去除 set bid = xxx
        TableInfo bizIdTableInfo = UpdateByBizId.filterBizId(tableInfo, bizIdField);

        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
            sqlSet(logicDelete, false, bizIdTableInfo, false, ENTITY, ENTITY_DOT), bizIdField.getColumn(),
            SqlScriptUtils.convertForeach("#{item}", COLLECTION, null, "item", COMMA), additional);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource);
    }

}
