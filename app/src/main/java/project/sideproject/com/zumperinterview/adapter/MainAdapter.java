package project.sideproject.com.zumperinterview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
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


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private Context context;

    private List<RestaurantModel> dataList;

    public MainAdapter(){
        dataList = new ArrayList<>();
    }

    public void addItem(RestaurantModel item){
        dataList.add(item);
        notifyDataSetChanged();
    }

    public void deleteItems(){
        dataList.clear();
        notifyDataSetChanged();
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

        loadName(holder,item);
        loadRatings(holder,item);
        loadImage(holder,item);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.name) TextView name;
        @BindView(R.id.rating) TextView rating;
        @BindView(R.id.image) ImageView image;

        public MyViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);

            name.setTypeface(Fonts.getRobotoLight(v));
            rating.setTypeface(Fonts.getRobotoLight(v));
        }
    }

    // Helper methods

    private void loadName(MyViewHolder holder, RestaurantModel item) {
        holder.name.setText(item.getName());
    }

    private void loadRatings(MyViewHolder holder, RestaurantModel item) {
        holder.name.setText(item.getRatings());
    }

    private void loadImage(MyViewHolder holder, RestaurantModel item) {

        Picasso.with(context)
                .load(item.getImage())
                .placeholder(R.drawable.not_available)
                .error(R.drawable.not_available)
                .into(holder.image);
    }
}
