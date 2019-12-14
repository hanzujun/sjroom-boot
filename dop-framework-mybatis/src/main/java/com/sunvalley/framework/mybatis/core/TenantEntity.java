package com.sunvalley.framework.mybatis.core;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;


/**
 * @author Katrel.Zhou
 * @date 2019/5/31 11:29
 */
@Getter
@Setter
@Deprecated
public class TenantEntity extends BaseEntity implements ITenantEntity {

    /**
     * 所属角色
     */
    @TableField(value = "own_role_id", fill = FieldFill.INSERT)
    private Long ownRoleId;

    /**
     * 所属租户
     */
    @TableField(value = "tenant_id", fill = FieldFill.INSERT)
    private Long tenantId;


}
