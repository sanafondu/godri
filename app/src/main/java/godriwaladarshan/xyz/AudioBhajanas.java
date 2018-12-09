package godriwaladarshan.xyz;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;


public class AudioBhajanas extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "RecyclerViewExample";
    private List<AudioFeedItem> audiofeedsList;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;
    private AdioBhajanAdapter adapter;
    private SearchView audioSearch;
    private Context ctx;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_aarti);

        audioSearch =(SearchView) findViewById(R.id.searchBhajan);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        mRecyclerView = (RecyclerView) findViewById(R.id.audioList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        new AsyncHttpTask().execute();

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                AudioFeedItem audios = audiofeedsList.get(position);
                Toast.makeText(getApplicationContext(), audios.getBhajanName(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        audioSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<AudioFeedItem> filteredLists = new ArrayList<>();

                if(newText !=null && newText.length() > 0){

                    newText = newText.toString().toUpperCase();


                    for(int i=0; i<audiofeedsList.size(); i++){

                        if(audiofeedsList.get(i).getBhajanName().toUpperCase().contains(newText)){

                            filteredLists.add(audiofeedsList.get(i));

                        }
                    }
                    mLayoutManager = new LinearLayoutManager(ctx);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    adapter = new AdioBhajanAdapter(getApplicationContext(), filteredLists, AudioBhajanas.this);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

               else{
                    mLayoutManager = new LinearLayoutManager(ctx);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    adapter = new AdioBhajanAdapter(getApplicationContext(), audiofeedsList, AudioBhajanas.this);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                return true;
            }

        });
        audioSearch.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    audioSearch.setIconified(true);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    public class AsyncHttpTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected void onPreExecute() {
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Integer result = 0;

            String json="";
            HttpURLConnection urlConnection;
            try {
                String url = "http://www.buzzmycode.com/SindhiAppFile/audio_list.php";


                ServiceHandler serviceClient = new ServiceHandler();

                json = serviceClient.makeServiceCall(url,
                        ServiceHandler.POST);

                if(json != null) {

                    try {
                        audiofeedsList = new ArrayList<>();
                        JSONArray jArray = new JSONArray(json);
                        for (int i = 0; i < jArray.length(); i++)
                        {

                            JSONObject post = jArray.getJSONObject(i);

                            AudioFeedItem item = new AudioFeedItem();
                            item.setBhajanName(post.optString("file"));
                           audiofeedsList.add(item);
                            result = 1;

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    result = 0;

                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Let us update UI
            progressBar.setVisibility(View.GONE);

            if (result !=0) {
                adapter = new AdioBhajanAdapter(getApplicationContext(), audiofeedsList,AudioBhajanas.this);
                mRecyclerView.setAdapter(adapter);
            }
            else {
                Toast.makeText(AudioBhajanas.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private AudioBhajanas.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final AudioBhajanas.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(getApplicationContext(), GridViewActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_bhajan) {
            Intent intent = new Intent(getApplicationContext(), AudioBhajanas.class);
            startActivity(intent);

        } else if (id == R.id.nav_video) {
            String url = "https://www.youtube.com/playlist?list=PLhC6MqWaOM8ceJtsaOqaHUdweJ9B8bl2w";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        } else if (id == R.id.nav_ringtone) {
            Intent intent = new Intent(getApplicationContext(), AudioRingtone.class);
            startActivity(intent);

        } else if (id == R.id.nav_lyrics) {
            Intent intent = new Intent(getApplicationContext(), LyricsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_aarti) {
            Intent intent = new Intent(getApplicationContext(), AudioAarti.class);
            startActivity(intent);

        } else if (id == R.id.nav_mahima) {
            Intent intent = new Intent(getApplicationContext(), MahimaActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_jivni) {
            Intent intent = new Intent(getApplicationContext(), JivniActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_dham) {
            Intent intent = new Intent(getApplicationContext(), DhamActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_prasad) {
            Intent intent = new Intent(getApplicationContext(), PrasadActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_live) {
            String url = "https://www.youtube.com/channel/UCv4JfasZ7GYcQzv00_qYIDA/live";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        else if (id == R.id.nav_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Install Godriwala Darshan Android App");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=godriwaladarshan.xyz");
            startActivity(Intent.createChooser(shareIntent, "Share Via"));

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(getApplicationContext(), Contact.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
