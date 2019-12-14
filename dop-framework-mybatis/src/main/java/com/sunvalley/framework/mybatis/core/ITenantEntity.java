package com.sunvalley.framework.mybatis.core;


/**
 * @author Katrel.Zhou
 * @date 2019/5/31 11:27
 */
public interface ITenantEntity extends IBaseEntity {

    /**
     * 所属角色
     *
     * @return
     */
    Long getOwnRoleId();

    /**
     * 所属租户
     *
     * @return
     */
    Long getTenantId();
}
