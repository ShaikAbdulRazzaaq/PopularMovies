package com.projects.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

class UtilsClass {

        private boolean connected;
        private static UtilsClass instance;

        static UtilsClass getInstance() {
            if (null != instance) {
                return instance;
            } else {
                instance = new UtilsClass();
            }
            return instance;
        }

        private UtilsClass() {
        }

        boolean isNetworkAvailable(Context context) {
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

                assert connectivityManager != null;
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                connected = networkInfo != null && networkInfo.isAvailable() &&
                        networkInfo.isConnected();
                return connected;

            } catch (Exception e) {
                System.out.println("CheckConnectivity Exception: " + e.getMessage());
                Log.v("connectivity", e.toString());
            }
            return connected;
        }
    }
