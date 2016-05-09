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