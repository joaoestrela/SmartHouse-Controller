package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.LoginInfo;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.RegistrationInfo;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.StatusResponse;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthenticationApi {
  /**
   * 
   * authenticate the user
   * @param loginInfo User information. (optional)
   * @return Call&lt;StatusResponse&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("login")
  Call<StatusResponse> login(
    @retrofit2.http.Body LoginInfo loginInfo
  );

  /**
   * 
   * Register as user.
   * @param registrationInfo User information. (optional)
   * @return Call&lt;StatusResponse&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("register")
  Call<StatusResponse> register(
    @retrofit2.http.Body RegistrationInfo registrationInfo
  );

}
