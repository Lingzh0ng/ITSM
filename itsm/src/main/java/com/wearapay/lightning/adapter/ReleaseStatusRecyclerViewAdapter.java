package com.wearapay.lightning.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.wearapay.lightning.R;
import com.wearapay.lightning.bean.BAutoDeploy;
import java.util.List;

/**
 * Created by lyz on 2017/8/1.
 */
public class ReleaseStatusRecyclerViewAdapter
    extends RecyclerView.Adapter<ReleaseStatusRecyclerViewAdapter.ViewHolder> {
  private Context context;
  private List<BAutoDeploy> mValues;

  public ReleaseStatusRecyclerViewAdapter(Context context, List<BAutoDeploy> mValues) {
    this.context = context;
    this.mValues = mValues;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.release_status_item, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.tvContent.setTextColor(Color.BLACK);
    stopLoadingAnimation(holder.ivStatus);
    if (position == 0) {
      holder.ivStatus.setVisibility(View.GONE);
      holder.tvTitle.setVisibility(View.VISIBLE);
      holder.tvTitle.setText("Status");
      holder.tvContent.setText("Deploy Steps");
      holder.tvContent.setGravity(Gravity.CENTER);
      //LinearLayout.LayoutParams layoutParams =
      //    (LinearLayout.LayoutParams) holder.tvContent.getLayoutParams();
      //layoutParams.setMarginStart(0);
      //holder.tvContent.setLayoutParams(layoutParams);
    } else {
      BAutoDeploy bAutoDeploy = mValues.get(position - 1);
      holder.ivStatus.setVisibility(View.VISIBLE);
      holder.tvTitle.setVisibility(View.GONE);
      holder.tvContent.setText(bAutoDeploy.getScriptName());
      holder.tvContent.setGravity(Gravity.CENTER_VERTICAL);

      switch (bAutoDeploy.getStatus()) {
        case "Waiting":
          holder.ivStatus.setImageResource(R.drawable.wait_do);
          break;
        case "Process":
          holder.ivStatus.setImageResource(R.drawable.doing);
          holder.tvContent.setTextColor(Color.YELLOW);
          startLoadingAnimation(holder.ivStatus);
          break;
        case "Success":
          holder.ivStatus.setImageResource(R.drawable.done);
          holder.tvContent.setTextColor(Color.GREEN);
          break;
        case "Failed":
          holder.ivStatus.setImageResource(R.drawable.setting);
          holder.tvContent.setTextColor(Color.RED);
          break;
      }

      //LinearLayout.LayoutParams layoutParams =
      //    (LinearLayout.LayoutParams) holder.tvContent.getLayoutParams();
      //layoutParams.setMarginStart(AppUtils.dip2px(context, 20f));
      //holder.tvContent.setLayoutParams(layoutParams);
    }
  }

  @Override public int getItemCount() {
    return mValues == null ? 1 : mValues.size() + 1;
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvContent) TextView tvContent;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.ivStatus) ImageView ivStatus;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  private void startLoadingAnimation(ImageView imageView) {

    RotateAnimation loadingAnimation =
        new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f);
    loadingAnimation.setDuration(1000);
    loadingAnimation.setRepeatCount(-1);
    loadingAnimation.setInterpolator(new LinearInterpolator());
    imageView.setAnimation(loadingAnimation);
    loadingAnimation.start();
  }

  private void stopLoadingAnimation(ImageView imageView) {
    Animation animation = imageView.getAnimation();
    if (animation != null && animation.hasStarted()) {
      animation.setAnimationListener(new Animation.AnimationListener() {
        @Override public void onAnimationStart(Animation animation) {

        }

        @Override public void onAnimationEnd(Animation animation) {
          imageView.clearAnimation();
        }

        @Override public void onAnimationRepeat(Animation animation) {

        }
      });
      animation.cancel();
    } else {
      imageView.clearAnimation();
    }
  }
}
