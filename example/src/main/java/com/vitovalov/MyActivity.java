package com.vitovalov;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
import com.github.lzyzsd.circleprogress.AngularChartView;
import com.github.lzyzsd.circleprogressexample.ArcInFragment;
import com.github.lzyzsd.circleprogressexample.ItemListActivity;
import com.github.lzyzsd.circleprogressexample.R;
import com.github.lzyzsd.circleprogressexample.ViewPagerActivity;

import java.util.*;

public class MyActivity extends Activity {
    private Timer timer;
  private AngularChartView donutProgress;
  private AngularChartView donutProgress2;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
      donutProgress = (AngularChartView) findViewById(R.id.arc_progress2);
      donutProgress2 = (AngularChartView) findViewById(R.id.activity_chart2);
        //
      //AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.progress_anim2);
        //set.setTarget(donutProgress);
      //set.start();
        //
      //ValueAnimator animation = ValueAnimator.ofObject(new IntEvaluator(), 0, 100);
        //animation.setDuration(1000);
      //animation.start();
        //
        ///*PropertyValuesHolder donutAlphaProperty = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        //PropertyValuesHolder donutProgressProperty = PropertyValuesHolder.ofInt("donut_progress", 0, 100);
        //ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(donutProgress, donutAlphaProperty, donutProgressProperty);
        //animator.setDuration(2000);
        //animator.setInterpolator(new AccelerateDecelerateInterpolator());
        //animator.start();*/
        //
      timer = new Timer();
      timer.schedule(new TimerTask() {
        @Override public void run() {
          runOnUiThread(new Runnable() {
            @Override public void run() {
              ObjectAnimator anim = ObjectAnimator.ofInt(donutProgress, "progress1", 180, 10);
              anim.setInterpolator(new DecelerateInterpolator());
              anim.setDuration(800);
              anim.start();
              ObjectAnimator anim2 = ObjectAnimator.ofInt(donutProgress2, "progress1", 0, 135);
              anim2.setInterpolator(new DecelerateInterpolator());
              anim2.setDuration(800);
              anim2.start();
            }
          });
        }
      }, 0, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //timer.cancel();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_viewpager:
                startActivity(new Intent(this, ViewPagerActivity.class));
                return true;
            case R.id.action_list:
                startActivity(new Intent(this, ItemListActivity.class));
                return true;
            case R.id.action_arch_tab:
                startActivity(new Intent(this, ArcInFragment.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
