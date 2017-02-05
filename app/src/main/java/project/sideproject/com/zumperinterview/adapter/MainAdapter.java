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

    public void addItem(RestaurantModel item){
        dataList.add(item);
        notifyDataSetChanged();
    }

    public void setCurrentLocation(Location loc){
        this.currentLocation = loc;
    }

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

        holder.distance.setText(distance + " mt");
    }

}
