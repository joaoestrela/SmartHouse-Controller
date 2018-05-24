package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.R;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api.AuthenticationApi;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.RegistrationInfo;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.StatusResponse;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by VP on 23/05/2018.
 */

public class RegisterActivity extends AppCompatActivity {


    private EditText et_username, et_password, et_secret;
    private AuthenticationApi authenticationApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authenticationApi = RetrofitClientInstance.getRetrofitInstance().create(AuthenticationApi.class);

        et_username = findViewById(R.id.input_email);
        et_password = findViewById(R.id.input_password);
        et_secret = findViewById(R.id.input_secret);

        et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextInputLayout textInputLayout = findViewById(R.id.input_email_layout);
                if (s.length()==0 || !android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError("Invalid username!");
                } else {
                    textInputLayout.setError(null);
                    textInputLayout.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextInputLayout textInputLayout = findViewById(R.id.input_password_layout);
                if (s.length() < 3) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError("Too Short");
                } else if (s.length() > 10) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError("Too Large");
                } else {
                    textInputLayout.setError(null);
                    textInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_secret.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextInputLayout textInputLayout = findViewById(R.id.input_secret_layout);
                if (s.length() == 0){
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError("Invalid secret!");
                } else {
                    textInputLayout.setError(null);
                    textInputLayout.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button submit_button = findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate_email(et_username.getText().toString()) &&
                        validate_password(et_password.getText().toString())) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    submitRegister();
                }

                if(et_password.getText().toString().isEmpty()){
                    TextInputLayout textInputLayout = findViewById(R.id.input_password_layout);
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError("Invalid Password");
                }
            }
        });
    }

    public void submitRegister(){
        RegistrationInfo registrationInfo = new RegistrationInfo();
        registrationInfo.setUsername(et_username.getText().toString());
        registrationInfo.setPassword(et_password.getText().toString());
        registrationInfo.setSecret(et_secret.getText().toString());

        Call<StatusResponse> new_register = authenticationApi.register(registrationInfo);
        new_register.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if(response.code() ==  200) goLoginActivity();
                else {
                    Log.d("Register", response.message());
                    TextView error = findViewById(R.id.errorMessage);
                    error.setText(response.body().toString());
                    error.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {


                Log.d("Register", t.getMessage());


            }
        });
    }


    public static boolean validate_email(String email){
        return (!email.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean validate_password(String password){
        return (!password.isEmpty() && password.length() >= 3 && password.length() < 10);
    }

    private void goLoginActivity(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }



}
