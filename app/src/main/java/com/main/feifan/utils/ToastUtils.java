package com.main.feifan.utils;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.google.android.material.snackbar.Snackbar;
import com.main.x_base.utils.Utils;

public class ToastUtils {
    public static void showLongToast(String text) {
        Toast.makeText(Utils.getApp().getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(String text) {
        Toast.makeText(Utils.getApp().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
    public static Snackbar makeTextShort(View view, @StringRes int id) {
        return Snackbar.make(view, id, Snackbar.LENGTH_SHORT);
    }

    public static void showTextShort(View view, @StringRes int id) {
        Snackbar.make(view, id, Snackbar.LENGTH_SHORT).show();
    }

    public static void showTextShort(View view, @StringRes int id, @StringRes int actionId, View.OnClickListener listener) {
        Snackbar.make(view, id, Snackbar.LENGTH_SHORT).setAction(actionId, listener).show();
    }

    public static void showTextShort(View view, String text, String actionText, View.OnClickListener listener) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).setAction(actionText, listener).show();
    }

    public static void showTextShort(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }

    public static void showTextLong(View view, @StringRes int id) {
        Snackbar.make(view, id, Snackbar.LENGTH_LONG).show();
    }

    public static void showTextLong(View view, @StringRes int id, @StringRes int actionId, View.OnClickListener listener) {
        Snackbar.make(view, id, Snackbar.LENGTH_LONG).setAction(actionId, listener).show();
    }

    public static void showTextLong(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
    }

    public static void showTextLong(View view, String text, String actionText, View.OnClickListener listener) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).setAction(actionText, listener).show();
    }
}
