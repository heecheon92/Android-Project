package rest;

import java.util.List;

import model.Event;
import model.Speaker;
import model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LoginService {
    @POST("/api/v1/login")
    Call<User> basicLogin();

/*
    Learned a new thing:

    java.lang.IllegalArgumentException: URL query string "Username={username}&Password={password}"
    must not have replace block. For dynamic query parameters use @Query.

    I guess.... I have to use @Query instead of @Path when accessing data through queries
 */

//@POST("api/v1/login?Username={username}&Password={password}")
@POST("api/v1/login")
    Call<User> getResponse(
            @Query("Username") String username,
            @Query("Password") String password);

//@GET("api/v1/events")
//    Call<ArrayList<Event>> getEvent(Callback<ArrayList<Event>> event);

@GET("api/v1/events")
Call<List<Event>> getEvent(@Header("Authorization") String token);

@GET("api/v1/events/{id}")
    Call<Event> getEachEvent(@Header("Authorization") String token,
                             @Path("id") Integer id);

@GET("api/v1/speakers/{id}")
    Call<Speaker> getSpeaker(@Header("Authorization") String token,
                             @Path("id") Integer id);
}
