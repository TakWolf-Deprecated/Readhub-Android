package me.readhub.android.md.ui.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;

import me.readhub.android.md.R;
import me.readhub.android.md.model.storage.shared.SettingShared;
import me.readhub.android.md.ui.activity.ArticleActivity;
import me.readhub.android.md.util.ResUtils;

public final class Navigator {

    private Navigator() {}

    public static void openInBrowser(@NonNull Context context, @NonNull String url) {
        new CustomTabsIntent.Builder()
                .setToolbarColor(ResUtils.getThemeAttrColor(context, R.attr.colorPrimary))
                .build()
                .launchUrl(context, Uri.parse(url));
    }

    public static void openShare(@NonNull Context context, @NonNull String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)));
    }

    public static void openArticle(@NonNull Activity activity, @NonNull String title, @NonNull String url) {
        if (SettingShared.isOpenArticleInApp(activity)) {
            ArticleActivity.start(activity, title, url);
        } else {
            openInBrowser(activity, url);
        }
    }

}
