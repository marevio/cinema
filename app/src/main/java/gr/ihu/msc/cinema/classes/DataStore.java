package gr.ihu.msc.cinema.classes;

import android.content.Context;
import android.content.res.Resources;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.List;

import gr.ihu.msc.cinema.R;

public class DataStore {

    public static String KEY_POSITION = "POSITION";

    public static String KEY_ID = "ID";
    public static String KEY_TITLE = "TITLE";
    public static String KEY_DIRECTOR = "DIRECTOR";
    public static String KEY_ACTOR = "ACTOR";
    public static String KEY_CATEGORYID = "CATEGORYID";
    public static String KEY_CATEGORYNAME = "CATEGORYNAME";
    public static String KEY_IMBDURL = "IMBDURL";
    public static String KEY_COVERURL = "COVERURL";
    public static String KEY_DATE = "DATE";
    public static String KEY_TIME = "TIME";
    public static String KEY_PRICE = "PRICE";


    public static Context AppContext = null;
    public static Resources AppResources = null;
    public static String[] Categories = null;
    public static ArrayList<HashMap<String, Object>> Movies= new ArrayList<HashMap<String, Object>>();

    public static void Init(Context context){
        AppContext = context;
        AppResources = AppContext.getResources();
        Categories = AppResources.getStringArray(R.array.movies_categories);
    }

    public static void LoadMovies(String filterTitle, int filterCategoryId, String filterDate, String filterTime, String filterPrice) {
        DataStore.Movies.clear();

        String contents = AssetsUtils.getFileContentsFromAssets(AppContext, "movies.json");
        //String urlString = String.format("http://informatics.teicm.gr/msc/android/books_sample.json.txt?author=%s&title=%s&genreid=%d", filterAuthor, filterTitle, filterGenreId);
        //String contents = NetworkUtils.getFileContentsFromFromUrl(urlString);

        JSONObject json = JsonParser.getJsonObject(contents);
        JSONArray jMovies = json.optJSONArray("movies");

        if (jMovies == null) return;
        int nMovies = jMovies.length();
        for (int i=0; i<nMovies; i++){
            JSONObject jCurMovie = jMovies.optJSONObject(i);

            int movieID = jCurMovie.optInt(DataStore.KEY_ID,0);

            String movieTitle = jCurMovie.optString(DataStore.KEY_TITLE);
            int movieCategoryId = jCurMovie.optInt(DataStore.KEY_CATEGORYID,0);
            String movieDirector= jCurMovie.optString(DataStore.KEY_DIRECTOR);
            String movieActor= jCurMovie.optString(DataStore.KEY_ACTOR);
            String movieImbdUrl = jCurMovie.optString(DataStore.KEY_IMBDURL);
            String movieCoverUrl = jCurMovie.optString(DataStore.KEY_COVERURL);
            String movieDate = jCurMovie.optString(DataStore.KEY_DATE);
            String movieTime = jCurMovie.optString(DataStore.KEY_TIME);
            String moviePrice = jCurMovie.optString(DataStore.KEY_PRICE);



            //get Category name by ID
            String movieCategoryName = DataStore.Categories[movieCategoryId];

            // hold each book in a HashMap (Associative Array)
            HashMap<String, Object> movie = new HashMap<String, Object>();

            movie.put(DataStore.KEY_ID, movieID);

            movie.put(DataStore.KEY_TITLE, movieTitle);
            movie.put(DataStore.KEY_CATEGORYID,movieCategoryId);
            movie.put(DataStore.KEY_CATEGORYNAME, movieCategoryName);
            movie.put(DataStore.KEY_DIRECTOR, movieDirector);
            movie.put(DataStore.KEY_ACTOR, movieActor);
            movie.put(DataStore.KEY_DIRECTOR, movieDirector);
            movie.put(DataStore.KEY_IMBDURL, movieImbdUrl);
            movie.put(DataStore.KEY_COVERURL, movieCoverUrl);
            movie.put(DataStore.KEY_DATE, movieDate);
            movie.put(DataStore.KEY_TIME, movieTime);
            movie.put(DataStore.KEY_PRICE, moviePrice);



            DataStore.Movies.add(movie);
        }
    }

}


