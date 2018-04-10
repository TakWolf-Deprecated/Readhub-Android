package me.readhub.android.md.ui.holder;

import android.support.annotation.NonNull;
import android.view.View;

public abstract class Controller {

    public static Controller assertType(@NonNull Object object) {
        if (object instanceof Controller) {
            return (Controller) object;
        } else {
            throw new AssertionError("Impossible controller type.");
        }
    }

    @NonNull
    public abstract View getContentView();

}
