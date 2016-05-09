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
        return new HeaterImpl();
    }
}