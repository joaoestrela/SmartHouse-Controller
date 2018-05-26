package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.R;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api.LightsApi;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.Light;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.StatusResponse;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LightAdapter extends ArrayAdapter<Light>{

    private Context mContext;
    private List<Light> lightsList;

    public LightAdapter(@NonNull Context context, ArrayList<Light> list) {
        super(context, 0 , list);
        mContext = context;
        lightsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null) listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_button,parent,false);
        final Light currentLight = lightsList.get(position);
        TextView listItemText = listItem.findViewById(R.id.list_item_string);
        listItemText.setText(currentLight.getDescription());
        final ToggleButton toggleButton = listItem.findViewById(R.id.btn_toggle);
        toggleButton.setChecked(currentLight.isTurnon());
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleButton.setEnabled(false);
                LightsApi lightsApi = RetrofitClientInstance.getRetrofitInstance().create(LightsApi.class);
                if(toggleButton.isChecked()){
                    Call<StatusResponse> call = lightsApi.setLightState(currentLight.getId().toString(),"on");
                    call.enqueue(new Callback<StatusResponse>() {
                        @Override
                        public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                            toggleButton.setEnabled(true);
                        }
                        @Override
                        public void onFailure(Call<StatusResponse> call, Throwable t) {
                            toggleButton.setChecked(false);
                            toggleButton.setEnabled(true);
                            Log.e("LightsFragment",t.getMessage());
                            Toast.makeText(getContext(),t.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Call<StatusResponse> call = lightsApi.setLightState(currentLight.getId().toString(),"off");
                    call.enqueue(new Callback<StatusResponse>() {
                        @Override
                        public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                            toggleButton.setEnabled(true);
                        }

                        @Override
                        public void onFailure(Call<StatusResponse> call, Throwable t) {
                            toggleButton.setChecked(true);
                            toggleButton.setEnabled(true);
                            Log.e("LightsFragment",t.getMessage());
                            Toast.makeText(getContext(),t.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return listItem;
    }
}