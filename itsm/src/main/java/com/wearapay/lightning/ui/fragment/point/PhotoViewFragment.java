package com.wearapay.lightning.ui.fragment.point;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wearapay.lightning.R;
import com.wearapay.lightning.base.BaseMvpFragment;
import com.wearapay.lightning.base.mvp.BasePresenter;
import com.wearapay.lightning.bean.BChartInfo;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by lyz on 2017/9/13.
 */

public class PhotoViewFragment extends BaseMvpFragment {

  @BindView(R.id.photoView) PhotoView photoView;
  private Unbinder unbinder;
  private BChartInfo bChartInfo;

  @Override protected BasePresenter[] initPresenters() {
    return new BasePresenter[0];
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    bChartInfo = (BChartInfo) getArguments().getSerializable("BChartInfo");
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View inflate = inflater.inflate(R.layout.fragment_photo, container, false);
    unbinder = ButterKnife.bind(this, inflate);
    return inflate;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Glide.with(this)
        .load(bChartInfo.getUrl())
        .asBitmap()
        .placeholder(R.drawable.loading)
        .error(R.drawable.error)
        .into(new SimpleTarget<Bitmap>() {
          @Override public void onResourceReady(Bitmap bitmap,
              GlideAnimation<? super Bitmap> glideAnimation) {
            photoView.setImageBitmap(bitmap);
          }
        });
    photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

      @Override public void onPhotoTap(View view, float x, float y) {
        getActivity().finish();
      }
    });
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}
