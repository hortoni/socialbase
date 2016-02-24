package socialbase.com.br.challengesocialbase.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by hortoni on 22/10/15.
 */
public class ConnectionUtil {
    public static boolean isConnected(Context context) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return ((networkInfo == null) ? false : networkInfo.isConnected());
    }

    public static void generateDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title).setMessage(message).setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        try {
                            this.finalize();
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }).show();
    }
}
