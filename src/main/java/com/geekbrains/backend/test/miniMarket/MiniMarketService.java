package com.geekbrains.backend.test.miniMarket;

import java.io.IOException;

import com.geekbrains.backend.test.miniMarket.model.Product;
import com.geekbrains.backend.test.miniMarket.model.ProductResponse;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MiniMarketService {

    private final MiniMarketApi api;

    public MiniMarketService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://minimarket1.herokuapp.com/market/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(MiniMarketApi.class);
    }

    public ProductResponse getProduct(Long id) throws IOException {
        Response<Product> response = api.getProduct(id).execute();
        ProductResponse productResponse = new ProductResponse();
        if (response.isSuccessful()) {
            productResponse.setProduct(response.body());
        } else {
            productResponse.setError(response.errorBody().string());
        }
        return productResponse;
    }
}
