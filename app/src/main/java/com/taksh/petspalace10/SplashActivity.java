package com.taksh.petspalace10;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final FrameLayout container = findViewById(R.id.splash_container);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        final float screenW = metrics.widthPixels;
        final float screenH = metrics.heightPixels;
        final float centerX = screenW / 2;
        final float centerY = screenH / 2;

        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            final View square = new View(this);
            int size = random.nextInt(20) + 10; // Tiny squares 10-30dp
            square.setLayoutParams(new FrameLayout.LayoutParams(size, size));
            square.setBackgroundColor(0x88FFFFFF); // Semi-transparent white

            // Determine starting side: 0=Top, 1=Bottom, 2=Left, 3=Right
            int side = random.nextInt(4);
            float startX = 0, startY = 0;

            switch (side) {
                case 0: // Top
                    startX = random.nextInt((int) screenW);
                    startY = -100;
                    break;
                case 1: // Bottom
                    startX = random.nextInt((int) screenW);
                    startY = screenH + 100;
                    break;
                case 2: // Left
                    startX = -100;
                    startY = random.nextInt((int) screenH);
                    break;
                case 3: // Right
                    startX = screenW + 100;
                    startY = random.nextInt((int) screenH);
                    break;
            }

            square.setX(startX);
            square.setY(startY);
            container.addView(square);

            // Animate to Center and Disappear
            square.animate()
                    .x(centerX - (size / 2))
                    .y(centerY - (size / 2))
                    .alpha(0f) // Fade out as it hits center
                    .scaleX(0.2f) // Shrink
                    .scaleY(0.2f)
                    .setDuration(1500 + random.nextInt(1500))
                    .setStartDelay(random.nextInt(2000))
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            container.removeView(square);
                        }
                    })
                    .start();
        }

        // Delay transition to see the effect
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }, 4500);
    }
}