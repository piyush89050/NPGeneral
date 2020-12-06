package com.rjn.adsdemo

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
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
    var npNativeBannerAds: NPNativeBannerAds? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // NPToken(this).execute()

        NPApplication(this)
        //npNativeAds = NPNativeAds(this, "298525301324695_298527264657832", NPAdsType.GOOGLE, adView_Container_Native)
        npNativeAds = NPNativeAds(this, "298525301324695_298525967991295", NPAdsType.GOOGLE, adView_Container_Native);
        //npNativeBannerAds = NPNativeBannerAds(this, "298525301324695_298525967991295", NPAdsType.FACEBOOK, adView_Container_Native)

        npBannerAds = NPBannerAds(this, "298525301324695_298527264657832", NPAdsType.FACEBOOK, adView_Container)

        npInterstitialAds = NPInterstitialAds(this, "298525301324695_298527867991105", NPAdsType.FACEBOOK)
        npInterstitialAds?.setAdListener(object : NPAdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
            }
        })
        npInterstitialAds?.loadAd()

        btnInterstitial.setOnClickListener {
            npInterstitialAds?.show()
        }

        btnReloadBanner.setOnClickListener {
            npBannerAds?.loadBanner()
        }
    }
}