package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api;


import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.LoginInfo;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.ModelAPIResponse;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthenticationApi {
  /**
   * 
   * authenticate the user
   * @param loginInfo User information. (optional)
   * @return Call&lt;ModelAPIResponse&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("login")
  Call<ModelAPIResponse> login(
    @retrofit2.http.Body LoginInfo loginInfo
  );

  /**
   * 
   * Logout of the platform.
   * @return Call&lt;ModelAPIResponse&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("logout")
  Call<ModelAPIResponse> logout();
    

}
