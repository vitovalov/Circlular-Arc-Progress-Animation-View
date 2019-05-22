package com.github.lzyzsd.circleprogress;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * Created by vito on 12/09/2017
 */

public class UiUtils {
  public static void showKeyboard(View view) {
    if (view == null) {
      return;
    }
    InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
  }

  public static void hideKeyboard(final View view) {
    if (view == null) {
      return;
    }
    InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }


  public static void forceHideKeyboard(final View view) {
    if (view == null) {
      return;
    }
    InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
  }

  public static void forceShowKeyboard(View view) {
    if (view == null) {
      return;
    }
    InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
  }


  public static void underlineTextView(int stringId, TextView tv) {
    Context context = tv.getContext();
    if (context != null) {

      String text = context.getResources().getString(stringId);
      SpannableString underlinedString = new SpannableString(text);
      underlinedString.setSpan(new UnderlineSpan(), 0, underlinedString.length(), 0);

      tv.setText(underlinedString);
    }
  }

  public static void underlineTextView(String text, TextView tv) {
    Context context = tv.getContext();
    if (context != null) {

      SpannableString underlinedString = new SpannableString(text);
      underlinedString.setSpan(new UnderlineSpan(), 0, underlinedString.length(), 0);

      tv.setText(underlinedString);
    }
  }

  public static float dpToPx(Resources res, int val) {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, val, res.getDisplayMetrics());
  }

  public static float sp2px(Resources resources, int sp) {
    final float scale = resources.getDisplayMetrics().scaledDensity;
    return sp * scale;
  }
}
