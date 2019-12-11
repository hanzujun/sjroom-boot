# 简介 | Intro
java 是专门针对web入口进行封装的基础组件，让使用者尽量少配置就可以达到开箱即用。
> Mybatis 增强工具包 - 只做增强不做改变，简化CRUD操作。采用了mybatis -plus 的核心思想构建。

## 项目特性
无侵入：Mybatis-Plus 在 Mybatis 的基础上进行扩展，只做增强不做改变，引入 Mybatis-Plus 不会对您现有的 Mybatis 构架产生任何影响，而且 MP 支持所有 Mybatis 原生的特性
依赖少：仅仅依赖 Mybatis 以及 Mybatis-Spring
损耗小：启动即会自动注入基本CURD，性能基本无损耗，直接面向对象操作
通用CRUD操作：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求
支持代码生成：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 层代码，支持模板引擎.

## 项目配置
### 配置一
```xml
 <parent>
      <groupId>com.github.sjroom</groupId>
        <artifactId>sjroom-boot-pom</artifactId>
        <version>1.0.0-SNAPSHOT</version>
 </parent>
```


# 项目结构
```xml
├──sjroom
│  	└─src/main/java
│  		├─com.github.sjroom
│  		    ├─common 		--基础组件
│              ├─exception                
│              ├─request                  
│              ├─response 
│              └─web	
│  		    ├─jdbc --Mybatis 增强工具包 - 只做增强不做改变，简化CRUD操作。采用了mybatis -plus 的核心思想构建
│  		    ├─util --util工具类
│  		    └─web  --专门针对web入口进行封装的基础组件，让使用者尽量少配置就可以达到开箱即用。
```

### 第一步
目前项目还未发布到中央仓库，所以需要用的话请下载代码maven install到本地仓库后在使用
以后在自己的本项目中添加
```bash
git clone https://github.com/zw23534572/sjroom-boot.git
```
### 第二步
```bash
mvn install
```
### 在项目的pom.xml添加
```xml
 <parent>
      <groupId>com.github.sjroom</groupId>
        <artifactId>sjroom-boot-pom</artifactId>
        <version>1.0.0-SNAPSHOT</version>
 </parent>
```

## 注意
本项目是基于spring boot 项目的封装，无需在添加spring boot 的配置.
1. sjroom-web的使用请查看,[README-WEB.md](https://github.com/zw23534572/sjroom-boot/blob/master/README-WEB.md)
2. sjroom-jdbc的使用请查看,[README-JDBC.md](https://github.com/zw23534572/sjroom-boot/blob/master/README-JDBC.md)
