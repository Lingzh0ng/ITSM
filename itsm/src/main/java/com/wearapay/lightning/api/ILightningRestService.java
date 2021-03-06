package com.wearapay.lightning.api;

import com.wearapay.lightning.bean.BAppAutoDeploy;
import com.wearapay.lightning.bean.BChangeCount;
import com.wearapay.lightning.bean.BChartInfo;
import com.wearapay.lightning.bean.BCountIncidentByTime;
import com.wearapay.lightning.bean.BIncidentCount;
import com.wearapay.lightning.bean.BIncidentRemark;
import com.wearapay.lightning.bean.BIncidentTime;
import com.wearapay.lightning.bean.IncidentDto;
import io.reactivex.Observable;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by lyz on 2017/6/27.
 */
public interface ILightningRestService {

  @GET("day/history") Observable<ResponseBody> getTimeList();

  @GET("app/incident/{id}") Observable<IncidentDto> getEventDetails(@Path("id") String id);

  @GET("app/incident/activity/all") Observable<List<IncidentDto>> getWaitAllEvent();

  @GET("app/incident/activity/user") Observable<List<IncidentDto>> getWaitUserEvent();

  @GET("app/incident/ack/all") Observable<List<IncidentDto>> getActAllEvent();

  @GET("app/incident/ack/user") Observable<List<IncidentDto>> getActUserEvent();

  @GET("app/incident/resolved/all") Observable<List<IncidentDto>> getResolvedAllEvent();

  @GET("app/incident/resolved/user") Observable<List<IncidentDto>> getResolveUserEvent();

  @GET("app/incident/count") Observable<BIncidentCount> getEventCount();

  @PUT("app/incident/{id}/{status}") Observable<IncidentDto> updateEventStatus(
      @Path("id") String id, @Path("status") int status, @Body BIncidentRemark remark);

  @PUT("app/incident/getIncidentByTime") Observable<List<BCountIncidentByTime>> getIncidentByTime(
      @Body BIncidentTime bIncidentTime);

  @PUT("app/incident/{id}/{status}/{userId}") Observable<IncidentDto> eventCompile(
      @Path("id") String id, @Path("status") int status, @Path("userId") String userId,
      @Body BIncidentRemark remark);

  @POST("app/deploy/{env}/{changeNo}") Observable<String> appDeploy(@Path("env") String env,
      @Path("changeNo") String changeNo);

  @GET("app/deploy/status/{env}/{changeNo}") Observable<BAppAutoDeploy> getDeployStatus(
      @Path("env") String env, @Path("changeNo") String changeNo);

  @GET("app/deploy/{env}/all") Observable<List<BAppAutoDeploy>> getDeployAll(
      @Path("env") String env);

  @GET("app/deploy/{env}/user") Observable<List<BAppAutoDeploy>> getDeployUser(
      @Path("env") String env);

  @GET("app/deploy/{env}/count") Observable<BChangeCount> getDeployCount(@Path("env") String env);

  @GET("app/deploy/finish/{env}/{changeNo}") Observable<String> getDeployFinishStatus(
      @Path("env") String env, @Path("changeNo") String changeNo);

  @GET("app/incident/charts") Observable<List<BChartInfo>> getIncidentCharts();
}
