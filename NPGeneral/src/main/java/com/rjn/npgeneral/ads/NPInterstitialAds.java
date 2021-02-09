package com.rjn.npgeneral.ads;

/*
 * Copyright (C) 2020 NP SoftTech.
 *
 * Created By Piyush Narola
 * Mo. +91 8153012869
 */

import android.app.Activity;
import android.content.Context;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.unity3d.ads.UnityAds;

public class NPInterstitialAds {

    private final Context context;
    private String adUnit;
    private boolean isFailed = false;
    private NPAdListener listener;
    private NPAdsType npAdsType;

    private InterstitialAd interstitialAdGoogle;

    private com.facebook.ads.InterstitialAd interstitialAdFacebook;
    private InterstitialAdListener interstitialFacebookAdListener;

    /**
     * Create a new {@link NPInterstitialAds}.
     *
     * @param context An Android {@link Context}.
     */
    public NPInterstitialAds(Context context, String npAdUnit, NPAdsType npAdsType) {
        this.context = context;
        this.adUnit = npAdUnit;
        this.npAdsType = npAdsType;

        if (npAdUnit != null && !npAdUnit.isEmpty()) {
            switch (npAdsType) {
                case GOOGLE:
                    interstitialAdGoogle = new InterstitialAd(context);
                    interstitialAdGoogle.setAdUnitId(NPApplication.isTestAds ? "ca-app-pub-3940256099942544/1033173712" : adUnit);
                    break;
                case FACEBOOK:
                    interstitialAdFacebook = new com.facebook.ads.InterstitialAd(context, adUnit);
                    break;
            }
        }
    }

    /**
     * Sets a {@link NPAdListener} to listen for ad events.
     *
     * @param listener The ad listener.
     */
    public void setAdListener(NPAdListener listener) {
        this.listener = listener;
        switch (npAdsType) {
            case GOOGLE:
                interstitialAdGoogle.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        loadAd();
                        listener.onAdClosed();
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        isFailed = true;
                    }

                    @Override
                    public void onAdLeftApplication() {
                        super.onAdLeftApplication();
                    }

                    @Override
                    public void onAdOpened() {
                        super.onAdOpened();
                    }

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                    }

                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }
                });
                break;
            case FACEBOOK:
                interstitialFacebookAdListener = new InterstitialAdListener() {

                    @Override
                    public void onError(Ad ad, AdError adError) {
                        isFailed = true;
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {

                    }

                    @Override
                    public void onAdClicked(Ad ad) {

                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {

                    }

                    @Override
                    public void onInterstitialDisplayed(Ad ad) {

                    }

                    @Override
                    public void onInterstitialDismissed(Ad ad) {
                        loadAd();
                        listener.onAdClosed();
                    }
                };
                break;
            case UNITY:

                break;
        }
    }

    /**
     * Load the interstitial.
     */
    public void loadAd() {
        if (adUnit != null && !adUnit.isEmpty()) {
            isFailed = false;
            switch (npAdsType) {
                case GOOGLE:
                    interstitialAdGoogle.loadAd(new AdRequest.Builder().build());
                    break;
                case FACEBOOK:
                    interstitialAdFacebook.loadAd(interstitialAdFacebook.buildLoadAdConfig().withAdListener(interstitialFacebookAdListener).build());
                    break;
            }
        }
    }

    /**
     * Load the interstitial.
     */
    public void show() {
        if (isFailed) {
            if (listener != null)
                listener.onAdClosed();
        } else {
            if (adUnit != null && !adUnit.isEmpty()) {
                switch (npAdsType) {
                    case GOOGLE:
                        if (interstitialAdGoogle != null && interstitialAdGoogle.isLoaded())
                            interstitialAdGoogle.show();
                        else {
                            loadAd();
                            if (listener != null)
                                listener.onAdClosed();

                        }
                        break;
                    case FACEBOOK:
                        if (interstitialAdFacebook != null && interstitialAdFacebook.isAdLoaded())
                            interstitialAdFacebook.show();
                        else
                            loadAd();
                        break;
                    case UNITY:
                        if (UnityAds.isReady(adUnit)) {
                            UnityAds.show((Activity) context, adUnit);
                        }
                        break;
                }
            } else {
                if (listener != null)
                    listener.onAdClosed();

            }
        }
    }
}
