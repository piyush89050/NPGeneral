package com.rjn.npgeneral.ads;

/*
 * Copyright (C) 2020 NP SoftTech.
 *
 * Created By Piyush Narola
 * Mo. +91 8153012869
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.rjn.npgeneral.BuildConfig;
import com.rjn.npgeneral.R;

import java.util.ArrayList;
import java.util.List;

public class NPNativeAds {

    private final Context context;
    private final String adUnit;
    private final NPAdsType npAdsType;
    private final FrameLayout adContainerView;

    private UnifiedNativeAd nativeAd;

    private NativeAd nativeAdFacebook;
    private NativeAdListener nativeAdListenerFacebook;

    /**
     * Create a new {@link NPNativeAds}.
     *
     * @param context An Android {@link Context}.
     */

    public NPNativeAds(Context context, String adUnit, NPAdsType npAdsType, FrameLayout adContainerView) {
        this.context = context;
        this.adUnit = adUnit;
        this.npAdsType = npAdsType;
        this.adContainerView = adContainerView;

        if (adUnit != null && !adUnit.isEmpty())
            loadNativeAds();
    }

    public void loadNativeAds() {
        switch (npAdsType) {
            case GOOGLE:
                googleNativeAd();
                break;
            case FACEBOOK:
                facebookNativeAd();
                break;
            case UNITY:
                break;
        }
    }

    private void googleNativeAd() {
        AdLoader.Builder builder = new AdLoader.Builder(context, BuildConfig.DEBUG ? "ca-app-pub-3940256099942544/2247696110" : adUnit);
        // OnUnifiedNativeAdLoadedListener implementation.
        builder.forUnifiedNativeAd(unifiedNativeAd -> {
            if (nativeAd != null) {
                nativeAd.destroy();
            }
            nativeAd = unifiedNativeAd;
            UnifiedNativeAdView adView = (UnifiedNativeAdView) ((Activity) context).getLayoutInflater().inflate(R.layout.google_native_ad_unit, null);
            populateUnifiedNativeAdView(unifiedNativeAd, adView);
            adContainerView.removeAllViews();
            adContainerView.addView(adView);
        });

//        VideoOptions videoOptions = new VideoOptions.Builder()
//                .setStartMuted(startVideoAdsMuted.isChecked())
//                .build();

//        NativeAdOptions adOptions = new NativeAdOptions.Builder()
//                .setVideoOptions(videoOptions)
//                .build();

//        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
//                refresh.setEnabled(true);
                Toast.makeText(context, "Failed to load native ad: " + errorCode, Toast.LENGTH_SHORT).show();
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView
            adView) {
        adView.setMediaView(adView.findViewById(R.id.ad_media));

        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd);

    }

    private void facebookNativeAd() {
        nativeAdFacebook = new NativeAd(context, adUnit);

        nativeAdListenerFacebook = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
                if (nativeAdFacebook != null) {
                    nativeAdFacebook.loadAd(nativeAdFacebook.buildLoadAdConfig().withAdListener(nativeAdListenerFacebook).build());
                }
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Native ad is loaded and ready to be displayed
                // Race condition, load() called again before last ad was displayed
                if (nativeAdFacebook == null || nativeAdFacebook != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                facebookInflateAd(nativeAdFacebook);
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
            }
        };

        // Request an ad
        if (nativeAdFacebook != null)
            nativeAdFacebook.loadAd(nativeAdFacebook.buildLoadAdConfig().withAdListener(nativeAdListenerFacebook).build());
    }

    private void facebookInflateAd(NativeAd nativeAd) {

        nativeAd.unregisterView();
        View viewAdMain = LayoutInflater.from(context).inflate(R.layout.fb_native_ad_unit, null);
        NativeAdLayout nativeAdLayout = viewAdMain.findViewById(R.id.native_ad_container);

        LinearLayout adViewN = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.fb_native_ad_contain, nativeAdLayout, false);
        nativeAdLayout.addView(adViewN);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = adViewN.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(context, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = adViewN.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adViewN.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adViewN.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adViewN.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adViewN.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adViewN.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adViewN.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adViewN, nativeAdMedia, nativeAdIcon, clickableViews);

        adContainerView.addView(viewAdMain);
    }


}
