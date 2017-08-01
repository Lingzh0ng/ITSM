package com.wearapay.lightning.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.wearapay.lightning.R;
import java.util.List;

/**
 * Created by lyz on 2017/8/1.
 */
public class ReleaseStatusRecyclerViewAdapter
    extends RecyclerView.Adapter<ReleaseStatusRecyclerViewAdapter.ViewHolder> {
  private Context context;
  private List<String> mValues;

  public ReleaseStatusRecyclerViewAdapter(Context context, List<String> mValues) {
    this.context = context;
    this.mValues = mValues;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.release_status_item, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    if (position == 0) {
      holder.ivStatus.setVisibility(View.GONE);
      holder.tvTitle.setVisibility(View.VISIBLE);
      holder.tvTitle.setText("状态");
      holder.tvContent.setText("发布内容");
      holder.tvContent.setGravity(Gravity.CENTER);
      //LinearLayout.LayoutParams layoutParams =
      //    (LinearLayout.LayoutParams) holder.tvContent.getLayoutParams();
      //layoutParams.setMarginStart(0);
      //holder.tvContent.setLayoutParams(layoutParams);
    } else {
      holder.ivStatus.setVisibility(View.VISIBLE);
      holder.tvTitle.setVisibility(View.GONE);
      holder.tvContent.setText(mValues.get(position - 1));
      holder.tvContent.setGravity(Gravity.CENTER_VERTICAL);
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
}
