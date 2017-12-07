# 大型Android项目的工程化之路：编译与构建

**关于作者**

>郭孝星，程序员，吉他手，主要从事Android平台基础架构方面的工作，欢迎交流技术方面的问题，可以去我的[Github](https://github.com/guoxiaoxing)提issue或者发邮件至guoxiaoxingse@163.com与我交流。

**文章目录**

- 一 Groovy语言基础
- 二 Gradle脚本构建
- 三 Gradle多项目构建
- 四 Gradle多渠道打包
- 五 Gradle测试


本篇文章是《大型Android项目的工程化之路》的开篇之作，用来讨论Android项目架构的最佳实践。


>[Gradle](https://zh.wikipedia.org/wiki/Gradle)是一个基于Apache Ant和Apache Maven概念的项目自动化建构工具。它使用一种基于Groovy的特定领域语言来声明项目设置，大部分功能都通过
插件的方式实现。

官方网站：https://gradle.org/

## 一 Groovy语言基础

[Groovy](http://groovy-lang.org/)是基于JVM的一种动态语言，语法与Java相似，也完全兼容Java。

这里我们简单的说一些我们平时用的到的Groovy语言的一些特性，方便大家理解和编写Gradle脚本，事实上如果你熟悉Kotlin、JavaScript这些语言，那么
Groovy对你来说会有种很相似的感觉。

注：Groovy是完全兼容Java的，也就意味着如果你对Groovy不熟悉，也可以用Java来写Gradle脚本。

- 单引号表示纯字符串，双引号表示对字符串求值，例如$取值。

```java
def version = '26.0.0'

dependencies {
    compile "com.android.support:appcompat-v7:$version"
}

```

- Groovy完全兼容Java的集合，并且进行了扩展。

```java
task printList {
    def list = [1, 2, 3, 4, 5]
    println(list)
    println(list[1])//访问第二个元素
    println(list[-1])//访问最后一个元素
    println(list[1..3])//访问第二个到第四个元素
}

task printMap {
    def map = ['width':720, 'height':1080]
    println(map)
    println(map.width)//访问width
    println(map.height)//访问height
    map.each {//遍历map
        println("Key:${it.key}, Value:${it.value}")
    }
}
```

- Groovy方法的定义方式和Java类似，调用方式比Java灵活，有返回值的函数也可以不写return语句，这个时候会把最后一行代码的值作为返回值返回。

```java
def method(int a, int b){
    if(a > b){
        a
    }else {
        b
    }
}

def  callMethod(){
    method 1, 2
}
```

可以看到，和Kotlin这些现代编程语言一样，有很多语法糖。

## 二 Gradle脚本构建



Gradle Wrapper是对Gradle的一层包装，目的在于团队开发中统一Gradle版本，一般可以通过gradle wrapper命令构建，会生成以下文件：

- gradle-wrapper.jar
- gradle-wrapper.properties

文件用来进行Gradle Wrapper进行相关配置。如下所示：

```java
#Fri Nov 24 17:39:29 CST 2017
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-4.1-all.zip
```

我们通常关心的是distributionUrl，它用来配置Gradle的版本，它会去该路径下载相应的Gradle包。

注：如果官方的gradle地址下载比较慢，可以去国内的镜像地址http://mirrors.flysnow.org/下载。


include表示工程在构建时将demo包含进去，我们还可以自定义demo所在目录，如果没有指定，默认指定当前根目录。

```java
include ':demo'
project(':demo').projectDir = new File('your demo path')
```


强制刷新依赖

```java
gradle --refresh-dependencies assemble
```
