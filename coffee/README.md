#咖啡机例子解析

官方例子的场景描述：
>一个泵压式咖啡机(CoffeeMaker)由两个主要零件组成，泵浦(Pump)和加热器(Heater)，咖啡机有一个功能是煮泡咖啡(brew)，当进行煮泡咖啡时，会按如下几个步骤进行打开加热器进行加热，泵浦加压，萃取出咖啡，然后关闭加热器，一杯咖啡就算制作完毕了。

首先看一下工程结构，该工程针对官方例子做了改造，更加清晰明了，并添加类详细的注释。

![](https://github.com/guoxiaoxing/google-dagger-bset-practice/blob/master/image/coffee-project.png)

##一 确定依赖和被依赖对象

依赖对象是CoffeeMaker对象，被依赖对象是Heater类型对象和Pump类型对象。

```java

```


这里Heater和Pump都是接口。既然是接口，就意味着必须要有对应的实现类才可以创建出相应的对象。ElectricHeater和Heater接口的实现类，Thermosiphon是Pump接口的实现类：

```java
```


##二 创建@Module类
@Module类的命名惯例是以Module作为类名称的结尾。而@Module类中的@Provides方法名称的命名惯例是以provide作为前缀。

官方案例在这里使用了2个@Module类，一个@Module类作为了另一个@Module类的组成部分。

```java
```

##三 获得了@Module类之后，对Step1中的需要注入的地方进行相应的注解。

```java
```

这里仍然使用为构造器添加@Inject注解的方式，到时在创建CoffeeMaker对象的时候，Dagger2会自动调用这个带@Inject的构造器，同时根据@Module去获得需要传入构造器的Heater类型对象与Pump类型对象。另外，这里对heater属性采用了“延迟加载”机制，即Heater类型对象的真正实例化是在第一次调用heater.get()方法的时候进行(因为此时heater是Lazy<Heater>类型，因此要先调用get方法来获得Lazy<Heater>中封装的Heater对象，进而才能调用Heater对象的方法)。
@Module类中，provideHeater方法提供Heater类型对象的时候，是直接显示的调用ElectricHeater对象的构造器来进行的，因此不存在什么注入不注入的事情，即ElectricHeater不用进行任何改写。而providePump方法在提供Pump类型对象的时候，是把方法的形参作为返回值返回的，为了保证Dagger2在创建CoffeeMaker对象的时候，可以自动装配pump属性，因此必须要为Thermosiphon类的构造器添加@Inject注解，没有@Inject注解的类，Dagger2是不认识的，更无法自动进行构造器的调用创建实例。

src/coffee/Thermosiphon.java

[java] view plain copy
class Thermosiphon implements Pump {  
  
 private final Heater heater;  
  
 @Inject  
  
 Thermosiphon(Heater heater) {  
  
   this.heater = heater;  
  
  }  
  
   
  
 @Override public void pump() {  
  
   if (heater.isHot()) {  
  
     System.out.println("=> => pumping => =>");  
  
    }  
  
  }  
  
}  
##四 创建一个接口，让@Inject和@Module建立起联系
经过Step2和Step3对Step1中创建的类的修改，实际上CoffeeMaker的依赖关系已经描述完毕了，接下来就是要创建一个@Commpoment接口，通过接口中的一个无参数方法来“驱使”Dagger2来创建出一个CoffeeMaker对象，并且随着这个对象的建立，CoffeeMaker中所有依赖对象都装配好。官方例子中这个接口被作为一个内部接口的方式提供：

```java
```

##五 利用Dagger2自动生成的@Commpoment接口实现类创建Coffee类型的实例。
一旦利用Dagger2自动生成的@Commpoment接口实现类创建出Coffee类型的实例，就可以调用maker方法获得CoffeeMaker类型的实例。此时这个CoffeeMaker实例中所有的依赖关系都被装配好了。官方例子中，是这样写的：

src/coffee/CoffeeApp.java

[java] view plain copy
public class CoffeeApp {  
  
 @Singleton  
  
 @Component(modules = { DripCoffeeModule.class })  
  
  publicinterface Coffee {  
  
   CoffeeMaker maker();  
  
  }  
  
 public static void main(String[] args) {  
  
   Coffee coffee = DaggerCoffeeApp_Coffee.builder().build();  
  
   coffee.maker().brew();  
  
  }  
  
}  
@Component接口作为CoffeeApp的内部接口来呈现。那么此时Dagger2为该接口自动生成的实现类名称就是Dagger外部类名称_接口名称，即DaggerCoffeeApp_Coffee。然后再调用相应的builder方法来创建出Coffee实例。官方的例子在创建Coffee类型的实例时，使用的是DaggerCoffeeApp_Coffee.builder().build()，如果完全按照上一个例子的写法，将该方法写完整DaggerCoffeeApp_Coffee.builder().dripCoffeeModule(newDripCoffeeModule()).build();效果是完全一样的，这一点通过查看一下DaggerCoffeeApp_Coffee的源码即可得知。而且，如果@Module类实例的创建是通过默认无参构造器来创建的，那么可以不使用Build模式，将这简写为DaggerCoffeeApp_Coffee.create()即可。
另外，如果DaggerCoffeeApp_Coffee已经生成，对@Inject或者@Module做出改动不会影响到DaggerCoffeeApp_Coffee，但是对@Commpoment接口或者DaggerCoffeeApp_Coffee调用方法的相关代码做出改动，Eclipse中都会对DaggerCoffeeApp_Coffee类报错。此时要强行对项目进行一次重建(rebuild)。但是Eclipse是没有rebuild这个功能键的。要在Eclipse中对一个项目进行rebuild的方式可以参考“在Eclipse中搭建Dagger和Dagger2使用环境”的做法，先将Annotation Processing的Enable projectspecific settings选项对勾去掉，单击“apply”，此时Eclipse会有一个提示，点击yes即可对项目进行一次rebuild，此时rebuild的结果肯定是错误的，然后在重新将Annotation Processing的Enable projectspecific settings选项对勾选中，单击“apply”，此时Eclipse会再次出现一个提示，点击yes对项目再进行一次rebuild，此时就可以得到正确的DaggerCoffeeApp_Coffee类了。

最后，在Step2中，如果不对@Module注解使用includes属性，完全可以将DripCoffeeModule和PumpModule合并成一个@Module类：


在providePump方法中返回Thermosiphon对象的时候，因为构造器需要一个Heater类型的对象，因此把这个Heater类型的对象从方法参数传进去，到时候Dagger2在找这个Heater类型对象的时候，自然会去根据providHeater方法来自动装配一个ElectricHeater实例进去。这一点可以参考DaggerCoffeeApp_Coffee类的源码，看看它是如何处理@Module类中的@Provides方法的。
