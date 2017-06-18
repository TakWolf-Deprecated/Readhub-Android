package me.readhub.android.md.ui.listener;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;

public final class FloatingActionButtonBehaviorListener {

    private FloatingActionButtonBehaviorListener() {}

    public static class ForAppBarLayout implements AppBarLayout.OnOffsetChangedListener {

        private final FloatingActionButton floatingActionButton;

        private int lastAppBarVerticalOffset = 0;

        public ForAppBarLayout(@NonNull FloatingActionButton floatingActionButton) {
            this.floatingActionButton = floatingActionButton;
        }

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if (verticalOffset < lastAppBarVerticalOffset) {
                floatingActionButton.hide();
            } else if (verticalOffset > lastAppBarVerticalOffset) {
                floatingActionButton.show();
            }
            lastAppBarVerticalOffset = verticalOffset;
        }

    }

}
