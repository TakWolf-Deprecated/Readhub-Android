package me.readhub.android.md.ui.listener;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.NonNull;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Interpolator;

public final class BackToTopAndRefreshButtonBehaviorListener {

    private BackToTopAndRefreshButtonBehaviorListener() {}

    public static class ForRecyclerView extends RecyclerView.OnScrollListener {

        private static final int SHOW_HIDE_ANIM_DURATION = 200;

        private static final Interpolator SHOW_ANIM_INTERPOLATOR = new LinearOutSlowInInterpolator();
        private static final Interpolator HIDE_ANIM_INTERPOLATOR = new FastOutLinearInInterpolator();

        private static final int ANIM_STATE_SHOWING = 0;
        private static final int ANIM_STATE_SHOWN = 1;
        private static final int ANIM_STATE_HIDING = 2;
        private static final int ANIM_STATE_HIDDEN = 3;

        private final View button;
        private final float movingDistance;
        private int animState = ANIM_STATE_HIDDEN;

        public ForRecyclerView(@NonNull View button) {
            this.button = button;
            movingDistance = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 64.0f, button.getResources().getDisplayMetrics());
            button.setTranslationY(-movingDistance);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            updateState(recyclerView, 0);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            updateState(recyclerView, dy);
        }

        private void updateState(@NonNull RecyclerView recyclerView, int dy) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                if (((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition() == 0) {
                    hide();
                } else {
                    if (dy > 0) {
                        show();
                    } else if (dy < 0) {
                        hide();
                    }
                }
            } else {
                hide();
            }
        }

        private boolean isOrWillBeShown() {
            return animState == ANIM_STATE_SHOWING || animState == ANIM_STATE_SHOWN;
        }

        private boolean isOrWillBeHidden() {
            return animState == ANIM_STATE_HIDING || animState == ANIM_STATE_HIDDEN;
        }

        private void show() {
            if (isOrWillBeShown()) {
                return;
            }
            button.animate().cancel();
            animState = ANIM_STATE_SHOWING;
            button.animate()
                    .alpha(1.0f)
                    .translationY(0.0f)
                    .setDuration(SHOW_HIDE_ANIM_DURATION)
                    .setInterpolator(SHOW_ANIM_INTERPOLATOR)
                    .setListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animState = ANIM_STATE_SHOWN;
                        }

                    });
        }

        private void hide() {
            if (isOrWillBeHidden()) {
                return;
            }
            button.animate().cancel();
            animState = ANIM_STATE_HIDING;
            button.animate()
                    .alpha(0.0f)
                    .translationY(-movingDistance)
                    .setDuration(SHOW_HIDE_ANIM_DURATION)
                    .setInterpolator(HIDE_ANIM_INTERPOLATOR)
                    .setListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animState = ANIM_STATE_HIDDEN;
                        }

                    });
        }

    }

}
