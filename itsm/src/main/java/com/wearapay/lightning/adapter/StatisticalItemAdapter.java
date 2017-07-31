package com.wearapay.lightning.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.wearapay.lightning.App;
import com.wearapay.lightning.R;
import com.wearapay.lightning.bean.BCountIncident;
import java.util.List;

public class StatisticalItemAdapter
    extends RecyclerView.Adapter<StatisticalItemAdapter.ViewHolder> {

  private final List<BCountIncident> mValues;
  private OnItemOnClickListener onItemOnClickListener;

  public void setOnItemOnClickListener(OnItemOnClickListener onItemOnClickListener) {
    this.onItemOnClickListener = onItemOnClickListener;
  }

  public interface OnItemOnClickListener {
    void onItemClick(int position, BCountIncident countIncident);
  }

  public StatisticalItemAdapter(List<BCountIncident> items) {
    mValues = items;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.fragment_statistical_item, parent, false);
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
    @BindView(R.id.tvItemName) TextView tvItemName;
    @BindView(R.id.tvItemCount) TextView tvItemCount;
    @BindView(R.id.tvItemContent) TextView tvItemContent;
    @BindView(R.id.tvContentIP) TextView tvContentIP;
    @BindView(R.id.tvContentServer) TextView tvContentServer;
    @BindView(R.id.tvItemTime) TextView tvItemTime;
    @BindView(R.id.tvItemTimeNear) TextView tvItemTimeNear;

    public ViewHolder(View view) {
      super(view);
      mView = view;
      ButterKnife.bind(this, view);
    }

    public void bindView(BCountIncident mItem) {
      tvItemName.setText(mItem.getAlarmContent());
      //FormatUtlis.setText(tvItemCount, mItem.getIncidentCount());
      tvItemCount.setText(
          String.format(App.app.getString(R.string.item_alarm_count), mItem.getIncidentCount()));
      tvItemTimeNear.setText(
          String.format(App.app.getString(R.string.item_near_last_day), mItem.getLastDay()));
      tvItemContent.setText(mItem.getBusinessName());
      tvContentIP.setText(mItem.getEntityIp());
      tvContentServer.setText(mItem.getEntityName());
      String format = String.format(App.app.getString(R.string.item_long_average_time_hint),
          mItem.getLongestTime(), mItem.getAverageTime());
      tvItemTime.setText(format);
      mView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
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
