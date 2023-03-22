**Mybatis**

环境：

+ jdk 1.8
+ Mysql 5.7
+ maven 3.6.1
+ IDEA





### 1 简介

![image-20230320203131899](Mybatis.assets/image-20230320203131899.png)

+ MyBatis 是一款优秀的**持久层框架**，它支持自定义 SQL、存储过程以及高级映射。MyBatis **免除了几乎所有的 JDBC 代码**以及设置参数和获取结果集的工作。MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO为数据库中的记录。

+ MyBatis本是apache的一个[开源项目](https://baike.baidu.com/item/开源项目/3406069?fromModule=lemma_inlink)iBatis，2010年这个项目由apache software foundation迁移到了[google code](https://baike.baidu.com/item/google code/2346604?fromModule=lemma_inlink)，并且改名为MyBatis。2013年11月迁移到[Github](https://baike.baidu.com/item/Github/10145341?fromModule=lemma_inlink)。



github:https://github.com/mybatis/mybatis-3/tree/mybatis-3.5.13

文档：https://mybatis.org/mybatis-3/zh/index.html

Maven：

```xml
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.2</version>
</dependency>

```

==持久层==

**数据持久化**

+ 持久化就是将程序的数据在持久状态和瞬时状态转化的过程
+ 内存有断电即失的特性
+ 数据库(JDBC)，io文件持久化

**为什么需要持久化？**

有一些对象不能丢失，内存太贵了



持久层：

Dao层、Service层、Controller层...

+ 完成持久化工作的代码块
+ 层界限十分明显

**为什么需要Mybatis**

+ 方便，传统的JDBC复杂，需要框架简化、自动化

+ 帮关注将数据存入到数据库中

+ 不用Mybatis也可以~



### 2 第一个Mybatis程序

思路 ： 搭建环境 --> 导入Mybatis -->编写代码 --> 测试



#### 2.1 搭建环境

**搭建数据库**

```mysql
CREATE DATABASE `mybatis`;

USE `mybatis`;

CREATE TABLE `user`(
`id` INT(20) NOT NULL PRIMARY KEY,
`name` VARCHAR(30) DEFAULT NULL,
`pwd` VARCHAR(30) DEFAULT NULL`user`

)ENGINE = INNODB DEFAULT CHARSET=utf8mb4;

INSERT INTO `user` (`id`,`name`,`pwd`) VALUES
(1,'lk','123456'),
(2,'hxf','123456'),
(3,'zxm','123456')
```

**新建项目**

1 新建一个普通maven项目

2 删除src目录

3 导入maven依赖

+ mysql驱动
+ mybatis
+ junit

#### 2.2 创建模块

+ 编写mybatis的核心配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>

<?import java.lang.*?>
<?import java.util.*?>
<?import javafx.scene.*?>
<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>


<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="$com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="org/mybatis/example/BlogMapper.xml"/>
    </mappers>
</configuration>
```



+ 编写Mybatis工具类

```java
public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;
    static {

        InputStream inputStream = null;
        try {
            //使用Mybatis 第一步 ：获取sqlSessionFactory对象
            String resource = "org/mybatis/example/mybatis-config.xml";
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    }
//    既然有了 SqlSessionFactory，顾名思义，我们可以从中获得 SqlSession 的实例。
//    SqlSession 提供了在数据库执行 SQL 命令所需的所有方法。

    public static SqlSession getSqlSession() {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        return sqlSession;
        return sqlSessionFactory.openSession();
    }

}
```

#### 2.3 编写代码

+ 实体类

```java
public class User {
    private int id;
    private String name;
    private String pwd;

    public User() {
    }

    public User(int id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
```



+ Dao接口

```java
public interface UserDao{
    List<User> getUserList();
}
```



+ 接口实现类

==由原来的UserDaoImpl转换为一个Mapper配置文件==

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace 绑定一个对应的Dao接口-->
<mapper namespace="com.lk.Dao.UserDao">
    <!--id 对应实现接口的方法名字-->
    <select id="getUserList" resultType="com.lk.pojo.User">
        select * from mybatis.user
    </select>
</mapper>
```

#### 2.4 测试

+ junit测试

```java
public class UserDaoTest {
    @Test
    public void test(){
        //获得sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //方式一：getMapper
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<User> userList = mapper.getUserList();
        for (User user : userList) {
            System.out.println(user);
        }

        //关闭SqlSession
        sqlSession.close();

    }
}
```

![image-20230321221640819](Mybatis.assets/image-20230321221640819.png)

![image-20230321221826199](Mybatis.assets/image-20230321221826199.png)





### 3 增删改查实现

==namespace== 

Mapper.xml配置文件中 **namespace** 中的包名要和Dao/mapper接口的包名一致！

![image-20230321223256118](Mybatis.assets/image-20230321223256118.png)

+ select

选择，查询语句  `select * from mybatis.user`

![image-20230321223442157](Mybatis.assets/image-20230321223442157.png)

**id** 就是对应的namespace中的方法名；

  ![image-20230321223502174](Mybatis.assets/image-20230321223502174.png)

**resultType** 就是Sql语句执行的返回值

