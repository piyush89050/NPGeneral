package com.rjn.npgeneral.ads;

/*
 * Copyright (C) 2020 NP SoftTech.
 *
 * Created By Piyush Narola
 * Mo. +91 8153012869
 */

public class NPRewardedAd {

    /**
     * Ad Unit ID to initialize a Sample Rewarded Ad.
     */
    private final String adUnitId;

    /**
     * A flag that indicates whether a rewarded ad is ready to show.
     */
    private boolean isAdAvailable;

    /**
     * A listener to forward any rewarded ad events.
     */
    private NPAdListener listener;

    /**
     * The reward amount associated with the ad.
     */
    private int reward;

    public NPRewardedAd(String adUnitId) {
        this.adUnitId = adUnitId;
    }

    /**
     * Sets the rewarded ad listener to which the rewarded ad events will be forwarded.
     */
    public void setListener(NPAdListener listener) {
        this.listener = listener;
    }

    /**
     * Returns a rewarded ad listener to forward the rewarded events.
     */
    public NPAdListener getListener() {
        return listener;
    }

    /**
     * Returns if the rewarded ad is available to show.
     */
    public boolean isAdAvailable() {
        return isAdAvailable;
    }

    /**
     * Gets the reward for this rewarded ad. Returns 0 until an ad is available.
     */
    public int getReward() {
        return reward;
    }

    /**
     * Loads a rewarded ad.
     */
    public void loadAd(NPAdRequest npAdRequest) {

    }

}
