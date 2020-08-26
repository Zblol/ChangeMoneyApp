package com.app.zblol.changermoneyapps.CurrencyList;

import com.google.gson.annotations.SerializedName;

public class PostCode {

    private String base;

    @SerializedName("Rate")
    private String text;

    public String getBase() {
        return base;
    }

    public String getText() {
        return text;
    }
}
