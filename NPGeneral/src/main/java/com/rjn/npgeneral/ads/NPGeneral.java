package com.rjn.npgeneral.ads;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.google.android.gms.ads.AdSize;

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

    public enum NativeLayoutType {
        MediaView, BodyView, HeadlineView, CallToActionView, IconView, PriceView, StarRatingView, StoreView, AdvertiserView, NativeAdsType,
        AdChoicesContainer, AdSocialContext, SponsoredLabel
    }

    public enum NativeAdsType {
        NativeBannerAd, NativeAd
    }

}
