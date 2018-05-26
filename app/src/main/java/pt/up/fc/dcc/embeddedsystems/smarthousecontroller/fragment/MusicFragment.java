package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.R;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.activity.PlaylistActivity;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api.MusicApi;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.MusicPlayerStatus;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicFragment extends Fragment {

    MusicApi musicApi;
    MusicPlayerStatus musicPlayerStatus;
    View view;

    public static android.support.v4.app.Fragment newInstance() {
        MusicFragment fragment = new MusicFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicApi = RetrofitClientInstance.getRetrofitInstance().create(MusicApi.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_music, container, false);
        updateMusicPlayerStatus();
        Button btn_stop = view.findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setEnabled(false);
                Call<MusicPlayerStatus> call = musicApi.setMusicState("off");
                call.enqueue(new Callback<MusicPlayerStatus>() {
                    @Override
                    public void onResponse(Call<MusicPlayerStatus> call, Response<MusicPlayerStatus> response) {
                        view.findViewById(R.id.btn_stop).setVisibility(View.GONE);
                        view.findViewById(R.id.btn_play).setVisibility(View.VISIBLE);
                        TextView music_title = view.findViewById(R.id.text_view_music_title);
                        music_title.setText("Not playing !");
                        view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        view.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Call<MusicPlayerStatus> call, Throwable t) {
                        view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        view.setEnabled(true);
                        Log.e("MusicFragment",t.getMessage());
                        Toast.makeText(getView().getContext(),t.toString(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        Button btn_play = view.findViewById(R.id.btn_play);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int trackId;
                view.setEnabled(false);
                if(musicPlayerStatus.getTrack() == null || musicPlayerStatus.getTrack().getId() == null) trackId = 1;
                else trackId = musicPlayerStatus.getTrack().getId();
                Call<MusicPlayerStatus> call = musicApi.playTrack(trackId);
                call.enqueue(new Callback<MusicPlayerStatus>() {
                    @Override
                    public void onResponse(Call<MusicPlayerStatus> call, Response<MusicPlayerStatus> response) {
                        musicPlayerStatus = response.body();
                        view.findViewById(R.id.btn_stop).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.btn_play).setVisibility(View.GONE);
                        TextView music_title = view.findViewById(R.id.text_view_music_title);
                        music_title.setText(musicPlayerStatus.getTrack().getName());
                        view.setEnabled(true);
                        view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<MusicPlayerStatus> call, Throwable t) {
                        view.setEnabled(true);
                        view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        Log.e("MusicFragment",t.getMessage());
                        Toast.makeText(getView().getContext(),t.toString(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        Button btn_playlist = view.findViewById(R.id.btn_playlist);
        btn_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PlaylistActivity.class);
                intent.putExtra("activeMusic",musicPlayerStatus.getTrack().getId());
                startActivity(intent);
            }
        });
        return view;
    }

    private void updateMusicPlayerStatus(){
        view.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        Call<MusicPlayerStatus> call = musicApi.musicSummary();
        call.enqueue(new Callback<MusicPlayerStatus>() {
            @Override
            public void onResponse(Call<MusicPlayerStatus> call, Response<MusicPlayerStatus> response) {
                musicPlayerStatus = response.body();
                if(musicPlayerStatus.isState()){
                    view.findViewById(R.id.btn_stop).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.btn_play).setVisibility(View.GONE);
                    TextView music_title = view.findViewById(R.id.text_view_music_title);
                    music_title.setText(musicPlayerStatus.getTrack().getName());
                }else{
                    view.findViewById(R.id.btn_stop).setVisibility(View.GONE);
                    view.findViewById(R.id.btn_play).setVisibility(View.VISIBLE);
                    TextView music_title = view.findViewById(R.id.text_view_music_title);
                    music_title.setText("Not playing !");
                }
                view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MusicPlayerStatus> call, Throwable t) {
                view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                Log.e("MusicFragment",t.getMessage());
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
