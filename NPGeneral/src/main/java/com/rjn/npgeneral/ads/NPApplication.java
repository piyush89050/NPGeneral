package com.rjn.npgeneral.ads;

import android.content.Context;

import com.google.android.gms.ads.MobileAds;

public class NPApplication {

    public static boolean isTestAds = true;

    private final Context context;

    /**
     * Create a new {@link NPApplication}.
     *
     * @param context An Android {@link Context}.
     */

    public NPApplication(Context context, boolean isTestAds) {
        this.context = context;
        NPApplication.isTestAds = isTestAds;
        // Initialize the SDK:
        //UnityAds.initialize (context, "3919527", NPApplication.isTestAds,true);

        //Call the function to initialize AdMob SDK
        MobileAds.initialize(context, initializationStatus -> {
        });
    }
}
