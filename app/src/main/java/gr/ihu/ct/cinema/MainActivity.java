package gr.ihu.ct.cinema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText textAuthor;
    private EditText textTitle;
    private Spinner spinnerGenre;;
    private Button buttonSearch;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
                case R.id.menu_exit:
                    finish();
                    return true;
                case R.id.menu_settings:
                    Toast.makeText(this, "Under construction", Toast.LENGTH_LONG).show();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textAuthor = (EditText)findViewById(R.id.editTextAuthor);
        textTitle = (EditText)findViewById(R.id.editTextTitle);
        spinnerGenre = (Spinner) findViewById(R.id.spinnerGenre);
        buttonSearch = (Button)findViewById(R.id.buttonSearch);

        ArrayAdapter<CharSequence> genreAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.book_genres,
                android.R.layout.simple_spinner_item
        );
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(genreAdapter);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filterAuthor = textAuthor.getText().toString();
                String filterTitle = textTitle.getText().toString();
                int filterGenreId = spinnerGenre.getSelectedItemPosition();
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("AUTHOR", filterAuthor);
                intent.putExtra("TITLE", filterTitle);
                intent.putExtra("GENREID", filterGenreId);
                startActivity(intent);
            }
        });

    }
}