package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api;

import java.util.List;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.MusicPlayerStatus;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.Track;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MusicApi {
  /**
   * 
   * asking for available tracks
   * @return Call&lt;List&lt;Track&gt;&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("music/availableTracks/")
  Call<List<Track>> musicAvailableTracks();
    

  /**
   * 
   * status of music player
   * @return Call&lt;MusicPlayerStatus&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("music")
  Call<MusicPlayerStatus> musicSummary();
    

  /**
   * 
   * play track based on query
   * @param trackId  (required)
   * @return Call&lt;MusicPlayerStatus&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("music/play")
  Call<MusicPlayerStatus> playTrack(
    @retrofit2.http.Query("trackId") Integer trackId
  );

  /**
   * 
   * turns the music on or off
   * @param state  (required)
   * @return Call&lt;MusicPlayerStatus&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("music/{state}")
  Call<MusicPlayerStatus> setMusicState(
    @retrofit2.http.Path("state") String state
  );

}
