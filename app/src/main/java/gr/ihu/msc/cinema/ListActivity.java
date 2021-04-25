package gr.ihu.msc.cinema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import gr.ihu.msc.cinema.classes.DataStore;
import gr.ihu.msc.cinema.classes.LazyAdapter;

public class ListActivity extends AppCompatActivity {
    TextView textViewInfo;
    ListView listViewMovies;

    private void findViews() {
        textViewInfo = (TextView)findViewById(R.id.textViewInfo);
        listViewMovies = (ListView)findViewById(R.id.listViewMovies);
    }

    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.hold, R.anim.push_out_to_right);
        super.onPause();
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
        //String filterTime = intent.getStringExtra("TIME");
        int filterTimeId = intent.getIntExtra("TIMEID", 0);
        //String filterPrice = intent.getStringExtra("PRICE");
        int filterPriceId = intent.getIntExtra("PRICEID", 0);
        findViews();

        //show user filters for information
        String message = String.format("Title: %s\nCategory: %d\nDate: %s\nTime: %d\nPrice: %d", filterTitle, filterCategoryId, filterDate, filterTimeId, filterPriceId);
        textViewInfo.setText(message);
        //show all genres on our list
        DataStore.LoadMovies(filterTitle, filterCategoryId, filterDate, filterTimeId, filterPriceId);


      /*  ArrayAdapter<CharSequence> categoriesAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.movies_categories,
                android.R.layout.simple_list_item_1
        );
        listViewMovies.setAdapter(categoriesAdapter);*/

        //COMPLEX OBJECT BINDING
        /*ListAdapter moviesAdapter = new SimpleAdapter(
                this,
                DataStore.Movies,
                R.layout.list_item,
                new String[] {DataStore.KEY_TITLE, DataStore.KEY_CATEGORYNAME, DataStore.KEY_DATE, DataStore.KEY_TIMENAME, DataStore.KEY_PRICENAME},
                new int[] {R.id.movie_item_title, R.id.movie_item_category, R.id.movie_item_date,R.id.movie_item_time,R.id.movie_item_price}
        );
        listViewMovies.setAdapter(moviesAdapter);*/

        LazyAdapter movieAdapter = new LazyAdapter(this, DataStore.Movies);
        listViewMovies.setAdapter(movieAdapter);

        listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailsIntent = new Intent(ListActivity.this, DetailsActivity.class);
                detailsIntent.putExtra(DataStore.KEY_POSITION, position);
                startActivity(detailsIntent);
            }
        });

    }


}