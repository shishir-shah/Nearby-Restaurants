package project.sideproject.com.zumperinterview.adapter;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.sideproject.com.zumperinterview.Fonts;
import project.sideproject.com.zumperinterview.OnItemClickListener;
import project.sideproject.com.zumperinterview.R;
import project.sideproject.com.zumperinterview.model.RestaurantModel.RestaurantModel;

/**
 * Created by Shishir on 2/4/2017.
 */


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private Context context;
    private Location currentLocation;
    private List<RestaurantModel> dataList;

    private final OnItemClickListener listener;

    public MainAdapter(OnItemClickListener listener){

        this.dataList = new ArrayList<>();
        this.listener = listener;
    }

    public void addItem(RestaurantModel item){
        dataList.add(item);
        notifyDataSetChanged();
    }

    public void deleteItems(){
        dataList.clear();
        notifyDataSetChanged();
    }

    public void setCurrentLocation(Location loc){
        this.currentLocation = loc;
    }

    public Location getCurrentLocation(){
        return this.currentLocation;
    }


    @Override
    public MainAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        RestaurantModel item = dataList.get(position);

        holder.bind(item,listener);

        loadName(holder, item);
        loadRatings(holder, item);
        loadImage(holder, item);
        loadAddress(holder, item);
        loadDistance(holder,item);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.name) TextView name;
        @BindView(R.id.rating) TextView rating;
        @BindView(R.id.image) ImageView image;
        @BindView(R.id.address) TextView address;
        @BindView(R.id.distance) TextView distance;

        public MyViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);

            name.setTypeface(Fonts.getRobotoBlack(v));
            rating.setTypeface(Fonts.getRobotoLight(v));
            address.setTypeface(Fonts.getRobotoLight(v));
            distance.setTypeface(Fonts.getRobotoLight(v));

        }

        public void bind(final RestaurantModel item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    // Helper methods

    private void loadName(MyViewHolder holder, RestaurantModel item) {
        String name = item.getName();
        if(name != null){
            holder.name.setText(name);
        }
        else {
            holder.name.setText("Name not found");
        }

    }

    private void loadRatings(MyViewHolder holder, RestaurantModel item) {

        if(item.getRating() != null){
            holder.rating.setText("Rating : " + String.valueOf(item.getRating()) + "/5");
        }

    }

    private void loadImage(MyViewHolder holder, RestaurantModel item) {
        Picasso.with(context)
                .load(item.getImage())
                .placeholder(R.drawable.not_available)
                .error(R.drawable.not_available)
                .into(holder.image);
    }

    private void loadAddress(MyViewHolder holder, RestaurantModel item) {
        String vicinity = item.getVicinity();
        if(vicinity != null){
            holder.address.setText(vicinity);
        }
    }

    private void loadDistance(MyViewHolder holder, RestaurantModel item) {

        // Get the location of restaurant obtained from response
        /*
        Location restaurantLoc = new Location("");
        restaurantLoc.setLatitude(item.getLatitude());
        restaurantLoc.setLongitude(item.getLongitude());
        */

        String distance = "N/A";

        LatLng current = null;
        LatLng destination = null;

        if (currentLocation != null) {
            current = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        }

        if (item.getLatitude() != null && item.getLongitude() != null) {
            destination = new LatLng(item.getLatitude(), item.getLongitude());
        }

        if (destination != null && current != null) {
            double dist = SphericalUtil.computeDistanceBetween(current, destination);
            distance = String.valueOf(Math.round(dist * 100) / 100);
        }

        //String distance = String.valueOf(getDistance(currentLocation.getLatitude(), currentLocation.getLongitude(), item.getLatitude(), item.getLongitude()));
        holder.distance.setText(distance + " mt");
    }



    /*
    public String getDistance(double lat1, double lon1, double lat2, double lon2){

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        // Rounding distance to two decimal places
        dist = Math.floor(dist*100)/100;
        return (Double.toString(dist) + " mi");
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
    */
}
