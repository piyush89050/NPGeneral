package com.rjn.npgeneral.ads;

import java.util.Set;

public class NPAdRequest {

    public NPAdRequest(){

    }

    /**
     * Sets keywords for targeting purposes.
     *
     */
    public void setKeywords(Set<String> keywords) {
        // Normally we'd save the keywords. But since this is a sample network, we'll do nothing.
    }

    /**
     * Designates a request for test mode.
     */
    public void setTestMode(boolean useTesting) {
        // Normally we'd save this flag. But since this is a sample network, we'll do nothing.
    }

    public void setShouldAddAwesomeSauce(boolean shouldAddAwesomeSauce) {
        // Normally we'd save this flag but since this is a sample network, we'll do nothing.
    }

    public void setIncome(int income) {
        // Normally we'd save this value but since this is a sample network, we'll do nothing.
    }
}
