package pt.up.fc.dcc.embeddedsystems.smarthousecontroller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.R;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.api.AuthenticationApi;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.LoginInfo;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.model.StatusResponse;
import pt.up.fc.dcc.embeddedsystems.smarthousecontroller.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText et_email;
    private EditText et_password;
    private AuthenticationApi authenticationApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authenticationApi = RetrofitClientInstance.getRetrofitInstance().create(AuthenticationApi.class);
        et_email = findViewById(R.id.input_email);
        et_password = findViewById(R.id.input_password);
        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextInputLayout textInputLayout = findViewById(R.id.input_email_layout);
                if (s.length()==0 || !android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.error_invalid_email));
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
                    textInputLayout.setError(getString(R.string.TooShort));
                } else if (s.length() > 10) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.TooLarge));
                } else {
                    textInputLayout.setError(null);
                    textInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

        });
        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate_email(et_email.getText().toString()) &&
                        validate_password(et_password.getText().toString())){
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    submitLogin();
                }
                if(et_email.getText().toString().isEmpty()){
                    TextInputLayout textInputLayout = findViewById(R.id.input_email_layout);
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.error_invalid_email));
                }
                if(et_password.getText().toString().isEmpty()){
                    TextInputLayout textInputLayout = findViewById(R.id.input_password_layout);
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.error_invalid_password));
                }
            }
        });
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        Button register_button = findViewById(R.id.btn_register);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRegisterActivity();
            }
        });
    }

    private void submitLogin(){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);


        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUsername(et_email.getText().toString());
        loginInfo.setPassword(et_password.getText().toString());
        Call<StatusResponse> call = authenticationApi.login(loginInfo);
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if(response.code() ==  200) goMainActivity();
                else {
                    alertDialog
                            .setTitle("Login Error")
                            .setMessage("Login Failed!")
                            .create().show();

                    Log.d("Login", response.message());
                }
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Log.d("Login", t.getMessage());
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }
        });
    }

    private void goMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public static boolean validate_email(String email){
        return (!email.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean validate_password(String password){
        return (!password.isEmpty() && password.length() >= 3 && password.length() < 10);
    }

    private void goRegisterActivity(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        this.finish();
    }

}
