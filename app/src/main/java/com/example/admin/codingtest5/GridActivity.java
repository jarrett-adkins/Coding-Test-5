package com.example.admin.codingtest5;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.admin.codingtest5.data.RetrofitHelper;
import com.example.admin.codingtest5.model.Hit;
import com.example.admin.codingtest5.model.MyResponse;
import com.example.admin.codingtest5.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GridActivity extends AppCompatActivity {

    private static final String TAG = "GridActivity";

    @BindView(R.id.rvQueryList)
    RecyclerView rvQueryList;
    @BindView(R.id.tvStatus)
    TextView tvStatus;

    private String query;
    private int from, to;
    private List<Recipe> recipeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        ButterKnife.bind(this);

        query = getIntent().getStringExtra(getString(R.string.intent_query_key));
        setTitle(query);

        int cols = 3;
        if( getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE )
            cols = 5;

        GridLayoutManager manager = new GridLayoutManager(this, cols);
        rvQueryList.setLayoutManager(manager);
        rvQueryList.setItemAnimator(new DefaultItemAnimator());
        rvQueryList.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                Toast.makeText(GridActivity.this, "onLoadMore", Toast.LENGTH_SHORT).show();
//                from = to + 1;
//                to += 10;
            }
        });

        search(query, 0, 20);
    }

    public void search(String q, int from, int to) {
        tvStatus.setText( "Searching...");

        RetrofitHelper.getResponse(q, from, to)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<MyResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MyResponse myResponse) {
//                        Log.d(TAG, "onNext: " + myResponse.getQ());
//                        Log.d(TAG, "onNext: " + myResponse.getCount());

                        if (myResponse.getCount() > 0) {
                            List<Hit> hitList = myResponse.getHits();

                            for (Hit h : hitList) {
                                recipeList.add(h.getRecipe());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                        e.printStackTrace();
                        tvStatus.setText( "No Results.");
                    }

                    @Override
                    public void onComplete() {
                        MyItemListAdapter myItemListAdapter = new MyItemListAdapter(recipeList);
                        rvQueryList.setAdapter(myItemListAdapter);

                        String s = (recipeList.size() > 0) ? "" : "No Results";
                        tvStatus.setText( s );
                    }
                });
    }
}

//recycler view
//grid layout manager
//image scale fit xy