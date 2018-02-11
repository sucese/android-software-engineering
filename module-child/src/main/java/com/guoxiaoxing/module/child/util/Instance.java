package com.guoxiaoxing.module.child.util;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;

/**
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingse@163.com.
 *
 * @author guoxiaoxing
 * @since 2018/2/11 下午3:52
 */
public class Instance {

    private static OkHttpClient sOkhttpInstance;
    private static Gson sGsonInstance;

    public static OkHttpClient okhttpInstance(){
        if(sOkhttpInstance == null){
            synchronized (Instance.class){
                if(sOkhttpInstance == null){
                    sOkhttpInstance = new OkHttpClient();
                }
            }
        }
        return sOkhttpInstance;
    }

    public static Gson gsonInstance(){
        if(sGsonInstance == null){
            synchronized (Instance.class){
                if(sGsonInstance == null){
                    sGsonInstance = new Gson();
                }
            }
        }
        return sGsonInstance;
    }
}
