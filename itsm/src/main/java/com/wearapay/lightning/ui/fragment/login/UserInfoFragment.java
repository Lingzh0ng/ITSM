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
import com.wearapay.lightning.base.BaseFragment;
import com.wearapay.lightning.net.ApiHelper;
import com.wearapay.lightning.net.BaseObserver;
import com.wearapay.lightning.net.model.PPResultBean;
import com.wearapay.lightning.uitls.RxBus;
import com.wearapay.lightning.uitls.event.UpdateEvent;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class UserInfoFragment extends BaseFragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  public static final String Title = "title";
  public static final String User_Name = "User_Name";
  @BindView(R.id.tvUserName) TextView tvUserName;
  @BindView(R.id.tvCompany) TextView tvCompany;
  @BindView(R.id.btn_logout) Button btnLogout;
  Unbinder unbinder;

  // TODO: Rename and change types of parameters
  private String title;
  private String userName;

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
    wrap(Observable.zip(ApiHelper.getInstance().logout(), ApiHelper.getInstance().ZSCLogout(),
        new BiFunction<PPResultBean, PPResultBean, Boolean>() {
          @Override public Boolean apply(@NonNull PPResultBean ppResultBean,
              @NonNull PPResultBean ppResultBean2) throws Exception {
            return true;
          }
        })).map(new Function<Boolean, Boolean>() {
      @Override public Boolean apply(@NonNull Boolean aBoolean) throws Exception {
        ApiHelper.getInstance().localLogout();
        return aBoolean;
      }
    }).subscribe(new BaseObserver<Boolean>(getActivity()) {
      @Override public void onNext(@NonNull Boolean aBoolean) {
        super.onNext(aBoolean);
        UpdateEvent updateEvent = new UpdateEvent(true, false);
        RxBus.getInstance().send(updateEvent);
        getActivity().finish();
      }
    });
  }
}
