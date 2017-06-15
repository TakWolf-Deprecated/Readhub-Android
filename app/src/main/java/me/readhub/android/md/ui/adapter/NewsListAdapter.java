package me.readhub.android.md.ui.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.readhub.android.md.R;
import me.readhub.android.md.model.entity.News;
import me.readhub.android.md.ui.activity.DetailActivity;
import me.readhub.android.md.util.FormatUtils;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private final Activity activity;
    private final LayoutInflater inflater;
    private final List<News> newsList = new ArrayList<>();

    public NewsListAdapter(@NonNull Activity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    public List<News> getNewsList() {
        return newsList;
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.update(newsList.get(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_summary)
        TextView tvSummary;

        @BindView(R.id.tv_info)
        TextView tvInfo;

        private News news;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void update(@NonNull News news) {
            this.news = news;
            tvTitle.setText(news.getTitle());
            tvSummary.setText(news.getSummary());
            tvSummary.setVisibility(TextUtils.isEmpty(news.getSummary()) ? View.GONE : View.VISIBLE);
            if (TextUtils.isEmpty(news.getAuthorName())) {
                tvInfo.setText(activity.getString(R.string.site_name_$_time, news.getSiteName(), FormatUtils.getRelativeTimeSpanString(news.getPublishDate())));
            } else {
                tvInfo.setText(activity.getString(R.string.site_name_$_author_name_$_time, news.getSiteName(), news.getAuthorName(), FormatUtils.getRelativeTimeSpanString(news.getPublishDate())));
            }
        }

        @OnClick(R.id.btn_item)
        void onBtnItemClick() {
            DetailActivity.start(activity, news.getTitle(), news.getUrl());
        }

    }

}
