package project.sideproject.com.zumperinterview;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
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
import project.sideproject.com.zumperinterview.adapter.ItemAdapter;
import project.sideproject.com.zumperinterview.model.RestaurantModel.RestaurantModel;
import project.sideproject.com.zumperinterview.model.item_search.Restaurant;
import project.sideproject.com.zumperinterview.service.GetDataService;
import project.sideproject.com.zumperinterview.service.ServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Shishir on 2/4/2017.
 */
public class Items extends AppCompatActivity{

    @BindView(R.id.selected_items_tool_bar) Toolbar toolbar;
    @BindView(R.id.selected_items_list) RecyclerView recycleList;

    private ItemAdapter adapter;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_item_layout);
        ButterKnife.bind(this);

        setupToolBar();

        Intent intent = getIntent();
        String placeId = intent.getExtras().getString("placeId");
        currentLocation = intent.getExtras().getParcelable("Location");

        createRecyclerView();

        createAndSetCustomAdapter();

        makeAPICallForSelectedItem(placeId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.selected_items_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.clear) {
            adapter.clearItems();
        }
        return super.onOptionsItemSelected(item);
    }

    // Helper methods

    private void makeAPICallForSelectedItem(String placeId) {

        final String key = getSharedPreferences(Keys.API_KEYS,MODE_PRIVATE).getString(Keys.GOOGLE_PLACES_KEY,"No key found");

        Call<Restaurant> call = ServiceFactory
                .createRetrofitService(GetDataService.class,GetDataService.itemDetailUrl)
                .getRestaurantDetails(placeId,key);

        call.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {

                Observable<RestaurantModel> observable = AsyncLoad.getSingleRestaurant(response.body(), key);
                observable.onBackpressureBuffer()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DataSubscriber());
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
            }
        });

    }

    private void setupToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void createRecyclerView(){
        recycleList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleList.setLayoutManager(layoutManager);
    }

    private void createAndSetCustomAdapter(){
        adapter = new ItemAdapter(new Listener());
        recycleList.setAdapter(adapter);
    }

    private class DataSubscriber extends Subscriber<RestaurantModel> {

        @Override
        public void onCompleted() {
            Log.i("onCompeleted","true");
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

            if(currentLocation != null && item.getLongitude()!= null  && item.getLatitude() != null){

                Uri uri = Uri.parse("http://maps.google.com/maps?saddr=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude() + "&daddr=" + item.getLatitude() + "," + item.getLongitude() + "\"");

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        }

    }
}
