package github.sjroom.example.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import github.sjroom.example.dao.comp.AccountDaoCompImpl;
import github.sjroom.example.service.IAccountService;
import github.sjroom.example.service.impl.AccountServiceImpl;
import github.sjroom.mybatis.annotation.FillField;
import github.sjroom.mybatis.annotation.FillFieldName;
import github.sjroom.mybatis.core.BaseEntity;
import github.sjroom.mybatis.annotation.TableBId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2019-12-16 14:14
 */
@TableName("plat_account")
@Data
@EqualsAndHashCode(callSuper = true)
public class Account extends BaseEntity {

    /**
     * 业务主键
     */
    @TableBId
    private Long accountId;
    /**
     * 账号类型（0.超级管理员，1.普通管理员，2.业务管理员，3.业务人员）
     */
    private Integer accountType;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 登录用户名
     */
    private String loginName;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐
     */
    private String salt;
    /**
     * 用户姓名
     */
    private String realName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 语言
     */
    private Integer language;
    /**
     * 长度单位
     */
    private Integer lengthUnit;
    /**
     * 重量单位
     */
    private Integer volumeUnit;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 是否已分配公司（0.未分配 1.已分配）
     */
    private Integer distribution;
    /**
     * 是否首次登录
     */
    private Integer landed;
    /**
     * 登录状态
     */
    private Integer loginStatus;
    /**
     * 最近登录时间
     */
    private Date lastLoginTime;
}
