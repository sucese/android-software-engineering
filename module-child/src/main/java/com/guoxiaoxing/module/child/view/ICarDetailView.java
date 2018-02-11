package com.guoxiaoxing.module.child.view;

import com.guoxiaoxing.module.child.base.BaseView;
import com.guoxiaoxing.module.child.model.CarDetailModel;

/**
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingse@163.com.
 *
 * @author guoxiaoxing
 * @since 2018/2/11 下午3:03
 */
public interface ICarDetailView extends BaseView{

    void showProgress();

    void dismissProgress();

    void bindData(CarDetailModel carDetailModel);
}
