package gr.ihu.msc.cinema;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.os.Build;
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
//init for permissions
    private static final int MY_PERMISSIONS_REQUEST_CODE = 1001;
    public static String[] RequiredPermissions = {
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    private EditText textTitle;
    private Spinner  spinnerCategory;
    private EditText textDate;

    private Spinner  spinnerTime;
    private Spinner  spinnerPrice;
    private Button   buttonSearch;

    // Date spiner
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    /*public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);*/

    private String formatedDate = "";

   @RequiresApi(api = Build.VERSION_CODES.O)
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textTitle = (EditText)findViewById(R.id.editTextTitle);
        spinnerCategory = (Spinner)findViewById(R.id.spinnerCategory);
        spinnerTime = (Spinner)findViewById(R.id.spinnerTime);
        spinnerPrice = (Spinner)findViewById(R.id.spinnerPrice);
        buttonSearch = (Button)findViewById(R.id.buttonSearch);

       //initialize Date picker set today's Date
       initDatePicker();

       dateButton = findViewById(R.id.datePickerButton);
       dateButton.setText(getTodaysDate());
       dateButton.setTooltipText(getSearchTodaysDate());
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

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                String filterTitle = textTitle.getText().toString();
                int filterCategoryId = spinnerCategory.getSelectedItemPosition();
                String filterDate = dateButton.getTooltipText().toString();
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

         // executes the new permissions mechanism(from android M and later)
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           if (!checkPermissions()){
               requestPermissions(RequiredPermissions, MY_PERMISSIONS_REQUEST_CODE);
           }
       }

    }

    // Help function for DatePicker
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private String getSearchTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return  searchDateString(day, month, year);
    }

    //Help function for DatePicker
    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                String searchDate = searchDateString(day, month, year);
                dateButton.setTooltipText(searchDate);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK ;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

        //Time limit for datePicker
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    //    Help function for DatePicker
    private String makeDateString(int day, int month, int year)
    {
        this.formatedDate = String.format("%04d-%02d-%02d", year, month, day);
        return day + "/" + month + "/" + year;
        //return year + "-" + month + "-" + day;
    }


    private String searchDateString(int day, int month, int year)
    {
        this.formatedDate = String.format("%04d-%02d-%02d", year, month, day);
        return year + "-" + month + "-" + day;
    }

    //    Help function for DatePicker
    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    //check for permissions from array true if all permissions false if only one not give permission
    @RequiresApi(Build.VERSION_CODES.M)
    public boolean checkPermissions(){
        for (String permission : RequiredPermissions){
            if (ActivityCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                boolean permissionGranted = false;
                if (grantResults.length > 0){
                    permissionGranted = true;
                    for (int grantResult : grantResults) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            permissionGranted = false;
                            break;
                        }
                    }
                }
                if (permissionGranted){
                    //possibly show a message to the user
                }
            }
            break;
        }
    }



}