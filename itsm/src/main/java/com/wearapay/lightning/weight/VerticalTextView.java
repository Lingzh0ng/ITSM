package com.wearapay.lightning.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.wearapay.lightning.R;

/**
 * Created by lyz on 2017/7/31.
 */
public class VerticalTextView extends LinearLayout {

  public VerticalTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setOrientation(VERTICAL);
    this.context = context;
    initAttrs(attrs);
  }

  private void initAttrs(AttributeSet attrs) {
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerticalTextView);
    text = (String) typedArray.getText(R.styleable.VerticalTextView_vertical_text);
    size = typedArray.getInt(R.styleable.VerticalTextView_vertical_text_size, 16);
    color = typedArray.getColor(R.styleable.VerticalTextView_vertical_text_color, 0x000000);
    typedArray.recycle();
    addText();
  }

  private String text;
  private Context context;
  private int color;
  private int size;

  public VerticalTextView(Context context) {
    this(context, null);
  }

  public void setText(String text) {
    this.text = text;
    addText();
  }

  private void addText() {
    removeAllViews();
    if (text != null) {
      char[] chara = text.toCharArray();
      for (int i = 0; i < chara.length; i++) {
        System.out.println("------" + text);
        TextView oneText = new TextView(context);
        oneText.setTextColor(color);

        oneText.setText(text.substring(i, i + 1));
        if (size > 0) {
          oneText.setTextSize(size);
        }
        addView(oneText);
      }
    }
  }

  public void setTextColor(int color) {
    this.color = color;
  }

  public void setTextSize(int size) {
    this.size = size;
  }
}
