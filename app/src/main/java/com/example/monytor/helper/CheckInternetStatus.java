package com.example.monytor.helper;

import android.content.Context;
import android.net.ConnectivityManager;

public class CheckInternetStatus {

    public static boolean isInternetAvaiable (Context context) {

        final ConnectivityManager connectivity_manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivity_manager.getActiveNetworkInfo() != null &&
                connectivity_manager.getActiveNetworkInfo().isConnected();
    }
}
