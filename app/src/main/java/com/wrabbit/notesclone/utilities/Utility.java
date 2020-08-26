package com.wrabbit.notesclone.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.wrabbit.notesclone.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


public class Utility {

    public static void checkAppUpdate() {
    }

    public static HashMap<String, String> toMap(JSONObject object) {
        HashMap<String, String> map = new HashMap<String, String>();
        Iterator<String> keysItr = object.keys();
        String key, value;
        while (keysItr.hasNext()) {
            try {
                key = keysItr.next();
                value = object.getString(key);
                map.put(key, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }


    public static class DownloadTimeTask extends
            AsyncTask<String[], Void, String[]> {
        // Downloading data in non-ui thread

        TextView tv;
        String[] urlarray;
        String url = "";

        public DownloadTimeTask(TextView tv, String[] UrlArray) {
            this.tv = tv;
            this.urlarray = UrlArray;
        }

        @Override
        protected String[] doInBackground(String[]... urls) {

            String[] data = new String[urlarray.length];
            try {
                for (int i = 0; i < urlarray.length; i++) {
                    url = urlarray[i];
                    if (url != null && url.trim().length() > 0) {
                        data[i] = downloadUrl(url);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            if (result.length < 1) {
                // tv.setText("Not Available");
                tv.setText("--");
                return;
            }

            JSONObject jObject;

            JSONArray jRows = new JSONArray();
            int[] TimeArr = new int[result.length];
            Integer min = 5;
            TimeArr[0] = -1;

            for (int k = 0; k < result.length; k++) {
                try {
                    if (result[k] == null) {
                        continue;
                    }
                    jObject = new JSONObject(result[k]);
                    jRows = jObject.getJSONArray("rows");
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    continue;
                }
                JSONArray jElements = null;
                String duration = null;
                for (int i = 0; i < jRows.length(); i++) {
                    try {
                        jElements = ((JSONObject) jRows.get(i))
                                .getJSONArray("elements");
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    for (int j = 0; j < jElements.length(); j++) {
                        try {
                            // String dist = ((JSONObject) ((JSONObject)
                            // jElements.get(j)).get("distance")).getString("value").toString();
                            duration = ((JSONObject) ((JSONObject) jElements
                                    .get(j)).get("duration"))
                                    .getString("value").toString();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (duration != null) {
                        TimeArr[k] = (Integer.parseInt(duration) / 60);
                        if (min > TimeArr[k]) {
                            min = TimeArr[k];
                            tv.setText(min + " min");
                        }
                        // if ((Integer.parseInt(duration) / 60) > 0) {
                        // TimeArr[k] = (Integer.parseInt(duration) / 60);
                        // } else {
                        // TimeArr[k] = 1;
                        // }
                    }
                }
                if (k == 0) {
                    min = TimeArr[k];
                    tv.setText(min + " min");
                }
            }
            Arrays.sort(TimeArr);
            if (TimeArr[0] >= 0) {
                tv.setText(TimeArr[0] + " min");
            } else {
                // tv.setText("Not Available");
                tv.setText("--");
            }
        }
    }

    @SuppressLint("LongLogTag")
    public static String downloadUrl(String strUrl) throws IOException {
        String data = null;
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        } catch (Exception e) {
            Log.e("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    public static double getjObjFieldDouble(JSONObject jObj, String field)
            throws NumberFormatException, JSONException {
        double ret = 0;
        try {
            if (jObj.has(field)) {
                if (!((jObj.get(field) + "").trim().isEmpty())) {
                    ret = Double.parseDouble(jObj.get(field) + "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret = 0;
        }
        return ret;
    }

    public static boolean isEmpty(EditText et, String str) {
        if (et.getText().toString().trim().length() < 1) {
            et.clearFocus();
            et.requestFocus();
            et.setError(str);
            return false;
        }
        return true;
    }

    public static void setVersionName(TextView tv, Context ctx) {
        try {
            tv.setText(ctx.getPackageManager().getPackageInfo(
                    ctx.getPackageName(), 0).versionName
                    + " v");
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean isNetworkAvailable(Context cntx) {
        ConnectivityManager cm = (ConnectivityManager) cntx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null) { // && ni.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    public static Uri getOutputMediaFileUri(String picName, Context ctx) {
        File file = getOutputMediaFile(picName);
        if (file == null) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, picName);
            return ctx.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
        return Uri.fromFile(file);
    }

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile(String picName) {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                "HospitalM");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraVideo",
                        "Failed to create directory MyCameraVideo.");
                return null;
            }
        }
        File mediaFile = null;
        {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + picName);
        }
        return mediaFile;
    }

//    public static Bitmap getBitmap(Uri uri, Context ctx, DBUtil db) {
//
//        // Uri uri = Uri.fromFile(new File(path));
//        InputStream in = null;
//        try {
//
//            Integer maxImgSize = (ctx.getResources()
//                    .getInteger(R.integer.img_max_siz));
//            String dbStr = db.getValue4Key("img_max_siz");
//            if (dbStr != null) {
//                try {
//                    maxImgSize = Integer.parseInt(dbStr);
//                } catch (Exception e) {
//                }
//            }
//
//            final int IMAGE_MAX_SIZE = maxImgSize;// 500000 // 1200000; // 1.2MP
//            in = ctx.getContentResolver().openInputStream(uri);
//
//            // Decode image size
//            BitmapFactory.Options o = new BitmapFactory.Options();
//            o.inJustDecodeBounds = true;
//            BitmapFactory.decodeStream(in, null, o);
//            in.close();
//
//            int scale = 1;
//            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) > IMAGE_MAX_SIZE) {
//                scale++;
//            }
//
//            Bitmap b = null;
//            in = ctx.getContentResolver().openInputStream(uri);
//            if (scale > 1) {
//                scale--;
//                // scale to max possible inSampleSize that still yields an image
//                // larger than target
//                o = new BitmapFactory.Options();
//                o.inSampleSize = scale;
//                b = BitmapFactory.decodeStream(in, null, o);
//
//                // resize to desired dimensions
//                int height = b.getHeight();
//                int width = b.getWidth();
//
//                double y = Math.sqrt(IMAGE_MAX_SIZE
//                        / (((double) width) / height));
//                double x = (y / height) * width;
//
//                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
//                        (int) y, true);
//                b.recycle();
//                b = scaledBitmap;
//                System.gc();
//            } else {
//                b = BitmapFactory.decodeStream(in);
//            }
//            in.close();
//            return b;
//        } catch (IOException e) {
//            Log.e("", e.getMessage(), e);
//            return null;
//        }
//    }

    public static String ConvertToDateHr(long time) {
        try {
            DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date netDate = new Date(time);
            return sdf.format(netDate) + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time + "";
    }

    public static String ConvertToDate(String time) {
        try {
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date netDate = (new Date(Long.parseLong(time.trim())));
            return sdf.format(netDate) + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time + "";
    }

    public static void setRecyclerviewHeightBasedOnChildren(RecyclerView view) {
        RecyclerView.Adapter listAdapter = view.getAdapter();
        if (listAdapter == null) {
            return;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        for (int i = 0; i < listAdapter.getItemCount(); i++)
            params.height = params.height + params.height;
        view.setLayoutParams(params);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1))
                + 13;
        listView.setLayoutParams(params);
    }

    public static class DownloadFileFromURL extends AsyncTask<String, String, String> {
        private Activity act;
        private Context ctx;
        private String fileNm;
        private ProgressDialog pDialog;
        private String BROADCAST_DOWNLOAD;

        public DownloadFileFromURL(Activity activity, Context context, String filename, String BROADCAST_DOWNLOAD) {
            this.act = activity;
            this.ctx = context;
            this.fileNm = filename;
            this.BROADCAST_DOWNLOAD = BROADCAST_DOWNLOAD;
        }

        /**
         * Before starting background thread Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(act);
            pDialog.setMessage("Please wait..\n File is Downloading...");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(true);
            pDialog.show();
            pDialog.setProgress(0);
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                FileOutputStream fos;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    fos = act.openFileOutput(fileNm,
                            Context.MODE_PRIVATE);
                } else {
                    fos = act.openFileOutput(fileNm,
                            Context.MODE_WORLD_READABLE);
                }
                OutputStream output = fos;
                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (FileNotFoundException e) {
                act.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(ctx, "File Not Found", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            File file2 = new File(act.getApplicationContext().getFilesDir(),
                    fileNm);
            if (file2.exists()) {
                Toast.makeText(ctx,
                        "File Downloaded", Toast.LENGTH_LONG)
                        .show();

//                String
                if (BROADCAST_DOWNLOAD != null) {
                    Intent i = new Intent();
                    i.putExtra("response", "");
                    i.setAction(BROADCAST_DOWNLOAD);
                    ctx.sendBroadcast(i);
                }
            } else {
                Toast.makeText(ctx.getApplicationContext(),
                        "Please Download Again",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public static void toastText(Activity mActivity, CharSequence str, int color, int duration) {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.layout_toast, (ViewGroup) mActivity.findViewById(R.id.toast_layout_root));
        TextView _textvw = layout.findViewById(R.id.text);
        _textvw.setText(str);
        LinearLayout ly_Root = layout.findViewById(R.id.toast_layout_root);
        ly_Root.setBackgroundColor(mActivity.getResources().getColor(R.color.colorPrimary));
        Toast toast = new Toast(mActivity);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setDuration(duration);
        toast.setView(layout);
        toast.show();
    }

    public static boolean isConnectingToInternet(Context _context) {
        ConnectivityManager connectivity = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }
}
