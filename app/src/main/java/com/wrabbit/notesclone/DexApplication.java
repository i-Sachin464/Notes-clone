package com.wrabbit.notesclone;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.BatteryManager;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;


import org.json.JSONObject;

import java.util.HashMap;

public class DexApplication extends MultiDexApplication {
    private String BASE_URL = "";

    private HashMap<String, String> deviceDetails = new HashMap<String, String>();
//    private DB db = null;
    static String version = "";

    public static String getApplicationVersion() {
        return version;
    }

    String dbName = "DatabaseName.db";

    public String getDbName() {
        return dbName;
    }

    String erroMsg = "";

    public String getErroMsg() {
        return erroMsg;
    }

    public void setErroMsg(String erroMsg) {
        this.erroMsg = erroMsg;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE);
            KeyguardLock lock = keyguardManager
                    .newKeyguardLock(KEYGUARD_SERVICE);
            lock.disableKeyguard();
        } catch (Exception e) {

        }

        try {
            version = getPackageManager().getPackageInfo(getPackageName(),
                    0).versionName;
        } catch (NameNotFoundException e1) {
            e1.printStackTrace();
        }

//        try {
//            this.db = new DB(DexApplication.this);
//
//        } catch (Exception e) {
//            Log.e("", e.getMessage());
//        }
        this.registerReceiver(this.batteryInfoReceiver, new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED));

    }

//    public DB getDB() {
//        try {
//            if (this.db == null) {
//                this.db = new DB(DexApplication.this);
//            }
//        } catch (Exception e) {
//            Log.e(TAG, e.getMessage());
//        }
//        return this.db;
//    }

    public String getWebAddress() {
        return deviceDetails.get("webAddress");

    }

    public String getDeviceUUID() {
        return deviceDetails.get("uuid_dev");

    }

    public String getDeviceCreateDate() {
        return deviceDetails.get("createDate");

    }

    public String getDeviceStatus() {
        return deviceDetails.get("status");

    }

    public String getBaseDevicePhNo() {
        return deviceDetails.get("baseDevPhNo");

    }

    @Override
    public void onLowMemory() {
        // Log.e("onLowMemory", "onLowMemory");
        super.onLowMemory();
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;

    @Override
    public void onTerminate() {
//        if (this.db != null) {
//            this.db.close();
//        }
//        this.stopLocTracking();
        super.onTerminate();
        activityVisible = false;
    }

    String email = "", password = "";

    public String getEmail() {
        return email;
    }

    public String getEmailPass() {
        return password;
    }

    public static final String TAG = DexApplication.class.getSimpleName();
    private static DexApplication mInstance;

    public static synchronized DexApplication getInstance() {
        return mInstance;
    }

    private JSONObject jobjPhoneState = new JSONObject();

    public JSONObject getJobjPhoneState() {
        return jobjPhoneState;
    }

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            try {
                jobjPhoneState.put("bl", level);
                jobjPhoneState.put("bt", (temp / 10));
                jobjPhoneState.put("bh", getHealthString(health));
            } catch (Exception e) {
                e.printStackTrace();
            }
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
                    || status == BatteryManager.BATTERY_STATUS_FULL;
            String batteryStatus = "Discharging";
            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,
                    -1);
            boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
            boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
            if (isCharging) {
                batteryStatus = "Charging";
                if (usbCharge) {
                    batteryStatus = batteryStatus + " - USB";
                } else if (acCharge) {
                    batteryStatus = batteryStatus + " - AC";
                }
            }
            try {
                jobjPhoneState.put("bs", batteryStatus);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private String getHealthString(int health) {
        String healthString = "Unknown";
        switch (health) {
            case BatteryManager.BATTERY_HEALTH_DEAD:
                healthString = "Dead";
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                healthString = "Good";
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                healthString = "Over Voltage";
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                healthString = "Over Heat";
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                healthString = "Failure";
                break;
        }
        return healthString;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

}
