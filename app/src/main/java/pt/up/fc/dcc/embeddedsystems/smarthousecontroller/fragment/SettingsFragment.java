package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.math.BigDecimal;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.R;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api.SettingsApi;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.Setting;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.StatusResponse;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsFragment extends Fragment {

    SettingsApi settingsApi;

    public SettingsFragment() {
        }

    public static android.support.v4.app.Fragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsApi = RetrofitClientInstance.getRetrofitInstance().create(SettingsApi.class);
        //The code bellow is just an example !!! Should be removed soon.
        Setting setting = new Setting();
        setting.setAutomatic(true);
        setting.setThreshold(new BigDecimal(15.5));
        Call<StatusResponse> call = settingsApi.setHomeSettings(setting);
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                Call<Setting> call2 = settingsApi.homeSettings();
                call2.enqueue(new Callback<Setting>() {
                    @Override
                    public void onResponse(Call<Setting> call, Response<Setting> response) {
                        Log.d("CONA",response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<Setting> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {

            }
        });
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }



}
