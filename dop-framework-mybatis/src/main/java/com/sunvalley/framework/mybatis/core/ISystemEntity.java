package com.sunvalley.framework.mybatis.core;


/**
 * @author Katrel.Zhou
 * @date 2019/5/31 11:27
 */
public interface ISystemEntity extends IBaseEntity {

    /**
     * 所属角色ID
     *
     * @return
     */
    Long getOwnRoleId();

    /**
     * 所属系统
     *
     * @return
     */
    Long getSystemId();
}
