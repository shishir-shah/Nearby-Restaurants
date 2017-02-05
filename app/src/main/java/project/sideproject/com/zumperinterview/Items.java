package project.sideproject.com.zumperinterview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import project.sideproject.com.zumperinterview.adapter.ItemAdapter;
import project.sideproject.com.zumperinterview.adapter.MainAdapter;

/**
 * Created by Shishir on 2/4/2017.
 */
public class Items extends AppCompatActivity{
    @BindView(R.id.selected_items_tool_bar) Toolbar toolbar;
    @BindView(R.id.selected_items_list) RecyclerView recycleList;

    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolBar();

        createRecyclerView();

        createAndSetCustomAdapter();
    }

    // Helper methods
    private void setupToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true)
    }

    private void createRecyclerView(){
        recycleList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleList.setLayoutManager(layoutManager);
    }

    private void createAndSetCustomAdapter(){
        adapter = new ItemAdapter();
        recycleList.setAdapter(adapter);
    }
}
