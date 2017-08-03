package com.wearapay.lightning.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.wearapay.lightning.R;
import com.wearapay.lightning.bean.BAutoDeployInfo;
import java.util.List;

/**
 * Created by lyz on 2017/8/1.
 */
public class ReleaseAppItem2RecyclerViewAdapter
    extends RecyclerView.Adapter<ReleaseAppItem2RecyclerViewAdapter.ViewHolder> {
  private Context context;
  private List<BAutoDeployInfo> mValues;

  public ReleaseAppItem2RecyclerViewAdapter(Context context, List<BAutoDeployInfo> mValues) {
    this.context = context;
    this.mValues = mValues;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.release_app_item_2, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.tvAppName.setText(mValues.get(position).getScriptName());
    holder.tvAppVersion.setText(mValues.get(position).getDeployVersion());
  }

  @Override public int getItemCount() {
    return mValues == null ? 0 : mValues.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvAppName) TextView tvAppName;
    @BindView(R.id.tvAppVersion) TextView tvAppVersion;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
