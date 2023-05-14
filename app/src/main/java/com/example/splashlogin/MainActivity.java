package com.example.splashlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    ImageView imageView;
    TextView logoText, slogan;

    private static int SPLASH_SCREEN = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imageView = findViewById(R.id.imageView);
        logoText = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

        imageView.setAnimation(topAnim);
        logoText.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);

            Pair[] pairs = new Pair[2];
            pairs[0] = new Pair<View,String>(imageView, "logo_image");
            pairs[1] = new Pair<View,String>(logoText, "logo_text");

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
            startActivity(intent,options.toBundle());
            finish();
        }, SPLASH_SCREEN);

    }
}