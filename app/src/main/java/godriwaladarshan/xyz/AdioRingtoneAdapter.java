package godriwaladarshan.xyz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;


/**
 * Created by SANA on 7/5/2016.
 */
public class AdioRingtoneAdapter extends RecyclerView.Adapter<AdioRingtoneAdapter.CustomViewHolder>{

    MediaPlayer mediaPlayer;
    public List<AudioFeedItem> feedItemList, filterList;
    private Context mContext;
    Activity mActivity;

    public AdioRingtoneAdapter(Context context, List<AudioFeedItem> feedItemList , Activity mActivity) {
        this.feedItemList = feedItemList;
        this.mContext = context;
        this.filterList= feedItemList;
        this.mActivity = mActivity;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.audio_list_item , null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override

    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        AudioFeedItem feedItem = feedItemList.get(i);


        customViewHolder.bhajanName.setText(Html.fromHtml(feedItem.getBhajanName()));
        mediaPlayer = new MediaPlayer();

        customViewHolder.txtDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customViewHolder.txtDownload.setTextColor(Color.GREEN);
                String bhajanNameforDownload = customViewHolder.bhajanName.getText().toString();
                String UrlDownload = "http://www.buzzmycode.com/Ringtone/ringtone/" + bhajanNameforDownload;
                DownloadManager downloadManager = (DownloadManager) mActivity.getSystemService(mActivity.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(UrlDownload);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                Long reference = downloadManager.enqueue(request);
                Toast.makeText(mActivity, " Downloading...", Toast.LENGTH_LONG).show();
                mActivity.registerReceiver(onComplete,
                        new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


            }
        });

         customViewHolder.txtPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  String playstop = customViewHolder.txtPlay.getText().toString();


                if(playstop.equals("Play")) {
                   // customViewHolder.txtPlay.setTextColor(Color.GREEN);
                    //customViewHolder.txtPlay.setText("Stop");


                    String bhajanNameforPlay = customViewHolder.bhajanName.getText().toString();
                    String UrlPlay = "http://www.buzzmycode.com/SindhiAppFile/audios/" + bhajanNameforPlay;
                    try {

                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(UrlPlay);
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.prepare();
                        mediaPlayer.start();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    showAlertDialog();
                }
                else if(playstop.equals("Stop"))
                {
                    customViewHolder.txtPlay.setTextColor(Color.BLACK);
                    customViewHolder.txtPlay.setText("Play");
                    if(mediaPlayer!=null && mediaPlayer.isPlaying()){
                        mediaPlayer.stop();
                        Intent i = new Intent(mContext, AudioRingtone.class);
                        mContext.startActivity(i);
                    }
                }*/

                String bhajanNameforPlay = customViewHolder.bhajanName.getText().toString();

                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        mActivity);
                alertDialog2.setCancelable(false);

                alertDialog2.setTitle("Playing...");


                alertDialog2.setMessage(bhajanNameforPlay);


                alertDialog2.setPositiveButton("STOP",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                onStop();

                                Intent i = new Intent(mActivity, AudioRingtone.class);
                                mActivity.finish();
                                mActivity.startActivity(i);

                            }
                        });

                alertDialog2.show();

                String UrlPlay = "http://www.buzzmycode.com/Ringtone/ringtone/" + bhajanNameforPlay;


                try {
                    mediaPlayer.setDataSource(UrlPlay);

                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


     }

       public void onStop(){

        if(mediaPlayer!=null && mediaPlayer.isPlaying()){

            mediaPlayer.stop();

        }
    }

    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            Toast.makeText(mActivity, " Downloading Completed...", Toast.LENGTH_LONG).show();

        }
    };

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView bhajanName, txtDownload , txtPlay;

        public CustomViewHolder(View view) {
            super(view);

            this.bhajanName = (TextView) view.findViewById(R.id.bhajan_name);
            this.txtDownload = (TextView) view.findViewById(R.id.download_bhajan);
            this.txtPlay = (TextView) view.findViewById(R.id.play_bhajan);

        }

    }

}
