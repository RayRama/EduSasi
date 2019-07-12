package com.smanegeri1sindang.edusasi.Scanner.History;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.muddzdev.styleabletoast.StyleableToast;
import com.smanegeri1sindang.edusasi.R;
import com.smanegeri1sindang.edusasi.Scanner.History.Adapter.HistoryAdapter;
import com.smanegeri1sindang.edusasi.Scanner.History.Entity.History;
import com.smanegeri1sindang.edusasi.Scanner.History.SQLite.ORM.HistoryORM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.smanegeri1sindang.edusasi.Scanner.History.Entity.History.byDate;

public class HistoryActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    // Init ui elements
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout historySwipeRefreshLayout;
    @BindView(R.id.history_item)
    RecyclerView historyRecyclerView;

    HistoryORM h = new HistoryORM();
    List<History> historyList;


    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    ImageView NotFound;
    TextView NotFoundText, NotFoundDesc;
    Button NotFoundBtn;
    ImageButton back_btn, clear_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_history);
        ButterKnife.bind(this);

        historySwipeRefreshLayout.setOnRefreshListener(this);

        historyList = new ArrayList<>();
        historyList = h.getAll(this);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        historyRecyclerView.setLayoutManager(layoutManager);
        getData();

        back_btn = (ImageButton) findViewById(R.id.btnBack);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        clear_btn = (ImageButton) findViewById(R.id.btnClear);
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
                builder.setTitle("Clear History");
                builder.setMessage("Are you sure to ?");
                builder.setNeutralButton("I'm sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (historyList.isEmpty()) {
                            StyleableToast.makeText(getBaseContext().getApplicationContext(), "History item is empty. Please make sure you have scan before", R.style.alert_scan).show();
                        } else {
                            h.clearAll(getApplicationContext());
                            historySwipeRefreshLayout.setRefreshing(true);
                            getData();
                            onRefresh();
                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Nothing*/
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                alert.setCanceledOnTouchOutside(false);
            }
        });

        NotFound = (ImageView) findViewById(R.id.image_view_empty_box);
        NotFoundText = (TextView) findViewById(R.id.text_view_no_item_placeholder);
        NotFoundDesc = (TextView) findViewById(R.id.text_view_no_item_desc);
        NotFoundBtn = (Button) findViewById(R.id.back_to_scanner);
            NotFoundBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        if (historyList.isEmpty()) {
            NotFound.setVisibility(View.VISIBLE);
            NotFoundText.setVisibility(View.VISIBLE);
            NotFoundDesc.setVisibility(View.VISIBLE);
            NotFoundBtn.setVisibility(View.VISIBLE);
        } else {
            NotFound.setVisibility(View.GONE);
            NotFoundText.setVisibility(View.GONE);
            NotFoundDesc.setVisibility(View.GONE);
            NotFoundBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
                historySwipeRefreshLayout.setRefreshing(false);
                if (historyList.isEmpty()) {
                    NotFound.setVisibility(View.VISIBLE);
                    NotFoundText.setVisibility(View.VISIBLE);
                    NotFoundDesc.setVisibility(View.VISIBLE);
                    NotFoundBtn.setVisibility(View.VISIBLE);
                } else {
                    NotFound.setVisibility(View.GONE);
                    NotFoundText.setVisibility(View.GONE);
                    NotFoundDesc.setVisibility(View.GONE);
                    NotFoundBtn.setVisibility(View.GONE);
                }
            }
        }, 2000);
    }

    private void getData() {
        historyList = h.getAll(getApplicationContext());
        adapter = new HistoryAdapter(historyList);
        historyRecyclerView.setAdapter(adapter);
        Collections.sort(historyList, byDate);
        adapter.notifyDataSetChanged();
    }
}
