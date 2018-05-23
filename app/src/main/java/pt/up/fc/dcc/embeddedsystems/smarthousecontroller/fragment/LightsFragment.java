package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.R;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.activity.LightActivity;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api.LightsApi;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.Light;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LightsFragment extends Fragment {


    List<Light> lightsList;
    public LightsFragment() {
    }

    public static android.support.v4.app.Fragment newInstance() {
        LightsFragment fragment = new LightsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lights, container, false);

        final ListView ListViewLights = view.findViewById(R.id.ListViewLights);

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
                    lightsList = response.body();

                    List<Map<String, String>> data = new ArrayList<>();
                    for (int i=0; i<lightsList.size(); i++) {
                        Map<String, String> datum = new HashMap<>(2);
                        datum.put("title", lightsList.get(i).getDescription());
                        if(lightsList.get(i).isTurnon() == null || !lightsList.get(i).isTurnon()) datum.put("subtitle", "OFF");
                        else datum.put("subtitle", "ON");
                        data.add(datum);
                    }

                    SimpleAdapter adapter = new SimpleAdapter(getContext(), data,
                            android.R.layout.simple_list_item_2,
                            new String[] {"title", "subtitle"},
                            new int[] {android.R.id.text1,
                                    android.R.id.text2});

                    ListViewLights.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Light>> call, Throwable t) {
            }

        });

        ListViewLights.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ListViewLights.getContext(), LightActivity.class);
                intent.putExtra("id",lightsList.get(position).getId().toString());
                startActivity(intent);

            }
        });
        return view;
    }
}

