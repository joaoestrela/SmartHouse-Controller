package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api;

import java.util.List;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.Light;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.StatusResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;

public interface LightsApi {
  /**
   * 
   * status of the specied light
   * @param lightID  (required)
   * @return Call&lt;Light&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("lights/{lightID}")
  Call<Light> getLightState(
    @retrofit2.http.Path("lightID") String lightID
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
   * @param lightID  (required)
   * @param state  (required)
   * @return Call&lt;StatusResponse&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @PUT("lights/{lightID}/{state}")
  Call<StatusResponse> setLightState(
    @retrofit2.http.Path("lightID") String lightID, @retrofit2.http.Path("state") String state
  );

}
