package com.wearapay.lightning.ui.fragment.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.wearapay.lightning.R;
import com.wearapay.lightning.base.BaseMvpFragment;
import com.wearapay.lightning.base.mvp.BasePresenter;
import com.wearapay.lightning.ui.fragment.login.presenter.UserLogoutPresenter;
import com.wearapay.lightning.ui.fragment.login.view.IUserInfoView;
import com.wearapay.lightning.uitls.RxBus;
import com.wearapay.lightning.uitls.event.UpdateEvent;

public class UserInfoFragment extends BaseMvpFragment implements IUserInfoView {
  public static final String Title = "title";
  public static final String User_Name = "User_Name";
  @BindView(R.id.tvUserName) TextView tvUserName;
  @BindView(R.id.tvCompany) TextView tvCompany;
  @BindView(R.id.btn_logout) Button btnLogout;
  Unbinder unbinder;

  private String title;
  private String userName;
  private UserLogoutPresenter userLogoutPresenter;

  public UserInfoFragment() {
    // Required empty public constructor
  }

  public static UserInfoFragment newInstance(String param1, String param2) {
    UserInfoFragment fragment = new UserInfoFragment();
    Bundle args = new Bundle();
    args.putString(Title, param1);
    args.putString(User_Name, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      title = getArguments().getString(Title);
      userName = getArguments().getString(User_Name);
    }
  }

  @Override protected BasePresenter[] initPresenters() {
    userLogoutPresenter = new UserLogoutPresenter(getActivity());
    return new BasePresenter[] { userLogoutPresenter };
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View inflate = inflater.inflate(R.layout.fragment_user_info, container, false);
    unbinder = ButterKnife.bind(this, inflate);
    return inflate;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView();
  }

  private void initView() {
    tvUserName.setText(userName);
  }

  @Override public void onDetach() {
    super.onDetach();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick(R.id.btn_logout) public void onViewClicked() {
    userLogoutPresenter.logout();
  }

  @Override public void logoutSuccess() {
    UpdateEvent updateEvent = new UpdateEvent(true, false);
    RxBus.getInstance().send(updateEvent);
    getActivity().finish();
  }

  @Override public void logoutFail() {

  }
}
