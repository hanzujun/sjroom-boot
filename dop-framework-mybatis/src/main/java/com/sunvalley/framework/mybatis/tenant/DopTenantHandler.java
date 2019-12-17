package com.sunvalley.framework.mybatis.tenant;

import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.sunvalley.framework.mybatis.config.IMetaObjectDataSources;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

/**
 * 租户处理器，目前只添加未做配置
 *
 * <p>
 *     docs: https://mybatis.plus/guide/tenant.html
 * </p>
 *
 * @author manson.zhou
 */
@RequiredArgsConstructor
public class DopTenantHandler implements TenantHandler {
	private final IMetaObjectDataSources metaObjectDataSources;

	@Override
	public Expression getTenantId(boolean where) {
		// 注意： where 表示 Insert、Update 等语句。
		// 因为： 有情况是 tenant_id 外部没生成的情况。
		Object tenantId = metaObjectDataSources.getXTenantId();
		return new LongValue((long) tenantId);
	}

	@Override
	public String getTenantIdColumn() {
		// 租户字段名
		return "tenant_id";
	}

	@Override
	public boolean doTableFilter(String tableName) {
		// 需要过滤的表名
		return false;
	}
}
