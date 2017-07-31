package com.wearapay.lightning.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.wearapay.lightning.R;
import java.util.List;

public class ReleaseLogRecyclerViewAdapter
    extends RecyclerView.Adapter<ReleaseLogRecyclerViewAdapter.ViewHolder> {

  private final List<String> mValues;

  public ReleaseLogRecyclerViewAdapter(List<String> items) {
    mValues = items;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_item, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.tvLog.setText(mValues.get(position));
  }

  @Override public int getItemCount() {
    return mValues == null ? 0 : mValues.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    @BindView(R.id.tvLog) TextView tvLog;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      mView = view;
    }
  }
}
