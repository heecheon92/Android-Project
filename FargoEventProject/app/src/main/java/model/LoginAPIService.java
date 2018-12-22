package model;

import java.util.List;

import rest.LoginService;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAPIService {
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://challenge.myriadapps.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private LoginService loginService;

    public LoginAPIService()
    {
        loginService = retrofit.create(LoginService.class);
    }

    public void getToken(Callback<User> callback, String username, String password)
    {
        loginService.getResponse(username, password).enqueue(callback);
    }

    public void getEvent(Callback<List<Event>> callback, String token)
    {
        loginService.getEvent(token).enqueue(callback);
    }

    public void getEachEvent(Callback<Event> callback, String token, Integer id)
    {
        loginService.getEachEvent(token, id).enqueue(callback);
    }

    public void getSpeaker(Callback<Speaker> callback, String token, Integer id)
    {
        loginService.getSpeaker(token, id).enqueue(callback);
    }
}
