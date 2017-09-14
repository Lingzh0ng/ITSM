//package com.wearapay.lightning.uitls.glide;
//
//import android.content.Context;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.Registry;
//import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
//import com.bumptech.glide.load.model.GlideUrl;
//import com.bumptech.glide.module.AppGlideModule;
//import java.io.InputStream;
//
///**
// * Created by lyz on 2017/9/12.
// */
//@GlideModule public class CustomGlideModule extends AppGlideModule {
//  @Override public boolean isManifestParsingEnabled() {
//    return true;
//  }
//
//  public void registerComponents(Context context, Glide glide, Registry registry) {
//    registry.replace(GlideUrl.class, InputStream.class,
//        new OkHttpUrlLoader.Factory(GlideUtils.getOkHttpClient()));
//  }
//}
//https://zabbix.wearapay.com/chart2.php?graphid=1681&period=86400&width=1950&height=1080