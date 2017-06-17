package me.readhub.android.md.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.readhub.android.md.R;
import me.readhub.android.md.model.storage.shared.SettingShared;
import me.readhub.android.md.ui.base.StatusBarActivity;
import me.readhub.android.md.ui.listener.NavigationFinishClickListener;

public class SettingActivity extends StatusBarActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.switch_open_article_in_app)
    SwitchCompat switchOpenArticleInApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        switchOpenArticleInApp.setChecked(SettingShared.isOpenArticleInApp(this));
    }

    @OnClick(R.id.btn_open_article_in_app)
    void onBtnOpenArticleInAppClick() {
        switchOpenArticleInApp.toggle();
        SettingShared.setOpenArticleInApp(this, switchOpenArticleInApp.isChecked());
    }

}
