package com.wrabbit.notesclone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public final class LoginActivity extends AppCompatActivity {

    Context mCtx;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCtx = LoginActivity.this;
        setContentView(R.layout.activity_signup_login_main);
        MaterialButton login = findViewById(R.id.btn_login);
        TextView skip = findViewById(R.id.skip);
        skip.setVisibility(View.GONE);
        if (login != null) {
            login.setOnClickListener((new View.OnClickListener() {
                public final void onClick(View it) {
                    Intent intent = new Intent(mCtx, OwnProfileActivity.class);
                    mCtx.startActivity(intent);
                }
            }));
        }
        if (skip != null) {
            skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mCtx, MainActivity.class);
                    mCtx.startActivity(intent);
                }
            });
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            changeStatusBarColor(Color.WHITE);
        }
    }

    public final void changeStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }
}

