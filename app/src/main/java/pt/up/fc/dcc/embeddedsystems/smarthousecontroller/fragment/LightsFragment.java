package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.R;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.adapter.LightAdapter;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api.LightsApi;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.Light;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LightsFragment extends Fragment {
    private ListView listView;
    private LightAdapter mAdapter;
    ArrayList<Light> lightsList;

    public static android.support.v4.app.Fragment newInstance() {
        LightsFragment fragment = new LightsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lightsList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lights, container, false);
        listView = view.findViewById(R.id.ListViewLights);
        LightsApi lightsApi = RetrofitClientInstance.getRetrofitInstance().create(LightsApi.class);
        final Call<List<Light>> requestLights = lightsApi.getLights();
        requestLights.enqueue(new Callback<List<Light>>() {
            @Override
            public void onResponse(Call<List<Light>> call, Response<List<Light>> response) {
                if (!response.isSuccessful()) {
                    Log.i("Erro de resposta: ", "" + response.code());
                } else {
                    Log.i("Lights",response.toString());
                    if(response.body() == null) return;
                    lightsList.addAll(response.body());
                    mAdapter = new LightAdapter(getContext(),lightsList);
                    listView.setAdapter(mAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<Light>> call, Throwable t) {
                Log.e("LightsFragment",t.getMessage());
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}

