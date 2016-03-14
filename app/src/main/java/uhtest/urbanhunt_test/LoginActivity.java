package uhtest.urbanhunt_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uhtest.urbanhunt_test.API.uhApi;

/**
 * Created by Jinesh on 13/03/16.
 */
public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView info;
    String API = "http://127.0.0.1:8000";
    String client_id =  "RCrBdYlmCkXwIUOuB1L36Pto3FfcFCHlvpkhk13F";
    String client_secret = "RAQOkq9i4InHt4cPQl80B8oRbY46EWMZymjAj71Y2rsr2VaApkTG6U5bZGfneqwSd7G1PaMTWShRU3YroFK0NPnEVJLHz5qmjfUoFDKYnbsSiP6NNBlMR7jXjZMdc9fj";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.login);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        info = (TextView)findViewById(R.id.info);


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();

                uhApi uh = restAdapter.create(uhApi.class);
                Log.d("fb_auth_token", loginResult.getAccessToken().getToken());

                uh.createFBUser("convert_token", client_id, client_secret, "facebook",
                        loginResult.getAccessToken().getToken(), new Callback<Dummy>() {
                            @Override
                            public void success(Dummy dummy, Response response) {
                                Log.d("Success","True");
                                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(i);
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });



//                info.setText("User ID: "
//                                + loginResult.getAccessToken().getUserId()
//                                + "\n" +
//                                "Auth Token: "
//                                + loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
