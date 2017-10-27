package me.readhub.android.md.ui.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.readhub.android.md.R;
import me.readhub.android.md.model.entity.Topic;
import me.readhub.android.md.model.entity.TopicNews;
import me.readhub.android.md.ui.util.Navigator;
import me.readhub.android.md.util.FormatUtils;

public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.TopicViewHolder> {

    private final Activity activity;
    private final LayoutInflater inflater;
    private final List<Topic> topicList = new ArrayList<>();
    private final SparseBooleanArray expandStateMap = new SparseBooleanArray();
    private final List<View> newsViewPool = new ArrayList<>();

    public TopicListAdapter(@NonNull Activity activity) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    public List<Topic> getTopicList() {
        return topicList;
    }

    public void clearExpandStates() {
        expandStateMap.clear();
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TopicViewHolder(inflater.inflate(R.layout.item_topic, parent, false));
    }

    @Override
    public void onBindViewHolder(TopicViewHolder holder, int position) {
        holder.update(topicList.get(position), position);
    }

    class TopicViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_summary)
        TextView tvSummary;

        @BindView(R.id.tv_info)
        TextView tvInfo;

        @BindView(R.id.img_expand_state)
        ImageView imgExpandState;

        @BindView(R.id.layout_expand)
        ExpandableLayout layoutExpand;

        @BindView(R.id.layout_source)
        ViewGroup layoutSource;

        private Topic topic;
        private int position;

        TopicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void update(@NonNull Topic topic, int position) {
            this.topic = topic;
            this.position = position;
            tvTitle.setText(topic.getTitle());
            tvSummary.setText(topic.getSummary());
            tvSummary.setVisibility(TextUtils.isEmpty(topic.getSummary()) ? View.GONE : View.VISIBLE);
            tvInfo.setText(activity.getString(R.string.time___source_count, FormatUtils.getRelativeTimeSpanString(topic.getPublishDate()), topic.getNewsList().size()));
            boolean expand = expandStateMap.get(position, false);
            imgExpandState.setImageResource(expand ? R.drawable.ic_expand_less_grey600_18dp : R.drawable.ic_expand_more_grey600_18dp);
            layoutExpand.setExpanded(expand, false);
            adjustLayoutSourceChildren(topic.getNewsList().size());
            for (int i = 0; i < layoutSource.getChildCount(); i++) {
                TopicNews news = topic.getNewsList().get(i);
                View view = layoutSource.getChildAt(i);
                NewsViewHolder holder = (NewsViewHolder) view.getTag();
                if (holder == null) {
                    holder = new NewsViewHolder(view);
                    view.setTag(holder);
                }
                holder.update(news);
            }
        }

        void adjustLayoutSourceChildren(int count) {
            if (layoutSource.getChildCount() < count) {
                int offset = count - layoutSource.getChildCount();
                for (int i = 0; i < offset; i++) {
                    View view;
                    if (newsViewPool.isEmpty()) {
                        view = inflater.inflate(R.layout.item_topic_news, layoutSource, false);
                    } else {
                        view = newsViewPool.remove(0);
                    }
                    layoutSource.addView(view);
                }
            } else if (layoutSource.getChildCount() > count) {
                int offset = layoutSource.getChildCount() - count;
                for (int i = 0; i < offset; i++) {
                    View view = layoutSource.getChildAt(0);
                    layoutSource.removeView(view);
                    newsViewPool.add(view);
                }
            }
        }

        @OnClick(R.id.btn_item)
        void onBtnItemClick() {
            if (expandStateMap.get(position, false)) {
                expandStateMap.put(position, false);
                imgExpandState.setImageResource(R.drawable.ic_expand_more_grey600_18dp);
                layoutExpand.setExpanded(false);
            } else {
                expandStateMap.put(position, true);
                imgExpandState.setImageResource(R.drawable.ic_expand_less_grey600_18dp);
                layoutExpand.setExpanded(true);
            }
        }

    }

    class NewsViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_info)
        TextView tvInfo;

        private TopicNews news;

        NewsViewHolder(@NonNull View itemView) {
            ButterKnife.bind(this, itemView);
        }

        void update(@NonNull TopicNews news) {
            this.news = news;
            tvTitle.setText(news.getTitle());
            tvInfo.setText(activity.getString(R.string.site_name___time, news.getSiteName(), FormatUtils.getRelativeTimeSpanString(news.getPublishDate())));
        }

        @OnClick(R.id.btn_item)
        void onBtnItemClick() {
            Navigator.openArticle(activity, news.getTitle(), news.getMobileUrl());
        }

    }

}
