package gr.ihu.ct.cinema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {
    TextView textViewInfo;
    ListView listViewBooks;

    private void findViews(){
        textViewInfo = (TextView)findViewById(R.id.textViewInfo);
        listViewBooks = (ListView)findViewById(R.id.listViewBooks);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Animation when this Activity appears
        overridePendingTransition(R.anim.pull_in_from_right, R.anim.hold);

        //get user filters from Intent
        Intent intent = getIntent();
        String filterAuthor = intent.getStringExtra("AUTHOR");
        String filterTitle = intent.getStringExtra("TITLE");
        int filterGenreId = intent.getIntExtra("GENREID", 0);


        findViews();
        //show user filters for information
        String message = String.format("Author: %s\nTitle: %s\nGenreId: %d", filterAuthor, filterTitle, filterGenreId);
        textViewInfo.setText(message);
        //show all genres on our list
        ArrayAdapter<CharSequence> genresAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.book_genres,
                android.R.layout.simple_list_item_1
        );
        listViewBooks.setAdapter(genresAdapter);
    }
    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.hold, R.anim.push_out_to_right);
        super.onPause();
    }

}