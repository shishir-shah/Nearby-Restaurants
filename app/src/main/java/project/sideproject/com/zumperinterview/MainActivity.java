package project.sideproject.com.zumperinterview;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.sideproject.com.zumperinterview.adapter.MainAdapter;
import project.sideproject.com.zumperinterview.model.RestaurantModel.RestaurantModel;
import project.sideproject.com.zumperinterview.model.search.Places;
import project.sideproject.com.zumperinterview.service.GetDataService;
import project.sideproject.com.zumperinterview.service.ServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycleList) RecyclerView recycleList;
    @BindView(R.id.tool_bar) Toolbar toolbar;

    private Location currentLocation;
    private MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*ButterKnife library induces view injection
        to create optimized code*/
        ButterKnife.bind(this);

        /*Algorithm to check if the application is running for first time
        * If so, add the keys to shared preferences*/
        if(checkFirstRun()){
            addKeys();
        }

        setupToolBar();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();


        /*Get the current location*/
        currentLocation = getCurrentLocation();

        // Remove this code
        /*currentLocation = new Location("");
        currentLocation.setLatitude(33.790802);
        currentLocation.setLongitude(-118.135482);*/

        /*Create a recycler view for loading of nearby restaurants*/
        createRecyclerView();

        /*Create a custom adpater to set view for items in
        * previously created recycler view*/
        createAndSetCustomAdapter();

        /*Make an API call to Google Places API using Restrofit library
        * Once the result is obtained it is loaded into the list of items
        * using RxJava asynchronously*/
        makeAPICall(currentLocation);
    }

    // Helper Methods

    private Location getCurrentLocation() {
        GetLocation locationService = new GetLocation(MainActivity.this);

        if(locationService.canGetLocation()){
            return locationService.getLocation();
        }
        else {
            locationService.showSettings();
            return new Location("");
        }
    }

    private void makeAPICall(Location currentLocation) {

        String location ="";

        if(currentLocation != null){
            location = String.valueOf(currentLocation.getLatitude())+","+String.valueOf(currentLocation.getLongitude());
        }

        final String key = getSharedPreferences(Keys.API_KEYS,MODE_PRIVATE).getString(Keys.GOOGLE_PLACES_KEY,"No key found");

        Call<Places> call = ServiceFactory
                .createRetrofitService(GetDataService.class,GetDataService.endpoint)
                .getNearbyRestaurants(location, key);

       call.enqueue(new Callback<Places>() {
            @Override
            public void onResponse(Call<Places> call, Response<Places> response) {

                Log.i("onResponse",response.code()+"");
                Observable<RestaurantModel> observable = AsyncLoad.getObservable(response.body(),key);
                observable.onBackpressureBuffer()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DataSubscriber());
            }

            @Override
            public void onFailure(Call<Places> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
            }
        });
    }

    private void setupToolBar(){
        setSupportActionBar(toolbar);
    }

    private void createRecyclerView(){

        recycleList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleList.setLayoutManager(layoutManager);
    }

    private void createAndSetCustomAdapter(){
        adapter = new MainAdapter(new Listener());
        adapter.setCurrentLocation(currentLocation);
        recycleList.setAdapter(adapter);
    }

    private void addKeys() {
        SharedPreferences pref = getSharedPreferences(Keys.API_KEYS,MODE_PRIVATE);
        pref.edit().putString(Keys.GOOGLE_PLACES_KEY, "AIzaSyB-bpw0ollWA5AKpT11Y2CL2qPFs4kC_dk")
                .putString(Keys.GOOGLE_MAPS_KEY, "AIzaSyBt08WxEypilTzyi2fQBm9OBIgzSt3uk2g").commit();

    }

    private boolean checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;


        // Get current version code
        int currentVersionCode = 0;
        try {
            currentVersionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            // handle exception
            e.printStackTrace();
            return true;
        }

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run
            return false;

        } else if (savedVersionCode == DOESNT_EXIST) {


        } else if (currentVersionCode > savedVersionCode) {

        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).commit();
        return true;
    }

    private class DataSubscriber extends Subscriber<RestaurantModel>{

        @Override
        public void onCompleted() {
            Log.i("onComeleted","true");
        }

        @Override
        public void onError(Throwable e) {
            Log.e("onError",e.getMessage());
        }

        @Override
        public void onNext(RestaurantModel item) {
            adapter.addItem(item);
        }
    }

    private class Listener implements OnItemClickListener{

        @Override
        public void onItemClick(RestaurantModel item) {

            Intent intent = new Intent(MainActivity.this,Items.class);
            intent.putExtra("placeId",item.getPlaceId());
            intent.putExtra("Location",currentLocation);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }

    }
}
