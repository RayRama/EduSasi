package com.smanegeri1sindang.edusasi.News.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.listeners.OnClickListener;
import com.mikepenz.fastadapter_extensions.items.ProgressItem;
import com.smanegeri1sindang.edusasi.News.NewsDetailActivity;
import com.smanegeri1sindang.edusasi.News.adapters.RelatedPostsAdapter;
import com.smanegeri1sindang.edusasi.News.models.post.Post;
import com.smanegeri1sindang.edusasi.News.network.ApiClient;
import com.smanegeri1sindang.edusasi.News.network.ApiInterface;
import com.smanegeri1sindang.edusasi.News.network.GetRecentPost;
import com.smanegeri1sindang.edusasi.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RelatedPostFrag extends Fragment {

    private static final String ARG_CAT_LIST = "arg_cats";
    private static final String ARG_POST_TO_EXCLUDE = "arg_exclude";
    private static final String ARG_COUNT = "arg_count";

    private int count;
    private int page = 1;
    private String query_category_string = null;
    private String postsToExclude;
    private boolean isActive;
    private FastAdapter fastAdapter;
    private ItemAdapter<ProgressItem> footerAdapter;
    ItemAdapter itemAdapter = new ItemAdapter();
    private List<Post> postList = new ArrayList<>();

    @BindView(R.id.relatedPostFragmentRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.relatedProgressBar)
    ProgressBar progressBar;
    @BindView(R.id.reloadBtn)
    Button reloadBtn;

    public RelatedPostFrag() {
        // Required empty public constructor
    }

    public static RelatedPostFrag newInstance(int count, String categories, String postsToExclude) {
        RelatedPostFrag fragment = new RelatedPostFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_COUNT, count);
        args.putString(ARG_CAT_LIST, categories);
        args.putString(ARG_POST_TO_EXCLUDE, postsToExclude);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            count = getArguments().getInt(ARG_COUNT);
            query_category_string = getArguments().getString(ARG_CAT_LIST);
            postsToExclude = getArguments().getString(ARG_POST_TO_EXCLUDE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_releated_post, container, false);
        ButterKnife.bind(this, view);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        fastAdapter = new FastItemAdapter().with(itemAdapter);
        recyclerView.setAdapter(fastAdapter);
        fastAdapter.withOnClickListener(new OnClickListener() {
            @Override
            public boolean onClick(View v, IAdapter adapter, IItem item, int position) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra(NewsDetailActivity.ARG_TITLE, postList.get(position).getTitle().getRendered());
                intent.putExtra(NewsDetailActivity.ARG_IMAGE, postList.get(position).getBetterFeaturedImage().getSourceUrl());
                intent.putExtra(NewsDetailActivity.ARG_POSTID, postList.get(position).getId());
                startActivity(intent);
                return true;
            }
        });
        sendRequest(page);
        return view;
    }

    private void sendRequest(int page){
        reloadBtn.setVisibility(View.GONE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        GetRecentPost getRecentPost = new GetRecentPost(apiInterface,getActivity());
        getRecentPost.setExcluded(postsToExclude);
        getRecentPost.setCategory(query_category_string);
        if (count!=0)
            getRecentPost.setPerPage(count);
        getRecentPost.setPage(page);
        getRecentPost.setOnCompleteListner(new GetRecentPost.Listner() {
            @Override
            public void onSuccessful(List<Post> posts, int totalPosts, int totalPages) {
                if(isActive) {
                    postList.addAll(posts);
                    for (Post p : posts) {
                        itemAdapter.add(new RelatedPostsAdapter(p,getContext()));
                    }
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(String msg) {
                reloadBtn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

            }
        });
        getRecentPost.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        isActive = true;
    }

    @Override
    public void onPause() {
        isActive = false;
        super.onPause();
    }
}