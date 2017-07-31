package com.wearapay.lightning.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.wearapay.lightning.BuildConfig;
import com.wearapay.lightning.LConsts;
import com.wearapay.lightning.R;
import com.wearapay.lightning.api.ILightningRestService;
import com.wearapay.lightning.api.IUserRestService;
import com.wearapay.lightning.bean.BCountIncidentByTime;
import com.wearapay.lightning.bean.BIncidentCount;
import com.wearapay.lightning.bean.BIncidentRemark;
import com.wearapay.lightning.bean.BIncidentTime;
import com.wearapay.lightning.bean.BLoginUser;
import com.wearapay.lightning.bean.IncidentDto;
import com.wearapay.lightning.bean.UserConfDto;
import com.wearapay.lightning.exception.NotLoginException;
import com.wearapay.lightning.net.converter.PPRestConverterFactory;
import io.reactivex.Observable;
import java.io.File;
import java.io.IOException;
import java.util.List;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by lyz on 2017/7/12.
 */

public class ApiHelper implements ILightningRestService, IUserRestService, ILocalHelper {

  private Context appContext;

  private ILightningRestService lightningRestService;
  private IUserRestService userRestService;

  private SharedPreferences sharedPreferences;
  public static String GANKIO_HSOT = "http://gank.io/api/";

  private boolean isLogining = false;

  private ApiHelper() {
  }

  public static ApiHelper getInstance() {
    return Holder.instance;
  }

  private static class Holder {
    static ApiHelper instance = new ApiHelper();
  }

  public void init(Context appContext) {
    this.appContext = appContext;
    this.lightningRestService = getAndInitEventRetrofit(getOkHttpClient(appContext));
    this.userRestService = getAndInitUserRetrofit(getOkHttpClient(appContext));
    this.sharedPreferences = appContext.getSharedPreferences("Light_SP", Context.MODE_PRIVATE);
  }

  private OkHttpClient getOkHttpClient(Context context) {
    OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
    okHttpBuilder.cache(new Cache(new File(context.getCacheDir().getAbsolutePath()), 100));
    okHttpBuilder.addInterceptor(new Interceptor() {
      @Override public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        //登录判断
        if (!loginStatus()) {
          if (!isLogining) throw new NotLoginException(context.getString(R.string.error_no_login));
        }
        if (!TextUtils.isEmpty(getUserId())) {
          builder.addHeader(LConsts.USER_TOKEN, getUserId());
        }
        Request build = builder.build();
        //
        return chain.proceed(build);
      }
    });
    if (BuildConfig.DEBUG) {
      // Log信息拦截器
      HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别

      //设置 Debug Log 模式
      okHttpBuilder.addInterceptor(loggingInterceptor);
    }
    return okHttpBuilder.build();
  }

  private ILightningRestService getAndInitEventRetrofit(OkHttpClient okHttpClient) {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(LConsts.ITSM_HSOT)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(PPRestConverterFactory.create(new Gson()))
        .build();
    return retrofit.create(ILightningRestService.class);
  }

  private IUserRestService getAndInitUserRetrofit(OkHttpClient okHttpClient) {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(LConsts.ITSM_HSOT)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(PPRestConverterFactory.create(new Gson()))
        .build();
    return retrofit.create(IUserRestService.class);
  }

  //-----------------------------------------事件网络接口---------------------------------------------
  @Override public Observable<ResponseBody> getTimeList() {
    return lightningRestService.getTimeList();
  }

  @Override public Observable<IncidentDto> getEventDetails(String id) {
    return lightningRestService.getEventDetails(id);
  }

  @Override public Observable<List<IncidentDto>> getWaitAllEvent() {
    return lightningRestService.getWaitAllEvent();
  }

  @Override public Observable<List<IncidentDto>> getWaitUserEvent() {
    return lightningRestService.getWaitUserEvent();
  }

  @Override public Observable<List<IncidentDto>> getActAllEvent() {
    return lightningRestService.getActAllEvent();
  }

  @Override public Observable<List<IncidentDto>> getActUserEvent() {
    return lightningRestService.getActUserEvent();
  }

  @Override public Observable<List<IncidentDto>> getResolvedAllEvent() {
    return lightningRestService.getResolvedAllEvent();
  }

  @Override public Observable<List<IncidentDto>> getResolveUserEvent() {
    return lightningRestService.getResolveUserEvent();
  }

  @Override public Observable<BIncidentCount> getEventCount() {
    return lightningRestService.getEventCount();
  }

  @Override
  public Observable<IncidentDto> updateEventStatus(String id, int status, BIncidentRemark remark) {
    return lightningRestService.updateEventStatus(id, status, remark);
  }

  @Override
  public Observable<List<BCountIncidentByTime>> getIncidentByTime(BIncidentTime bIncidentTime) {
    return lightningRestService.getIncidentByTime(bIncidentTime);
  }

  @Override public Observable<IncidentDto> eventCompile(String id, int status, String userId,
      BIncidentRemark remark) {
    return lightningRestService.eventCompile(id, status, userId, remark);
  }

  //-----------------------------------------用户网络接口---------------------------------------------

  @Override public Observable<String> login(BLoginUser loginUser) {
    isLogining = true;
    return userRestService.login(loginUser);
  }

  @Override public Observable<UserConfDto> getUserInfo(String id) {
    return userRestService.getUserInfo(id);
  }

  @Override public Observable<List<UserConfDto>> getAllUser() {
    return userRestService.getAllUser();
  }

  @Override public Observable<ResponseBody> logout() {
    return userRestService.logout();
  }

  //-----------------------------------------本地接口---------------------------------------------

  @Override public String getUserId() {
    return sharedPreferences.getString(LConsts.USER_TOKEN, "");
  }

  @Override public void storeUserId(String userId) {
    SharedPreferences.Editor edit = sharedPreferences.edit();
    edit.putString(LConsts.USER_TOKEN, userId);
    edit.apply();
  }

  @Override public boolean loginStatus() {
    return !TextUtils.isEmpty(sharedPreferences.getString(LConsts.USER_TOKEN, ""));
  }

  @Override public String getEmail() {
    return sharedPreferences.getString("email", null);
  }

  @Override public void storeEmail(String email) {
    SharedPreferences.Editor edit = sharedPreferences.edit();
    edit.putString("email", email);
    edit.apply();
  }

  public void setLogining(boolean isLogining) {
    this.isLogining = isLogining;
  }
}
