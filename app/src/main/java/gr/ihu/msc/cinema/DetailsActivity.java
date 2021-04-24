package gr.ihu.msc.cinema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import gr.ihu.msc.cinema.classes.DataStore;

public class DetailsActivity extends AppCompatActivity {

    TextView textViewTitle;
    TextView textViewCategory;
    TextView textViewDate;
    TextView textViewTime;
    TextView textViewPrice;
    Button buttonVisitWebsite;

    HashMap<String, Object> movie = null;

    private void findViews() {
        textViewTitle = (TextView) findViewById(R.id.movie_details_title);
        textViewCategory = (TextView)findViewById(R.id.movie_details_category);
        textViewDate = (TextView)findViewById(R.id.movie_details_date);
        textViewTime = (TextView)findViewById(R.id.movie_details_time);
        textViewPrice = (TextView)findViewById(R.id.movie_details_price);
        buttonVisitWebsite = (Button)findViewById(R.id.buttonVisitWebsite);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

            //Animation when this Activity appears
            overridePendingTransition(R.anim.pull_in_from_right, R.anim.hold);

            findViews();

            Intent intent = getIntent();
            int moviePosition = intent.getIntExtra(DataStore.KEY_POSITION, 0);

            movie = DataStore.Movies.get(moviePosition);
            String movieTitle = (String)movie.get(DataStore.KEY_TITLE);
            String movieCategoryName = (String)movie.get(DataStore.KEY_CATEGORYNAME);
            String movieDate = (String)movie.get(DataStore.KEY_DATE);
            String movieTimeName = (String)movie.get(DataStore.KEY_TIMENAME);
            String moviePriceName = (String)movie.get(DataStore.KEY_PRICENAME);

            textViewTitle.setText(movieTitle);
            textViewCategory.setText(movieCategoryName);
            textViewDate.setText(movieDate);
            textViewTime.setText(movieTimeName);
            textViewPrice.setText(moviePriceName);

            buttonVisitWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String movieImbdUrl = (String)movie.get(DataStore.KEY_IMBDURL);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(movieImbdUrl));
                    startActivity(browserIntent);
                }
            });
        }
    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.hold, R.anim.push_out_to_right);
        super.onPause();
    }
}
