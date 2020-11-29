package com.rjn.npgeneral.ads;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;

/*
 * Copyright (C) 2020 NP SoftTech.
 *
 * Created By Piyush Narola
 * Mo. +91 8153012869
 */

public class NPToken extends AsyncTask<Void, Void, String> {
    String token = "";
    Context context;

    public NPToken(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
            token = adInfo.getId();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        }
        return token;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.i("onPostExecute: ", "Test Ad Token: " + token);
    }
}
