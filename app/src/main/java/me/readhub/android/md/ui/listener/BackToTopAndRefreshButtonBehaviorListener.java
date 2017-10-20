package me.readhub.android.md.ui.listener;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public final class BackToTopAndRefreshButtonBehaviorListener {

    private BackToTopAndRefreshButtonBehaviorListener() {}

    public static class ForRecyclerView extends RecyclerView.OnScrollListener {

        private final View btnBackToTopAndRefresh;

        public ForRecyclerView(@NonNull View btnBackToTopAndRefresh) {
            this.btnBackToTopAndRefresh = btnBackToTopAndRefresh;
            btnBackToTopAndRefresh.setVisibility(View.GONE);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
           handleBtnState(recyclerView);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            handleBtnState(recyclerView);
        }

        private void handleBtnState(@NonNull RecyclerView recyclerView) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                if (((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition() == 0) {
                    hideBtn();
                } else {
                    showBtn();
                }
            } else {
                hideBtn();
            }
        }

        private void showBtn() {
            btnBackToTopAndRefresh.setVisibility(View.VISIBLE);
        }

        private void hideBtn() {
            btnBackToTopAndRefresh.setVisibility(View.GONE);
        }

    }

}
