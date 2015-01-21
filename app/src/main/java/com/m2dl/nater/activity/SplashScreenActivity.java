package com.m2dl.nater.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.m2dl.nater.R;
import com.m2dl.nater.database.UserDAO;

public class SplashScreenActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    private UserDAO user;
    private Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = this;
        user = new UserDAO(this);
        user.open();
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                if (user.isEmpty()) {
                    user.close();
                    Intent i = new Intent(activity, RegisterActivity.class);
                    startActivity(i);
                    finish();
                    return;
                }
                user.close();
                Intent i = new Intent(activity, PictureActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
