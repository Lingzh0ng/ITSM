package com.wearapay.lightning.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.wearapay.lightning.R;
import com.wearapay.lightning.bean.BChartInfo;
import com.wearapay.lightning.ui.fragment.point.PointFragment;
import java.util.List;

public class PointItemAdapter extends RecyclerView.Adapter<PointItemAdapter.ViewHolder> {

  private final List<BChartInfo> mValues;
  private OnItemOnClickListener onItemOnClickListener;
  private PointFragment fragment;

  public void setOnItemOnClickListener(OnItemOnClickListener onItemOnClickListener) {
    this.onItemOnClickListener = onItemOnClickListener;
  }

  public interface OnItemOnClickListener {
    void onItemClick(int position, BChartInfo countIncident);
  }

  public PointItemAdapter(PointFragment fragment, List<BChartInfo> items) {
    this.fragment = fragment;
    mValues = items;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.point_item, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.bindView(mValues.get(position));
  }

  @Override public int getItemCount() {
    return mValues == null ? 0 : mValues.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.imageView) ImageView imageView;

    public ViewHolder(View view) {
      super(view);
      mView = view;
      ButterKnife.bind(this, view);
    }

    public void bindView(BChartInfo mItem) {
      tvTitle.setText(mItem.getAppDisplay());
      Glide.with(fragment)
          .load(mItem.getUrl())
          .asBitmap()
          .thumbnail(0.1f)
          //.placeholder(R.drawable.loading)
          .error(R.drawable.error)
          .dontAnimate()
          .into(imageView);
      mView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (onItemOnClickListener != null) {
            onItemOnClickListener.onItemClick(getLayoutPosition(), mItem);
          }
        }
      });
    }

    @Override public String toString() {
      return super.toString() + "'";
    }
  }
}
