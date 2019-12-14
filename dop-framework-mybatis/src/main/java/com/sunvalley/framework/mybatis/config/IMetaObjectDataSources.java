package com.sunvalley.framework.mybatis.config;

import com.sunvalley.framework.core.context.call.BusinessContextHolders;
import com.sunvalley.framework.core.context.plat.PlatContextHolders;
import com.sunvalley.framework.mybatis.util.UtilId;

/**
 * MetaObject 数据来源
 *
 * @author dream.lu
 */
public interface IMetaObjectDataSources {

	/**
	 * 租户id
	 *
	 * @return 租户id
	 */
	default Object getXTenantId() {
		return PlatContextHolders.getXTenantId();
	}

	/**
	 * 公司id
	 *
	 * @return 公司id
	 */
	default Object getXCompanyId() {
		return BusinessContextHolders.getXCompanyId();
	}

	/**
	 * 系统id
	 *
	 * @return 系统id
	 */
	default Object getXSystemId() {
		return BusinessContextHolders.getXSystemId();
	}

	/**
	 * 操作者
	 *
	 * @return 账号id
	 */
	default Object getOperator() {
		return PlatContextHolders.getXAccountId();
	}

	/**
	 * 获取授权后透传的角色id
	 *
	 * @return 角色id
	 */
	default Object getOwner() {
		return BusinessContextHolders.getXRoleId();
	}

	/**
	 * 获取业务id
	 *
	 * @return 业务id
	 */
	default Object getBizId() {
		return UtilId.getBId();
	}

}
