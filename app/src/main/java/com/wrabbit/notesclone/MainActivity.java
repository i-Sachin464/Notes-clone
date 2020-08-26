package com.wrabbit.notesclone;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    String URL = "http://hradmin.aryupay.io/tracking/viewport.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("success") == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
//                        Toast.makeText(getApplicationContext(), "" + jsonArray, Toast.LENGTH_LONG).show();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String name = jsonObject1.getString("name");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });
        requestQueue.add(stringRequest);
    }

    private void dialogEditItems(HashMap item) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_item_view);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        final LinearLayout form = dialog.findViewById(R.id.form);
//        final Button save = dialog.findViewById(R.id.btn_save);
//        final Button cancel = dialog.findViewById(R.id.btn_cancel);
        long ts = (new Date()).getTime();
        dialog.show();
        if (item != null) {
            JSONObject jObj = new JSONObject(item);
            try {
                ts = jObj.getLong("ts");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                dialog.dismiss();
//            }
//        });
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
    }
}
