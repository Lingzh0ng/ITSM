package com.wearapay.lightning.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.wearapay.lightning.App;
import com.wearapay.lightning.R;
import com.wearapay.lightning.bean.DealStatus;
import com.wearapay.lightning.bean.IncidentDto;
import com.wearapay.lightning.uitls.FormatUtlis;
import java.util.List;

public class MyItemRecyclerViewAdapter
    extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

  private final List<IncidentDto> mValues;
  private final DealStatus status;

  public void setOnHomeItemClickListener(OnHomeItemClickListener onHomeItemClickListener) {
    this.onHomeItemClickListener = onHomeItemClickListener;
  }

  private OnHomeItemClickListener onHomeItemClickListener;

  public MyItemRecyclerViewAdapter(List<IncidentDto> items, DealStatus status,
      OnHomeItemClickListener onHomeItemClickListener) {
    mValues = items;
    this.status = status;
    this.onHomeItemClickListener = onHomeItemClickListener;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.deal_item_general, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.bindView(position, mValues.get(position));
  }

  @Override public int getItemCount() {
    return mValues == null ? 0 : mValues.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public final View mView;

    @BindView(R.id.blank) View blank;
    @BindView(R.id.tvItemNumber) TextView tvItemNumber;
    @BindView(R.id.tvItemId) TextView tvItemId;
    @BindView(R.id.tvItemTitle) TextView tvItemTitle;
    @BindView(R.id.tvItemTime) TextView tvItemTime;
    @BindView(R.id.tvItemEvent) TextView tvItemEvent;
    @BindView(R.id.tvItemType) TextView tvItemType;
    @BindView(R.id.llContent) LinearLayout llContent;
    @BindView(R.id.tvItemServer) TextView tvItemServer;
    @BindView(R.id.tvItemLook) TextView tvItemLook;
    @BindView(R.id.tvItemMemory) TextView tvItemMemory;
    @BindView(R.id.llMemory) LinearLayout llMemory;
    @BindView(R.id.tvItemMan) TextView tvItemMan;
    @BindView(R.id.viewLine) View viewLine;
    @BindView(R.id.tvItemCount) TextView tvItemCount;
    @BindView(R.id.bntYes) Button bntYes;
    @BindView(R.id.bntClose) Button bntClose;
    @BindView(R.id.llBtns) LinearLayout llBtns;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      mView = view;
    }

    public void bindView(final int position, final IncidentDto mItem) {

      tvItemNumber.setText(mItem.getIncidentNo());
      tvItemId.setText(mItem.getAlarmId());
      tvItemTitle.setText(mItem.getAlarmType());
      tvItemTime.setText(
          FormatUtlis.chargeStringFormatTime(mItem.getAlarmCreationTime(), "yyyy-MM-dd hh:mm:ss",
              "MM/dd HH:mm"));
      tvItemEvent.setText(
          String.format(App.app.getString(R.string.item_alarm_level_time), mItem.getLevel()));
      tvItemType.setText(mItem.getProcessingType());
      tvItemServer.setText(
          mItem.getBusinessName() + "  " + mItem.getEntityIp() + "  " + mItem.getEntityName());
      tvItemLook.setText(mItem.getAlarmContent());
      tvItemMan.setText(String.format((App.app.getString(R.string.item_alarm_handle_name)),
          mItem.getHandlingName()));
      tvItemCount.setText(
          String.format((App.app.getString(R.string.item_alarm_count)), mItem.getIncidentCount()));

      if (TextUtils.isEmpty(mItem.getAlarmValue())) {
        tvItemMemory.setVisibility(View.GONE);
      } else {
        tvItemMemory.setText(mItem.getAlarmDuration());
      }
      switch (status) {
        case DEAL_WAIT:
          break;
        case DEAL_DOING:
          bntYes.setVisibility(View.GONE);
          break;
        case DEAL_COMPLETE:
          llBtns.setVisibility(View.GONE);
          viewLine.setVisibility(View.GONE);
          break;
        default:
          break;
      }

      mView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (onHomeItemClickListener != null) {
            onHomeItemClickListener.onItemClick(position, mItem);
          }
        }
      });

      bntYes.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (onHomeItemClickListener != null) {
            onHomeItemClickListener.onYesButtonClick(position, mItem);
          }
        }
      });

      bntClose.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (onHomeItemClickListener != null) {
            onHomeItemClickListener.onCloseButtonClick(position, mItem);
          }
        }
      });
    }

    @Override public String toString() {
      return super.toString();
    }
  }

  public interface OnHomeItemClickListener {
    void onItemClick(int position, IncidentDto item);

    void onYesButtonClick(int position, IncidentDto item);

    void onCloseButtonClick(int position, IncidentDto item);
  }
}
