package com.example.navoffice.ui.map;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.navoffice.DBHelper;
import com.example.navoffice.R;

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
                        /*File dbshka = new File(Environment.DIRECTORY_DOWNLOADS + "test.db");
                        if (!dbshka.exists()) {
                            String url = "https://getfile.dokpub.com/yandex/get/https://disk.yandex.ru/d/cG5HA1cS9UVR3w";
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE |
                                    DownloadManager.Request.NETWORK_WIFI);
                            request.setTitle("test");
                            request.setDescription("NaVOffice DB");
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "test.db");
                            DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                            manager.enqueue(request);
                        }

                         */
                        DBHelper dbHelper = new DBHelper(getActivity());

                        SQLiteDatabase database = dbHelper.getWritableDatabase();


                        //ContentValues contentValues = new ContentValues();




                        //contentValues.put(DBHelper.KEY_NAME, "hello");
                        //contentValues.put(DBHelper.KEY_MAIL, "maillll");

                        //database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                        //добавление

                        Cursor cursor = database.query("main", null, null, null, null, null, null);
                        if (cursor.moveToFirst()) {
                            int nameRoom = cursor.getColumnIndex("Room");
                            int nameDay = cursor.getColumnIndex(DBHelper.KEY_DAY);
                            do {
                                Toast.makeText(getActivity(), cursor.getString(nameRoom) + cursor.getString(nameDay), Toast.LENGTH_LONG).show();
                                //Log.i("DB:", "ROOM = " + cursor.getString(nameRoom));
                                // Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                //       ", name = " + cursor.getString(nameIndex) +
                                //      ", email = " + cursor.getString(emailIndex));
                            }
                            while (cursor.moveToNext()) ;
                        }
                        else {
                            Toast.makeText(getActivity(), "roghwoigw", Toast.LENGTH_LONG).show();
                        }
                        //Button btn = (Button) findViewById(R.id.button);
                        //отображение

/*
                database.delete(DBHelper.TABLE_CONTACTS, null, null);
                    //удаление
  */
                        cursor.close();
                        dbHelper.close();

                        //Date data = new Date();
                        //  НИЖЕ КОММЕНТ ЧЕК
                        //  ДИМА, ЭТА ШТУКА СКРЫВАЕТ IT КАБИНЕТ!!!
                        //  ВЫШЕ КОММЕНТ ЧЕК
                        //ImageView it = (ImageView) getActivity().findViewById(R.id.it_cab);
                        //it.setVisibility(View.VISIBLE);
                    }
                });


            }
        });
        return root;
    }
}
