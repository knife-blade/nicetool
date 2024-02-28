# nice-util

## 介绍
超好用的Java工具类。特点是：稳定、最大程度利用SpringBoot原生工具。

## 功能
### 基本
1. BeanUtil：Bean工具。功能：对象浅拷贝、对象深拷贝等
2. JsonUtil：Json工具。功能：JSON字符串转对象、对象转JSON字符串等。
3. PropertyUtil：属性工具。功能：获得值为null的属性名；把对象中的 String 类型的空字段，转换为指定字符串；等。
4. ThrowableUtil：异常工具。功能：获得异常的堆栈信息（字符串）等。
5. ValidateUtil：校验工具。功能：手动校验对象，等同于@Valid功能。

### 日期
1. DateTimeUtil：日期时间工具。功能：格式化LocalDateTime、Date；解析时间字符串为Date；将LocalDateTime转为Date；等等；

### 反射
1. MethodUtil：方法工具。功能：解析方法详情；将参数解析为Map等。

### Spring
1. AopUtil：AOP工具。功能：获得目标类；获得代理Bean；
2. ApplicationContextHolder：持有Spring的ApplicationContext，可以静态调用。例如：ApplicationContextHolder.getContext().getBean(Xxx.class);

### web

1. HttpUrlUtil：Url工具。功能：将URL片段拼接为完整URL；将URL转化为参数字符串；将Map参数转为URL等；
2. ClientIpUtil：IP工具。功能：获得调用方IP；获得客户端真实IP等；

## 快速使用

### 1.引入依赖
```xml
<dependency>
    <groupId>com.suchtool</groupId>
    <artifactId>nice-util-spring-boot-starter</artifactId>
    <version>{newest-version}</version>
</dependency>
```
### 2.使用
```
String jsonString = JsonUtil.toJsonString(obj);
```

