package godriwaladarshan.xyz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Aasim on 15/07/2016.
 */
public class LyricsFullImage extends AppCompatActivity
{

    TextView post_content;
    protected void  onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyrics_full_image);
        TextView post_content = (TextView) findViewById(R.id.postContent);




        Bundle b = getIntent().getExtras();
        String ImageUrl = b.getString("Full_Img_Url");

        post_content.setText(ImageUrl);


       // Glide.with(this).load(ImageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate().into(imageView);





    }

}
