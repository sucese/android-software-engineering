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