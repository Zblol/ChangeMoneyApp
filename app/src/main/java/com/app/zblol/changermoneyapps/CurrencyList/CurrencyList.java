package com.app.zblol.changermoneyapps.CurrencyList;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.app.zblol.changermoneyapps.R;

import org.w3c.dom.Text;

import java.util.List;

public class CurrencyList extends AppCompatActivity {

    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);

        textViewResult  = (TextView) findViewById(R.id.textViewResult);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiCurrencyList apiCurrencyList = retrofit.create(ApiCurrencyList.class);

        Call<List<PostCode>> call = apiCurrencyList.getPostCodes();

        call.enqueue(new Callback<List<PostCode>>() {
            @Override
            public void onResponse(Call<List<PostCode>> call, Response<List<PostCode>> response) {

                if(!response.isSuccessful()) {
                    textViewResult.setText("Code:" + response.code());
                    return;
                }

                List<PostCode> postcodes = response.body();

                for(PostCode postCode : postcodes) {
                    String content = "";
                    content+= postCode.getText();

                    textViewResult.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<PostCode>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }
}