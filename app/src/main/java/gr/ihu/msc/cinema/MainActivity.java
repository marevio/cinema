package gr.ihu.msc.cinema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.String.format;

public class MainActivity extends AppCompatActivity {
    private EditText textTitle;
    private Spinner spinnerCategory;
    private EditText textDate;
    private EditText textTime;
    private EditText textPrice;
    private Button buttonSearch;

    /*public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);*/

   @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textTitle = (EditText)findViewById(R.id.editTextTitle);
        textDate = (EditText)findViewById(R.id.editTextDate);
        textTime = (EditText)findViewById(R.id.editTextTime);
        textPrice = (EditText)findViewById(R.id.editTextPrice);
        spinnerCategory = (Spinner)findViewById(R.id.spinnerCategory);
        buttonSearch = (Button)findViewById(R.id.buttonSearch);


        ArrayAdapter<CharSequence> genreAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.movies_categories,
                android.R.layout.simple_spinner_item
        );
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(genreAdapter);

        spinnerCategory.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spinnerCategory.getSelectedView()).setTextColor(Color.rgb(191,169,140) );
            }
        });

       /* buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                // Code will run on Button Click
                //
                String Title = textTitle.getText().toString();
                String Category = spinnerCategory.getSelectedItem().toString();
                String Date = textDate.getText().toString();
                String Time = textTime.getText().toString();
                String Price = textPrice.getText().toString();
                String message = String.format("Title: %s\nCategory: %s\nDate: %s\nTime: %s\nPrice:%s", Title, Category, Date,Time,Price);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }

        });
        return true;
    }*/
         buttonSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String filterTitle = textTitle.getText().toString();
                String filterDate = textDate.getText().toString();
                String filterTime = textTitle.getText().toString();
                String filterPrice = textDate.getText().toString();
                int filterCategoryId = spinnerCategory.getSelectedItemPosition();

                Intent intent = new Intent(MainActivity.this, ListActivity.class);

                intent.putExtra("TITLE", filterTitle);
                intent.putExtra("DATE", filterDate);
                intent.putExtra("TIME", filterTime);
                intent.putExtra("PRICE", filterPrice);
                intent.putExtra("CATEGORYID", filterCategoryId);
                 startActivity(intent);
            }
        });

    /*public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_exit:
                finish();
                return true;
            case R.id.menu_settings:
                Toast.makeText(this, "Under construction", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }*/
    }
    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }*/
}