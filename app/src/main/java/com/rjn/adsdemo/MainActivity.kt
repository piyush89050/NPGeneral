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
        //npNativeAds = NPNativeAds(this, "", NPAdsType.GOOGLE, adView_Container_Native)
        npNativeAds = NPNativeAds(this, "", NPAdsType.GOOGLE, adView_Container_Native);
        //npNativeBannerAds = NPNativeBannerAds(this, "", NPAdsType.FACEBOOK, adView_Container_Native)

        npBannerAds = NPBannerAds(this, "", NPAdsType.FACEBOOK, adView_Container)

        npInterstitialAds = NPInterstitialAds(this, "", NPAdsType.FACEBOOK)
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