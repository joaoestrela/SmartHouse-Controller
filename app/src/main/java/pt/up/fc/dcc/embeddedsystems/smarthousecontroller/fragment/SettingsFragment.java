package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.math.BigDecimal;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.R;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api.EnvironmentApi;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api.SettingsApi;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.SensorData;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.Setting;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.StatusResponse;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsFragment extends Fragment {

    SettingsApi settingsApi;
    EnvironmentApi environmentApi;
    Setting setting;
    ToggleButton toggle;
    TextView seekBar_value;
    SeekBar seekBar;
    TextView setting_message;
    Button btn_apply;
    TextView tv_temperature;
    TextView tv_luminosity;


    public static android.support.v4.app.Fragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        environmentApi = RetrofitClientInstance.getRetrofitInstance().create(EnvironmentApi.class);

        Call<SensorData> env_call = environmentApi.luminosity();
        env_call.enqueue(new Callback<SensorData>() {
            @Override
            public void onResponse(Call<SensorData> call, Response<SensorData> response) {
                tv_luminosity.setText(String.format("%s %s", response.body().getValue(), response.body().getUnit()));
            }

            @Override
            public void onFailure(Call<SensorData> call, Throwable t) {

            }
        });
        env_call = environmentApi.temperature();
        env_call.enqueue(new Callback<SensorData>() {
            @Override
            public void onResponse(Call<SensorData> call, Response<SensorData> response) {
                tv_temperature.setText(String.format("%s %s", response.body().getValue(), response.body().getUnit()));
            }

            @Override
            public void onFailure(Call<SensorData> call, Throwable t) {

            }
        });
        settingsApi = RetrofitClientInstance.getRetrofitInstance().create(SettingsApi.class);
        Call<Setting> call = settingsApi.homeSettings();
        call.enqueue(new Callback<Setting>() {
            @Override
            public void onResponse(Call<Setting> call, Response<Setting> response) {
                if(response.body() == null){
                    setting = new Setting();
                }
                else setting = response.body();
                if(setting.isAutomatic() == null) setting.setAutomatic(true);
                if(setting.getThreshold() == null) setting.setThreshold(new BigDecimal(10.5));
                toggle.setChecked(setting.isAutomatic());
                seekBar.setProgress(setting.getThreshold().intValue());
                getView().findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                setting_message.setVisibility(View.GONE);
                if(setting.isAutomatic()) getView().findViewById(R.id.threshold_layout).setVisibility(View.VISIBLE);
                else getView().findViewById(R.id.threshold_layout).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Setting> call, Throwable t) {
                Log.e("SettingsFragment", t.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        view.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        tv_temperature = view.findViewById(R.id.tv_temperature);
        tv_luminosity = view.findViewById(R.id.tv_luminosity);
        setting_message = view.findViewById(R.id.settings_message);
        seekBar_value = view.findViewById(R.id.seek_bar_value);
        toggle = view.findViewById(R.id.s_auto);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setting.automatic(isChecked);
                if(setting.isAutomatic()) getView().findViewById(R.id.threshold_layout).setVisibility(View.VISIBLE);
                else getView().findViewById(R.id.threshold_layout).setVisibility(View.GONE);
                enableApply();
            }
        });
        seekBar = view.findViewById(R.id.seek_bar_threshold);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar_value.setText(progress+"");
                enableApply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        btn_apply = view.findViewById(R.id.btn_apply);
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                BigDecimal bd = new BigDecimal(seekBar.getProgress());
                setting.setThreshold(bd);
                Call<StatusResponse> call = settingsApi.setHomeSettings(setting);
                call.enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        setting_message.setVisibility(View.GONE);
                        btn_apply.setEnabled(false);
                    }
                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        Log.e("SettingsFragment",t.getMessage());
                        Toast.makeText(view.getContext(),t.toString(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return view;
    }

    private void enableApply(){
        setting_message.setVisibility(View.VISIBLE);
        btn_apply.setEnabled(true);
    }

}
