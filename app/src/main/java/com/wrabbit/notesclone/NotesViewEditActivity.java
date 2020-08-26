package com.wrabbit.notesclone;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.wrabbit.notesclone.utilities.Progress;

import org.json.JSONObject;

public class NotesViewEditActivity extends AppCompatActivity {
    private static final String TAG = "NotesViewEditActivity";
    private TextInputEditText title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_view_edit);
        initWidgets();
        loadNotes();
        initImageBitmaps();
    }

    private void loadNotes() {
        final Progress progress = new Progress(NotesViewEditActivity.this);
        progress.start();
    }

    private void initWidgets() {
        Toolbar mToolbar = findViewById(R.id.z_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
//        fab_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(NoteDisplayMainActivity.this,
//                        NoteAddViewActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void initImageBitmaps() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sign, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        if (item.getItemId() == R.id.menu_bookmark) {
            item.setIcon(R.drawable.ic_v_bookmark_on_w);
        }
        if (item.getItemId() == R.id.menu_reminder) {
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveNote();
    }

    private void saveNote() {
    }

    private void saveData() {
        try {
            String jObjData = getSavingObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getSavingObject() {
        JSONObject jObjData = new JSONObject();
        try {

            jObjData.put("ver", DexApplication.getApplicationVersion() + " v");
            jObjData.put("device_type", "M");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jObjData.toString();
    }
}
