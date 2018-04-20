## 简介

sjroom-web 是专门针对web入口进行封装的基础组件，让使用者尽量少配置就可以达到开箱即用。

目前有以下功能：

1.统一异常拦截

2.统一正常封包


### 使用1：统一异常拦截

测试代码

```java
/**
  * 业务系统异常
*/
@RequestMapping("/test2")
@ResponseBody
public List<String> test2() {
    throw new ArgumetException(500, "业务系统的空指针异常");
}
```

输出结果

```
{
    "code":500
    "msg":"业务系统的空指针异常",
    "success":false   --响应正常返回true,异常返回false
}
```


### 使用2：统一正常封包

测试代码

```java
/**
  * 返回正常的实体     
*/
@RequestMapping("/test")
@ResponseBody
public List<String> test() {
  List<String> stringList = new ArrayList<>();
  stringList.add("测试1");
  stringList.add("测试2");
  return stringList;
}
```

输出结果

```
{
	"code":88200,
	"data":[
		"测试1",
		"测试2"
	],
	"msg":"成功",
	"success":true
}
```

> 这样好处是，数据封装的操作交给了sjroom-boot.自动会加上code,message,success的封包




