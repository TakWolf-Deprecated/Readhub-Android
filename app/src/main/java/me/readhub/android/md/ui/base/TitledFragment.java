package me.readhub.android.md.ui.base;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

public abstract class TitledFragment extends Fragment {

    @StringRes
    public abstract int getPageTitleId();

}
