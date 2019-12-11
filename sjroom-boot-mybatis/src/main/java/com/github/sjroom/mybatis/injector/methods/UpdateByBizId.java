package com.github.sjroom.mybatis.injector.methods;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.github.sjroom.mybatis.injector.BizSqlMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据 ID 更新有值字段
 *
 * @author hubin
 * @since 2018-04-06
 */
@Slf4j
public class UpdateByBizId extends AbstractBizIdMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        // 业务主键信息
        TableFieldInfo bizIdField = super.getBIdField(modelClass, tableInfo);
        if (bizIdField == null) {
            log.info("Entity {} 没有 @TableBId 注解.", modelClass);
            return null;
        }
        boolean logicDelete = tableInfo.isLogicDelete();
        BizSqlMethod sqlMethod = BizSqlMethod.UPDATE_BY_BID;
        final String additional = optlockVersion() + tableInfo.getLogicDeleteSql(true, false);

        // bid 更新的时候要去除 set bid = xxx
        TableInfo bizIdTableInfo = UpdateByBizId.filterBizId(tableInfo, bizIdField);

        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
            sqlSet(logicDelete, false, bizIdTableInfo, false, ENTITY, ENTITY_DOT),
            bizIdField.getColumn(), ENTITY_DOT + bizIdField.getProperty(), additional);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource);
    }

    /**
     * 因为根据业务id进行更新的时候有值也不能 set
     *
     * @param tableInfo TableInfo
     * @return TableInfo
     */
    public static TableInfo filterBizId(TableInfo tableInfo, TableFieldInfo bizIdField) {
        BizIdTableInfo bizTableInfo = new BizIdTableInfo();
        List<TableFieldInfo> bizFieldInfoList = new ArrayList<>(tableInfo.getFieldList());
        bizFieldInfoList.remove(bizIdField);
        bizTableInfo.setFieldList(bizFieldInfoList);
        return bizTableInfo;
    }
}
