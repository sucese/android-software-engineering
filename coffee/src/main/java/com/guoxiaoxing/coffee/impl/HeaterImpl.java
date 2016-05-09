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