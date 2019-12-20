package github.sjroom.mybatis.config;

import github.sjroom.common.context.plat.PlatContextHolders;
import github.sjroom.common.util.UtilId;

/**
 * MetaObject 数据来源
 *
 * @author manson.zhou
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
