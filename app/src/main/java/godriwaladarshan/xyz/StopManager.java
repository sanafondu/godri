package godriwaladarshan.xyz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;

/**
 * Created by Aasim on 18/07/2016.
 */
public class StopManager {

    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        if(status != null)
            // Setting alert dialog icon
            alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        // Setting OK Button
        alertDialog.setButton("STOP", new DialogInterface.OnClickListener()  {
            public void onClick(DialogInterface dialog, int which) {

                MediaPlayer mp = new MediaPlayer();
                mp.stop();

            }

        });

        // Showing Alert Message
        alertDialog.show();


    }
}
