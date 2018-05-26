package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api.MusicApi;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.MusicPlayerStatus;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.StatusResponse;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.Track;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackAdapter extends  ArrayAdapter<Track>{

    private Context mContext;
    private List<Track> tracksList;
    int active;

    public TrackAdapter(@NonNull Context context, ArrayList<Track> list, int active) {
        super(context, 0 , list);
        mContext = context;
        tracksList = list;
        this.active = active;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null) listItem = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1,parent,false);
        if(position == active-1) listItem.setBackgroundColor(Color.DKGRAY);
        final Track currentTrack = tracksList.get(position);
        TextView listItemText = listItem.findViewById(android.R.id.text1);
        listItemText.setText(currentTrack.getName());
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                MusicApi musicApi = RetrofitClientInstance.getRetrofitInstance().create(MusicApi.class);
                Call<MusicPlayerStatus> call = musicApi.playTrack(currentTrack.getId());
                call.enqueue(new Callback<MusicPlayerStatus>() {
                    @Override
                    public void onResponse(Call<MusicPlayerStatus> call, Response<MusicPlayerStatus> response) {
                        Activity activity = (Activity)view.getParent();
                        activity.finish();
                    }

                    @Override
                    public void onFailure(Call<MusicPlayerStatus> call, Throwable t) {

                    }
                });
            }
        });
        return listItem;
    }
}