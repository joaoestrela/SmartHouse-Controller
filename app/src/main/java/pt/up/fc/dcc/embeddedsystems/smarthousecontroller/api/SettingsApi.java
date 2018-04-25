package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.Setting;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.StatusResponse;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.PUT;

public interface SettingsApi {
  /**
   * 
   * set global settings
   * @param settings  (required)
   * @return Call&lt;StatusResponse&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @PUT("settings/home/")
  Call<StatusResponse> setHomeSettings(
    @retrofit2.http.Body Setting settings
  );

}
