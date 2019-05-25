package com.me.notessaver.utils;

import android.content.Context;


public class PrefrencesHelper {


    public static void storeApiKey(Context context, String apiKey) {

        CustomPrefrencesHandler prefsHandler = new CustomPrefrencesHandler(
                context.getApplicationContext(),
                PrefsConstants.SHARED_PREFS_NAME);

        prefsHandler.writeString(PrefsConstants.API_KEY, apiKey);

    }

    public static String getApiKey(Context context) {
        CustomPrefrencesHandler prefsHandler = new CustomPrefrencesHandler(
                context.getApplicationContext(),
                PrefsConstants.SHARED_PREFS_NAME);

        return prefsHandler.getStringValueOf(PrefsConstants.API_KEY);
    }


}
