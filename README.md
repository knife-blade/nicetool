# nice-util

## 介绍
超好用的Java工具类。特点是：稳定、最大程度利用SpringBoot原生工具。

## 功能
1. ApplicationContextHolder：持有Spring的ApplicationContext，可以静态调用。例如：ApplicationContextHolder.getContext().getBean(Xxx.class);
2. JsonUtil：Json工具。功能：JSON字符串转对象、对象转JSON字符串等。
3. ThrowableUtil：异常工具。功能：获得异常的堆栈信息（字符串）等
4. DateTimeUtil：日期时间工具。功能：格式化LocalDateTime、Date；解析时间字符串为Date；将LocalDateTime转为Date等
5. BeanUtil：Bean工具。功能：对象浅拷贝、对象深拷贝等
6. ValidateUtil：校验工具。功能：手动校验对象，等同于@Valid功能。
7. IpUtil：IP工具。功能：获得调用方IP；获得客户端真实IP等；
8. 其他功能

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

