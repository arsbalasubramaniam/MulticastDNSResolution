package com.example.multicastdnsresolution.Utils;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.view.Gravity;
import android.widget.Toast;

public class AppUtils {

    //Initialize NSDManager
    public static NsdManager initializeNSDManger(Context mContext) {
        return (NsdManager) mContext.getSystemService(Context.NSD_SERVICE);
    }
    /*
    Show Toast to the user
    * */
    public static void showToast(Context context, String message) {
        if (context != null) {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

}
