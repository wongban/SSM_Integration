# SSM_Integration
将一个基于jdbc和servlet的应用整合MyBatis和Spring

复习Spring知识和学习现在很多人用的MyBatis。想到之前做过个基于jdbc和servlet的应用，是教务系统里的成绩录入功能，就把它拿来练手

## 使用总结

### MyBatis
&emsp;&emsp;整合了MyBatis后，最大的感受就是SQL参数和返回结果集基本都是自动映射的了，极大的减少了开发的代码量（50%以上）。Mapper接口和XML代替了原来的DAO层。

&emsp;&emsp;对于复杂的结果系，MyBatis也提供了高级结果映射，能很好处理这种需求。还提供了懒加载功能。不过要注意一点，懒加载是使用代理模式实现的，所以在转换`json`数据的时候会出现问题。

&emsp;&emsp;MyBatis的另一个强大特性：动态SQL。它能够很好的处理SQL拼接的时候出现的各种繁琐的问题。例如查询条件的`WHERE`（如果没有查询条件它不应该出现）和`AND`（如果是第一个查询条件它不应该出现）。还有`UPDATE`语句的`SET`（最后一个字段应该去掉逗号）。

&emsp;&emsp;由于SQL还是要自己写，所以后期如果要修改、优化的话也很方便。所以说MYBatis是一个半自动ORM框架。

MyBatis使用注意事项：

+ `SqlSessionFactoryBuilder`构建完`SqlSessionFactory`实例后就没有存在的意义，所以它的合理作用域应该是方法级别。
+ `SqlSessionFactory`应该在整个应用的生命周期中一直存在，所以它的合理作用域是应用级别。
+ `SqlSession`的作用域应该是方法级别，开启它来处理一个事务，并且要在结束时关闭它。
+ 将`Mapper`接口和`XML`映射文件放在同一个包中（这点很重要），在MyBatis配置文件中指定<package name="com.data"/>来扫描这个包下面的`Mapper`接口，MyBatis会将他们自动关联起来，所以不再需要写配置去引用`XML`映射文件了。