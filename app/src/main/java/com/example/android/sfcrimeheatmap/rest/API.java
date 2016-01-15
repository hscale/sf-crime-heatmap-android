package com.example.android.sfcrimeheatmap.rest;

import com.example.android.sfcrimeheatmap.rest.models.CrimeIncidentStatistic;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface API {

    @GET("/resource/cuks-n6tp.json")
    Call<ArrayList<CrimeIncidentStatistic>> getCrimeCountsPerDistrict(@Query("$select") String selectClause,
                                                                      @Query("$where") String whereClause,
                                                                      @Query("$group") String groupClause);

    @GET("/resource/cuks-n6tp.json")
    Call<ArrayList<CrimeIncidentStatistic>> getCrimeIncidentsPerDistrict(@Query("$where") String whereClause,
                                                                         @Query("$offset") Integer offsetClause,
                                                                         @Query("$limit") Integer limitClause);
}