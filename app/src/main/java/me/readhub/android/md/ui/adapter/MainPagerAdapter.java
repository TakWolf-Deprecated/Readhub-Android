package me.readhub.android.md.ui.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.readhub.android.md.R;
import me.readhub.android.md.ui.viewholder.Controller;
import me.readhub.android.md.ui.viewholder.NewsListController;
import me.readhub.android.md.ui.viewholder.TopicListController;

public class MainPagerAdapter extends PagerAdapter {

    private static final int[] TITLE_IDS = {
            R.string.tab_topic,
            R.string.tab_news,
            R.string.tab_technews
    };

    private final Activity activity;
    private final List<Controller> controllerList = new ArrayList<>();

    public MainPagerAdapter(@NonNull Activity activity, @NonNull ViewPager viewPager) {
        this.activity = activity;
        controllerList.add(new TopicListController(activity, viewPager));
        controllerList.add(new NewsListController(activity, viewPager, NewsListController.TAB_NEWS));
        controllerList.add(new NewsListController(activity, viewPager, NewsListController.TAB_TECHNEWS));
    }

    @Override
    public int getCount() {
        return controllerList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return activity.getString(TITLE_IDS[position]);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Controller controller = controllerList.get(position);
        container.addView(controller.getContentView());
        return controller;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Controller controller = Controller.assertType(object);
        container.removeView(controller.getContentView());
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        Controller controller = Controller.assertType(object);
        return view == controller.getContentView();
    }

}
