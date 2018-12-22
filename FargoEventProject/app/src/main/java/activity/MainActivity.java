package activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heecheonpark.parkfinalproject.CustomListAdapter;
import com.example.heecheonpark.parkfinalproject.R;
import com.example.heecheonpark.parkfinalproject.SimpleDividerItemDecoration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import model.Event;
import model.LoginAPIService;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    boolean loginStatus = false;
    String token;
    SearchView searchView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        //Set ActionBar Title
        getSupportActionBar().setTitle("Events");
        //Set ActionBar Background Color
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#035686")));

        //Set text color of Action Bar
        Toolbar actionBarToolbar = (Toolbar)findViewById(R.id.action_bar);
        if (actionBarToolbar != null)
            actionBarToolbar.setTitleTextColor(Color.WHITE);

        //Set Status Bar color (Only available from Lollipop (SDK Level 21 or above))
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorMyriadStatusBar));
        }
        loginStatus = getIntent().getBooleanExtra("loginStatus", false);
        checkLogin();
        token = pref.getString("token", null);
        if (token != null) {
            LoginAPIService loginAPIService = new LoginAPIService();
            loginAPIService.getEvent(new Callback<List<Event>>() {

                @Override
                public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                        List<Event> eventList = response.body();

                /*
                Learned a new thing:

                Not using this block but learned how to convert an arraylist to a static array.

                TextView tv_testAPI;
                Button btn_testAPI;
                ArrayList<Integer> eventIDList;
                ArrayList<String> eventTitleList;
                 ArrayList<String> eventImageURLList;
                ArrayList<String> eventLocationList;

                for (int i = 0; i < eventList.size(); i++)
                {
                    eventIDList.add(eventList.get(i).getId());
                    eventTitleList.add(eventList.get(i).getTitle());
                    eventImageURLList.add(eventList.get(i).getImage_url());
                    eventLocationList.add(eventList.get(i).getLocation());
                }

                Integer[] eventIDs = eventIDList.toArray(new Integer[eventIDList.size()]);
                String[] eventTitles = eventTitleList.toArray(new String[eventTitleList.size()]);
                String[] eventImageURLs = eventImageURLList.toArray(new String[eventImageURLList.size()]);
                String[] eventLocations = eventLocationList.toArray(new String[eventLocationList.size()]);

                */
                    searchView = findViewById(R.id.mSearch);
                    RecyclerView recyclerView = findViewById(R.id.recycler_view);
                    CustomListAdapter mAdapter = new CustomListAdapter(eventList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
                    recyclerView.setAdapter(mAdapter);

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String query) {
                            //FILTER AS YOU TYPE
                            mAdapter.getFilter().filter(query);
                            return false;
                        }
                    });
//                        Toast.makeText(getApplicationContext(), "Response: " + event.get(0).getTitle(), Toast.LENGTH_LONG).show();
//                        tv_testAPI.setText(event.toString());

                }

                @Override
                public void onFailure(Call<List<Event>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Something went wrong: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }, token);
        }


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
            editor.clear();
            editor.apply();
            intent.putExtra("loginStatus", loginStatus);
            startActivity(intent);
            return(true);

        case R.id.checkToken:
            Toast.makeText(getApplicationContext(), pref.getString("token", null), Toast.LENGTH_SHORT).show();
    }
        return(super.onOptionsItemSelected(item));
    }

    public void checkLogin()
    {

        try {
            if (!loginStatus)
            {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("loginStatus", loginStatus);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("loginStatus", loginStatus);
            startActivity(intent);
        }
    }

}
