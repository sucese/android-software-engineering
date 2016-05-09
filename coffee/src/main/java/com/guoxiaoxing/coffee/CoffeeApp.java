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

