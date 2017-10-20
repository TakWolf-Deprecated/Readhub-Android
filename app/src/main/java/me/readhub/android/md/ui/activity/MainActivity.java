package me.readhub.android.md.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.readhub.android.md.R;
import me.readhub.android.md.ui.adapter.MainPagerAdapter;
import me.readhub.android.md.ui.base.FullLayoutActivity;
import me.readhub.android.md.ui.listener.NavigationOpenClickListener;
import me.readhub.android.md.ui.util.Navigator;
import me.readhub.android.md.ui.util.ToastUtils;

public class MainActivity extends FullLayoutActivity {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private MainPagerAdapter pagerAdapter;

    private long firstBackPressedTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        drawerLayout.setDrawerShadow(R.drawable.navigation_drawer_shadow, GravityCompat.START);
        toolbar.setNavigationOnClickListener(new NavigationOpenClickListener(drawerLayout));

        pagerAdapter = new MainPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            long secondBackPressedTime = System.currentTimeMillis();
            if (secondBackPressedTime - firstBackPressedTime > 2000) {
                ToastUtils.with(this).show(R.string.press_back_again_to_exit);
                firstBackPressedTime = secondBackPressedTime;
            } else {
                super.onBackPressed();
            }
        }
    }

    @OnClick(R.id.btn_visit_official_web)
    void onBtnVisitOfficialWebClick() {
        Navigator.openInBrowser(this, getString(R.string.official_home_page_content));
    }

    @OnClick(R.id.btn_share_to_friends)
    void onBtnShareToFriendsClick() {
        String text = getString(R.string.official_home_page_content) + "\n" + getString(R.string.app_name) + "\n" + getString(R.string.app_slogan);
        Navigator.openShare(this, text);
    }

    @OnClick(R.id.btn_setting)
    void onBtnSettingClick() {
        startActivity(new Intent(this, SettingActivity.class));
    }

    @OnClick(R.id.btn_about)
    void onBtnAboutClick() {
        startActivity(new Intent(this, AboutActivity.class));
    }

}
