package com.wearapay.lightning.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.wearapay.lightning.R;
import com.wearapay.lightning.adapter.FragmentItemAdapter;
import com.wearapay.lightning.bean.DealStatus;
import java.util.ArrayList;
import java.util.List;

public class BlankFragment extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_TITLE = "title";
  private static final String ARG_STATUS = "status";
  @BindView(R.id.vp) ViewPager vp;
  @BindView(R.id.tabs) TabLayout tabs;
  Unbinder unbinder;

  // TODO: Rename and change types of parameters
  private String title;
  private DealStatus status;

  private OnFragmentInteractionListener mListener;
  private FragmentItemAdapter fragmentItemAdapter;
  private List<ItemFragment> list;
  private int itemMyCount = 0;
  private int itemAllCount = 0;

  public int getItemMyCount() {
    return itemMyCount;
  }

  public void setItemMyCount(int itemMyCount) {
    this.itemMyCount = itemMyCount;
  }

  public int getItemAllCount() {
    return itemAllCount;
  }

  public void setItemAllCount(int itemAllCount) {
    this.itemAllCount = itemAllCount;
  }

  public BlankFragment() {
    // Required empty public constructor
  }

  public static BlankFragment newInstance(String title, DealStatus status, int itemMyCount,
      int itemAllCount) {
    BlankFragment fragment = new BlankFragment();
    Bundle args = new Bundle();
    fragment.setItemMyCount(itemMyCount);
    fragment.setItemAllCount(itemAllCount);
    args.putString(ARG_TITLE, title);
    args.putSerializable(ARG_STATUS, status);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      title = getArguments().getString(ARG_TITLE);
      status = (DealStatus) getArguments().getSerializable(ARG_STATUS);
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_blank, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //tv.setText(mParam1);
    list = new ArrayList<>();
    list.add(ItemFragment.newInstance(1, 0, itemMyCount, status));
    list.add(ItemFragment.newInstance(1, 1, itemAllCount, status));
    fragmentItemAdapter = new FragmentItemAdapter(getChildFragmentManager(), list);
    vp.setAdapter(fragmentItemAdapter);
    System.out.println("B onViewCreated");
    tabs.setupWithViewPager(vp);
  }

  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    System.out.println("B onAttach");
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(
          context.toString() + " must implement OnFragmentInteractionListener");
    }
  }

  @Override public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    System.out.println("B onDestroyView");
    vp.removeAllViews();
    FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
    fragmentTransaction.detach(list.get(0));
    fragmentTransaction.detach(list.get(1));
    fragmentTransaction.commitAllowingStateLoss();
    //unbinder.unbind();
  }

  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    void onFragmentInteraction(Uri uri);
  }
}
