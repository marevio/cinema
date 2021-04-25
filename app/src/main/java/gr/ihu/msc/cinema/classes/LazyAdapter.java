package gr.ihu.msc.cinema.classes;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import gr.ihu.msc.cinema.R;

public class LazyAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, Object>> data;
    private static LayoutInflater inflater=null;
    public gr.ihu.msc.cinema.classes.ImageLoader imageLoader;

    public LazyAdapter(Activity a, ArrayList<HashMap<String, Object>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imageLoader=new gr.ihu.msc.cinema.classes.ImageLoader(activity);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item, null);

        TextView title = (TextView)vi.findViewById(R.id.movie_item_title);
        TextView category = (TextView)vi.findViewById(R.id.movie_item_category);
        TextView date = (TextView)vi.findViewById(R.id.movie_item_date);
        TextView time = (TextView)vi.findViewById(R.id.movie_item_time);
        TextView price = (TextView)vi.findViewById(R.id.movie_item_price);
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.imageView);

        HashMap<String, Object> movie = new HashMap<String, Object>();
        movie = data.get(position);

        // Setting all values in listview
        title.setText((String)movie.get(DataStore.KEY_TITLE));
        category.setText((String)movie.get(DataStore.KEY_CATEGORYID));
        date.setText((String)movie.get(DataStore.KEY_DATE));
        time.setText((String)movie.get(DataStore.KEY_TIMEID));
        price.setText((String)movie.get(DataStore.KEY_PRICEID));
        imageLoader.DisplayImage((String)movie.get(DataStore.KEY_COVERURL), thumb_image);
        return vi;
    }

}
