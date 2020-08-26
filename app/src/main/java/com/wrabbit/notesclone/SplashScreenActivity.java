package com.wrabbit.notesclone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wrabbit.notesclone.utilities.Utility;


public class SplashScreenActivity extends Activity {
    //    DB db;
    LinearLayout lyProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        lyProgress = findViewById(R.id.lyProgress);
        lyProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        boolean isInternetPresent = Utility.isConnectingToInternet(getApplicationContext());
//        if (!isInternetPresent) {
//            Utility.toastText(MainSpalshScreen.this, "No Internet", Color.RED, Toast.LENGTH_SHORT);
//            showAlertDialog(MainSpalshScreen.this, "No Internet Connection",
//                    "Please Enable your internet connection.", false);
//            return;
//        }
        Utility.checkAppUpdate();
        try {
            lyProgress.setVisibility(View.VISIBLE);
            startThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void updateAppPopUp(String title, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashScreenActivity.this,
                android.R.style.Widget_Material_Light_ButtonBar_AlertDialog);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        alertDialog.setMessage(message);

        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String appPackageName = getBaseContext()
                                .getPackageName();
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri
                                    .parse("market://details?id="
                                            + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id="
                                            + appPackageName)));
                        } catch (Exception e) {
                            Log.e("PlayStore", "NotFound");
                            Toast.makeText(getApplicationContext(),
                                    "PlayStore Not Found.", Toast.LENGTH_LONG)
                                    .show();
                        }
                        finish();
                    }
                });
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void startThread() {
        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(2 * 1000);
                    Intent i = new Intent(getBaseContext(),
                            NotesDashboardActivity.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                }
            }
        };
        background.start();
    }

    private void showAlertDialog(Context context, String title, String message,
                                 Boolean status) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashScreenActivity.this,
                android.R.style.Widget_Material_Light_ButtonBar_AlertDialog);
        // Setting Dialog Title
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        // Setting OK Button
        alertDialog.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                        // finish();
                    }
                });
        alertDialog.setCancelable(false);
        // Showing Alert Message
        alertDialog.show();
    }
}