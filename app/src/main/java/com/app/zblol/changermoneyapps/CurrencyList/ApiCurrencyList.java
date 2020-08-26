package com.app.zblol.changermoneyapps.CurrencyList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCurrencyList {

String apiKey= "latest?access_key=4019896702a9dfa26551a68fa0ac3638";

    @GET(apiKey)
    Call<List<PostCode>> getPostCodes();
}
