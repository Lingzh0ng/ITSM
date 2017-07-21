package com.wearapay.lightning.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.wearapay.lightning.App;
import com.wearapay.lightning.R;
import com.wearapay.lightning.ui.fragment.ItemFragment;
import java.util.List;

/**
 * Created by lyz54 on 2017/6/28.
 */

public class FragmentItemAdapter extends FragmentPagerAdapter {
  List<ItemFragment> list;

  public FragmentItemAdapter(FragmentManager fm, List<ItemFragment> list) {
    super(fm);
    this.list = list;
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return ((Fragment) object).getView() == view;
  }

  @Override public Object instantiateItem(ViewGroup container, int position) {
    return super.instantiateItem(container, position);
  }

  @Override public void destroyItem(ViewGroup container, int position, Object object) {
    super.destroyItem(container, position, object);
  }

  @Override public Fragment getItem(int position) {
    return list.get(position);
  }

  @Override public int getCount() {
    return list.size();
  }

  @Override public CharSequence getPageTitle(int position) {
    if (position == 0) {
      return App.app.getString(R.string.item_tab_mine) + format(list.get(position).getItemCount());
    } else {
      return App.app.getString(R.string.item_tab_all) + format(list.get(position).getItemCount());
    }
  }

  private String format(int number) {
    return number == 0 ? "" : String.format("(%d)", number);
  }
}
