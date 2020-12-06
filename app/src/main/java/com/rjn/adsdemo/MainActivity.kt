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

    }
}