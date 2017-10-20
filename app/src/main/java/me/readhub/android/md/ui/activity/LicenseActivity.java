package me.readhub.android.md.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.readhub.android.md.R;
import me.readhub.android.md.ui.base.BaseActivity;
import me.readhub.android.md.ui.listener.NavigationFinishClickListener;
import me.readhub.android.md.ui.util.ToastUtils;
import me.readhub.android.md.util.ResUtils;

public class LicenseActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_license)
    TextView tvLicense;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));

        try {
            tvLicense.setText(ResUtils.getRawString(this, R.raw.open_source));
        } catch (IOException e) {
            tvLicense.setText(null);
            ToastUtils.with(this).show("资源读取失败");
        }
    }

}
