# Spring Cloud

## Eureka(注册中心)

### 服务端Server搭建

1. 依赖引入

   ```xml
   	<parent>
   		<groupId>org.springframework.boot</groupId>
   		<artifactId>spring-boot-starter-parent</artifactId>
   		<version>2.2.2.RELEASE</version>
   		<relativePath/> <!-- lookup parent from repository -->
   	</parent>
   
   	<groupId>com.kinglead</groupId>
   	<artifactId>maven-spring-cloud-eureka-server</artifactId>
   	<version>0.0.1-SNAPSHOT</version>
   	<name>maven-spring-cloud-eureka-server</name>
   	<description>Demo project for Spring Boot</description>
   
   	<properties>
   		<java.version>1.8</java.version>
   		<spring-cloud.version>Hoxton.SR1</spring-cloud.version>
   	</properties>
   
   	<dependencies>
   		<dependency>
   			<groupId>org.springframework.cloud</groupId>
   			<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
   		</dependency>
   
   		<dependency>
   			<groupId>org.springframework.boot</groupId>
   			<artifactId>spring-boot-starter-test</artifactId>
   			<scope>test</scope>
   			<exclusions>
   				<exclusion>
   					<groupId>org.junit.vintage</groupId>
   					<artifactId>junit-vintage-engine</artifactId>
   				</exclusion>
   			</exclusions>
   		</dependency>
   	</dependencies>
   
   	<dependencyManagement>
   		<dependencies>
   			<dependency>
   				<groupId>org.springframework.cloud</groupId>
   				<artifactId>spring-cloud-dependencies</artifactId>
   				<version>${spring-cloud.version}</version>
   				<type>pom</type>
   				<scope>import</scope>
   			</dependency>
   		</dependencies>
   	</dependencyManagement>
   
   	<build>
   		<plugins>
   			<plugin>
   				<groupId>org.springframework.boot</groupId>
   				<artifactId>spring-boot-maven-plugin</artifactId>
   			</plugin>
   		</plugins>
   	</build>
   ```

2. 添加配置

   application.yml

   ```yaml
   server:
     port: 8080
   eureka:
     instance:
       hostname: localhost #标识正运行在哪台服务器上
     client:
       #register-with-eureka、fetch-registry：是否与Eureka服务进行交互，即是否集群
       register-with-eureka: false
       fetch-registry: false
       #设置与Eureka注册中心交互的地址，查询服务和注册服务用到
       service-url:
         defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ #单机
         # defaultZone: http://eureka9001.kinglead.com:9001/eureka/,http://eureka9002.kinglead.com:9002/eureka/ # 集群
   ```

3. 修改启动类

   ```java
   @SpringBootApplication
   @EnableEurekaServer //标识为Eureka服务端
   public class EurekaServerApplication {
      public static void main(String[] args) {
         SpringApplication.run(EurekaServerApplication.class, args);
      }
   
   }
   ```

4. 访问管理页面

   http://localhost:8080

   ![image-20201118181911712](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/image-20201118181911712.png)

### 注册服务（客户端Client搭建）

1. 引入依赖

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
      <parent>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-parent</artifactId>
         <version>2.2.2.RELEASE</version>
         <relativePath/> <!-- lookup parent from repository -->
      </parent>
      <groupId>com.kinglead</groupId>
      <artifactId>maven-spring-cloud-eureka-client</artifactId>
      <version>0.0.1-SNAPSHOT</version>
      <name>maven-spring-cloud-eureka-client</name>
      <description>Demo project for Spring Boot</description>
   
      <properties>
         <java.version>1.8</java.version>
         <spring-cloud.version>Hoxton.SR1</spring-cloud.version>
      </properties>
   
      <dependencies>
         <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
         </dependency>
         <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
         </dependency>
         <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
               <exclusion>
                  <groupId>org.junit.vintage</groupId>
                  <artifactId>junit-vintage-engine</artifactId>
               </exclusion>
            </exclusions>
         </dependency>
      </dependencies>
   
      <dependencyManagement>
         <dependencies>
            <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-dependencies</artifactId>
               <version>${spring-cloud.version}</version>
               <type>pom</type>
               <scope>import</scope>
            </dependency>
         </dependencies>
      </dependencyManagement>
   
      <build>
         <plugins>
            <plugin>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
         </plugins>
      </build>
   
   </project>
   ```

2. 添加配置

   application.yml

   ```yaml
   spring:
     application:
       name: user-service #服务名
   server:
     port: 9200
   eureka:
     client:
       service-url:
         defaultZone: http://localhost:9000/eureka/  #Eureka服务端地址。配置多个地址，使用逗号进行分割，它将会一个一个进行尝试注册
   ```

3. 修改启动类

   ```java
   @SpringBootApplication
   @EnableEurekaClient //标记为Eureka客户端
   public class EurekaClientApplication {
   
      public static void main(String[] args) {
         SpringApplication.run(EurekaClientApplication.class, args);
      }
   
   }
   ```

4. 启动服务，看Eureka服务端是否注册

   ![img](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_16057671415094.png)

### 消费服务

​	Spring cloud有两种服务调用方式：1、restTemplate+ribbon(cloud的负载均衡组件)；2、feign(cloud的httpclient组件,默认集成ribbo)

#### 方式一、restTemplate+ribbon

1. 引入依赖

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
      <parent>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-parent</artifactId>
         <version>2.2.2.RELEASE</version>
         <relativePath/> <!-- lookup parent from repository -->
      </parent>
      <groupId>com.kinglead</groupId>
      <artifactId>maven-spring-cloud-eureka-client</artifactId>
      <version>0.0.1-SNAPSHOT</version>
      <name>maven-spring-cloud-eureka-client</name>
      <description>Demo project for Spring Boot</description>
   
      <properties>
         <java.version>1.8</java.version>
         <spring-cloud.version>Hoxton.SR1</spring-cloud.version>
      </properties>
   
      <dependencies>
         <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
         </dependency>
         <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
         </dependency>
         <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
               <exclusion>
                  <groupId>org.junit.vintage</groupId>
                  <artifactId>junit-vintage-engine</artifactId>
               </exclusion>
            </exclusions>
         </dependency>
      </dependencies>
   
      <dependencyManagement>
         <dependencies>
            <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-dependencies</artifactId>
               <version>${spring-cloud.version}</version>
               <type>pom</type>
               <scope>import</scope>
            </dependency>
         </dependencies>
      </dependencyManagement>
   
      <build>
         <plugins>
            <plugin>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
         </plugins>
      </build>
   
   </project>
   ```

2. 配置文件

   application.yml

   ```yaml
   spring:
     application:
       name: service-rest #服务名
   server:
     port: 8001
   eureka:
     client:
       service-url:
         defaultZone: http://localhost:9000/eureka/  #Eureka服务端地址。配置多个地址，使用逗号进行分割，它将会一个一个进行尝试注册
   ```

3. 修改启动类

   ```java
   @SpringBootApplication
   @EnableEurekaClient
   public class ConsumerRibboApplication {
   
      public static void main(String[] args) {
         SpringApplication.run(ConsumerRibboApplication.class, args);
      }
   
      //向程序的ioc注入一个bean: restTemplate;并通过@LoadBalanced注解表明这个restRemplate开启负载均衡的功能。
      @Bean
      @LoadBalanced
      RestTemplate restTemplate() {
         return new RestTemplate();
      }
      
   }
   ```

4. 创建controller

   ```java
   @RestController
   public class HelloController {
   
       @Resource
       HelloService helloService;
   
       @RequestMapping("/hello")
       public String hello(){
           return helloService.helloService();
       }
   
   }
   ```

5. 创建service

   ```java
   @Service
   public class HelloService {
   
       @Resource
       RestTemplate restTemplate;
   
       public String helloService(){
           return restTemplate.getForObject("http://user-service/hello",String.class);
       }
   
   }
   ```

6. 访问接口

   http://localhost:8001/hello

#### 方式二、feign

Feign是一个声明式的伪Http客户端，它使得写Http客户端变得更简单。使用Feign，只需要创建一个接口并注解。它具有可插拔的注解特性，可使用Feign 注解和JAX-RS注解。Feign支持可插拔的编码器和解码器。Feign默认集成了Ribbon，并和Eureka结合，默认实现了负载均衡的效果。简而言之：Feign 采用的是基于接口的注解;Feign 整合了ribbon

1. 引入依赖

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
      <parent>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-parent</artifactId>
         <version>2.2.2.RELEASE</version>
         <relativePath/> <!-- lookup parent from repository -->
      </parent>
      <groupId>com.kinglead</groupId>
      <artifactId>maven-spring-cloud-eureka-comsumer-ribbo</artifactId>
      <version>0.0.1-SNAPSHOT</version>
      <name>maven-spring-cloud-eureka-comsumer-ribbo</name>
      <description>Demo project for Spring Boot</description>
   
      <properties>
         <java.version>1.8</java.version>
         <spring-cloud.version>Hoxton.SR1</spring-cloud.version>
      </properties>
   
      <dependencies>
         <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
           </dependency>
         <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
         </dependency>
         <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
         </dependency>
         <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
               <exclusion>
                  <groupId>org.junit.vintage</groupId>
                  <artifactId>junit-vintage-engine</artifactId>
               </exclusion>
            </exclusions>
         </dependency>
      </dependencies>
   
      <dependencyManagement>
         <dependencies>
            <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-dependencies</artifactId>
               <version>${spring-cloud.version}</version>
               <type>pom</type>
               <scope>import</scope>
            </dependency>
         </dependencies>
      </dependencyManagement>
   
      <build>
         <plugins>
            <plugin>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
         </plugins>
      </build>
   
   </project>
   ```

2. 配置文件

   ```yaml
   spring:
     application:
       name: service-feign
   server:
     port: 8001
   eureka:
     client:
       serviceUrl:
         defaultZone: http://localhost:9000/eureka/
   ```

3. 修改启动类

   ```java
   @SpringBootApplication
   @EnableEurekaClient  //标识Eureka客户端
   @EnableFeignClients  //开启feign
   public class ConsumerFeignApplication {
   
      public static void main(String[] args) {
         SpringApplication.run(ConsumerFeignApplication.class, args);
      }
   
   }
   ```

4. 创建controller

   ```java
   @RestController
   public class HelloController {
   
       @Resource
       HelloAction helloAction;
   
       @RequestMapping(value = "/hello", method = RequestMethod.GET)
       public String hello(){
           return helloAction.hello();
       }
   
   }
   ```

5. 创建接口（action）

   ```java
   @FeignClient(value = "user-service")
   public interface HelloAction {
   
       @RequestMapping(value = "/hello",method = RequestMethod.GET)
       String hello();
   
   }
   ```

6. 访问

   http://localhost:8001/hello



## Hystrix(断路器)

### 由来

​	在微服务架构中，根据业务来拆分成一个个的服务，服务与服务之间可以相互调用（RPC），在Spring Cloud可以用RestTemplate+Ribbon和Feign来调用。为了保证其高可用，单个服务通常会集群部署。由于网络原因或者自身的原因，服务并不能保证100%可用，如果单个服务出现问题，调用这个服务就会出现线程阻塞，此时若有大量的请求涌入，Servlet容器的线程资源会被消耗完毕，导致服务瘫痪。服务与服务之间的依赖性，故障会传播，会对整个微服务系统造成灾难性的严重后果，这就是服务故障的“雪崩”效应。
为了解决这个问题，业界提出了断路器模型。

### 是什么

Netflix开源了Hystrix组件，实现了断路器模式，SpringCloud对这一组件进行了整合。在微服务架构中，一个请求需要调用多个服务是非常常见的，较底层的服务如果出现故障，会导致连锁故障。当对特定的服务的调用的不可用达到一个阀值（Hystric 是5秒20次） 断路器将会被打开。断路打开后，可用避免连锁故障，fallback方法可以直接返回一个固定值。

### 具体使用

#### 依赖

```xml
<!--断路器依赖-->
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
<!--健康检查依赖-->
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<!--仪表盘依赖-->
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
</dependency>
```

#### 在restTemplate+ribbon方式下

1. 改造service

   在方法上加上@HystrixCommand注解。该注解对该方法创建了熔断器的功能，并指定了fallbackMethod熔断方法

   ```java
   @Service
   public class HelloService {
   
       @Resource
       RestTemplate restTemplate;
   
       @HystrixCommand(fallbackMethod = "error")
       public String helloService(){
           return restTemplate.getForObject("http://user-service/hello",String.class);
       }
   
       public String error(){
           return "error";
       }
   
   }
   ```

2. 开启熔断：启动类

   ```java
   @SpringBootApplication
   @EnableEurekaClient //标识Eureka客户端
   @EnableHystrix  //开启断路器
   @EnableHystrixDashboard //开启断路器仪表盘
   public class ConsumerRibboApplication {
   
      public static void main(String[] args) {
         SpringApplication.run(ConsumerRibboApplication.class, args);
      }
   
      //向程序的ioc注入一个bean: restTemplate;并通过@LoadBalanced注解表明这个restRemplate开启负载均衡的功能。
      @Bean
      @LoadBalanced
      RestTemplate restTemplate() {
         return new RestTemplate();
      }
   
      //加入断路器仪表盘，注入bean
      @Bean
      public ServletRegistrationBean getServlet(){
         HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
         ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
         registrationBean.setLoadOnStartup(1);
         registrationBean.addUrlMappings("/actuator/hystrix.stream");
         registrationBean.setName("HystrixMetricsStreamServlet");
         return registrationBean;
      }
   }
   ```

3. 查看仪表盘

   http://localhost:8002/hystrix

   ![img](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_16057698702449.png)

   输入URL：http://localhost:8002/actuator/hystrix.stream

   ![img](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_16057699416225.png)

4. 设置超时时间（默认1s）

   可以使用设置commandProperties属性来设置:execution.isolation.thread.timeoutInMilliseconds

   ```java
   @HystrixCommand(fallbackMethod = "error",
           commandProperties = {
               @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
           })
   public String helloService(){
       return restTemplate.getForObject("http://user-service/hello",String.class);
   }
   ```

   关闭超时设置:execution.timeout.enabled设为false

   ```java
   @HystrixCommand(fallbackMethod = "error",
           commandProperties = {
               @HystrixProperty(name = "execution.timeout.enabled", value = "false")
           })
   public String helloService(){
       return restTemplate.getForObject("http://user-service/hello",String.class);
   }
   ```

   其它值得关注的属性

   ​	circuitBreaker.requestVolumeThreshold:在给定的时间范围内，方法应该被调用的次数

   ​	circuitBreaker.errorThresholdPercentage:在给定的时间范围内，方法调用产生失败的百分比

   ​	metrics.rollingStats.timeInMilliseconds:控制请求量和错误百分比的滚动时间周期
   
   ​	circuitBreaker.sleepWindowInMilliseconds:处于打开状态的断路器要经过多长时间才会进入到半开状态，进入半开状态之后，将会再次尝试失败的原始方法。

#### 在feign方式下

1. 添加配置开启断路器

   ```yaml
   feign:
     hystrix:
       enabled: true #开启断路器
   ```

2. 创建熔断处理类，实现对应接口

   ```java
   @Component
   public class ErrorHander implements HelloAction {
       @Override
       public String hello() {
           return "feign error";
       }
   }
   ```

3. 接口指定熔断处理类,fallback

```java
@FeignClient(value = "user-service",fallback = ErrorHander.class)
public interface HelloAction {

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    String hello();

}
```

4.设置超时时间

```yaml
#hystrix断路参数配置
hystrix:
  command:
    default: #default时为默认配置, 相关参数说明在 HystrixCommandProperties
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 60000
```

### 聚合多个Hystrix流

使用的是Netfilx的另外一个项目Turbine

依赖引入

```xml
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-netflix-turbine</artifactId>
</dependency>
```

启动类开启

​	@EnableTurbine

添加配置

```yaml
turbine:
	app-config: service-1,service-2  #需要注册的服务
	cluster-name-expression: "'default'" #default表示收集集群中的所有聚合流
```



## Config Server(配置中心)

配置文件是我们再熟悉不过的了，尤其是 Spring Boot 项目，除了引入相应的 maven 包之外，剩下的工作就是完善配置文件了，例如 mysql、redis 、security 相关的配置。除了项目运行的基础配置之外，还有一些配置是与我们业务有关系的，比如说七牛存储、短信相关、邮件相关，或者一些业务上的开关。
对于一些简单的项目来说，我们一般都是直接把相关配置放在单独的配置文件中，以 properties 或者 yml 的格式出现，更省事儿的方式是直接放到 application.properties 或 application.yml 中。但是这样的方式有个明显的问题，那就是，当修改了配置之后，必须重启服务，否则配置无法生效。
目前有一些用的比较多的开源的配置中心，比如携程的 Apollo、蚂蚁金服的 disconf 等，对比 Spring Cloud Config，这些配置中心功能更加强大。有兴趣的可以拿来试一试。

接下来，我们开始在 Spring Boot 项目中集成 Spring Cloud Config，并以 github 作为配置存储。除了 git 外，还可以用数据库、svn、本地文件等作为存储。

### 最简单的配置中心

1. 在 github 中建立配置文件仓库

   ![image-20201119173450004](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/image-20201119173450004.png)

   config-single-client-dev.yml

   ```yaml
   data:
     env: localhost-dev
     user:
       username: dev
       password: 123456
   ```

   config-single-client-prod.ym

   ```yaml
   data:
     env: localhost-prod
     user:
       username: prod
       password: 123456
   ```

   注意文件的名称不是乱起的，例如上面的 config-single-client-dev.yml 和 config-single-client-prod.yml 这两个是同一个项目的不同版本，项目名称为 config-single-client， 一个对应开发版，一个对应正式版。

2. 配置中心服务端

   - 引入依赖

     ```xml
     <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-config-server</artifactId>
     </dependency>
     ```

   - 增加配置

     ```yaml
     spring:
       application:
         name: config-single-server
       cloud:
         config:
           server:
             git:
               uri: https://github.com/kinglead2012/spring-cloud-config-repository.git
               search-paths: config
               username: kinglead2012
               password: kinglead201314
               force-pull: true
           label: master
     server:
       port: 8888
     ```

   - 修改启动类

     ```java
     @SpringBootApplication
     @EnableConfigServer //开启配置中心
     public class ConfigApplication {
     
        public static void main(String[] args) {
           SpringApplication.run(ConfigApplication.class, args);
        }
     
     }
     ```

   - 测试

     http://localhost:8888/config-single-client/dev
     通过访问以上地址，如果可以正常返回数据，则说明配置中心服务端一切正常。

     ![img](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_1605778913842.png)

     访问方式：

     /{application}/{profile}[/{label}]
     /{application}-{profile}.yml
     /{label}/{application}-{profile}.yml
     /{application}-{profile}.properties
     /{label}/{application}-{profile}.properties

     {application} 就是应用名称，对应到配置文件上来，就是配置文件的名称部分，例如我上面创建的配置文件。
     {profile} 就是配置文件的版本，我们的项目有开发版本、测试环境版本、生产环境版本，对应到配置文件上来就是以 application-{profile}.yml 加以区分，例如application-dev.yml、application-sit.yml、application-prod.yml。
     {label} 表示 git 分支，默认是 master 分支，如果项目是以分支做区分也是可以的，那就可以通过不同的 label 来控制访问不同的配置文件了。

3. 配置中心客户端

   - 引入依赖

     ```xml
     <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-config</artifactId>
     </dependency>
     ```

   - 增加配置

     ```yaml
     spring:
       profiles:
         active: dev
     server:
       port: 9002
     
     ---
     spring:
       profiles: dev
       application:
         name: config-single-client
       cloud:
         config: #配置中心
           uri: http://localhost:8888/  #服务地址
           label: master #仓库分支
           profile: dev  #环境
     
     ---
     spring:
       profiles: prod
       application:
         name: config-single-client
       cloud:
         config:
           uri: http://localhost:8888/
           label: master #仓库分支
           profile: prod #环境
     ```

   - 编写配置类

     ```java
     package com.kinglead.config;
     
     import org.springframework.beans.factory.annotation.Value;
     import org.springframework.stereotype.Component;
     
     @Component
     public class GitConfig {
         @Value("${data.env}")
         private String env;
     
         @Value("${data.user.username}")
         private String username;
     
         @Value("${data.user.password}")
         private String password;
     
         public String getEnv() {
             return env;
         }
     
         public void setEnv(String env) {
             this.env = env;
         }
     
         public String getUsername() {
             return username;
         }
     
         public void setUsername(String username) {
             this.username = username;
         }
     
         public String getPassword() {
             return password;
         }
     
         public void setPassword(String password) {
             this.password = password;
         }
     }
     ```

   - 编写controller

     ```java
     @RestController
     public class TestController {
     
         @Autowired
         GitConfig gitConfig;
     
         @RequestMapping("/test")
         public String tset(){
             System.out.println("配置参数name=data.env,value=" + gitConfig.getEnv());
             return gitConfig.getEnv();
         }
     
     }
     ```

   - 测试

     ![img](../../readme/assets/%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_16057801782537.png)

   - 注意：Spring Cloud Config 的端口 port 不是 8888 的时候的坑

     当你配置的配置中心的 server.port 不是 8888 的时候，其他服务就起不来了，从日志中可以发现，服务启动的时候，Fetching config from server at: http://localhost:8888，说明其他服务还是去 8888 这个默认端口取配置中心的文件，而不去你在 application.yaml 文件中配置的配置中心读取配置文件，这是什么原因呢？
     其实这就是一个配置文件的优先级的问题，当服务启动的时候，它需要加载一些配置才能启动成功，而当你把配置信息放至配置中心的时候，启动服务首先就要去配置中心获取配置信息，然后加载这些信息才能成功启动。
     当你的服务配置文件使用 application.yaml 文件时，服务启动还没到加载 application.yaml 文件那一步，所以并不会去你配置的注册中心里的配置中心读取所需要的配置信息，因为application.yaml 的优先级不高，而此时又需要一些配置信息（例如数据库的信息），系统才能继续往下执行启动，这个时候就需要使用 bootstrap.yaml 引导配置文件，使用这个配置文件时，服务在启动的时候就会先加载 bootstrap.yaml 配置文件，这样就会去你配置的注册中心里的配置中心读取配置文件信息，然后加载信息进行启动。

### 实现自动刷新

#### 单台

Spring Cloud Config 在项目启动时加载配置内容这一机制，导致了它存在一个缺陷，修改配置文件内容后，不会自动刷新。例如我们上面的项目，当服务已经启动的时候，去修改 github 上的配置文件内容，这时候，再次刷新页面，对不起，还是旧的配置内容，新内容不会主动刷新过来。但是，总不能每次修改了配置后重启服务吧。如果是那样的话，还是不要用它了为好，直接用本地配置文件岂不是更快。它提供了一个刷新机制，但是需要我们主动触发。那就是 @RefreshScope 注解并结合 actuator ，注意要引入 spring-boot-starter-actuator 包。

1. 配置中心客户端

- 配置中增加 actuator 配置(其实这里主要用到的是 refresh 这个接口)

  ```yaml
  management:
    endpoint:
      shutdown:
        enabled: false
    endpoints:
      web:
        exposure:
          include: "*"
  ```

- 在需要读取配置的类上增加 @RefreshScope 注解，我们是 controller 中使用配置，所以加在 controller 中。

  ```java
  @RestController
  @RefreshScope //刷新配置
  public class TestController {
  
      @Autowired
      GitConfig gitConfig;
  
      @Autowired
      GitAutoRefreshConfig gitAutoRefreshConfig;
  
      @RequestMapping("/test")
      public String tset(){
          System.out.println("配置参数name=data.env,value=" + gitConfig.getEnv());
          return gitConfig.getEnv();
      }
  
      @RequestMapping("/test2")
      public String tset2(){
          System.out.println("配置参数name=data.env,value=" + gitAutoRefreshConfig.getEnv());
          return gitAutoRefreshConfig.getEnv();
      }
  
  }
  ```

- 重启配置中心客户端

- 修改 github 上的配置文件内容，并提交更改，再次刷新页面，还是没有更新

- 接下来，我们发送 POST 请求到 http://localhost:3302/actuator/refresh 这个接口，刷新配置

- 再次访问 RESTful 接口，http://localhost:9002/test2 这个接口获取的数据已经是最新的了，说明 refresh 机制起作用了。
  但是http://localhost:9002/test接口还是旧数据，这是@Value注解的问题

2.在 github 中配置 Webhook

这就结束了吗，并没有，总不能每次改了配置后，就用 postman 访问一下 refresh 接口吧，还是不够方便呀。github 提供了一种 webhook 的方式，当有代码变更的时候，会调用我们设置的地址，来实现我们想达到的目的。

进入 github 仓库配置页面，选择 Webhooks ，并点击 add webhook

填上回调的地址，也就是上面提到的 actuator/refresh 这个地址，但是必须保证这个地址是可以被 github 访问到的。如果是内网就没办法了。这也仅仅是个演示，一般公司内的项目都会有自己的代码管理工具，例如自建的 gitlab，gitlab 也有 webhook 的功能，这样就可以调用到内网的地址了。

#### 多台

Spring Cloud Bus 将分布式系统的节点与轻量级消息代理链接。这可以用于广播状态更改（例如配置更改）或其他管理指令。一个关键的想法是，Bus 就像一个扩展的 Spring Boot 应用程序的分布式执行器，但也可以用作应用程序之间的通信渠道。

如果只有一个 client 端的话，那我们用 webhook ，设置手动刷新都不算太费事，但是如果端比较多的话呢，一个一个去手动刷新未免有点复杂。这样的话，我们可以借助 Spring Cloud Bus 的广播功能，让 client 端都订阅配置更新事件，当配置更新时，触发其中一个端的更新事件，Spring Cloud Bus 就把此事件广播到其他订阅端，以此来达到批量更新。

1. Spring Cloud Bus 核心原理其实就是利用消息队列做广播，所以要先有个消息队列，目前官方支持 RabbitMQ 和 kafka。

   这里用的是 RabbitMQ， 所以先要搭一套 RabbitMQ 环境。请自行准备环境，这里不再赘述。我是用 docker 直接创建的，然后安装了 rabbitmq-management 插件，这样就可以在浏览器访问 15672 查看 UI 管理界面了。

2. 在 client 端增加相关的包，注意，只在 client 端引入就可以。

   ```xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-bus-amqp</artifactId>
   </dependency>
   ```

3. 在配置文件中增加 RabbitMQ 相关配置，默认的端口应该是 5672 ，因为我是用 docker 创建的，所以有所不同。

   ```yaml
   spring:
     rabbitmq:
       host: localhost
       port: 32775
       username: guest
       password: guest
   ```

4. 启动两个或多个 client 端，准备来做个测试

   在启动的时候分别加上 vm option：-Dserver.port=3302 和 -Dserver.port=3303 ，然后分别启动就可以了。

   ![image-20201119185111252](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/image-20201119185111252.png)

5. 分别打开 http://localhost:3302/autoShow 和 http://localhost:3303/autoShow，查看内容，然后修改 github 上配置文件的内容并提交。再次访问这两个地址，数据没有变化。

6. 访问其中一个的 actuator/bus-refresh 地址，注意还是要用 POST 方式访问。之后查看控制台输出，会看到这两个端都有一条这样的日志输出

   o.s.cloud.bus.event.RefreshListener: Received remote refresh request. Keys refreshed

7. 再次访问第 5 步的两个地址，会看到内容都已经更新为修改后的数据了。

8. 综上所述，当我们修改配置后，使用 webhook ，或者手动触发的方式 POST 请求一个 client 端的 actuator/bus-refresh 接口，就可以更新给所有端了。



### 高可用配置中心

​	结合 Eureka 使用 Spring Cloud Config

1. 将“配置中心服务端”注册到“注册中心”

2. “配置中心客户端”直接跟“注册中心”打交道：
   将之前的“配置中心服务端”的url换成“注册中心”的applicationName

   ```yaml
   ---
   spring:
     profiles: dev
     application:
       name: config-eureka-client
     cloud:
        config:
          label: master  #指定仓库分支
          profile: dev   #指定版本 本例中建立了dev 和 prod两个版本
          discovery:
             enabled: true  # 开启
             service-id: config-eureka-server # 指定配置中心服务端的server-id 
   ```

   

## Zuul(网关)

### 什么是API Gateway

在微服务架构中，通常会有多个服务提供者。设想一个电商系统，可能会有商品、订单、支付、用户等多个类型的服务，而每个类型的服务数量也会随着整个系统体量的增大也会随之增长和变更。作为UI端，在展示页面时可能需要从多个微服务中聚合数据，而且服务的划分位置结构可能会有所改变。网关就可以对外暴露聚合API，屏蔽内部微服务的微小变动，保持整个系统的稳定性。

当然这只是网关众多功能中的一部分，它还可以做负载均衡，统一鉴权，协议转换，监控监测等一系列功能。

在Spring Cloud微服务系统中，一种常见的负载均衡方式是，客户端的请求首先经过负载均衡（zuul、Ngnix），再到达服务网关（zuul集群），然后再到具体的服务。

### 什么是Zuul

Zuul是Spring Cloud全家桶中的微服务API网关。
所有从设备或网站来的请求都会经过Zuul到达后端的Netflix应用程序。作为一个边界性质的应用程序，Zuul提供了动态路由、监控、弹性负载和安全功能。Zuul底层利用各种filter实现如下功能：
认证和安全 识别每个需要认证的资源，拒绝不符合要求的请求。
性能监测 在服务边界追踪并统计数据，提供精确的生产视图。
动态路由 根据需要将请求动态路由到后端集群。
压力测试 逐渐增加对集群的流量以了解其性能。
负载卸载 预先为每种类型的请求分配容量，当请求超过容量时自动丢弃。
静态资源处理 直接在边界返回某些响应。

### 路由功能

1. 引入依赖

   ```xml
    <dependency>
             <groupId>org.springframework.cloud</groupId>
             <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
   </dependency>
   ```

2. 添加配置

   ```yaml
   zuul:
     routes:
       api-a:
         path: /api-ribbon/**
         service-id: service-ribbon
       api-b:
         path: /api-feign/**
         service-id: service-feign
   ```

3. 修改启动类

   1. @EnableZuulProxy 

   ```java
   @SpringBootApplication
   @EnableEurekaClient //开启Eyreka
   @EnableZuulProxy  //开启Zuul
   public class ZuulApplication {
   
      public static void main(String[] args) {
         SpringApplication.run(ZuulApplication.class, args);
      }
   
   }
   ```

4. 测试

   安装上面的操作，Zuul就能起到路由的功能。/api-ribbon命名空间的请求全部会路由到service-ribbon服务上；/api-feign命名空间的请求全部会路由到service-feign服务上



### 过滤功能

根据上面的路由功能改造

添加自定义过滤器MyFilter：

```java
package com.kinglead.eurekaclient;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class MyFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(MyFilter.class);

    /**
     * 返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型
     * pre：路由之前; routing：路由之时; post： 路由之后; error：发送错误调用
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤的顺序
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 这里可以写逻辑判断，是否要过滤，本例true,永远过滤。
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问。
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Object accessToken = request.getParameter("token");
        if(accessToken == null) {
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            }catch (Exception e){}

            return null;
        }
        log.info("ok");
        return null;

    }
}
```