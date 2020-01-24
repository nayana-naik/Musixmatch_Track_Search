package com.example.inclass07;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_search;
    TextView tv_limit_search;
SeekBar sb_limit;
    RadioButton rg_sort_artist;
    RadioButton rg_sort_track;
    ProgressBar pb_loading;
   // ListView lv_search_result;
    EditText et_search_query;
    String URLPopulate;
    int limit;
    RadioGroup rg_sort;
    String radio;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                String res=data.getExtras().getString("Confirm");
                String URL1=data.getExtras().getString("URL-1");
                Toast.makeText(getApplicationContext(),"Result Verified",Toast.LENGTH_SHORT).show();

                new GetMusicResults().execute(URL1);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("MusixMatch Track Search");

        rg_sort=(RadioGroup)findViewById(R.id.rg_sort);
        btn_search=(Button)findViewById(R.id.btn_search);
        tv_limit_search=(TextView)findViewById(R.id.tv_limit);
        rg_sort_artist=(RadioButton)findViewById(R.id.rb_sort_by_artist_rating);
        rg_sort_track=(RadioButton)findViewById(R.id.rb_sort_by_track_rating);
        et_search_query=(EditText)findViewById(R.id.et_search_text);
        sb_limit=(SeekBar)findViewById(R.id.sb_Limit);
        pb_loading=(ProgressBar)findViewById(R.id.pb_loading);
        pb_loading.setVisibility(View.INVISIBLE);
       // lv_search_result=(ListView)findViewById(R.id.lv_search_results);
        recyclerView=(RecyclerView)findViewById(R.id.rc_view_sample);
        sb_limit.setProgress(0);
        recyclerView.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);


        rg_sort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(rg_sort.getCheckedRadioButtonId()){
                    case R.id.rb_sort_by_track_rating:
                            radio="s_track_rating";
                    case R.id.rb_sort_by_artist_rating:
                        radio="s_artist_rating";
                }
            }
        });

        sb_limit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                limit=i;
                tv_limit_search.setText(String.valueOf(i));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!isConnected()){
                    Toast.makeText(getApplicationContext(),"Internet Not Connected",Toast.LENGTH_SHORT).show();
                }
                else{

                    String searchInput=et_search_query.getText().toString();
                    String URL="http://api.musixmatch.com/ws/1.1/track.search?apikey=0bb660cfa9891d01dfd22629dc7983e7&q="+searchInput+"&page_size="+limit+"&"+radio+"=desc";
                    //Log.d("URL",URL);
                    //new GetMusicResults().execute(URL);

                    //Implicit Intent example
                    Intent i=new Intent("com.example.inclass07.action.VIEW");
                    i.putExtra("URL",URL);
                    //startActivity(i);
                    startActivityForResult(i,1);

                }






            }
        });






    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }


    private class GetMusicResults extends AsyncTask<String, Void, ArrayList<Music> >{

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            pb_loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<Music> musicArrayList) {
            //super.onPostExecute(music);
            //Log.d("Result",stringR);
            pb_loading.setVisibility(View.INVISIBLE);
            Log.d("ResultMusic",musicArrayList.toString());

            //final ArrayList<Music> musicList=musicArrayList;

            mAdapter=new MusicAdapterRecycle(musicArrayList);
            recyclerView.setAdapter(mAdapter);


           // final MusicAdapter musicAdapter=new MusicAdapter(MainActivity.this, R.layout.music_item,musicList);

           // MainActivity.this.lv_search_result.setAdapter(musicAdapter);
            //MainActivity.this.lv_search_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              //  @Override
               /* public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    String url=musicAdapter.getMusicItemURL(position);
                    //String url = "http://www.example.com";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });*/
        }

        @Override
        protected ArrayList<Music>  doInBackground(String... strings) {
//            return null;

            HttpURLConnection connection = null;
            ArrayList<Music> resultMusic = new ArrayList<>();
            //HashMap<String, String> sourceHashMap=new HashMap<>();
           // ArrayList<String> resultString=new ArrayList<>();
            //String re;
            StringBuilder st=new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                //connection.setRequestMethod("POST");
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF8");

                    JSONObject root = new JSONObject(json);
                    JSONObject searchResults = root.getJSONObject("message");
                    JSONObject bodyResult= searchResults.getJSONObject("body");
                    JSONArray track_array=bodyResult.getJSONArray("track_list");
                   // Log.d("Track",track_array.toString());
                    for(int i=0;i<track_array.length();i++){
                       JSONObject jO=track_array.getJSONObject(i);

                        JSONObject j2=jO.getJSONObject("track");
                        Music m=new Music();
                        m.artistName=j2.getString("artist_name");
                        m.trackShareUrl=j2.getString("track_share_url");
                        m.updatedTime=j2.getString("updated_time");
                        m.albumName=j2.getString("album_name");
                        m.trackName=j2.getString("track_name");

                        resultMusic.add(m);

                    }

                }
            } catch (Exception e) {
                //Handle Exceptions
            } finally {
                //Close the connections
            }

            return resultMusic;

        }
    }

}
