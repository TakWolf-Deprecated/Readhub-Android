package me.readhub.android.md.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.readhub.android.md.R;
import me.readhub.android.md.ui.base.BaseActivity;
import me.readhub.android.md.ui.listener.NavigationFinishClickListener;
import me.readhub.android.md.ui.util.Navigator;
import me.readhub.android.md.util.HandlerUtils;

public class ArticleActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, Runnable, Toolbar.OnMenuItemClickListener {

    private static final String EXTRA_TITLE = "title";
    private static final String EXTRA_URL = "url";

    public static void start(@NonNull Activity activity, @NonNull String title, @NonNull String url) {
        Intent intent = new Intent(activity, ArticleActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_URL, url);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.web_view)
    WebView webView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private String title;
    private String url;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);

        title = getIntent().getStringExtra(EXTRA_TITLE);
        url = getIntent().getStringExtra(EXTRA_URL);

        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        toolbar.inflateMenu(R.menu.detail);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setTitle(title);

        refreshLayout.setColorSchemeResources(R.color.color_primary);
        refreshLayout.setOnRefreshListener(this);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        //noinspection deprecation
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }

        });
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
            }

        });
        webView.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_in_browser:
                Navigator.openInBrowser(this, url);
                return true;
            case R.id.action_share:
                String text = "《" + title + "》\n" + url + "\n—— 来自Readhub的分享";
                Navigator.openShare(this, text);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onRefresh() {
        webView.reload();
        HandlerUtils.handler.postDelayed(this, 1000);
    }

    @Override
    public void run() {
        refreshLayout.setRefreshing(false);
    }

}
