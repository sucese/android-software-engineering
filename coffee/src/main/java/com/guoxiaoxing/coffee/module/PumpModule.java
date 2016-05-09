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