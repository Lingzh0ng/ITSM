package com.wearapay.lightning.uitls.glide;

import android.util.Log;
import com.wearapay.lightning.App;
import com.wearapay.lightning.BuildConfig;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by lyz on 2017/9/12.
 */

public class GlideUtils {

  private static final String KEY_STORE_TYPE_P12 = "PKCS12";
  private static final String TAG = "GlideUtils";

  public static class TrustAllCerts implements X509TrustManager {
    @Override public void checkClientTrusted(X509Certificate[] chain, String authType) {
    }

    @Override public void checkServerTrusted(X509Certificate[] chain, String authType) {
    }

    @Override public X509Certificate[] getAcceptedIssuers() {
      return new X509Certificate[0];
    }
  }

  private static SSLSocketFactory createSSLSocketFactory() {
    SSLSocketFactory ssfFactory = null;

    try {
      KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
      InputStream ksIn = App.app.getAssets().open("wearapay.p12");
      try {
        keyStore.load(ksIn, new char[] {});
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          ksIn.close();
        } catch (Exception ignore) {
          Log.e(TAG, ignore.getMessage(), ignore);
        }
      }
      KeyManagerFactory keyManagerFactory =
          KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
      keyManagerFactory.init(keyStore, new char[] {});

      SSLContext sc = SSLContext.getInstance("TLS");

      sc.init(keyManagerFactory.getKeyManagers(), new TrustManager[] { new TrustAllCerts() },
          new SecureRandom());

      //sc.init(null, trustManagerFactory.getTrustManagers(), null);

      ssfFactory = sc.getSocketFactory();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("---------------------------------");
    }

    return ssfFactory;
  }

  private static OkHttpClient okHttpClient;

  public static synchronized OkHttpClient getOkHttpClient() {
    if (okHttpClient == null) {
      OkHttpClient.Builder builder = new OkHttpClient.Builder();
      builder.connectTimeout(90, TimeUnit.SECONDS);
      builder.sslSocketFactory(createSSLSocketFactory());
      builder.hostnameVerifier(new HostnameVerifier() {
        @Override public boolean verify(String hostname, SSLSession session) {
          return true;
        }
      });
      if (BuildConfig.DEBUG) {
        // Log信息拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别

        //设置 Debug Log 模式
        builder.addInterceptor(loggingInterceptor);
      }
      okHttpClient = builder.build();
    }
    return okHttpClient;
  }
}
