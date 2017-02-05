package project.sideproject.com.zumperinterview.service;

import project.sideproject.com.zumperinterview.model.item_search.Restaurant;
import project.sideproject.com.zumperinterview.model.search.Places;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Shishir on 2/4/2017.
 */
public interface GetDataService {
    String endpoint = "https://maps.googleapis.com/maps/api/place/nearbysearch/";

    String itemDetailUrl = "https://maps.googleapis.com/maps/api/place/details/";

    @GET("json?type=restaurant&rankby=distance")
    Call<Places> getNearbyRestaurants(@Query("location") String location, @Query("key") String key);

    @GET("json?")
    Call<Restaurant> getRestaurantDetails(@Query("placeid") String placeId, @Query("key") String key);

}
