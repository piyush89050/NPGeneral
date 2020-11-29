package com.rjn.npgeneral.ads;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.rjn.npgeneral.BuildConfig;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;

public class NPApplication {

    private final Context context;

    /**
     * Create a new {@link NPApplication}.
     *
     * @param context An Android {@link Context}.
     */

    public NPApplication(Context context) {
        this.context = context;
        // Initialize the SDK:
        UnityAds.initialize (context, "3919527", BuildConfig.DEBUG,true);

        //Call the function to initialize AdMob SDK
        MobileAds.initialize(context, initializationStatus -> {
        });
    }
}
