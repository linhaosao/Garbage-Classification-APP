package com.example.graduation.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageButton;
import com.example.graduation.MainActivity;
import com.example.graduation.R;
import com.example.graduation.SelectActivity;

public class StartActivity extends Activity implements OnClickListener
{private ImageButton btn_close;@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final View view = View.inflate(this, R.layout.flash, null);
    setContentView(view);

//    btn_close = (ImageButton) findViewById(R.id.btn_close);
//    btn_close.setOnClickListener(this);

    //渐变展示启动屏
    AlphaAnimation start = new AlphaAnimation(0.3f,1.0f);
    start.setDuration(3000);
    view.startAnimation(start);
    start.setAnimationListener(new AnimationListener()
    {
        @Override
        public void onAnimationEnd(Animation arg0) {
            redirectTo();
        }
        @Override
        public void onAnimationRepeat(Animation animation) {

        }
        @Override
        public void onAnimationStart(Animation animation) {}

    });

}
    /**
     * 跳转到...
     */
    private void redirectTo()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onClick(View v) {

    }
}
