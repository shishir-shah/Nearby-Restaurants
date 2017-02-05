package project.sideproject.com.zumperinterview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    List<RestaurantModel> dataList;

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
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
    }
}
