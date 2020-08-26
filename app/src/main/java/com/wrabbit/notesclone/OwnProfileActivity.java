package com.wrabbit.notesclone;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class OwnProfileActivity extends AppCompatActivity {
    private Context ctx;
    private Activity act;
    private CardView cv_appntmnt, cv_precrpton, cv_docmnt, cv_notes;
    private LinearLayout ly_prog = null;
    private String brLoad = "load.profile";

    private RelativeLayout ptient_rltv;
    private Toolbar app_bar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getApplicationContext();
        act = OwnProfileActivity.this;
        setContentView(R.layout.activity_detailed_profile);
        ptient_rltv = findViewById(R.id.ptient_rltv);
        cv_appntmnt = findViewById(R.id.cv_ptnt_appntmnt);
        cv_precrpton = findViewById(R.id.cv_ptnt_prescrptn);
        cv_docmnt = findViewById(R.id.cv_ptnt_dcmnt);
        cv_notes = findViewById(R.id.cv_ptnt_notes);
        app_bar = findViewById(R.id.app_bar);
        loadPatientDetails();
        appntmntOnClick();
        prescrptionOnClick();
        documentOnClick();
        notesOnClick();
        app_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        registerReceiver(brLoadPatient, new IntentFilter(brLoad));
    }

    private void loadPatientDetails() {
        TextView patient_name, mobile, nxt_appnt, patient_age, patient_sex, patient_grp;
        patient_name = findViewById(R.id.patnt_name);
        patient_age = findViewById(R.id.patnt_age);
        patient_sex = findViewById(R.id.patnt_sex);
        mobile = findViewById(R.id.mobile);
        patient_grp = findViewById(R.id.patnt_grp);
        nxt_appnt = findViewById(R.id.nxt_appnt);
        try {
            ArrayList<HashMap<String, String>> dataLst = new ArrayList<>();
            ArrayList<HashMap<String, String>> arrayList = dataLst;
            Collections.sort(arrayList, new Comparator<HashMap<String, String>>() {

                @Override
                public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
                    if (Long.parseLong(lhs.get("appoint_ts")) < Long.parseLong(rhs.get("appoint_ts")))
                        return -1;
                    else if (Long.parseLong(lhs.get("appoint_ts")) > Long.parseLong(rhs.get("appoint_ts")))
                        return 1;
                    else return 0;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void appntmntOnClick() {
        cv_appntmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(OwnProfileActivity.this,
//                        .class);
//                startActivity(intent);

            }
        });
    }

    private void prescrptionOnClick() {
        cv_precrpton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(OwnProfileActivity.this,
//                        .class)               startActivity(intent);

            }
        });
    }

    private void documentOnClick() {
        cv_docmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(OwnProfileActivity.this,
//                        .class);
//                startActivity(intent);

            }
        });
    }

    private void notesOnClick() {
        cv_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(OwnProfileActivity.this,
//                        .class);
//                        startActivity(intent);

            }
        });
    }

    BroadcastReceiver brLoadPatient = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadPatientDetails();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem sync = menu.add(Menu.NONE, 0, Menu.NONE, "Sync");
        sync.setIcon(R.drawable.ic_v_sync);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            sync.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
//        if (item.getItemId() == 0)
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(brLoadPatient);
        } catch (Exception e) {
        }
    }

}

