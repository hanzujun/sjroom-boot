package com.sunvalley.framework.mybatis.core;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Katrel.Zhou
 * @date 2019/8/6 18:09
 */
@Getter
@Setter
public class BusinessEntity extends BaseEntity implements IBusinessEntity {
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

    /**
     * 所属公司
     */
    @TableField(value = "company_id", fill = FieldFill.INSERT)
    private Long companyId;
}
