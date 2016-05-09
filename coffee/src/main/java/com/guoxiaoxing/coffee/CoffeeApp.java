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

    @Singleton
    @Component(modules = {HeaterModule.class})
    public interface Coffee {
        CofferMaker maker();
    }

    public static void main(String[] args) {
    }
}

