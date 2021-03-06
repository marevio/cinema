package gr.ihu.msc.cinema;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.util.HashMap;
import java.util.Objects;

import gr.ihu.msc.cinema.classes.DataStore;
import gr.ihu.msc.cinema.classes.ImageLoader;

public class DetailsActivity extends AppCompatActivity {

    TextView textViewTitle;
    TextView textViewCategory;
    TextView textViewDate;
    TextView textViewTime;
    TextView textViewPrice;
    TextView textViewLabelDescription;
    TextView textViewDescription;
    ImageLoader imageLoader;
    ImageView imageViewCover;
    WebView webView;

    Button buttonVisitWebsite;

    HashMap<String, Object> movie = null;

    private void findViews() {
        textViewTitle = (TextView) findViewById(R.id.movie_details_title);
        textViewCategory = (TextView)findViewById(R.id.movie_details_category);
        textViewDate = (TextView)findViewById(R.id.movie_details_date);
        textViewTime = (TextView)findViewById(R.id.movie_details_time);
        textViewPrice = (TextView)findViewById(R.id.movie_details_price);
        textViewDescription=(TextView)findViewById(R.id.movie_details_description);
        buttonVisitWebsite = (Button)findViewById(R.id.buttonVisitWebsite);
        imageViewCover = (ImageView)findViewById(R.id.imageViewCover);
        webView = (WebView)findViewById(R.id.webview_player_view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

            //Animation when this Activity appears
            overridePendingTransition(R.anim.pull_in_from_right, R.anim.hold);

            findViews();

        //Check if it's a Phone or Tablet
        /*TelephonyManager manager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        if(manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE){
            //return "Tablet";
            webView.getSettings().getJavaScriptEnabled();
            webView.loadUrl("https://www.youtube.com/embed/YE7VzlLtp-4");
        }else{
            //return "Mobile";
        }*/




            Intent intent = getIntent();
            int moviePosition = intent.getIntExtra(DataStore.KEY_POSITION, 0);

            movie = DataStore.Movies.get(moviePosition);
            String movieTitle = (String)movie.get(DataStore.KEY_TITLE);
            String movieCategoryName = (String)movie.get(DataStore.KEY_CATEGORYID);
            String movieDate = (String)movie.get(DataStore.KEY_DATE);
            String movieTimeName = (String)movie.get(DataStore.KEY_TIMEID);
            String moviePriceName = (String)movie.get(DataStore.KEY_PRICEID);
            String movieCoverUrl=(String)movie.get(DataStore.KEY_COVERURL);
            String movieDescription=(String)movie.get(DataStore.KEY_DESCRIPTION);
            String movieTrailer=(String)movie.get(DataStore.KEY_TRAILER);

        //webview youtube trailer
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl(movieTrailer);

            textViewTitle.setText(movieTitle);
            textViewCategory.setText(movieCategoryName);
            textViewDate.setText(movieDate);
            textViewTime.setText(movieTimeName);
            textViewPrice.setText(moviePriceName);
            textViewDescription.setText(movieDescription);
            imageLoader = new ImageLoader(this);
            imageLoader.DisplayImage(movieCoverUrl,imageViewCover);


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
