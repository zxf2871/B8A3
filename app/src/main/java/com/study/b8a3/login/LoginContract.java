package com.study.b8a3.login;

import com.study.b8a3.base.BasePresenter;
import com.study.b8a3.base.BaseView;

/**
 * Created by Administrator on 2017/3/1.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void loginSuccess();
        void loginError(String message);
    }

    interface Presenter extends BasePresenter {
        void login(String userName, String password);
    }
}
