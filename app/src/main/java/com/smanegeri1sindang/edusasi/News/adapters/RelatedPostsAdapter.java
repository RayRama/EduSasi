package com.smanegeri1sindang.edusasi.News.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.smanegeri1sindang.edusasi.News.models.post.Post;
import com.smanegeri1sindang.edusasi.R;

import org.jsoup.Jsoup;

import java.util.List;

public class RelatedPostsAdapter extends AbstractItem<RelatedPostsAdapter, RelatedPostsAdapter.ViewHolder> {
    private Post post;
    private Context context;

    public RelatedPostsAdapter(Post post, Context context) {
        this.post = post;
        this.context = context;
    }

   @Override
    public int getType() {
        return R.id.postListHorizontal;
   }

   @Override
    public int getLayoutRes() {
        return R.layout.post_list_horizontal;
   }

    protected static class ViewHolder extends FastAdapter.ViewHolder<RelatedPostsAdapter> {
        TextView title, category;
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            this.title = view.findViewById(R.id.horizontalTexView);
            this.category = view.findViewById(R.id.horizontalCategory);
            this.imageView = view.findViewById(R.id.horizontalImgView);
        }

        @Override
        public void bindView(RelatedPostsAdapter item, List<Object> payloads) {
            title.setText(Jsoup.parse(item.post.getTitle().getRendered()).text());
            category.setText(Jsoup.parse(item.post.getCategoriesDetail().toString()).text());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.gradient_background);
            requestOptions.error(R.color.md_red_50);

            if (item.post.getBetterFeaturedImage() !=
                null && item.post.getBetterFeaturedImage().getSourceUrl() != null) {
                Glide.with(item.context)
                        .applyDefaultRequestOptions(requestOptions)
                        .load(item.post.getBetterFeaturedImage().getSourceUrl())
                        .into(imageView);
            }
        }

        @Override
        public void unbindView(RelatedPostsAdapter item) {

        }
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }
}
