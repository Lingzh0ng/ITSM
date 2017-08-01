package com.wearapay.lightning.api;

import com.wearapay.lightning.bean.BAppAutoDeploy;
import com.wearapay.lightning.bean.BChangeCount;
import com.wearapay.lightning.net.model.PPResultBean;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lyz on 2017/8/1.
 */
public interface IZSCReleaseRestService {

  @POST("app/deploy/{changeNo}") Observable<PPResultBean> appZSCDeploy(
      @Path("changeNo") String changeNo);

  @GET("app/deploy/status/{changeNo}") Observable<BAppAutoDeploy> getZSCDeployStatus(
      @Path("changeNo") String changeNo);

  @GET("app/deploy/finish/{changeNo}") Observable<String> getZSCDeployFinishStatus();

  @GET("app/deploy/all") Observable<List<BAppAutoDeploy>> getZSCDeployAll();

  @GET("app/deploy/user") Observable<List<BAppAutoDeploy>> getZSCDeployUser();

  @GET("app/deploy/count") Observable<BChangeCount> getZSCDeployCount();
}
