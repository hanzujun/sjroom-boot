package com.sunvalley.framework.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.sunvalley.framework.mybatis.util.UtilId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

/**
 * 全局参数填充
 *
 * @author dream.lu
 */
@Slf4j
@RequiredArgsConstructor
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
	private final IMetaObjectDataSources metaObjectDataSources;

	@Override
	public void insertFill(MetaObject metaObject) {
		log.info("mybatis plus start insert fill ....");
		fillBIdByAnnotation(metaObject);

		Object tenantId = metaObjectDataSources.getXTenantId();
		this.fillValIfNullByName("tenantId", tenantId, metaObject);

		Object companyId = metaObjectDataSources.getXCompanyId();
		this.fillValIfNullByName("companyId", companyId, metaObject);

		Object systemId = metaObjectDataSources.getXSystemId();
		this.fillValIfNullByName("systemId", systemId, metaObject);

		Object owner = metaObjectDataSources.getOwner();
		this.fillValIfNullByName("ownRoleId", owner, metaObject);

		Object operator = metaObjectDataSources.getOperator();
		this.fillValIfNullByName("createdBy", operator, metaObject);
		this.fillValIfNullByName("updatedBy", operator, metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		log.info("mybatis plus start update fill ....");

		Object operator = metaObjectDataSources.getOperator();
		this.fillValIfNullByName("updatedBy", operator, metaObject);
	}

	private void fillBIdByAnnotation(MetaObject metaObject) {
		Object originalObject = metaObject.getOriginalObject();
		Class<?> modelClass = originalObject.getClass();
		// 获取 bid 的属性名
		String fieldName = UtilId.getBIdFieldName(modelClass);
		if (fieldName == null) {
			return;
		}
		// 用户自己手动设置的 bid
		Object bizIdValue = this.getFieldValByName(fieldName, metaObject);
		if (bizIdValue != null) {
			return;
		}
		Object bizId = metaObjectDataSources.getBizId();
		this.setFieldValByName(fieldName, bizId, metaObject);
	}

	/**
	 * 填充值，先判断是否有手动设置
	 *
	 * @param fieldName  属性名
	 * @param fieldVal   属性值
	 * @param metaObject MetaObject
	 */
	private void fillValIfNullByName(String fieldName, Object fieldVal, MetaObject metaObject) {
		// 用户手动设置的值
		Object userSetValue = this.getFieldValByName(fieldName, metaObject);
		if (userSetValue != null) {
			return;
		}
		this.setFieldValByName(fieldName, fieldVal, metaObject);
	}

}
