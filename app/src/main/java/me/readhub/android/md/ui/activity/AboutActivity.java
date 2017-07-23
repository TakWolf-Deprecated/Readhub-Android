package me.readhub.android.md.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.readhub.android.md.BuildConfig;
import me.readhub.android.md.R;
import me.readhub.android.md.ui.base.StatusBarActivity;
import me.readhub.android.md.ui.listener.NavigationFinishClickListener;
import me.readhub.android.md.ui.util.Navigator;

public class AboutActivity extends StatusBarActivity {

    public static final String VERSION_TEXT = BuildConfig.VERSION_NAME + "-build-" + BuildConfig.VERSION_CODE;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        tvVersion.setText(VERSION_TEXT);
    }

    @OnClick(R.id.btn_version)
    void onBtnVersionClick() {
        // do nothing
    }

    @OnClick(R.id.btn_open_source_url)
    void onBtnOpenSourceUrlClick() {
        Navigator.openInBrowser(this, getString(R.string.open_source_url_content));
    }

    @OnClick(R.id.btn_official_home_page)
    void onBtnOfficialHomePageClick() {
        Navigator.openInBrowser(this, getString(R.string.official_home_page_content));
    }

    @OnClick(R.id.btn_open_source_license)
    void onBtnOpenSourceLicenseClick() {
        startActivity(new Intent(this, LicenseActivity.class));
    }

}
