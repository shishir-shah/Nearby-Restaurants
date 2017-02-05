package project.sideproject.com.zumperinterview;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.sideproject.com.zumperinterview.adapter.MainAdapter;
import project.sideproject.com.zumperinterview.model.search.Places;
import project.sideproject.com.zumperinterview.service.GetDataService;
import project.sideproject.com.zumperinterview.service.ServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recycleList) RecyclerView recycleList;
    @BindView(R.id.tool_bar) Toolbar toolbar;

    private MainAdapter adapter;

    public static final String API_KEYS = "API_KEYS";
    public static final String GOOGLE_PLACES_KEY = "GOOGLE_PLACES_KEY";
    public static final String GOOGLE_MAPS_KEY="GOOGLE_MAPS_KEY";

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
    protected void onStart() {
        super.onStart();

        /*Create a recycler view for loading of nearby restaurants*/
        createRecyclerView();

        /*Create a custom adpater to set view for items in
        * previously created recycler view*/
        createAndSetCustomAdapter();

        /*Make an API call to Google Places API using Restrofit library
        * Once the result is obtained it is loaded into the list of items
        * using RxJava asynchronously*/
        makeAPICall();
    }


    // Helper Methods

    private void makeAPICall() {

        String key = getSharedPreferences(API_KEYS,MODE_PRIVATE).getString(GOOGLE_PLACES_KEY,"No key found");

        Call<Places> call = ServiceFactory
                .createRetrofitService(GetDataService.class,GetDataService.endpoint)
                .getNearbyRestaurants("33.790802,-118.135482",key);

        call.enqueue(new Callback<Places>() {
            @Override
            public void onResponse(Call<Places> call, Response<Places> response) {
                int statusCode = response.code();

                Log.i("onResponse",statusCode+"");
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
        adapter = new MainAdapter();
        recycleList.setAdapter(adapter);
    }

    private void addKeys() {
        SharedPreferences pref = getSharedPreferences(API_KEYS,MODE_PRIVATE);
        pref.edit().putString(GOOGLE_PLACES_KEY, "AIzaSyB-bpw0ollWA5AKpT11Y2CL2qPFs4kC_dk")
                .putString(GOOGLE_MAPS_KEY, "AIzaSyBt08WxEypilTzyi2fQBm9OBIgzSt3uk2g").commit();

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

}
