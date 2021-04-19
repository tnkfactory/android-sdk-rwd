package com.tnkfactory.rwd.offerer.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.tnkfactory.ad.TnkStyle;
import com.tnkfactory.ad.rwd.AdvertisingIdInfo;

import java.lang.ref.WeakReference;

public class GAIDTask extends AsyncTask<Void, Void, String> {

    private WeakReference<Context> context;
    private Dialog progressDialog = null;
    private GAIDListener listener;

    public GAIDTask(Context context, boolean showProgress, GAIDListener listener) {
        this.context = new WeakReference(context);
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        if (progressDialog == null) {
            int dpSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.get().getResources().getDisplayMetrics());
            ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            RelativeLayout dialogLayout = new RelativeLayout(context.get());
            dialogLayout.setLayoutParams(layoutParam);

            RelativeLayout.LayoutParams pbLayoutParam = new RelativeLayout.LayoutParams(dpSize, dpSize);
            pbLayoutParam.addRule(RelativeLayout.CENTER_IN_PARENT);

            ProgressBar pb = new ProgressBar(context.get());
            pb.setIndeterminate(true);
            pb.setLayoutParams(pbLayoutParam);

            dialogLayout.addView(pb);

            progressDialog = new Dialog(context.get());
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setContentView(dialogLayout);

            try {
                if (!TnkStyle.showProgressDim) {
                    progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                }
                progressDialog.show();

            } catch (Exception e) {

            }
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        String gaid = null;
        try {
            gaid = AdvertisingIdInfo.requestIdInfo(context.get()).getId();
        } finally {
            return gaid;
        }
    }

    @Override
    protected void onPostExecute(String gaid) {
        if (listener != null) {
            listener.onSuccess(gaid);
        }

        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
                progressDialog = null;
            }
            catch(Exception ignored) {

            }
        }
    }

    public interface GAIDListener {
        void onSuccess(String gaid);
    }
}
