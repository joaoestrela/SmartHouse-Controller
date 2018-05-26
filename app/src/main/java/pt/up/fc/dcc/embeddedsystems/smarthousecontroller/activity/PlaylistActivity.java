package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.R;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.adapter.TrackAdapter;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api.MusicApi;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.Track;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistActivity extends AppCompatActivity {

    private ListView listView;
    private TrackAdapter mAdapter;
    private ArrayList<Track> musicList;
    MusicApi musicApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        listView = findViewById(R.id.playlist);
        musicApi = RetrofitClientInstance.getRetrofitInstance().create(MusicApi.class);
        musicList = new ArrayList<>();
        Call<List<Track>> call = musicApi.musicAvailable();
        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                musicList.addAll(response.body());
                mAdapter = new TrackAdapter(getApplicationContext(),musicList,getIntent().getIntExtra("activeMusic",0));
                listView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {

            }
        });
    }
}
