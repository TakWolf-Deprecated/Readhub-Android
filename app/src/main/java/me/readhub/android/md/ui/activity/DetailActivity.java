package me.readhub.android.md.ui.activity;

import android.os.Bundle;

import me.readhub.android.md.R;
import me.readhub.android.md.ui.base.BaseActivity;
import me.readhub.android.md.ui.base.StatusBarActivity;

public class DetailActivity extends StatusBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

}
