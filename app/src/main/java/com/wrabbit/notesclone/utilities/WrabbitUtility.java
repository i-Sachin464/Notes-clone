package com.wrabbit.notesclone.utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class WrabbitUtility {

    public WrabbitUtility() {
    }

    public static String getStrFieldJSON(JSONObject jObj, String field) {
        String ret = "";

        try {
            if (jObj.has(field) && !(jObj.get(field) + "").trim().isEmpty()) {
                ret = jObj.get(field) + "";
            }
        } catch (Exception var4) {
            var4.printStackTrace();
            ret = "";
        }

        return ret;
    }

    public static String getStrFieldJSON(JSONObject jObj, String field, String difVal) {
        String ret = difVal;

        try {
            if (jObj.has(field) && !(jObj.get(field) + "").trim().isEmpty()) {
                ret = jObj.get(field) + "";
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return ret;
    }

    public static long getLongFieldJSON(JSONObject jObj, String field) {
        long ret = 0L;

        try {
            if (jObj.has(field) && !(jObj.get(field) + "").trim().isEmpty()) {
                ret = Long.parseLong(jObj.get(field) + "");
            }
        } catch (Exception var5) {
            var5.printStackTrace();
            ret = 0L;
        }

        return ret;
    }

    public static int getIntFieldJSON(JSONObject jObj, String field) {
        int ret = 0;

        try {
            if (jObj.has(field) && !(jObj.get(field) + "").trim().isEmpty()) {
                ret = Integer.parseInt(jObj.get(field) + "");
            }
        } catch (Exception var4) {
            var4.printStackTrace();
            ret = 0;
        }

        return ret;
    }

    public static double getDoubleFieldJSON(JSONObject jObj, String field) {
        double ret = 0.0D;

        try {
            if (jObj.has(field) && !(jObj.get(field) + "").trim().isEmpty()) {
                ret = Double.parseDouble(jObj.get(field) + "");
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return ret;
    }

    public static JSONObject getJSONObjFieldJSON(JSONObject jObj, String field) {
        JSONObject jObjData = new JSONObject();

        try {
            if (jObj.has(field)) {
                jObjData = new JSONObject(jObj.getString(field));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return jObjData;
    }

    public static JSONArray getJSONArrayFieldJSON(JSONObject jObj, String field) {
        JSONArray jArry = new JSONArray();

        try {
            if (jObj.has(field)) {
                jArry = new JSONArray(jObj.getString(field));
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return jArry;
    }

    public static HashMap<String, String> jsonToHashmap(JSONObject object) {
        HashMap<String, String> map = new HashMap();
        Iterator keysItr = object.keys();
        while (keysItr.hasNext()) {
            try {
                String key = (String) keysItr.next();
                String value = object.getString(key);
                map.put(key, value);
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }
        return map;
    }

    public static long convertDateToTimeStamp(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date parsedTimeStamp = dateFormat.parse(date);
            return parsedTimeStamp.getTime();
        } catch (ParseException var4) {
            var4.printStackTrace();
            return -1L;
        }
    }
}
