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