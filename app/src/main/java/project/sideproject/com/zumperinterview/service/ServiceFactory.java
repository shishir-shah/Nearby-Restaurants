package project.sideproject.com.zumperinterview.service;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shishir on 2/4/2017.
 */
public class ServiceFactory {

    public static <T> T createRetrofitService(final Class<T> clazz, final String endPoint) {

        final Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        T service = restAdapter.create(clazz);

        return service;
    }
}
