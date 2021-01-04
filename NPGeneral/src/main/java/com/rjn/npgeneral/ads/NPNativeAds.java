package com.rjn.npgeneral.ads;

/*
 * Copyright (C) 2020 NP SoftTech.
 *
 * Created By Piyush Narola
 * Mo. +91 8153012869
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.rjn.npgeneral.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NPNativeAds {

    private final Context context;
    private final String adUnit;
    private final NPAdsType npAdsType;
    private final FrameLayout adContainerView;

    private UnifiedNativeAd nativeAdGoogle;
    private UnifiedNativeAdView unifiedNativeAdViewGoogle;
    private Map<NPGeneral.NativeLayoutType, Object> mapLayoutObj;

    private NativeAd nativeAdFacebook;
    private NativeBannerAd nativeBannerAdFacebook;
    private View adViewContainNative;
    NativeAdLayout nativeAdLayout;
    private NativeAdListener nativeAdListenerFacebook;

    /**
     * Create a new {@link NPNativeAds}.
     *
     * @param context An Android {@link Context}.
     */

    public NPNativeAds(Context context, String adUnit, NPAdsType npAdsType, FrameLayout adContainerView, View adViewContainNative, Map<NPGeneral.NativeLayoutType, Object> mapLayoutObj) {
        this.context = context;
        this.adUnit = adUnit;
        this.npAdsType = npAdsType;
        this.adContainerView = adContainerView;
        this.adViewContainNative = adViewContainNative;
        this.mapLayoutObj = mapLayoutObj;

        if (adUnit != null && !adUnit.isEmpty())
            loadNativeAds();
    }

    public void loadNativeAds() {
        switch (npAdsType) {
            case GOOGLE:
                if (adViewContainNative != null && mapLayoutObj != null && mapLayoutObj.size() > 0)
                    googleNativeAd();
                break;
            case FACEBOOK:
                if (adViewContainNative != null && mapLayoutObj != null && mapLayoutObj.size() > 0)
                    facebookNativeAd();
                break;
            case UNITY:
                break;
        }
    }

    AdLoader adLoader;

    private void googleNativeAd() {
        AdLoader.Builder builder = new AdLoader.Builder(context, NPApplication.isTestAds ? "ca-app-pub-3940256099942544/2247696110" : adUnit);
        // OnUnifiedNativeAdLoadedListener implementation.
        builder.forUnifiedNativeAd(unifiedNativeAd -> {
            if (nativeAdGoogle != null) {
                nativeAdGoogle.destroy();
            }
            nativeAdGoogle = unifiedNativeAd;
            if (unifiedNativeAdViewGoogle == null) {
                unifiedNativeAdViewGoogle = new UnifiedNativeAdView(context);
                //unifiedNativeAdViewGoogle.removeAllViews();
                unifiedNativeAdViewGoogle.addView(adViewContainNative);
            }
            populateUnifiedNativeAdView(unifiedNativeAd, unifiedNativeAdViewGoogle);
            adContainerView.removeAllViews();
            adContainerView.addView(unifiedNativeAdViewGoogle);
        });

//        VideoOptions videoOptions = new VideoOptions.Builder()
//                .setStartMuted(startVideoAdsMuted.isChecked())
//                .build();

//        NativeAdOptions adOptions = new NativeAdOptions.Builder()
//                .setVideoOptions(videoOptions)
//                .build();

//        builder.withNativeAdOptions(adOptions);

        adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView
            adView) {
        if (mapLayoutObj.containsKey(NPGeneral.NativeLayoutType.MediaView)) {
            adView.setMediaView((MediaView) mapLayoutObj.get(NPGeneral.NativeLayoutType.MediaView));
            adView.getMediaView().setMediaContent(nativeAd.getMediaContent());
        }

        if (mapLayoutObj.containsKey(NPGeneral.NativeLayoutType.BodyView)) {
            adView.setBodyView((View) mapLayoutObj.get(NPGeneral.NativeLayoutType.BodyView));

            if (nativeAd.getBody() == null) {
                adView.getBodyView().setVisibility(View.INVISIBLE);
            } else {
                adView.getBodyView().setVisibility(View.VISIBLE);
                ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
            }
        }

        if (mapLayoutObj.containsKey(NPGeneral.NativeLayoutType.HeadlineView)) {
            adView.setHeadlineView((View) mapLayoutObj.get(NPGeneral.NativeLayoutType.HeadlineView));
            // The headline and mediaContent are guaranteed to be in every UnifiedNativeAd.
            ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        }

        if (mapLayoutObj.containsKey(NPGeneral.NativeLayoutType.CallToActionView)) {
            adView.setCallToActionView((View) mapLayoutObj.get(NPGeneral.NativeLayoutType.CallToActionView));
            if (nativeAd.getCallToAction() == null) {
                adView.getCallToActionView().setVisibility(View.INVISIBLE);
            } else {
                adView.getCallToActionView().setVisibility(View.VISIBLE);
                ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
            }
        }
        if (mapLayoutObj.containsKey(NPGeneral.NativeLayoutType.IconView)) {
            adView.setIconView((View) mapLayoutObj.get(NPGeneral.NativeLayoutType.IconView));

            if (nativeAd.getIcon() == null) {
                adView.getIconView().setVisibility(View.GONE);
            } else {
                ((ImageView) adView.getIconView()).setImageDrawable(
                        nativeAd.getIcon().getDrawable());
                adView.getIconView().setVisibility(View.VISIBLE);
            }
        }
        if (mapLayoutObj.containsKey(NPGeneral.NativeLayoutType.PriceView)) {
            adView.setPriceView((View) mapLayoutObj.get(NPGeneral.NativeLayoutType.PriceView));
            if (nativeAd.getPrice() == null) {
                adView.getPriceView().setVisibility(View.INVISIBLE);
            } else {
                adView.getPriceView().setVisibility(View.VISIBLE);
                ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
            }
        }
        if (mapLayoutObj.containsKey(NPGeneral.NativeLayoutType.StarRatingView)) {
            adView.setStarRatingView((View) mapLayoutObj.get(NPGeneral.NativeLayoutType.StarRatingView));
            if (nativeAd.getStarRating() == null) {
                adView.getStarRatingView().setVisibility(View.INVISIBLE);
            } else {
                ((RatingBar) adView.getStarRatingView())
                        .setRating(nativeAd.getStarRating().floatValue());
                adView.getStarRatingView().setVisibility(View.VISIBLE);
            }
        }
        if (mapLayoutObj.containsKey(NPGeneral.NativeLayoutType.StoreView)) {
            adView.setStoreView((View) mapLayoutObj.get(NPGeneral.NativeLayoutType.StoreView));
            if (nativeAd.getStore() == null) {
                adView.getStoreView().setVisibility(View.INVISIBLE);
            } else {
                adView.getStoreView().setVisibility(View.VISIBLE);
                ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
            }
        }
        if (mapLayoutObj.containsKey(NPGeneral.NativeLayoutType.AdvertiserView)) {
            adView.setAdvertiserView((View) mapLayoutObj.get(NPGeneral.NativeLayoutType.AdvertiserView));
            if (nativeAd.getAdvertiser() == null) {
                adView.getAdvertiserView().setVisibility(View.GONE);
            } else {
                ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
                adView.getAdvertiserView().setVisibility(View.VISIBLE);
            }
        }
        adView.setNativeAd(nativeAd);

    }

    private void facebookNativeAd() {
        NPGeneral.NativeAdsType nativeAdsType = mapLayoutObj.containsKey(NPGeneral.NativeLayoutType.NativeAdsType) ? (NPGeneral.NativeAdsType) mapLayoutObj.get(NPGeneral.NativeLayoutType.NativeAdsType) : NPGeneral.NativeAdsType.NativeAd;
        if (nativeAdsType == NPGeneral.NativeAdsType.NativeAd) {
            nativeAdFacebook = new NativeAd(context, adUnit);
            nativeAdListenerFacebook = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                    // Native ad finished downloading all assets
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    // Native ad failed to load
                   /* if (nativeAdFacebook != null) {
                        nativeAdFacebook.loadAd(nativeAdFacebook.buildLoadAdConfig().withAdListener(nativeAdListenerFacebook).build());
                    }*/
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    // Native ad is loaded and ready to be displayed
                    // Race condition, load() called again before last ad was displayed
                    if (nativeAdFacebook == null || nativeAdFacebook != ad) {
                        return;
                    }
                    // Inflate Native Ad into Container
                    facebookInflateAd(nativeAdFacebook, null);
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
        } else {
            nativeBannerAdFacebook = new NativeBannerAd(context, adUnit);

            nativeAdListenerFacebook = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {

                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    if (nativeBannerAdFacebook != null) {
                        //nativeBannerAdFacebook.destroy();
                        //nativeBannerAdFacebook.loadAd(nativeBannerAdFacebook.buildLoadAdConfig().withAdListener(nativeAdListenerFacebook).build());
                    }
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    // Native ad is loaded and ready to be displayed
                    // Race condition, load() called again before last ad was displayed
                    if (nativeBannerAdFacebook == null || nativeBannerAdFacebook != ad) {
                        return;
                    }
                    // Inflate Native Banner Ad into Container
                    facebookInflateAd(null, nativeBannerAdFacebook);
                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };
            // load the ad
            if (nativeBannerAdFacebook != null)
                nativeBannerAdFacebook.loadAd(nativeBannerAdFacebook.buildLoadAdConfig().withAdListener(nativeAdListenerFacebook).build());
        }
    }

    private void facebookInflateAd(NativeAd nativeAd, NativeBannerAd nativeBannerAd) {

        if (nativeAd != null)
            nativeAd.unregisterView();

        if (nativeBannerAd != null)
            nativeBannerAd.unregisterView();

        View viewAdMain = LayoutInflater.from(context).inflate(R.layout.fb_native_ad_unit, null);

        if (mapLayoutObj.containsKey(NPGeneral.NativeLayoutType.AdHeight)) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewAdMain.getLayoutParams();
            layoutParams.height = (int) mapLayoutObj.get(NPGeneral.NativeLayoutType.AdHeight);
            viewAdMain.setLayoutParams(layoutParams);
        }

        if (nativeAdLayout == null) {
            nativeAdLayout = viewAdMain.findViewById(R.id.native_ad_container);
            nativeAdLayout.addView(adViewContainNative);
        }
        // Add the AdOptionsView
        if (mapLayoutObj.containsKey(NPGeneral.NativeLayoutType.AdChoicesContainer)) {
            LinearLayout adChoicesContainer = (LinearLayout) mapLayoutObj.get(NPGeneral.NativeLayoutType.AdChoicesContainer);
            AdOptionsView adOptionsView = null;

            if (nativeAd != null) {
                adOptionsView = new AdOptionsView(context, nativeAd, nativeAdLayout);
                adChoicesContainer.removeAllViews();
                adChoicesContainer.addView(adOptionsView, 0);
            } else if (nativeBannerAd != null) {
                adOptionsView = new AdOptionsView(context, nativeBannerAd, nativeAdLayout);
                adChoicesContainer.removeAllViews();
                adChoicesContainer.addView(adOptionsView, 0);
            }
        }
        // Create native UI using the ad metadata.
        com.facebook.ads.MediaView nativeAdMedia = null;
        if (mapLayoutObj.containsKey(NPGeneral.NativeLayoutType.MediaView)) {
            nativeAdMedia = (com.facebook.ads.MediaView) mapLayoutObj.get(NPGeneral.NativeLayoutType.MediaView);
        }
        if (mapLayoutObj.containsKey(NPGeneral.NativeLayoutType.BodyView)) {
            TextView nativeAdBody = (TextView) mapLayoutObj.get(NPGeneral.NativeLayoutType.BodyView);
            nativeAdBody.setText(nativeAd != null ? nativeAd.getAdBodyText() : nativeBannerAdFacebook != null ? nativeBannerAdFacebook.getAdBodyText() : "");
        }
        TextView nativeAdTitle = null;
        if (mapLayoutObj.containsKey(NPGeneral.NativeLayoutType.HeadlineView)) {
            nativeAdTitle = (TextView) mapLayoutObj.get(NPGeneral.NativeLayoutType.HeadlineView);
            nativeAdTitle.setText(nativeAd != null ? nativeAd.getAdvertiserName() : nativeBannerAdFacebook != null ? nativeBannerAdFacebook.getAdvertiserName() : "");
        }
        if (mapLayoutObj.containsKey(NPGeneral.NativeLayoutType.AdSocialContext)) {
            TextView nativeAdSocialContext = (TextView) mapLayoutObj.get(NPGeneral.NativeLayoutType.AdSocialContext);
            nativeAdSocialContext.setText(nativeAd != null ? nativeAd.getAdSocialContext() : nativeBannerAdFacebook != null ? nativeBannerAdFacebook.getAdSocialContext() : "");
        }
        if (mapLayoutObj.containsKey(NPGeneral.NativeLayoutType.SponsoredLabel)) {
            TextView sponsoredLabel = (TextView) mapLayoutObj.get(NPGeneral.NativeLayoutType.SponsoredLabel);
            sponsoredLabel.setText(nativeAd != null ? nativeAd.getSponsoredTranslation() : nativeBannerAdFacebook != null ? nativeBannerAdFacebook.getSponsoredTranslation() : "");
        }
        Button nativeAdCallToAction = null;
        if (mapLayoutObj.containsKey(NPGeneral.NativeLayoutType.CallToActionView)) {
            nativeAdCallToAction = (Button) mapLayoutObj.get(NPGeneral.NativeLayoutType.CallToActionView);
            nativeAdCallToAction.setVisibility(nativeAd != null && nativeAd.hasCallToAction() ? View.VISIBLE : nativeBannerAdFacebook != null && nativeBannerAdFacebook.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
            nativeAdCallToAction.setText(nativeAd != null ? nativeAd.getAdCallToAction() : nativeBannerAdFacebook != null ? nativeBannerAdFacebook.getAdCallToAction() : "");
        }

        com.facebook.ads.MediaView nativeAdIcon = null;
        if (mapLayoutObj.containsKey(NPGeneral.NativeLayoutType.IconView)) {
            nativeAdIcon = (com.facebook.ads.MediaView) mapLayoutObj.get(NPGeneral.NativeLayoutType.IconView);
        }

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        if (nativeAdTitle != null)
            clickableViews.add(nativeAdTitle);
        if (nativeAdCallToAction != null)
            clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        if (nativeAd != null) {
            if (nativeAdMedia != null && nativeAdIcon != null && clickableViews.size() > 0)
                nativeAd.registerViewForInteraction(adViewContainNative, nativeAdMedia, nativeAdIcon, clickableViews);
        } else if (nativeBannerAd != null) {
            if (nativeAdIcon != null && clickableViews.size() > 0)
                nativeBannerAd.registerViewForInteraction(adViewContainNative, nativeAdIcon, clickableViews);
        }
        adContainerView.addView(viewAdMain);
    }


}
