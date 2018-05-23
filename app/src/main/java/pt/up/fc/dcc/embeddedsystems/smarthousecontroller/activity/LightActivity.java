package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.R;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api.LightsApi;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.Light;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.StatusResponse;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by VP on 19/05/2018.
 */

public class LightActivity extends AppCompatActivity {

    TextView id_textview;
    Toolbar toolbar;
    Switch onOff_switch;
    String id_number;
    Boolean state;
    String onOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        Bundle bundle = getIntent().getExtras();

        toolbar = findViewById(R.id.description_toolbar);
        id_textview = findViewById(R.id.id_textView);
        onOff_switch = findViewById(R.id.onOff_switch);

        if (bundle != null){
            String id = bundle.getString("id");

            LightsApi lightsApi = RetrofitClientInstance.getRetrofitInstance().create(LightsApi.class);
            final Call<Light> stateLights = lightsApi.getLightState(id);

            stateLights.enqueue(new Callback<Light>() {
                @Override
                public void onResponse(Call<Light> call, Response<Light> response) {
                    if (!response.isSuccessful()){
                        Log.i("ERRO DE RESPOSTA: ", "" + response.code());
                    } else {
                        Log.i("INFO: ",response.toString());
                        toolbar.setTitle(response.body().getDescription());

                        id_textview.setText(response.body().getId().toString());
                        id_number = response.body().getId().toString();

                        onOff_switch.setChecked(response.body().isTurnon());
                        state = response.body().isTurnon();

                    }
                }

                @Override
                public void onFailure(Call<Light> call, Throwable t) {

                }
            });
        }

        onOff_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                    //Troca o estado de On para Off
                    if (state){
                        onOff = "off";
                    } else {
                        onOff = "on";
                    }

                    LightsApi lightsApi = RetrofitClientInstance.getRetrofitInstance().create(LightsApi.class);
                    Call<StatusResponse> setState = lightsApi.setLightState(id_number, onOff);
                    setState.enqueue(new Callback<StatusResponse>() {
                        @Override
                        public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                            onOff_switch.setEnabled(!state);
                        }

                        @Override
                        public void onFailure(Call<StatusResponse> call, Throwable t) {

                        }
                    });

                }
            }
        });

    }
}
