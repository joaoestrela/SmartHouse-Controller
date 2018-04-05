package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api;

import java.util.List;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.Light;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.ModelAPIResponse;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.Setting;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LightsApi {
  /**
   * 
   * status of the specied light
   * @param lightId  (required)
   * @return Call&lt;Light&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("lights/{lightId}")
  Call<Light> getLightState(
    @retrofit2.http.Path("lightId") String lightId
  );

  /**
   * 
   * status of all the lights
   * @return Call&lt;List&lt;Light&gt;&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("lights")
  Call<List<Light>> getLights();
    

  /**
   * 
   * turns the light on or off
   * @param lightId  (required)
   * @param state  (required)
   * @return Call&lt;ModelAPIResponse&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("lights/{lightId}/{state}")
  Call<ModelAPIResponse> setLightState(
    @retrofit2.http.Path("lightId") String lightId, @retrofit2.http.Path("state") String state
  );

  /**
   * 
   * turns the automatic light on/off
   * @param lightId  (required)
   * @param settings  (required)
   * @return Call&lt;ModelAPIResponse&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("lights/{lightId}/settings")
  Call<ModelAPIResponse> setLuminosityThreshhold(
    @retrofit2.http.Path("lightId") String lightId, @retrofit2.http.Body List<Setting> settings
  );

  /**
   * 
   * get all settings for a specific light
   * @param lightId  (required)
   * @return Call&lt;List&lt;Setting&gt;&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("lights/{lightId}/settings")
  Call<List<Setting>> settingsLight(
    @retrofit2.http.Path("lightId") String lightId
  );

}
