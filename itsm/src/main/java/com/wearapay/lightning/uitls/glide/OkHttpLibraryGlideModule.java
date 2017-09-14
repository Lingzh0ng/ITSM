//package com.wearapay.lightning.uitls.glide;
//
//import android.content.Context;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.GlideBuilder;
//import com.bumptech.glide.integration.okhttp3.OkHttpGlideModule;
//import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
//import com.bumptech.glide.load.model.GlideUrl;
//import java.io.InputStream;
//
///**
// * Created by lyz on 2017/9/12.
// */
//public class OkHttpLibraryGlideModule extends OkHttpGlideModule {
//
//  @Override public void applyOptions(Context context, GlideBuilder builder) {
//
//  }
//
//  @Override public void registerComponents(Context context, Glide glide) {
//    glide.register(GlideUrl.class, InputStream.class,
//        new OkHttpUrlLoader.Factory(GlideUtils.getOkHttpClient()));
//  }
//}
