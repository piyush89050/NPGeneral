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
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.rjn.npgeneral.BuildConfig;
import com.rjn.npgeneral.R;

import java.util.ArrayList;
import java.util.List;

public class NPNativeBannerAds {

    private final Context context;
    private final String adUnit;
    private final NPAdsType npAdsType;
    private final FrameLayout adContainerView;

    private UnifiedNativeAd nativeAdGoogle;

    private NativeBannerAd nativeBannerAd;
    private NativeAdListener nativeAdListenerBanner;

    /**
     * Create a new {@link NPNativeBannerAds}.
     *
     * @param context An Android {@link Context}.
     */

    public NPNativeBannerAds(Context context, String adUnit, NPAdsType npAdsType, FrameLayout adContainerView) {
        this.context = context;
        this.adUnit = adUnit;
        this.npAdsType = npAdsType;
        this.adContainerView = adContainerView;

        if (adUnit != null && !adUnit.isEmpty())
            loadNativeBanner();
    }

    public void loadNativeBanner() {
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
            if (nativeAdGoogle != null) {
                nativeAdGoogle.destroy();
            }
            nativeAdGoogle = unifiedNativeAd;
            UnifiedNativeAdView adView = (UnifiedNativeAdView) ((Activity) context).getLayoutInflater().inflate(R.layout.google_native_ad_unit, null);
            populateUnifiedNativeAdView(unifiedNativeAd, adView);
            adContainerView.removeAllViews();
            adContainerView.addView(adView);
        });

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
//                refresh.setEnabled(true);
                Toast.makeText(context, "Failed to load native ad: " + errorCode, Toast.LENGTH_SHORT).show();
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        // adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());

      /*  if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }
*/
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
        nativeBannerAd = new NativeBannerAd(context, adUnit);

        nativeAdListenerBanner = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                if (nativeBannerAd != null)
                    nativeBannerAd.loadAd(nativeBannerAd.buildLoadAdConfig().withAdListener(nativeAdListenerBanner).build());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Native ad is loaded and ready to be displayed
                // Race condition, load() called again before last ad was displayed
                if (nativeBannerAd == null || nativeBannerAd != ad) {
                    return;
                }
                // Inflate Native Banner Ad into Container
                inflateAdB(nativeBannerAd);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
        // load the ad
        if (nativeBannerAd != null)
            nativeBannerAd.loadAd(nativeBannerAd.buildLoadAdConfig().withAdListener(nativeAdListenerBanner).build());
    }

    private void inflateAdB(NativeBannerAd nativeBannerAd) {
        // Unregister last ad
        nativeBannerAd.unregisterView();

        View viewAdMain = LayoutInflater.from(context).inflate(R.layout.fb_native_ad_unit, null);
        NativeAdLayout nativeAdLayout = viewAdMain.findViewById(R.id.native_ad_container);
        // Add the Ad view into the ad container.
        // Inflate the Ad view.  The layout referenced is the one you created in the last step.
        LinearLayout adViewB = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.fb_native_banner_ad_unit, nativeAdLayout, false);
        nativeAdLayout.addView(adViewB);

        // Add the AdChoices icon
        LinearLayout adChoicesContainer = adViewB.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(context, nativeBannerAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        TextView nativeAdTitle = adViewB.findViewById(R.id.native_ad_title);
        TextView nativeAdSocialContext = adViewB.findViewById(R.id.native_ad_social_context);
        TextView sponsoredLabel = adViewB.findViewById(R.id.native_ad_sponsored_label);
        MediaView nativeAdIconView = adViewB.findViewById(R.id.native_icon_view);
        Button nativeAdCallToAction = adViewB.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdCallToAction.setText(nativeBannerAd.getAdCallToAction());
        nativeAdCallToAction.setVisibility(nativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdTitle.setText(nativeBannerAd.getAdvertiserName());
        nativeAdSocialContext.setText(nativeBannerAd.getAdSocialContext());
        sponsoredLabel.setText(nativeBannerAd.getSponsoredTranslation());

        // Register the Title and CTA button to listen for clicks.
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeBannerAd.registerViewForInteraction(adViewB, nativeAdIconView, clickableViews);
        adContainerView.addView(viewAdMain);
    }

}
