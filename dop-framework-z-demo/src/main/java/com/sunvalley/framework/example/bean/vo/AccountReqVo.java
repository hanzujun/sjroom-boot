package com.sunvalley.framework.example.bean.vo;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou    cc ReqVo  true
 * @version 1.0.0.
 * @date 2019-12-16 14:14
 */
@Data
public class AccountReqVo  {

    @ApiModelProperty("业务主键")
    private Long accountId;

    @ApiModelProperty("账号类型（0.超级管理员，1.普通管理员，2.业务管理员，3.业务人员）")
    private Integer accountType;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("登录用户名")
    private String loginName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("盐")
    private String salt;

    @ApiModelProperty("用户姓名")
    private String realName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("语言")
    private Integer language;

    @ApiModelProperty("长度单位")
    private Integer lengthUnit;

    @ApiModelProperty("重量单位")
    private Integer volumeUnit;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("是否已分配公司（0.未分配 1.已分配）")
    private Integer distribution;

    @ApiModelProperty("是否首次登录")
    private Integer landed;

    @ApiModelProperty("登录状态")
    private Integer loginStatus;

    @ApiModelProperty("最近登录时间")
    private Date lastLoginTime;

    @ApiModelProperty("租户ID")
    private Long tenantId;

    @ApiModelProperty("所属角色")
    private Long ownRoleId;

    @ApiModelProperty("创建人")
    private Long createdBy;

    @ApiModelProperty("创建时间")
    private Date createdAt;

    @ApiModelProperty("更新人")
    private Long updatedBy;

    @ApiModelProperty("更新时间")
    private Date updatedAt;
}
