package com.vension.frame.dialogs;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatDialog;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vension.frame.R;
import com.vension.frame.utils.VEmptyUtil;
import com.vension.frame.utils.VObsoleteUtil;
import com.vension.frame.views.VColorDrawable;

/**
 * 加载等待提示框
 *
 *  XLoadingDialog.with(this)
 .setCanceled(false)
 .setOrientation(XLoadingDialog.VERTICAL)
 .setBackgroundColor(Color.parseColor("#aa000000"))
 .setMessageColor(Color.WHITE)
 .setMessage("加载中...")
 .show();
 */
public class VLoadingDialog extends AppCompatDialog {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private static VLoadingDialog dialog;
    private Context context;
    private TextView loadingMessage;
    private ProgressBar progressBar;
    private LinearLayout loadingView;
    private VColorDrawable drawable;

    public VLoadingDialog(Context context) {
        super(context, R.style.loadingDialogStyle);
        this.context = context;
        drawable = new VColorDrawable();
        setContentView(R.layout.v_dialog_loading);
        loadingMessage = findViewById(R.id.vframe_loading_message);
        progressBar = findViewById(R.id.vframe_loading_progressbar);
        loadingView = findViewById(R.id.vframe_loading_view);
        loadingMessage.setPadding(15, 0, 0, 0);
        drawable.setColor(Color.WHITE);
        VObsoleteUtil.setBackground(loadingView, drawable);
    }

    public static VLoadingDialog with(Context context) {
        if (dialog == null) {
            synchronized (VLoadingDialog.class){
                if (dialog == null){
                    dialog = new VLoadingDialog(context);
                }
            }
        }
        return dialog;
    }

    public VLoadingDialog setOrientation(int orientation) {
        loadingView.setOrientation(orientation);
        if (orientation == HORIZONTAL) {
            loadingMessage.setPadding(15, 0, 0, 0);
        } else {
            loadingMessage.setPadding(0, 15, 0, 0);
        }
        return dialog;
    }

    public VLoadingDialog setBackgroundColor(@ColorInt int color) {
        drawable.setColor(color);
        VObsoleteUtil.setBackground(loadingView, drawable);
        return dialog;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (dialog != null)
            dialog = null;
    }

    public VLoadingDialog setCanceled(boolean cancel) {
        setCanceledOnTouchOutside(cancel);
        setCancelable(cancel);
        return dialog;
    }

    public VLoadingDialog setMessage(String message) {
        if (!VEmptyUtil.isSpace(message)) {
            loadingMessage.setText(message);
        }
        return this;
    }

    public VLoadingDialog setMessageColor(@ColorInt int color) {
        loadingMessage.setTextColor(color);
        return this;
    }

}
