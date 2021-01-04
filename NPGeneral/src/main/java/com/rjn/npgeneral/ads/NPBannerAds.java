package com.rjn.npgeneral.ads;

/*
 * Copyright (C) 2020 NP SoftTech.
 *
 * Created By Piyush Narola
 * Mo. +91 8153012869
 */

import android.app.Activity;
import android.content.Context;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.rjn.npgeneral.BuildConfig;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

import static com.rjn.npgeneral.ads.NPGeneral.getAdWidth;

public class NPBannerAds {

    private final Context context;
    private final String adUnit;
    private final NPAdsType npAdsType;
    private final FrameLayout adContainerView;

    private AdView adViewGoogle = null;
    private com.facebook.ads.AdView adViewFacebook = null;
    private BannerView adViewUnity;

    /**
     * Create a new {@link NPBannerAds}.
     *
     * @param context An Android {@link Context}.
     */

    public NPBannerAds(Context context, String adUnit, NPAdsType npAdsType, FrameLayout adContainerView) {
        this.context = context;
        this.adUnit = adUnit;
        this.npAdsType = npAdsType;
        this.adContainerView = adContainerView;

        if (adUnit != null && !adUnit.isEmpty()) {
            switch (npAdsType) {
                case GOOGLE:
                    adViewGoogle = new AdView(context);
                    adContainerView.addView(adViewGoogle);
                    adViewGoogle.setAdUnitId(NPApplication.isTestAds ? "ca-app-pub-3940256099942544/6300978111" : adUnit);
                    AdSize adSize = NPGeneral.getAdSize(context);
                    // Set the adaptive ad size to the ad view.
                    adViewGoogle.setAdSize(adSize);

                    adViewGoogle.setAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            super.onAdFailedToLoad(loadAdError);
                        }
                    });
                    break;
                case FACEBOOK:
                    adViewFacebook = new com.facebook.ads.AdView(context, adUnit, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
                    adContainerView.addView(adViewFacebook);
                    break;
                case UNITY:
                    adViewUnity = new BannerView((Activity) context, "Banner", new UnityBannerSize(getAdWidth(), 50));
                    adContainerView.addView(adViewUnity);
                    break;
            }
            loadBanner();
        }
    }

    public void loadBanner() {
        switch (npAdsType) {
            case GOOGLE:
                AdRequest adRequest = new AdRequest.Builder()
                        //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .build();
                // Start loading the ad in the background.
                adViewGoogle.loadAd(adRequest);
                break;
            case FACEBOOK:
                adViewFacebook.loadAd();
                break;
            case UNITY:
                adViewUnity.load();
                break;
        }
    }
}
