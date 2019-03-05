package activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.heecheonpark.parkfinalproject.R;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Event;
import model.LoginAPIService;
import model.Speaker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        SharedPreferences pref;
        SharedPreferences.Editor editor;

        Bundle bundle = getIntent().getBundleExtra("bundle");
        Event event = bundle.getParcelable("event");
        Integer id = bundle.getInt("id");
        ArrayList<Integer> speakersIDList = bundle.getIntegerArrayList("speakerID");
        event.setId(id);
        ImageView iv_eventImage = findViewById(R.id.iv_eventImage);
        ImageView iv_speaker = findViewById(R.id.iv_speaker);
        EditText ml_eventDescription = findViewById(R.id.ml_eventDescription);
        EditText ml_speakerDescription = findViewById(R.id.ml_speakerDescription);
        Picasso.get().load(event.getImage_url()).into(iv_eventImage);
        getSupportActionBar().setTitle(event.getTitle());
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#035686")));
        Toolbar actionBarToolbar = findViewById(R.id.action_bar);
        if (actionBarToolbar != null)
            actionBarToolbar.setTitleTextColor(Color.WHITE);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorMyriadStatusBar));
        }
        String dateStart = event.getStart_date_time();
        String dateEnd = event.getEnd_date_time();
        String[] dateStartParser = dateStart.split("T");
        String[] dateEndParser = dateEnd.split("T");
        String[] timeStartParser = dateStartParser[1].split(":");
        String[] timeEndParser = dateEndParser[1].split(":");
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String token = pref.getString("token", null);

        //ml_eventDescription.append("ID:" + event.getId() +"\n");
        ml_eventDescription.append("Title:" + event.getTitle() +"\n");
        ml_eventDescription.append("Description: " + event.getEvent_description()+"\n");
        ml_eventDescription.append("Location:" + event.getLocation() +"\n");
        ml_eventDescription.append("Date:" + dateStartParser[0] + " " +
                timeStartParser[0] + ":" + timeStartParser[1] + " ~ " +
                timeEndParser[0] + ":" + timeEndParser[1] + "\n");

        Toast.makeText(getApplicationContext(), event.getTitle().toString(), Toast.LENGTH_LONG).show();


        LoginAPIService loginAPIService = new LoginAPIService();
        loginAPIService.getSpeaker(new Callback<Speaker>() {
            @Override
            public void onResponse(Call<Speaker> call, Response<Speaker> response) {

                Speaker speaker = response.body();

                Toast.makeText(getApplicationContext(), "Response code: " + response.code() + "\n" +
                 response.body().getBio(), Toast.LENGTH_LONG).show();

                ml_speakerDescription.setText("Speaker: " + speaker.getFirst_name()+ " " +
                        speaker.getLast_name() + "\n" +
                        "Bio: " + speaker.getBio() + "\n");
                Picasso.get().load(speaker.getImage_url()).into(iv_speaker);

            }

            @Override
            public void onFailure(Call<Speaker> call, Throwable t) {

            }
        }, token, id);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        /*case R.id.add:

            return(true);
        case R.id.reset:

            return(true);
        case R.id.about:

            return(true);*/
        case R.id.logout:
            //add the function to perform here
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }
}
