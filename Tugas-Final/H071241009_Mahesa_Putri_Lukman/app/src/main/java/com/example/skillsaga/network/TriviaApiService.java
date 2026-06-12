package com.example.skillsaga.network;

import com.example.skillsaga.model.TriviaResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TriviaApiService {

    @GET("api.php")
    Call<TriviaResponse> getQuestions(
            @Query("amount") int amount,
            @Query("category") Integer category,
            @Query("type") String type
    );
}