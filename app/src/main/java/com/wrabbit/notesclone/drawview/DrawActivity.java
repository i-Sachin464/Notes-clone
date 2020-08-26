package com.wrabbit.notesclone.drawview;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.wrabbit.notesclone.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class DrawActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG = "tag";
    public static final String KEY_DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_layout);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            DrawFragment fragment = new DrawFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.flContainer, fragment, FRAGMENT_TAG).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sign, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        if (item.getItemId() == R.id.menuSave) {
            DrawFragment fragment = (DrawFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
            if (fragment != null) {
                File file = null;
                try {
                    FileOutputStream outputStream = null;
                    try {
                        String filePath = getIntent().getStringExtra(KEY_DATA);
                        // if there is no file path then save it to cache dir
                        if (filePath == null)
                            file = File.createTempFile("temp", "png", getCacheDir());
                        else
                            file = new File(filePath);
                        outputStream = new FileOutputStream(file);
                        fragment.save(outputStream);
                    } finally {
                        if (outputStream != null)
                            outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent result = new Intent();
                if (file != null) {
                    result.putExtra(KEY_DATA, file.getAbsolutePath());
                    this.setResult(RESULT_OK, result);
                }
                finish();
            }
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}