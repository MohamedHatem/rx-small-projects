package com.me.notessaver.utils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class CustomPrefrencesHandler {

    private SharedPreferences prefrences;
    private Editor prefsEditor;
    private Context ctx;
    private String sharedPrefsName;

    public CustomPrefrencesHandler(Context ctx, String sharedPrefsName) {
        this.ctx = ctx;
        this.sharedPrefsName = sharedPrefsName;
        prefrences = this.ctx.getSharedPreferences(this.sharedPrefsName,
                Context.MODE_PRIVATE);

    }

    public void writePrefrences(Map<String, String> map) {
        prefsEditor = prefrences.edit();
        for (Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            prefsEditor.putString(key, (String) value);
        }
        prefsEditor.commit();
    }

    public void writeString(String key, String value) {
        prefsEditor = prefrences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }


    public String getStringValueOf(String key) {
        String value = prefrences.getString(key, "");
        return value;
    }

    public boolean hasValueOf(String key) {
        boolean hasValue = prefrences.contains(key);
        return hasValue;
    }

    public void removeKeys(List<String> keysList) {
        prefsEditor = prefrences.edit();

        for (String key : keysList)
            prefsEditor.remove(key);

        prefsEditor.commit();

    }

    public void clearPrefrences() {
        prefsEditor = prefrences.edit();
        prefsEditor.clear();
        prefsEditor.commit();
    }

}
