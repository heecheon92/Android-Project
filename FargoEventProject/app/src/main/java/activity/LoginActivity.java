package activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heecheonpark.parkfinalproject.R;

import model.LoginAPIService;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView tv_loginStatus;
    EditText et_username, et_password;
    Button btn_login, btn_guestPass;
    String username, password;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorMyriadStatusBar));
        }

        tv_loginStatus = findViewById(R.id.tv_loginStatus);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_guestPass = findViewById(R.id.btn_guestPass);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        tv_loginStatus.setText("Token: " + pref.getString("token", null));

        btn_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                username = et_username.getText().toString();
                password = et_password.getText().toString();
                LoginAPIService loginAPIService = new LoginAPIService();
                loginAPIService.getToken(new Callback<User>()
                {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response)
                    {
                        User user = response.body();
                        if(response.isSuccessful())
                        {
                            tv_loginStatus.setText("Login Successful!! Lol\nThis is the response token: " + user.getToken());
                            Toast.makeText(getApplicationContext(), "Token: " + user.getToken(), Toast.LENGTH_LONG).show();
                            editor.putString("token", user.getToken());
                            editor.apply();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("loginStatus", true);
                            intent.putExtra("token", user.getToken());
                            startActivity(intent);
                            finish();

                        }
                        else
                        {
                            try {
                                tv_loginStatus.setText("Response: " + response.toString() + "\nToken size: " + user.getToken());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            tv_loginStatus.setText("Response: " + response.toString());
                            Toast.makeText(getApplicationContext(), "User = null, something is not right!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t)
                    {
                        // something went completely south (like no internet connection)
                        Log.d("Error", t.getMessage());
                        Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, username, password);
            }
        });
    }



    /*public void doLogin(View view) {

        username = et_username.getText().toString();
        password = et_password.getText().toString();

        LoginService loginService =
                ServiceGenerator.createService(LoginService.class, username, password);
        Call<User> call = loginService.basicLogin();
        call.enqueue(new Callback<User >() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                User user = response.body();

                if (response.isSuccessful()) {
                    // user object available
                    Toast.makeText(getApplicationContext(), "Login Successful! ", Toast.LENGTH_SHORT).show();
                } else {
                    // error response, no access to resource?
                    tv_loginStatus.setText("Login Status: Failed");
                    Toast.makeText(getApplicationContext(), "Check your credentials again! \nToken content: " + user.getToken(), Toast.LENGTH_LONG).show();
                }
            }



            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    public void guestLogin(View view) {
        if (pref.getString("token", null) != null)
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            boolean loginStatus = getIntent().getBooleanExtra("loginStatus", false);
            intent.putExtra("loginStatus", true);
            startActivity(intent);
        }
        else
        {
            Snackbar.make(view, "You must gain a login token first!", Snackbar.LENGTH_LONG).show();
        }

    }
}
