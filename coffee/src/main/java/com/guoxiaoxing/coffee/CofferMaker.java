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
     * CoffeeMaker对象依赖于Heater对象和Pump对象
     *
     * @param mHeater Heater
     * @param mPump   Pump
     */
    @Inject
    CofferMaker(Lazy<Heater> mHeater, Pump mPump) {
        this.mHeater = mHeater;
        this.mPump = mPump;
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