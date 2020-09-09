初始化spring boot项目的方式非常多，如使用Spring Tool Suite、使用IntelliJ IDEA、使用NetBeans、在start.spring.io网站中、curl命令、spring boot命令等等。考虑到国人使用IntelliJ IDEA和start.spring.io网站最多，下面就以这两种方式详细讲解下spring boot初始化项目过程，其它方式大家可以自行尝试。

### start.spring.io网站方式

- 浏览器中访问网址：https://start.spring.io/，进入首页

![image](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_15995369168136.png?token=AEVZWT7OQ3BN74GS3GFMPEC7K47RM)

- 选择项目类型：Maven、Gradle

- 选择编程语言：Java、Kotlin、Groovy

- 选择spring boot版本

- 填写项目信息：Group、Artifact、Name、Description、Package name、Packaging（打包方式）、Java版本

- 添加依赖

  ![image](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_15995372507844.png?token=AEVZWT7FXV7FILSBZRBZG527K47TE)

- 范例

 ![image](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_15995374802952.png?token=AEVZWT4YWKC4FMCFR6CCEZS7K47TY)

- 生成项目：点击GENERATE，会自动下载项目包，解压后，用自己喜爱的IDE（如IDEA）打开即可。

  

  

### IntelliJ IDEA方式

- 打开IDEA软件（笔者IDEA版本是2018.3，不同版本界面或有差异，但内容都一样）

- 打开项目创建界面：File->New->Project

  ![image](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/image-20200908120908802.png?token=AEVZWT6GAWWVE64PWN2VEO27K47U4)

- 根据实际需求选择创建方式（本文以Spring Initializr为例）

  ![image](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_15995382969552.png?token=AEVZWT26VF3MFWIA6BIYJLS7K47VM)

- Next.填写项目信息（同start.spring.io方式信息一样）

 ![image](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/image-20200908121320187.png?token=AEVZWT6TSGWH5HS2PNZY6HS7K47WA)

- Next.选择依赖包（可以增减）

  ![image](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/image-20200908121447034.png?token=AEVZWT6TSGWH5HS2PNZY6HS7K47WA)

- Next.填写项目名称和保存路径

  ![image](https://raw.githubusercontent.com/kinglead2012/myblog/master/img/image-20200908121607623.png?token=AEVZWT52FZTPI35GRN43KD27K47WO)

- 最后Finish即可

