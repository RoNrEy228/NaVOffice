package com.example.navoffice;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.navoffice.ui.map.MapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity  {

    Button btnAdd, btnRead, btnClear;
    EditText etName, etEmail;

    DBHelper dbHelper;
    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET}, 0);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_map, R.id.navigation_settings, R.id.navigation_back_call)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        dbHelper = new DBHelper(this);

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();




        contentValues.put(DBHelper.KEY_NAME, "hello");
        contentValues.put(DBHelper.KEY_MAIL, "maillll");

        database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
        //добавление

        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
            do {
                Log.i("DB:", "ID = " + cursor.getInt(idIndex) + ", name = " + cursor.getString(nameIndex) + ", email = " + cursor.getString(emailIndex));
                // Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                //       ", name = " + cursor.getString(nameIndex) +
                //      ", email = " + cursor.getString(emailIndex));
            }
            while (cursor.moveToNext()) ;
        }
        //Button btn = (Button) findViewById(R.id.button);
        //отображение

/*
                database.delete(DBHelper.TABLE_CONTACTS, null, null);
                    //удаление
  */
        cursor.close();
        dbHelper.close();
    }
}
