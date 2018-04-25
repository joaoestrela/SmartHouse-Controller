package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api;

import java.util.List;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.SensorData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface EnvironmentApi {
  /**
   * 
   * 
   * @return Call&lt;SensorData&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("luminosity")
  Call<SensorData> luminosity();
    

  /**
   * 
   * 
   * @return Call&lt;List&lt;SensorData&gt;&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("luminosity/history")
  Call<List<SensorData>> luminosityHistory();
    

  /**
   * 
   * 
   * @return Call&lt;SensorData&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("temperature")
  Call<SensorData> temperature();
    

  /**
   * 
   * 
   * @return Call&lt;List&lt;SensorData&gt;&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @GET("temperature/history")
  Call<List<SensorData>> temperatureHistory();
    

}
