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
        return pump;
    }
}  