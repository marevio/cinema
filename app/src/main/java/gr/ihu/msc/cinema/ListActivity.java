package gr.ihu.msc.cinema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
        String filterDate = intent.getStringExtra("DATE");
        String filterTime = intent.getStringExtra("TIME");
        String filterPrice = intent.getStringExtra("PRICE");
        int filterCategoryId = intent.getIntExtra("CATEGORYID", 0);

        findViews();
        //show user filters for information
        String message = String.format("Title: %s\nCategory: %s\nDate: %s\nTime: %s\nPrice:%s",filterTitle, filterCategoryId, filterDate,filterTime,filterPrice);
        textViewInfo.setText(message);
        //show all genres on our list
        ArrayAdapter<CharSequence> genresAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.movies_categories,
                android.R.layout.simple_list_item_1
        );
        listViewMovies.setAdapter(genresAdapter);
    }
    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.hold, R.anim.push_out_to_right);
        super.onPause();
    }

}