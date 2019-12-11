package com.github.sjroom.mybatis.config;

import com.github.sjroom.common.context.plat.PlatContextHolders;
import com.github.sjroom.mybatis.util.UtilId;

/**
 * MetaObject 数据来源
 *
 * @author dream.lu
 */
public interface IMetaObjectDataSources {

	/**
	 * 操作者
	 *
	 * @return 账号id
	 */
	default Object getOperator() {
		return PlatContextHolders.getXAccountId();
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
