# SSM_Integration
将一个基于jdbc和servlet的应用整合MyBatis和Spring

复习Spring知识和学习现在很多人用的MyBatis。想到之前做过个基于jdbc和servlet的应用，是教务系统里的成绩录入功能，就把它拿来练手

## 使用总结

### MyBatis
&emsp;&emsp;整合了MyBatis后，最大的感受就是SQL参数和返回结果集基本都是自动映射的了，极大的减少了开发的代码量（50%以上）。Mapper接口和XML代替了原来的DAO层。

&emsp;&emsp;对于复杂的结果系，MyBatis也提供了高级结果映射，能很好处理这种需求。还提供了懒加载功能。不过要注意一点，懒加载是使用代理模式实现的，所以在转换`json`数据的时候会出现问题。

&emsp;&emsp;MyBatis的另一个强大特性：动态SQL。它能够很好的处理SQL拼接的时候出现的各种繁琐的问题。例如查询条件的`WHERE`（如果没有查询条件它不应该出现）和`AND`（如果是第一个查询条件它不应该出现）。还有`UPDATE`语句的`SET`（最后一个字段应该去掉逗号）。

&emsp;&emsp;由于SQL还是要自己写，所以后期如果要修改、优化的话也很方便。所以说MYBatis是一个半自动ORM框架。

注意事项：
+ `SqlSessionFactoryBuilder`构建完`SqlSessionFactory`实例后就没有存在的意义，所以它的合理作用域应该是方法级别。
+ `SqlSessionFactory`应该在整个应用的生命周期中一直存在，所以它的合理作用域是应用级别。
+ `SqlSession`的作用域应该是方法级别，开启它来处理一个事务，并且要在结束时关闭它。
+ 将`Mapper`接口和`XML`映射文件放在同一个包中（这点很重要），在MyBatis配置文件中指定<package name="com.data"/>来扫描这个包下面的`Mapper`接口，MyBatis会将他们自动关联起来，所以不再需要写配置去引用`XML`映射文件了。

### Spring
&emsp;&emsp;整合了SpringFramework后，现阶段主要使用了它的依赖注入功能。主要区别就是对象之间的依赖关系不直接硬编码在代码里面了。通过注解`@Component`来标识哪些类需要实例化，以及`@Autowired`来标识哪些属性需要Spring帮我们注入依赖。对于特殊的`bean`（例如数据源），则在Spring的配置文件里配置进行实例化。

&emsp;&emsp;使用`MyBatis-Spring`将MyBatis整合到Spring中，进一步体现了Spring的作用。让Spring来管理`SqlSessionFactory`、`SqlSession`、`Mapper`。

注意事项：
+ 如果MyBatis映射器XML文件在和映射器类不在相同的路径下，需要在Spring配置文件下的`SqlSessionFactorybean`下面添加一个`mapperLocations`属性来加载一个目录中的所有文件。
+ 如果Spring的`PlatformTransactionManager`配置好了，就可以在Spring中以通常的做法来配置事务。事务管理器指定的`DataSource`必须和用来创建`SqlSessionFactoryBean`的是同一个数据源。
+ 不再需要直接使用`SqlSessionFactory`了,因为`bean`可以通过一个线程安全的`SqlSession`来注入。基于Spring的事务配置来自动提交，回滚，关闭`session`。不能在Spring管理的`SqlSession`上调用`commit()`,`rollback()`或`close()`方法。
+ `MapperFactoryBean`可以直接注入数据映射器接口到`service`层`bean`中。当使用映射器时，仅仅如调用`DAO`一样调用它们就可以了，你不需要编写任何DAO实现的代码，因为MyBatis-Spring将会创建代理。

&emsp;&emsp;整合了Spring MVC之后，`url`的映射和参数由`servlet`转到`controller`里处理，也减少了很多代码量。`url`的映射可以细化到方法级别，不需要通过`url`参数或者多个`servlet`来判断执行哪个业务了。Spring MVC能够自动的映射`url`参数到方法参数中，不需要操作底层`ServletRequest`来处理。而且能够自动的映射成POJO。还能将`json`数据自动映射（请求参数要加上`@RequestBody`，相应要加上`@ResponseBody`）。

注意事项：
+ SpringMVC默认是没有对象-`json`的转换器的，需要添加依赖
+ 如果要自动扫描`controller`，SpringMVC配置文件需要加两行配置信息`<context:component-scan base-package="com.controller"/>`、`<mvc:annotation-driven/>`。
+ 要对静态资源请求放行`<mvc:resources location="/" mapping="*.html"/>`
+ 很多场景`service`层要获取`request`（例如获取IP）。可以通过依赖注入来获取`request`，此时用到了`ThreadLocal`，是线程安全的。