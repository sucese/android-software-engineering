#咖啡机例子解析

官方例子的场景描述：
>一个泵压式咖啡机(CoffeeMaker)由两个主要零件组成，泵浦(Pump)和加热器(Heater)，咖啡机有一个功能是煮泡咖啡(brew)，当进行煮泡咖啡时，会按如下几个步骤进行打开加热器进行加热，泵浦加压，萃取出咖啡，然后关闭加热器，一杯咖啡就算制作完毕了。

首先看一下工程结构，该工程针对[官方例子](https://github.com/google/dagger/tree/master/examples/simple/src/main/java/coffee)做了改造，更加清晰明了，并添加类详细的注释。

![](https://github.com/guoxiaoxing/google-dagger-bset-practice/blob/master/image/coffee-project.png)

##一 确定依赖和被依赖对象

依赖对象是CoffeeMaker对象，被依赖对象是Heater类型对象和Pump类型对象。

```java
package com.guoxiaoxing.coffee;

import com.guoxiaoxing.coffee.interfaces.Heater;
import com.guoxiaoxing.coffee.interfaces.Pump;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site:  https://github.com/guoxiaoxing
 * Date: 16/5/9 上午10:44
 * Function:
 * 场景描述：
 * 一个泵压式咖啡机(CoffeeMaker)由两个主要零件组成，泵浦(Pump)和加热器(Heater)，咖啡机有一个
 * 功能是煮泡咖啡(brew)，当进行煮泡咖啡时，会按如下几个步骤进行打开加热器进行加热，泵浦加压，萃
 * 取出咖啡，然后关闭加热器，一杯咖啡就算制作完毕了。
 * <p/>
 * Modification history:
 * Date                 Author              Version             Description
 * --------------------------------------------------------------------------
 * 16/5/9 上午10:44     guoxiaoxing           1.0
 */
public class CofferMaker {
    private final Lazy<Heater> mHeater;
    private final Pump mPump;

    /**
     *
     * @param heater Heater
     * @param pump   Pump
     */
    CofferMaker(Lazy<Heater> heater, Pump pump) {
        mHeater = heater;
        mPump = pump;
    }

    /**
     * 煮咖啡三步
     */
    public void brew() {
        mHeater.get().on();
        mPump.pump();
        System.out.println("coffee is done");
        mHeater.get().off();
    }
}
```
```java
package com.guoxiaoxing.coffee.interfaces;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site:  https://github.com/guoxiaoxing
 * Date: 16/5/9 上午10:37
 * Function: 加热器
 * <p/>
 * Modification history:
 * Date                 Author              Version             Description
 * --------------------------------------------------------------------------
 * 16/5/9 上午10:37     guoxiaoxing           1.0                  加热器
 */
public interface Heater {
    void on();

    void off();

    boolean isHot();
}
```
```java
package com.guoxiaoxing.coffee.interfaces;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site:  https://github.com/guoxiaoxing
 * Date: 16/5/9 上午10:39
 * Function: 泵浦
 * <p/>
 * Modification history:
 * Date                 Author              Version             Description
 * --------------------------------------------------------------------------
 * 16/5/9 上午10:39     guoxiaoxing           1.0                   泵浦
 */
public interface Pump {
    void pump();
}

```

这里Heater和Pump都是接口。既然是接口，就意味着必须要有对应的实现类才可以创建出相应的对象。HeaterImpl和Heater接口的实现类，PumpImpl是Pump接口的实现类：

```java
package com.guoxiaoxing.coffee.impl;

import com.guoxiaoxing.coffee.interfaces.Heater;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site:  https://github.com/guoxiaoxing
 * Date: 16/5/9 上午10:43
 * Function: 加热器 Heater的实现类
 * <p/>
 * Modification history:
 * Date                 Author              Version             Description
 * --------------------------------------------------------------------------
 * 16/5/9 上午10:43     guoxiaoxing           1.0
 */
public class HeaterImpl implements Heater {

    private boolean isHeating;

    @Override
    public void on() {
        System.out.println("Heater is on");
        isHeating = true;
    }

    @Override
    public void off() {
        System.out.println("Heater is off");
        isHeating = false;
    }

    @Override
    public boolean isHot() {
        return isHeating;
    }
}
```

```java
package com.guoxiaoxing.coffee.impl;

import com.guoxiaoxing.coffee.interfaces.Heater;
import com.guoxiaoxing.coffee.interfaces.Pump;

import javax.inject.Inject;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site:  https://github.com/guoxiaoxing
 * Date: 16/5/9 上午10:43
 * Function:泵浦，Pump的实现类
 * <p/>
 * Modification history:
 * Date                 Author              Version             Description
 * --------------------------------------------------------------------------
 * 16/5/9 上午10:43     guoxiaoxing           1.0
 */
public class PumpImpl implements Pump {

    private final Heater mHeater;

    @Inject
    public PumpImpl(Heater heater) {
        mHeater = heater;
    }

    @Override
    public void pump() {
        if (mHeater.isHot()) {
            System.out.println("coffee maker is pumping");
        }
    }
}
```

##二 创建@Module类
@Module类的命名惯例是以Module作为类名称的结尾。而@Module类中的@Provides方法名称的命名惯例是以provide作为前缀。

官方案例在这里使用了2个@Module类，一个@Module类作为了另一个@Module类的组成部分。

```java
package com.guoxiaoxing.coffee.module;

import com.guoxiaoxing.coffee.impl.HeaterImpl;
import com.guoxiaoxing.coffee.interfaces.Heater;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site:  https://github.com/guoxiaoxing
 * Date: 16/5/9 上午10:53
 * Function:
 * HeaterModule使用includes属性，将PumpModule作为自己的一部分。
 * <p/>
 * Modification history:
 * Date                 Author              Version             Description
 * --------------------------------------------------------------------------
 * 16/5/9 上午10:53     guoxiaoxing           1.0
 */
@Module(includes = PumpModule.class)
public class HeaterModule {
    @Provides
    @Singleton
    Heater provideHeater() {
        // provideHeater方法提供Heater类型对象的时候，是直接显示的调HeaterImpl对象的构
        // 造器来进行的，因此不存在什么注入不注入的事情，即HeaterImpl不用进行任何改写。
        return new HeaterImpl();
    }
}
```
```java
package com.guoxiaoxing.coffee.module;

import com.guoxiaoxing.coffee.impl.PumpImpl;
import com.guoxiaoxing.coffee.interfaces.Pump;

import dagger.Module;
import dagger.Provides;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site:  https://github.com/guoxiaoxing
 * Date: 16/5/9 上午10:51
 * Function:
 * <p/>
 * Modification history:
 * Date                 Author              Version             Description
 * --------------------------------------------------------------------------
 * 16/5/9 上午10:51     guoxiaoxing           1.0
 */
@Module
public class PumpModule {
    @Provides
    Pump providePump(PumpImpl pump) {
        // providePump方法在提供Pump类型对象的时候，是把方法的形参作为返回值返回的，为了保证Dagger在创
        // 建CoffeeMaker对象的时候，可以自动装配pump属性，因此必须要为PumpImpl类的构造器添加@Inject
        // 注解，没有@Inject注解的类，Dagger2是不认识的，更无法自动进行构造器的调用创建实例。
        return pump;
    }
}  
```
##三 获得了@Module类之后，对Step1中的需要注入的地方进行相应的注解。
```java
package com.guoxiaoxing.coffee;

import com.guoxiaoxing.coffee.interfaces.Heater;
import com.guoxiaoxing.coffee.interfaces.Pump;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site:  https://github.com/guoxiaoxing
 * Date: 16/5/9 上午10:44
 * Function:
 * 场景描述：
 * 一个泵压式咖啡机(CoffeeMaker)由两个主要零件组成，泵浦(Pump)和加热器(Heater)，咖啡机有一个
 * 功能是煮泡咖啡(brew)，当进行煮泡咖啡时，会按如下几个步骤进行打开加热器进行加热，泵浦加压，萃
 * 取出咖啡，然后关闭加热器，一杯咖啡就算制作完毕了。
 * <p/>
 * Modification history:
 * Date                 Author              Version             Description
 * --------------------------------------------------------------------------
 * 16/5/9 上午10:44     guoxiaoxing           1.0
 */
public class CofferMaker {
    private final Lazy<Heater> mHeater;
    private final Pump mPump;

    /**
     * CoffeeMaker对象依赖于Heater对象和Pump对象，为构造器添加@Inject注解的方式，到时在创建CoffeeMaker对象的时候，
     * Dagger2会自动调用这个带@Inject的构造器，同时根据@Module去获得需要传入构造器的Heater类型对象与Pump类型对象。
     * 另外，这里对heater属性采用了“延迟加载”机制，即Heater类型对象的真正实例化是在第一次调用heater.get()方法的时候进行
     * (因为此时heater是Lazy<Heater>类型，因此要先调用get方法来获得Lazy<Heater>中封装的Heater对象，进而才能调用Heater
     * 对象的方法)。
     *
     * @param heater Heater
     * @param pump   Pump
     */
    @Inject
    CofferMaker(Lazy<Heater> heater, Pump pump) {
        mHeater = heater;
        mPump = pump;
    }

    /**
     * 煮咖啡三步
     */
    public void brew() {
        mHeater.get().on();
        mPump.pump();
        System.out.println("coffee is done");
        mHeater.get().off();
    }
}
```
##四 创建一个接口，让@Inject和@Module建立起联系，利用Dagger2自动生成的@Commpoment接口实现类创建Coffee类型的实例。
经过Step2和Step3对Step1中创建的类的修改，实际上CoffeeMaker的依赖关系已经描述完毕了，接下来就是要创建一个@Commpoment接口，通过接口中的一个无参数方法来“驱使”Dagger2来创建出一个CoffeeMaker对象，并且随着这个对象的建立，CoffeeMaker中所有依赖对象都装配好。官方例子中这个接口被作为一个内部接口的方式提供：

```java
package com.guoxiaoxing.coffee;

import com.guoxiaoxing.coffee.module.HeaterModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site:  https://github.com/guoxiaoxing
 * Date: 16/5/9 上午11:00
 * Function:
 * <p/>
 * Modification history:
 * Date                 Author              Version             Description
 * --------------------------------------------------------------------------
 * 16/5/9 上午11:00     guoxiaoxing           1.0
 */

public class CoffeeApp {
    public static void main(String[] args) {
        Coffee coffee = DaggerCoffeeApp_Coffee.builder().build();
        coffee.maker().brew();
    }

    // 创建一个@Commpoment接口，通过接口中的一个无参数方法来“驱使”Dagger2来创建出一
    // 个CoffeeMaker对象，并且随着这个对象的建立，CoffeeMaker中所有依赖对象都装配好
    @Singleton
    @Component(modules = {HeaterModule.class})
    public interface Coffee {
        CofferMaker maker();
    }
}

```
@Component接口作为CoffeeApp的内部接口来呈现。那么此时Dagger2为该接口自动生成的实现类名称就是Dagger外部类名称_接口名称，即DaggerCoffeeApp_Coffee。然后再调用相应的builder方法来创建出Coffee实例。官方的例子在创建Coffee类型的实例时，使用的是DaggerCoffeeApp_Coffee.builder().build()，如果完全按照上一个例子的写法，将该方法写完整DaggerCoffeeApp_Coffee.builder().dripCoffeeModule(newDripCoffeeModule()).build();效果是完全一样的，这一点通过查看一下DaggerCoffeeApp_Coffee的源码即可得知。而且，如果@Module类实例的创建是通过默认无参构造器来创建的，那么可以不使用Build模式，将这简写为DaggerCoffeeApp_Coffee.create()即可。
