package com.rjn.npgeneral.ads;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;

public class NPGeneral {

    public static AdSize getAdSize(Context context) {
        //Determine the screen width to use for the ad width.
        DisplayMetrics outMetrics = Resources.getSystem().getDisplayMetrics();
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        //you can also pass your selected width here in dp
        int adWidth = (int) (widthPixels / density);

        //return the optimal size depends on your orientation (landscape or portrait)
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }

    public static int getAdWidth() {
        //Determine the screen width to use for the ad width.
        DisplayMetrics outMetrics = Resources.getSystem().getDisplayMetrics();
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        //you can also pass your selected width here in dp
        int adWidth = (int) (widthPixels / density);

        //return the optimal size depends on your orientation (landscape or portrait)
        return adWidth;
    }



}
