# 简介 | Intro

> Mybatis 增强工具包 - 只做增强不做改变，简化CRUD操作。采用了mybatis -plus 的核心思想构建。

# 项目特性
* 无侵入：Mybatis-Plus 在 Mybatis 的基础上进行扩展，只做增强不做改变，引入 Mybatis-Plus 不会对您现有的 Mybatis 构架产生任何影响，而且 MP 支持所有 Mybatis 原生的特性
* 依赖少：仅仅依赖 Mybatis 以及 Mybatis-Spring
* 损耗小：启动即会自动注入基本CURD，性能基本无损耗，直接面向对象操作
* 通用CRUD操作：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求
* 支持代码生成：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 层代码，支持模板引擎.

# 支持的方法
| 方法             | 功能描述  |
| ---------------- | ---------------------- |
| selectById       | 通过id查找             |
| selectBatchIds   | 查询（根据ID 批量查询）  |
| selectOne   | 通过实体类查找单个  |
| selectList   | 通过实体类查询多个  |
| selectCount   | 通过实体类，返回查询个数  |
| insert   | 插入  |
| updateById   | 更新  |
| deleteById   | 根据主键删除  |
| deleteBatchByIds   | 删除（根据ID 批量删除）  |

# mybatis plus 的区别

| 区别             | dz-dazong-persistence  | mybatis plus |
| ---------------- | ---------------------- | ------------ |
| 热加载           | 不支持                 | 支持         |
| ActiveRecord     | 不支持                 | 支持         |
| 支持代码生成     | 支持部分service,mapper | 支持         |
| 分页插件         | 使用PageHelper         | 支持         |
| 内置性能分析插件 | 不支持                 | 支持         |
| 内置全局拦截插件 | 不支持                 | 支持         |

# 项目配置



 ## 配置2：```将自定义配置文件，加入对应的数据源```
 ```xml
    <!--自定义配置文件-->
    <bean id="myConfiguration" class="MyConfiguration"/>

    <!--常规配置-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="configuration" ref="myConfiguration"/> <!--自定义配置文件-->
        <property name="mapperLocations" value="classpath:mapper/*.xml"/>
    </bean>
    
    <!--常规配置-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
          <property name="basePackage" value="com.dazong.persistence.mybatis.mapper"/>
          <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
    </bean>
 ```
 > 配置完成.



# 开始使用

## 定义实体类
```xml
package com.dazong.persistence.mybatis.entity;

@TableName("user")
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户主键
     */
    @TableId
    private Long id;
    /**
     * 用户姓名
     */
    @TableField("name")
    private String name;
    /**
     * 用户姓名_扩展
     */
    @TableField("ext_name")
    private String extName;
    /**
     * 状态：0:创建中 1：创建成功， 2:创建失败
     */
    @TableField("status")
    private Integer status;
    /**
     * 用户性别
     */
    @TableField("age")
    private Integer age;
}

```
## 定义mapper类
```xml
package com.dazong.persistence.mybatis.mapper;

import BaseMapper;
import User;

public interface UserMapper extends BaseMapper<User> {

}

```


## 如何使用代码生成

  ```java
      ConfigGenerator configGenerator = new ConfigGenerator();
      configGenerator.setDbUrl("jdbc:mysql://172.16.21.15:3306/yunying");
      configGenerator.setDbUser("maoyi");
      configGenerator.setDbPassword("6qjiaVv6!nlz1JSo");
      configGenerator.setDbSchema("yunying");

       /**
         * 生成的表名
       */
       configGenerator.setGenerateTableName("sys_user");
       /**
       * 生成表的包名
       */
       configGenerator.setBasePackage("com.dazong.yunying.mybatis");
    
       new AutoGenerator(configGenerator).run();
  ```
  目前此代码在`D:\project\yunying\yunying-dao\src\test\java\GenerateCode.java`
  ![](https://git.dazong.com/platform/dz-common-project/uploads/babdf6498129fdb53081d669c9bc2e9d/QQ%E6%88%AA%E5%9B%BE20180312181057.png)

  ​

## 根据db字段的指定描述加@注解，可以生成对应方法
  ![](https://git.dazong.com/platform/dz-common-project/uploads/e3d73f783e682b64666ec2fbd42da874/QQ%E6%88%AA%E5%9B%BE20180313135103.png)

    * ``@no``      — 生成流水号方法,selectOneByNo
    * ``@yn``      — 生成逻辑删除deleteLogicById
    * ``@enum`` — 生成枚举@enum(1=创建中,2=创建成功,3=创建失败.)

