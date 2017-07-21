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

public class NoteRecyclerViewAdapter
    extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> {

  private final List<String> mValues;

  public NoteRecyclerViewAdapter(List<String> items) {
    mValues = items;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.bindView(position, mValues.get(position));
  }

  @Override public int getItemCount() {
    return mValues.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    @BindView(R.id.tvItemName) TextView tvItemName;
    @BindView(R.id.tvNote) TextView tvNote;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      mView = view;
    }

    public void bindView(int position, String string) {
      tvItemName.setText("name " + "111-11-1");
      tvNote.setText(string);
      tvItemName.setVisibility(View.GONE);
    }
  }
}
