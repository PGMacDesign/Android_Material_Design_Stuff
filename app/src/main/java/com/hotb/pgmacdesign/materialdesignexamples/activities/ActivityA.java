package com.hotb.pgmacdesign.materialdesignexamples.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBarActivity;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hotb.pgmacdesign.materialdesignexamples.R;



public class ActivityA extends ActionBarActivity implements View.OnClickListener {

    private ViewGroup mRoot;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            TransitionInflater inflater = TransitionInflater.from(this);
            Transition transition = inflater.inflateTransition(R.transition.content_transition_a);
            getWindow().setExitTransition(transition);
            Slide slide = new Slide();
            slide.setDuration(5000);
            getWindow().setReenterTransition(slide);
        }
        setContentView(R.layout.activity_a);
        mRoot = (ViewGroup) findViewById(R.id.container_a);

        mButton1 = (Button) findViewById(R.id.button_1);
        mButton2 = (Button) findViewById(R.id.button_2);
        mButton3 = (Button) findViewById(R.id.button_3);
        mButton4 = (Button) findViewById(R.id.button_4);
        mRoot.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_calling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
//        TransitionManager.beginDelayedTransition(mRoot);
//        toggleBounds(mButton1, mButton2, mButton3, mButton4);

        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, null);
        startActivity(new Intent(this, ActivityB.class), compat.toBundle());

    }

//    public void toggleBounds(View... views) {
//        for (View current : views) {
//            ViewGroup.LayoutParams params = current.getLayoutParams();
//            params.height = 100;
//            params.width = 200;
//            current.setLayoutParams(params);
//        }
//    }

}
