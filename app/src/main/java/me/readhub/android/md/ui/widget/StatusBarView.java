package me.readhub.android.md.ui.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.View;

import me.readhub.android.md.util.ResUtils;

public class StatusBarView extends View {

    public StatusBarView(@NonNull Context context) {
        super(context);
    }

    public StatusBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StatusBarView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public StatusBarView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), ResUtils.getStatusBarHeight(getContext()));
    }

}
