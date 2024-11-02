package com.saidev.PaiseWala;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.saidev.PaiseWala.R;

public class loadingDialogBar {

    Context context;
    Dialog dialog;

    public loadingDialogBar(Context context) {
        this.context = context;
    }

    public void ShowDialog(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.create();
        dialog.show();
    }

    public void ShowRewardOnWayDialog(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.reward_cnf_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.create();
        dialog.show();
    }

    public void HideDialog(){
        dialog.dismiss();
    }

}
