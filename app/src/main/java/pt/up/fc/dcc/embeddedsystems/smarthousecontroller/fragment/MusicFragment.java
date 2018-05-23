package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.R;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api.MusicApi;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api.SettingsApi;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.MusicPlayerStatus;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.Setting;
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
        updateMusicDisplay(view);
        return view;
    }

    private void updateMusicDisplay(final View view){
        view.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        Call<MusicPlayerStatus> call = musicApi.musicSummary();
        call.enqueue(new Callback<MusicPlayerStatus>() {
            @Override
            public void onResponse(Call<MusicPlayerStatus> call, Response<MusicPlayerStatus> response) {
                musicPlayerStatus = response.body();
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
                getView().findViewById(R.id.loadingPanel).setVisibility(View.GONE);
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
