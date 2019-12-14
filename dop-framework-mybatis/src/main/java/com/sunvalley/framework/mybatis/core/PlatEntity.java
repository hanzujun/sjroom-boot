package com.sunvalley.framework.mybatis.core;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Katrel.Zhou
 * @date 2019/8/6 18:09
 */
@Data
public class PlatEntity extends BusinessEntity implements ISystemEntity {

    /**
     * 所属系统
     */
    @TableField(value = "system_id", fill = FieldFill.INSERT)
    private Long systemId;
}
