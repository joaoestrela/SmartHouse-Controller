package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.ToggleButton;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.R;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api.LightsApi;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.Light;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by VP on 19/05/2018.
 */

public class LightActivity extends AppCompatActivity {

    TextView id_textview, textview5;
    Toolbar toolbar;
    ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        Bundle bundle = getIntent().getExtras();

        toolbar = findViewById(R.id.description_toolbar);
        id_textview = findViewById(R.id.id_textView);
        textview5 =findViewById(R.id.textView5);
        toggleButton = findViewById(R.id.automatic_button);

        if (bundle != null){
            String id = bundle.getString("id");

            LightsApi lightsApi = RetrofitClientInstance.getRetrofitInstance().create(LightsApi.class);
            final Call<Light> stateLights = lightsApi.getLightState(id);

            stateLights.enqueue(new Callback<Light>() {

                @Override
                public void onResponse(Call<Light> call, Response<Light> response) {
                    if (!response.isSuccessful()){
                        Log.i("Erro de resposta: ", "" + response.code());
                    } else {
                        toolbar.setTitle(response.body().getDescription().toString());
                        id_textview.setText(response.body().getId().toString());
                        //TODO: THIS IS DEPRECATED
                        textview5.setText(response.body().getThreshold().toString());

                        if (!response.body().isAutomatic()){
                            toggleButton.setChecked(false);
                            toggleButton.setBackgroundColor(23);
                        } else {
                            toggleButton.setChecked(true);
                            toggleButton.setBackgroundColor(24);
                        }

                    }
                }

                @Override
                public void onFailure(Call<Light> call, Throwable t) {

                }
            });
        }
    }
}
