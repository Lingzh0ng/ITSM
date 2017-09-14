package com.wearapay.lightning.base.mvp;

import android.content.Context;
import com.wearapay.lightning.base.BaseActivity;
import com.wearapay.lightning.base.BaseFragment;

/**
 * Created by lyz54 on 2017/6/27.
 */

public interface IBaseView {
    void showMessage(String message);

    void showMessage(int messageId);

    void showDiglog(String message);

    void showProgress(String message);

    void showProgress(int messageResourceId);

    void showProgress();

    void hideProgress();

    void processFail(Throwable t, String errorMessage);

    void navToHomePage();

    BaseFragment getUseFragment();

    BaseActivity getUseActivity();

    Context getUseContext();
}
