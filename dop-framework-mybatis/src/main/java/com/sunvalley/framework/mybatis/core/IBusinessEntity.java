package com.sunvalley.framework.mybatis.core;

/**
 * @author Katrel.Zhou
 * @date 2019/8/6 18:05
 */
public interface IBusinessEntity extends IBaseEntity {

    /**
     * 所属角色ID
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
    /**
     * 所属公司
     *
     * @return
     */
    Long getCompanyId();

}
