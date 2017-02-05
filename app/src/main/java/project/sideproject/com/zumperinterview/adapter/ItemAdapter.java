package project.sideproject.com.zumperinterview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.sideproject.com.zumperinterview.Fonts;
import project.sideproject.com.zumperinterview.R;
import project.sideproject.com.zumperinterview.model.RestaurantModel.RestaurantModel;

/**
 * Created by Shishir on 2/4/2017.
 */
public class ItemAdapter extends  RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private List<RestaurantModel> dataList;
    private Context context;

    public ItemAdapter(){
        dataList = new ArrayList<>();
    }

    public void addItem(RestaurantModel item){
        dataList.add(item);
        notifyDataSetChanged();
    }
    public void clearItems(){
        dataList.clear();
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.cardview_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RestaurantModel item = dataList.get(position);

        loadName(holder, item);
        loadRatings(holder, item);
        loadImage(holder, item);
        loadAddress(holder, item);
        loadPhoneNumber(holder,item);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.title) TextView name;
        @BindView(R.id.place_rating) TextView rating;
        @BindView(R.id.item_image) ImageView image;
        @BindView(R.id.place_address) TextView address;
        @BindView(R.id.phone_number) TextView phoneNumber;
        @BindView(R.id.price_level) TextView priceLevel;
        public MyViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);

            name.setTypeface(Fonts.getRobotoBlack(v));
            rating.setTypeface(Fonts.getRobotoRegular(v));
            address.setTypeface(Fonts.getRobotoRegular(v));
            phoneNumber.setTypeface(Fonts.getRobotoRegular(v));
            priceLevel.setTypeface(Fonts.getRobotoRegular(v));
        }
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
        String address = item.getFormattedAddress();
        if(address != null){
            holder.address.setText("Address : "+address);
        }
    }

    private void loadPhoneNumber(MyViewHolder holder, RestaurantModel item) {
        String phoneNumber = item.getPhoneNumber();
        if(phoneNumber != null){
            holder.phoneNumber.setText("Phone : "+phoneNumber);
        }
    }

}
