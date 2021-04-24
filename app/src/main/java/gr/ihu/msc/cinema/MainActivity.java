package gr.ihu.msc.cinema;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.Button;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import gr.ihu.msc.cinema.classes.DataStore;

import static java.lang.String.format;

public class MainActivity extends AppCompatActivity {
    private EditText textTitle;
    private Spinner  spinnerCategory;
    private EditText textDate;

    private Spinner  spinnerTime;
    private Spinner  spinnerPrice;
    private Button   buttonSearch;

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    /*public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);*/

   @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textTitle = (EditText)findViewById(R.id.editTextTitle);
        spinnerCategory = (Spinner)findViewById(R.id.spinnerCategory);
        /*textDate = (EditText)findViewById(R.id.editTextDate);*/
        spinnerTime = (Spinner)findViewById(R.id.spinnerTime);
        spinnerPrice = (Spinner)findViewById(R.id.spinnerPrice);
        buttonSearch = (Button)findViewById(R.id.buttonSearch);

       initDatePicker();
       dateButton = findViewById(R.id.datePickerButton);
       dateButton.setText(getTodaysDate());
        
       DataStore.Init(getApplicationContext());

       ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.movies_categories,
                android.R.layout.simple_spinner_item
        );

        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);


       ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(
               this,
               R.array.movies_time,
               android.R.layout.simple_spinner_item
       );
       timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinnerTime.setAdapter(timeAdapter);



       ArrayAdapter<CharSequence> priceAdapter = ArrayAdapter.createFromResource(
               this,
               R.array.movies_price,
               android.R.layout.simple_spinner_item
       );
       priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinnerPrice.setAdapter(priceAdapter);


        spinnerCategory.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spinnerCategory.getSelectedView()).setTextColor(Color.rgb(191,169,140) );
            }
        }
        );

       spinnerCategory.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
           @Override
           public void onGlobalLayout() {
               ((TextView) spinnerTime.getSelectedView()).setTextColor(Color.rgb(191,169,140) );
           }
       } );

       spinnerCategory.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
           @Override
           public void onGlobalLayout() {
               ((TextView) spinnerPrice.getSelectedView()).setTextColor(Color.rgb(191,169,140) );
           }
       } );



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
                int filterCategoryId = spinnerCategory.getSelectedItemPosition();
                String filterDate = textDate.getText().toString();
                int filterTimeId = spinnerTime.getSelectedItemPosition();
                int filterPriceId = spinnerPrice.getSelectedItemPosition();



                Intent intent = new Intent(MainActivity.this, ListActivity.class);

                intent.putExtra("TITLE", filterTitle);
                intent.putExtra("CATEGORYID", filterCategoryId);
                intent.putExtra("DATE", filterDate);
                intent.putExtra("TIMEID", filterTimeId);
                intent.putExtra("PRICEID", filterPriceId);


                startActivity(intent);
            }
        });

    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return month + " " + day + " " + year;
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

}