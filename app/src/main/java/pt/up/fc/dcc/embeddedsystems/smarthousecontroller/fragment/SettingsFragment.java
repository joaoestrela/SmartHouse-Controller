package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.math.BigDecimal;
import java.util.Set;

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
    Setting setting;
    ToggleButton toggle;
    NumberPicker numberPicker1;
    NumberPicker numberPicker2;
    TextView setting_message;
    Button btn_apply;

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
                numberPicker1.setValue(setting.getThreshold().intValue());
                numberPicker2.setValue(setting.getThreshold().remainder(BigDecimal.ONE).movePointLeft(1).intValue());
                getView().findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                setting_message.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Setting> call, Throwable t) {
                Log.e("SettingsFragment", t.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        view.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        setting_message = view.findViewById(R.id.settings_message);
        toggle = view.findViewById(R.id.s_auto);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setting.automatic(isChecked);
                enableApply();
            }
        });
        numberPicker1 = view.findViewById(R.id.int_picker);
        numberPicker1.setMinValue(0);
        numberPicker1.setMaxValue(20);
        numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                enableApply();
            }
        });
        numberPicker2 = view.findViewById(R.id.dec_picker);
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(99);
        numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                enableApply();
            }
        });
        btn_apply = view.findViewById(R.id.btn_apply);
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                BigDecimal bd = new BigDecimal(numberPicker1.getValue()+"."+numberPicker2.getValue());
                bd.setScale(2, BigDecimal.ROUND_HALF_EVEN);
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
