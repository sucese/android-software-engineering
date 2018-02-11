package com.guoxiaoxing.module.child.presenter;

import android.support.annotation.NonNull;

import com.guoxiaoxing.module.child.model.CarDetailModel;
import com.guoxiaoxing.module.child.util.Instance;
import com.guoxiaoxing.module.child.view.ICarDetailView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingse@163.com.
 *
 * @author guoxiaoxing
 * @since 2018/2/11 下午3:07
 */
public class CarDetailPresenter implements ICarDetailPresenter {

    private ICarDetailView mCarDetailView;

    public CarDetailPresenter(ICarDetailView iCarDetailView) {
        mCarDetailView = iCarDetailView;
    }

    @Override
    public void loadData() {
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .build();

        mCarDetailView.showProgress();
        Instance.okhttpInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                mCarDetailView.dismissProgress();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                mCarDetailView.dismissProgress();
                //TODO 这里只是模拟一下接口请求
                CarDetailModel carDetailModel = new CarDetailModel();
                carDetailModel.setContent("I am the content");
                mCarDetailView.bindData(carDetailModel);
            }
        });
    }

    @Override
    public void refreshData() {

    }

    @Override
    public void releaseData() {

    }

    @Override
    public void submit() {

    }
}
