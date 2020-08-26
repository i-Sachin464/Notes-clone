package com.wrabbit.notesclone;

import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.wrabbit.notesclone.utilities.Progress;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RecordingActivity extends AppCompatActivity {
    private static final String TAG = "RecordingActivity";
    private static final int REQUEST_AUDIO_PERMISSION_CODE = 999;
    private LinearLayout ly_start, ly_stop;
    private MediaRecorder mRecorder;
    private static String mFileName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        initWidgets();
        loadNotes();
        initImageBitmaps();
    }

    private int progressStatus = 0;
    private Handler handler = new Handler();

    private void loadNotes() {
        final Progress progress = new Progress(RecordingActivity.this);
        progress.start();
//        progress.stop();
    }

    private void initWidgets() {
        Toolbar mToolbar = findViewById(R.id.z_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        long ts = new Date().getTime();
        mFileName += "/rec_" + ts + ".aac";

        ly_stop = findViewById(R.id.ly_stop);
        ly_start = findViewById(R.id.ly_start);

        ly_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions()) {
                    ly_stop.setEnabled(true);
                    ly_start.setEnabled(false);
                    mRecorder = new MediaRecorder();
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mRecorder.setOutputFile(mFileName);
                    try {
                        mRecorder.prepare();
                    } catch (IOException e) {
                        Log.e(TAG, "prepare() failed");
                    }
                    mRecorder.start();
                    Toast.makeText(getApplicationContext(), "Recording Started", Toast.LENGTH_LONG).show();
                } else {
                    requestPermissions();
                }
            }
        });
        ly_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ly_stop.setEnabled(false);
                ly_start.setEnabled(true);
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
                Toast.makeText(getApplicationContext(), "Recording Stopped", Toast.LENGTH_LONG).show();
            }
        });

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
//            Toast.makeText(RecordingActivity.this, "Please Select Form Again.",
//                    Toast.LENGTH_LONG).show();
//            finish();
//            return;
            String strUnsaved = getMsgToUpload();
//            db.updateMsgUploadInetSmsMsg_dtls(msgsnd_ID, trip_dtls + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getMsgToUpload() {
        JSONObject josUnsaved = new JSONObject();
        try {
//            HashMap map = cloziApp.getInbox_item_map();
//            String work_data = "";
//            try {
//                if (map != null && map.containsKey("work_data")) {
//                    work_data = map.get("work_data") + "";
//                }
//                if (work_data != null && !work_data.isEmpty()) {
//                    josUnsaved = new JSONObject(work_data);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            ClozIFormUtil.getallText2JSONUpdateDD(josUnsaved, form, act, true, db);
            JSONObject jObjLoc = new JSONObject();
//            Location loc = getCurLocation();
//            try {
//                if (loc != null) {
//                    josUnsaved.put("lat", loc.getLatitude());
//                    josUnsaved.put("lon", loc.getLongitude());
//                    jObjLoc.put("lat", loc.getLatitude());
//                    jObjLoc.put("lon", loc.getLongitude());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            josUnsaved.put("tran_id", trans_id);
//            josUnsaved.put("tmpl_id", tmp_ID);
//            josUnsaved.put("empid", Util.getEmpDtls(db, "empid"));
//            josUnsaved.put("user", Util.getEmpDtls(db, "name"));
//            josUnsaved.put("msgsnd_ID", msgsnd_ID);
            josUnsaved.put("ver", DexApplication.getApplicationVersion() + " v");
            josUnsaved.put("client_type", "dev");
//            Util.getSndFlds(josUnsaved, db, RecordingActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return josUnsaved.toString();
    }

    public boolean checkPermissions() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(RecordingActivity.this,
                new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
}
