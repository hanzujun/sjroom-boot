package com.sunvalley.framework.mybatis.generatecode;

import com.sunvalley.framework.mybatis.enums.SystemEnum;
import lombok.Data;

/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-10 16:37
 */
@Data
public class ConfigGenerator {
    /**
     * 项目在硬盘上的基础路径
     */
    public static final String PROJECT_PATH = System.getProperty("user.dir");

    /**
     * 保存的路径
     */
    private String saveDir = PROJECT_PATH + "/gen_code";

    /**
     * 实体包名
     */
    private String basePackage = "com.test";
    /**
     * 驱动
     */
    private String dbDriverName = "com.mysql.cj.jdbc.Driver";
    /**
     * 用户名
     */
    private String dbUser;
    /**
     * 数据库名
     */
    private String dbSchema;
    /**
     * 用户密码
     */
    private String dbPassword;
    /**
     * 哪个系统生成代码
     */
    private SystemEnum systemEnum;
    /**
     * 数据库url
     */
    private String dbUrl;
    /**
     * 需要生成的表
     */
    private String generateTableName;
    /**
     * 替换表名前缀
     */
    private String prefixTableName;
    /**
     * 作者名称
     */
    private String author;

    /**
     * 忽略的字段
     */
    private String[] ignoreFieldArr = new String[]{"id", "create_time", "create_user", "update_time", "update_user", "update_date", "create_date", "yn"};

    /**
     * 所有包名
     */
    private String[] packageArr = new String[]{".bean.entity", ".bean.bo", ".bean.vo", ".dao", ".service", ".impl", ".controller", ".bean.model"};

    /**
     * 包名： code
     */
    public String getApiCodePackage() {
        return basePackage + ".code";
    }

    /**
     * 包名： 实体
     */
    public String getEntityPackage() {
        return basePackage + ".bean.entity";
    }

    /**
     * 包路径： 实体
     */
    public String getEntityPath() {
        return getSaveDir() + packageConvertPath(getEntityPackage());
    }

    /**
     * 包名： 实体
     */
    public String getBeanPackage() {
        return basePackage + ".bean";
    }

    /**
     * 包路径： 实体
     */
    public String getBeanPath() {
        return getSaveDir() + packageConvertPath(getBeanPackage());
    }

    /**
     * 包名： dao
     */
    public String getDaoPackage() {
        return basePackage + ".dao";
    }

    /**
     * 包路径： mapper
     */
    public String getDaoPath() {
        return getSaveDir() + packageConvertPath(getDaoPackage());
    }

    /**
     * 包名： service
     */
    public String getServicePackage() {
        return basePackage + ".service";
    }

    /**
     * 包路径： service
     */
    public String getServicePath() {
        return getSaveDir() + packageConvertPath(getServicePackage());
    }

    /**
     * 包名： service impl
     */
    public String getServiceImplPackage() {
        return basePackage + ".service.impl";
    }

    /**
     * 包路径： service impl
     */
    public String getServiceImplPath() {
        return getSaveDir() + packageConvertPath(getServiceImplPackage());
    }

    /**
     * 包名： controller
     */
    public String getControllerPackage() {
        return basePackage + ".service" ;
    }

    /**
     * 包路径： controller
     */
    public String getControllerPath() {
        return getSaveDir() + packageConvertPath(getControllerPackage());
    }


    /**
     * 报名与路径转换
     *
     * @param packageName
     * @return
     */
    public String packageConvertPath(String packageName) {
        return getSaveDir() + String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

}
