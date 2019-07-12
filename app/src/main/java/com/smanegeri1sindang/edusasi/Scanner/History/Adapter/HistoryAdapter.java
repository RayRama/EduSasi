package com.smanegeri1sindang.edusasi.Scanner.History.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muddzdev.styleabletoast.StyleableToast;
import com.smanegeri1sindang.edusasi.R;
import com.smanegeri1sindang.edusasi.Scanner.History.Entity.History;
import com.smanegeri1sindang.edusasi.YouTubeDL.YouTubeDownloader.DownloadActivity;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    // Variables
    private List<History> historyList;
    private Context context;

    public HistoryAdapter(List<History> historyList) {
        this.historyList = historyList;
        historyList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = null;

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_scan_history_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        context = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.context.setText(historyList.get(position).getContext());
        holder.date.setText(historyList.get(position).getDate());

        holder.open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", historyList.get(position).getContext());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();*/

                String url;
                if(Patterns.WEB_URL.matcher(historyList.get(position).getContext()).matches()) {
                    url = historyList.get(position).getContext();
                }else {
                    url = "http://www.google.com/#q=" + historyList.get(position).getContext();
                }
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
            }
        });

        if(Patterns.WEB_URL.matcher(historyList.get(position).getContext()).matches()) {
            holder.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StyleableToast.makeText(context.getApplicationContext(), "This feature is disabled", R.style.alert_scan).show();
                }
            });
            holder.category_img.setImageResource(R.drawable.ic_category);
        }else {
            holder.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isOnline()) {
                        Intent intent = new Intent(context.getApplicationContext(), DownloadActivity.class);
                        intent.putExtra("video_id", historyList.get(position).getContext());
                        context.startActivity(intent);
                        Toast.makeText(context.getApplicationContext(), "Download", Toast.LENGTH_SHORT).show();
                    } else {
                        StyleableToast.makeText(context.getApplicationContext(), "Error, no internet available", R.style.alert_scan).show();
                    }

                }
            });
            holder.category.setText("Other");
            holder.category_img.setImageResource(R.drawable.ic_other);
        }

    }

    protected boolean isOnline() {
        ConnectivityManager connectivityManager =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

  public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView context, category, date;
        public Button open, download, share;
        public ImageView category_img;

        public ViewHolder(View itemView) {
            super(itemView);
            context = (TextView) itemView.findViewById(R.id.text_history);
            category = (TextView) itemView.findViewById(R.id.text_category);
            category_img = (ImageView) itemView.findViewById(R.id.categoryIcon);
            date = (TextView) itemView.findViewById(R.id.text_date);
            open = (Button) itemView.findViewById(R.id.open_btn);
            download = (Button) itemView.findViewById(R.id.download_btn);
        }

    }
}
