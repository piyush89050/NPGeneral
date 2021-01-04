package com.rjn.adsdemo

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.rjn.npgeneral.ads.*
import kotlinx.android.synthetic.main.activity_main.*

/*
 * Copyright (C) 2020 NP SoftTech.
 *
 * Created By Piyush Narola
 * Mo. +91 8153012869
 */

class MainActivity : AppCompatActivity() {

    var npInterstitialAds: NPInterstitialAds? = null
    var npBannerAds: NPBannerAds? = null
    var npNativeAds: NPNativeAds? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NPApplication(this, BuildConfig.DEBUG)

        /*val adView: LinearLayout =
            layoutInflater.inflate(R.layout.google_native_ad_unit, null) as LinearLayout

        val mapAdsLayout: LinkedHashMap<NPGeneral.NativeLayoutType, Any> = LinkedHashMap()
        mapAdsLayout[NPGeneral.NativeLayoutType.NativeAdsType] =
            NPGeneral.NativeAdsType.NativeAd
        mapAdsLayout[NPGeneral.NativeLayoutType.MediaView] = adView.findViewById(R.id.ad_media)
        mapAdsLayout[NPGeneral.NativeLayoutType.HeadlineView] =
            adView.findViewById(R.id.ad_headline)
        mapAdsLayout[NPGeneral.NativeLayoutType.BodyView] = adView.findViewById(R.id.ad_body)
        mapAdsLayout[NPGeneral.NativeLayoutType.CallToActionView] =
            adView.findViewById(R.id.ad_call_to_action)
        mapAdsLayout[NPGeneral.NativeLayoutType.IconView] = adView.findViewById(R.id.ad_app_icon)
        mapAdsLayout[NPGeneral.NativeLayoutType.PriceView] = adView.findViewById(R.id.ad_price)
        mapAdsLayout[NPGeneral.NativeLayoutType.StarRatingView] = adView.findViewById(R.id.ad_stars)
        mapAdsLayout[NPGeneral.NativeLayoutType.StoreView] = adView.findViewById(R.id.ad_store)
        mapAdsLayout[NPGeneral.NativeLayoutType.AdvertiserView] =
            adView.findViewById(R.id.ad_advertiser)*/

        val adView: LinearLayout =
            layoutInflater.inflate(R.layout.fb_native_ad_contain, null) as LinearLayout
        val mapAdsLayout: LinkedHashMap<NPGeneral.NativeLayoutType, Any> = LinkedHashMap()

        /* mapAdsLayout[NPGeneral.NativeLayoutType.NativeAdsType] =
             NPGeneral.NativeAdsType.NativeAd*/

        mapAdsLayout[NPGeneral.NativeLayoutType.AdHeight] = 50;
        mapAdsLayout[NPGeneral.NativeLayoutType.AdChoicesContainer] =
            adView.findViewById(R.id.ad_choices_container)
        mapAdsLayout[NPGeneral.NativeLayoutType.MediaView] =
            adView.findViewById(R.id.native_ad_media)
        mapAdsLayout[NPGeneral.NativeLayoutType.BodyView] =
            adView.findViewById(R.id.native_ad_body)
        mapAdsLayout[NPGeneral.NativeLayoutType.HeadlineView] =
            adView.findViewById(R.id.native_ad_title)
        mapAdsLayout[NPGeneral.NativeLayoutType.AdSocialContext] =
            adView.findViewById(R.id.native_ad_social_context)
        mapAdsLayout[NPGeneral.NativeLayoutType.SponsoredLabel] =
            adView.findViewById(R.id.native_ad_sponsored_label)
        mapAdsLayout[NPGeneral.NativeLayoutType.IconView] =
            adView.findViewById(R.id.native_ad_icon)
        mapAdsLayout[NPGeneral.NativeLayoutType.CallToActionView] =
            adView.findViewById(R.id.native_ad_call_to_action)

        npNativeAds =
            NPNativeAds(
                this,
                "298525301324695_298525967991295",
                NPAdsType.FACEBOOK,
                adView_Container_Native,
                adView,
                mapAdsLayout
            )

        btnReloadBanner.setOnClickListener {
            npNativeAds!!.loadNativeAds()
        }


    }
}