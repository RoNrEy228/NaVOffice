package com.example.navoffice.ui.map;
import com.example.navoffice.*;
import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.navoffice.DBHelper;
import com.example.navoffice.MainActivity;
import com.example.navoffice.R;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MapFragment extends Fragment {

    private MapViewModel mapViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        mapViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Button button = (Button) getActivity().findViewById(R.id.button2);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        File dbshka = new File(Environment.DIRECTORY_DOWNLOADS + "/db.db");
                        if (!dbshka.exists()) {
                            String url = "https://getfile.dokpub.com/yandex/get/https://disk.yandex.ru/d/cG5HA1cS9UVR3w";
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE |
                                    DownloadManager.Request.NETWORK_WIFI);
                            request.setTitle("db");
                            request.setDescription("NaVOffice DB");
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "db.db");
                            DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                            manager.enqueue(request);
                            try {
                                TimeUnit.SECONDS.sleep(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        DBHelper dbHelper = new DBHelper(getActivity());

                        SQLiteDatabase database = dbHelper.getWritableDatabase();

                        ContentValues contentValues = new ContentValues();




                        //contentValues.put(DBHelper.KEY_NAME, "hello");
                        //contentValues.put(DBHelper.KEY_MAIL, "maillll");

                        //database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                        //добавление

                        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

                        if (cursor.moveToFirst()) {
                            int nameRoom = cursor.getColumnIndex(DBHelper.KEY_ROOM);
                            int nameGroup = cursor.getColumnIndex(DBHelper.KEY_GROUP);
                            int nameDay = cursor.getColumnIndex(DBHelper.KEY_DAY);
                            do {
                                Log.i("DB:", "GROUP = " + cursor.getString(nameGroup) + ", DAY = " + cursor.getString(nameDay) + ", ROOM = " + cursor.getString(nameRoom));
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

                        Date data = new Date();
                        //  НИЖЕ КОММЕНТ ЧЕК
                        //  ДИМА, ЭТА ШТУКА СКРЫВАЕТ IT КАБИНЕТ!!!
                        //  ВЫШЕ КОММЕНТ ЧЕК
                        ImageView it = (ImageView) getActivity().findViewById(R.id.it_cab);
                        it.setVisibility(View.VISIBLE);
                    }
                });


            }
        });
        return root;
    }
}
