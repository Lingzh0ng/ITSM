package com.wearapay.lightning.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.wearapay.lightning.R;
import com.wearapay.lightning.bean.BAppAutoDeploy;
import com.wearapay.lightning.bean.BAutoDeployInfo;
import com.wearapay.lightning.uitls.AppUtils;
import com.wearapay.lightning.uitls.FormatUtlis;
import java.util.List;

public class ReleaseItemRecyclerViewAdapter
    extends RecyclerView.Adapter<ReleaseItemRecyclerViewAdapter.ViewHolder> {

  private final List<BAppAutoDeploy> mValues;
  private final int positionType;
  private Context context;

  public void setOnHomeItemClickListener(OnReleaseItemClickListener onHomeItemClickListener) {
    this.onHomeItemClickListener = onHomeItemClickListener;
  }

  private OnReleaseItemClickListener onHomeItemClickListener;

  public ReleaseItemRecyclerViewAdapter(Context context, List<BAppAutoDeploy> items,
      int positionType, OnReleaseItemClickListener onHomeItemClickListener) {
    this.context = context;
    mValues = items;
    this.positionType = positionType;
    this.onHomeItemClickListener = onHomeItemClickListener;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.release_item_general, parent, false);
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
    @BindView(R.id.tvItemName) TextView tvItemName;
    @BindView(R.id.tvItemPeopleName) TextView tvItemPeopleName;
    @BindView(R.id.tvItemDegree) TextView tvItemDegree;
    @BindView(R.id.tvItemTime) TextView tvItemTime;
    @BindView(R.id.tvItemStartTime) TextView tvItemStartTime;
    @BindView(R.id.tvItemEndTime) TextView tvItemEndTime;
    @BindView(R.id.llContent) LinearLayout llContent;
    @BindView(R.id.tvItemPurpose) TextView tvItemPurpose;
    @BindView(R.id.viewLine) View viewLine;
    @BindView(R.id.bntRelease) Button bntRelease;
    @BindView(R.id.bntCheck) Button bntCheck;
    @BindView(R.id.llBtns) LinearLayout llBtns;
    @BindView(R.id.rvApp) RecyclerView rvApp;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      mView = view;
    }

    public void bindView(final int position, final BAppAutoDeploy mItem) {
      tvItemNumber.setText(mItem.getChangeNo());
      tvItemName.setText(mItem.getBusinessName());
      tvItemPeopleName.setText(mItem.getProposer());
      tvItemStartTime.setText(FormatUtlis.getStringFormatTime(mItem.getStartTime(), "MM/dd HH:mm"));
      tvItemEndTime.setText(FormatUtlis.getStringFormatTime(mItem.getEndTime(), "MM/dd HH:mm"));
      //tvItemAppName.setText(mItem.getDisplayInfo().get(0).getScriptName());
      //tvItemVersion.setText(mItem.getDisplayInfo().get(0).getDeployVersion());
      //tvItemIp.setText(mItem.getDisplayInfo().get(0).getIp());
      tvItemPurpose.setText(mItem.getPurpose());

      List<BAutoDeployInfo> displayInfo = mItem.getDisplayInfo();

      ViewGroup.LayoutParams layoutParams = rvApp.getLayoutParams();
      layoutParams.height = displayInfo.size() * AppUtils.dip2px(context, 28f);
      rvApp.setLayoutParams(layoutParams);
      rvApp.setLayoutManager(new LinearLayoutManager(context));
      rvApp.setAdapter(new ReleaseAppItem1RecyclerViewAdapter(context, displayInfo));

      switch (positionType) {
        case 0:
          llBtns.setVisibility(View.VISIBLE);
          viewLine.setVisibility(View.VISIBLE);
          break;
        case 1:
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

      bntRelease.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (onHomeItemClickListener != null) {
            onHomeItemClickListener.onReleaseButtonClick(position, mItem);
          }
        }
      });

      bntCheck.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (onHomeItemClickListener != null) {
            onHomeItemClickListener.onCheckButtonClick(position, mItem);
          }
        }
      });
    }

    @Override public String toString() {
      return super.toString();
    }
  }

  public interface OnReleaseItemClickListener {
    void onItemClick(int position, BAppAutoDeploy item);

    void onReleaseButtonClick(int position, BAppAutoDeploy item);

    void onCheckButtonClick(int position, BAppAutoDeploy item);
  }
}
