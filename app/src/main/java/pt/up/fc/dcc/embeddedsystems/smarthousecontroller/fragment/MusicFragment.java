package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.fragment;

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
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api.MusicApi;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.MusicPlayerStatus;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.StatusResponse;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicFragment extends Fragment {

    MusicApi musicApi;
    MusicPlayerStatus musicPlayerStatus;

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
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        updateMusicPlayerStatus(view);
        Button btn_stop = view.findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<MusicPlayerStatus> call = musicApi.setMusicState("off");
                call.enqueue(new Callback<MusicPlayerStatus>() {
                    @Override
                    public void onResponse(Call<MusicPlayerStatus> call, Response<MusicPlayerStatus> response) {
                        updateMusicDisplay(getView());
                    }

                    @Override
                    public void onFailure(Call<MusicPlayerStatus> call, Throwable t) {
                        getView().findViewById(R.id.loadingPanel).setVisibility(View.GONE);
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
                Call<MusicPlayerStatus> call = musicApi.setMusicState("on");
                call.enqueue(new Callback<MusicPlayerStatus>() {
                    @Override
                    public void onResponse(Call<MusicPlayerStatus> call, Response<MusicPlayerStatus> response) {
                        updateMusicDisplay(getView());
                    }

                    @Override
                    public void onFailure(Call<MusicPlayerStatus> call, Throwable t) {
                        getView().findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        Log.e("MusicFragment",t.getMessage());
                        Toast.makeText(getView().getContext(),t.toString(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        Button btn_next = view.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<StatusResponse> call = musicApi.playTrack(musicPlayerStatus.getTrack().getId()+1);
                call.enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        updateMusicDisplay(getView());
                    }

                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        getView().findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        Log.e("MusicFragment",t.getMessage());
                        Toast.makeText(getView().getContext(),t.toString(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return view;
    }

    private void updateMusicDisplay(final View view){
        TextView music_title = getView().findViewById(R.id.text_view_music_title);
        if(musicPlayerStatus.isStatus() == null || !musicPlayerStatus.isStatus()){
            view.findViewById(R.id.btn_stop).setVisibility(View.GONE);
            view.findViewById(R.id.btn_play).setVisibility(View.VISIBLE);
            view.findViewById(R.id.btn_next).setVisibility(View.GONE);
            music_title.setText("Not playing !");
        }
        else {
            view.findViewById(R.id.btn_stop).setVisibility(View.VISIBLE);
            view.findViewById(R.id.btn_play).setVisibility(View.GONE);
            view.findViewById(R.id.btn_next).setVisibility(View.VISIBLE);
            music_title.setText(musicPlayerStatus.getTrack().getName());
        }
        view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    private void updateMusicPlayerStatus(final View view){
        view.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        Call<MusicPlayerStatus> call = musicApi.musicSummary();
        call.enqueue(new Callback<MusicPlayerStatus>() {
            @Override
            public void onResponse(Call<MusicPlayerStatus> call, Response<MusicPlayerStatus> response) {
                musicPlayerStatus = response.body();
                updateMusicDisplay(view);
            }

            @Override
            public void onFailure(Call<MusicPlayerStatus> call, Throwable t) {
                view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                Log.e("MusicFragment",t.getMessage());
                Toast.makeText(view.getContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
