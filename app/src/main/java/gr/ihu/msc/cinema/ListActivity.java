package gr.ihu.msc.cinema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import gr.ihu.msc.cinema.classes.DataStore;

public class ListActivity extends AppCompatActivity {
    TextView textViewInfo;
    ListView listViewMovies;

    private void findViews() {
        textViewInfo = (TextView)findViewById(R.id.textViewInfo);
        listViewMovies = (ListView)findViewById(R.id.listViewMovies);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Animation when this Activity appears
        overridePendingTransition(R.anim.pull_in_from_right, R.anim.hold);

        //get user filters from Intent
        Intent intent = getIntent();
        String filterTitle = intent.getStringExtra("TITLE");
        int filterCategoryId = intent.getIntExtra("CATEGORYID", 0);
        String filterDate = intent.getStringExtra("DATE");
        String filterTime = intent.getStringExtra("TIME");
        String filterPrice = intent.getStringExtra("PRICE");


        findViews();
        //show user filters for information
        String message = String.format("Title: %s\nCategory: %s\nDate: %s\nTime: %s\nPrice:%s",filterTitle, filterCategoryId, filterDate,filterTime,filterPrice);
        textViewInfo.setText(message);
        //show all genres on our list
        DataStore.LoadMovies(filterTitle, filterCategoryId, filterDate,filterTime,filterPrice);


      /*  ArrayAdapter<CharSequence> categoriesAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.movies_categories,
                android.R.layout.simple_list_item_1
        );
        listViewMovies.setAdapter(categoriesAdapter);*/

        //COMPLEX OBJECT BINDING
        ListAdapter moviesAdapter = new SimpleAdapter(
                this,
                DataStore.Movies,
                R.layout.list_item,
                new String[] {DataStore.KEY_TITLE, DataStore.KEY_CATEGORYID, DataStore.KEY_DATE, DataStore.KEY_TIME, DataStore.KEY_PRICE},
                new int[] {R.id.movie_item_title, R.id.movie_item_category, R.id.movie_item_date,R.id.movie_item_time,R.id.movie_item_price}
        );
        listViewMovies.setAdapter(moviesAdapter);
    }
    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.hold, R.anim.push_out_to_right);
        super.onPause();
    }

}