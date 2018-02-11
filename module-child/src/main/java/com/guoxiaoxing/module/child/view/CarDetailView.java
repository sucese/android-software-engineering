package com.guoxiaoxing.module.child.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.guoxiaoxing.module.child.R;
import com.guoxiaoxing.module.child.model.CarDetailModel;
import com.guoxiaoxing.module.child.presenter.CarDetailPresenter;
import com.guoxiaoxing.module.child.presenter.ICarDetailPresenter;

/**
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingse@163.com.
 *
 * @author guoxiaoxing
 * @since 2018/2/11 下午2:36
 */
public class CarDetailView extends FrameLayout implements ICarDetailView, LifecycleObserver {

    private static final String TAG = "CarDetailBlock";

    private EditText mEtSubmit;
    private TextView mTvSubmit;

    private ICarDetailPresenter mICarDetailPresenter;

    public CarDetailView(@NonNull Context context) {
        super(context);
        setup();
    }

    public CarDetailView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public CarDetailView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    private void setup() {
        // Fragment和Activity都已经实现了LifecycleOwner接口，当然我们也可以自定义类实现LifecycleOwner接口
        if (getContext() instanceof LifecycleOwner) {
            // 添加监听器
            ((LifecycleOwner) getContext()).getLifecycle().addObserver(this);
        }
        inflate(getContext(), R.layout.view_car_detail, this);
        mEtSubmit = findViewById(R.id.car_detail_et_submit);
        mTvSubmit = findViewById(R.id.car_detail_tv_submit);

        // 可以按照需求替换CarDetailPresenter接口的实现
        mICarDetailPresenter = new CarDetailPresenter(this);
    }

    /****************************************** Activity/Fragment 生命周期回调 ***********************************/

    // 监听 onCreate 事件
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        Log.d(TAG, "onCreate()");
    }

    // 监听 onStart 事件
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart() {
        Log.d(TAG, "onStart()");
    }

    // 监听 onResume 事件
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume() {
        Log.d(TAG, "onResume()");
//        mICarDetailPresenter.loadData();
    }

    // 监听 onPause 事件
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        Log.d(TAG, "onPause()");
    }

    // 监听 onStop 事件
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop() {
        Log.d(TAG, "onStop()");
    }

    // 监听 onDestory 事件
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestory() {
        Log.d(TAG, "onDestory()");
        mICarDetailPresenter.releaseData();
    }

    // 监听所有事件
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onAny() {
        Log.d(TAG, "onAny()");
    }

    /***************************************** View 接口定义的方法，供 Presenter 使用 ******************************/

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void bindData(CarDetailModel carDetailModel) {
        mEtSubmit.setText(carDetailModel.getContent());
    }
}
