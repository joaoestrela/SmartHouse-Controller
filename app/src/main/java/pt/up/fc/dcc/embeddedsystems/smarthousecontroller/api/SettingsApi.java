package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api;

import java.util.List;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.Setting;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SettingsApi {
  /**
   * 
   * set global settings
   * @param settings  (required)
   * @return Call&lt;List&lt;Setting&gt;&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("settings/home/")
  Call<List<Setting>> setHomeSettings(
    @retrofit2.http.Body List<Setting> settings
  );

  /**
   * 
   * get all global settings
   * @return Call&lt;List&lt;Setting&gt;&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("settings/home/")
  Call<List<Setting>> settings();
    

}
