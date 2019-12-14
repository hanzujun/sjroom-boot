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
public class SystemEntity extends BaseEntity implements ISystemEntity {

    /**
     * 所属角色
     */
    @TableField(value = "own_role_id", fill = FieldFill.INSERT)
    private Long ownRoleId;

    /**
     * 所属系统
     */
    @TableField(value = "system_id", fill = FieldFill.INSERT)
    private Long systemId;
}
