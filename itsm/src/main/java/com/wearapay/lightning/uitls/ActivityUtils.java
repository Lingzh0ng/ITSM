package com.wearapay.lightning.uitls;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.wearapay.lightning.CommonActivity;
import com.wearapay.lightning.LConsts;
import com.wearapay.lightning.R;
import com.wearapay.lightning.base.BaseFragment;

public class ActivityUtils {

  public static void addFragment(@NonNull FragmentManager fragmentManager,
      @NonNull Fragment sourceFragment, @NonNull Fragment destFragment, int frameId) {
    checkNotNull(fragmentManager);
    checkNotNull(sourceFragment);
    checkNotNull(destFragment);
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.hide(sourceFragment);
    transaction.add(frameId, destFragment);
    transaction.addToBackStack(null);
    transaction.commit();
  }

  public static void addFragment(@NonNull FragmentManager fragmentManager,
      @NonNull Fragment sourceFragment, @NonNull LConsts.FragmentType fragmentType) {
    addFragment(fragmentManager, sourceFragment, fragmentType, false);
  }

  public static void addFragment(@NonNull FragmentManager fragmentManager,
      @NonNull Fragment sourceFragment, @NonNull LConsts.FragmentType fragmentType,
      boolean removeSourceFragment) {
    addFragment(fragmentManager, sourceFragment, fragmentType, removeSourceFragment, null);
  }

  public static void addFragment(@NonNull FragmentManager fragmentManager,
      @NonNull Fragment sourceFragment, @NonNull LConsts.FragmentType fragmentType,
      boolean removeSourceFragment, Bundle args) {
    addFragment(fragmentManager, sourceFragment, fragmentType, R.id.contentFrame,
        removeSourceFragment, args);
  }

  /**
   * pass argument.
   */
  public static void addFragment(@NonNull FragmentManager fragmentManager,
      @NonNull Fragment sourceFragment, @NonNull LConsts.FragmentType fragmentType, Bundle args) {
    addFragment(fragmentManager, sourceFragment, fragmentType, R.id.contentFrame, false, args);
  }

  public static void addFragment(@NonNull FragmentManager fragmentManager,
      @NonNull Fragment sourceFragment, @NonNull LConsts.FragmentType fragmentType, int frameId,
      boolean removeSourceFragment, Bundle args) {
    checkNotNull(fragmentManager);
    checkNotNull(sourceFragment);
    checkNotNull(fragmentType);
    if (removeSourceFragment) {
      fragmentManager.popBackStack();
    }
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    if (!removeSourceFragment) {
      transaction.hide(sourceFragment);
    }
    Class<BaseFragment> fragmentClass = fragmentType.getClazz();
    try {
      Fragment fragment = fragmentClass.newInstance();
      if (args != null) {
        fragment.setArguments(args);
      }
      transaction.add(frameId, fragment);
      transaction.addToBackStack(null);
      transaction.commit();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  public static void replaceFragment(@NonNull FragmentManager fragmentManager,
      @NonNull LConsts.FragmentType fragmentType, int frameId, Bundle args) {
    checkNotNull(fragmentManager);
    checkNotNull(fragmentType);
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    Class<BaseFragment> fragmentClass = fragmentType.getClazz();
    try {
      Fragment fragment = fragmentClass.newInstance();
      if (args != null) {
        fragment.setArguments(args);
      }
      transaction.replace(frameId, fragment);
      transaction.commit();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  public static void replaceFragment(@NonNull FragmentManager fragmentManager,
      @NonNull LConsts.FragmentType fragmentType, Bundle bundle) {
    replaceFragment(fragmentManager, fragmentType, R.id.contentFrame, bundle);
  }

  public static void replaceFragment(@NonNull FragmentManager fragmentManager,
      @NonNull Fragment fragment, int frameId) {
    checkNotNull(fragmentManager);
    checkNotNull(fragment);
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.replace(frameId, fragment);
    transaction.commit();
  }

  public static void startFragment(@NonNull Context context,
      @NonNull LConsts.FragmentType fragmentType, @Nullable Bundle bundle) {
    startFragment(context, fragmentType, bundle, 0);
  }

  public static void startFragment(@NonNull Context context,
      @NonNull LConsts.FragmentType fragmentType, @Nullable Bundle bundle, int flags) {
    checkNotNull(context);
    checkNotNull(fragmentType);

    Intent intent = new Intent(context, CommonActivity.class);
    if (flags != 0) {
      intent.addFlags(flags);
    }
    intent.putExtra(LConsts.FRAGMENT_TYPE, fragmentType);
    if (bundle != null) {
      intent.putExtras(bundle);
    }
    context.startActivity(intent);
  }

  public static <T> T checkNotNull(T reference) {
    if (reference == null) {
      throw new NullPointerException();
    }
    return reference;
  }

}
