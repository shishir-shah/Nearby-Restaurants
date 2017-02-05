package project.sideproject.com.zumperinterview.service;

import project.sideproject.com.zumperinterview.model.search.Places;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Shishir on 2/4/2017.
 */
public interface GetDataService {
    String endpoint = "https://maps.googleapis.com/maps/api/place/nearbysearch/";

    @GET("?json/&type=restaurant&rankby=distance")
    Call<Places> getNearbyRestaurants(@Query("location") String location, @Query("key") String key);

    //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=33.790802,-118.135482&type=restaurant&radius=500&key=AIzaSyB-bpw0ollWA5AKpT11Y2CL2qPFs4kC_dk
}
