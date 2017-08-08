package com.wearapay.lightning.api;

import com.wearapay.lightning.bean.BLoginUser;
import com.wearapay.lightning.bean.UserConfDto;
import com.wearapay.lightning.net.model.PPResultBean;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lyz on 2017/7/13.
 */

public interface IUserRestService {
  @POST("app/user/login") Observable<String> login(@Body BLoginUser loginUser);

  @GET("app/user/{id}/detail") Observable<UserConfDto> getUserInfo(@Path("id") String id);

  @GET("app/user/members") Observable<List<UserConfDto>> getAllUser();

  @POST("app/user/logout") Observable<PPResultBean> logout();
}
