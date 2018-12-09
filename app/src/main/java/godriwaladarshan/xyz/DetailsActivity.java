package godriwaladarshan.xyz;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by SANA on 7/17/2016.
 */
public class DetailsActivity extends AppCompatActivity {
    private TextView titleTextView;
    private ImageView imageView;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.img_toolbar);
        setSupportActionBar(toolbar);


         image = getIntent().getStringExtra("image");
        titleTextView = (TextView) findViewById(R.id.title);
        imageView = (ImageView) findViewById(R.id.grid_item_image);
        Picasso.with(this).load(image).into(imageView);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_image, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_download_image) {

            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(image);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            Long reference = downloadManager.enqueue(request);
            Toast.makeText(this, " Downloading...", Toast.LENGTH_LONG).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}