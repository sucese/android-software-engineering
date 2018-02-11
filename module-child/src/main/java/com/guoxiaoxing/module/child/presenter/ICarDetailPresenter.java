package com.guoxiaoxing.module.child.presenter;

import com.guoxiaoxing.module.child.base.BasePresenter;

/**
 * For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingse@163.com.
 *
 * @author guoxiaoxing
 * @since 2018/2/11 下午3:06
 */
public interface ICarDetailPresenter extends BasePresenter {

    void loadData();

    void refreshData();

    void submit();

    void releaseData();
}
