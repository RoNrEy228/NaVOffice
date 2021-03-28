package com.example.navoffice.ui.map;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.navoffice.DBHelper;
import com.example.navoffice.R;

import java.io.File;
import java.io.IOException;

public class MapFragment extends Fragment {

    private MapViewModel mapViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        mapViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                getActivity().findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            DBHelper dbHelper = new DBHelper(getActivity());

                            SQLiteDatabase database = new DBHelper(getActivity()).getWritableDatabase();


                            //ContentValues contentValues = new ContentValues();


                            //contentValues.put(DBHelper.KEY_NAME, "hello");
                            //contentValues.put(DBHelper.KEY_MAIL, "maillll");

                            //database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                            //добавление


                            Cursor cursor = database.rawQuery("SELECT * FROM main", null);
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
                                while (cursor.moveToNext());
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("База Данных повреждена")
                                        .setMessage("Перейдите в раздел настроек и обновите Базу")
                                        .setCancelable(false)
                                        .setNegativeButton("ОК",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                            //Button btn = (Button) findViewById(R.id.button);
                            //отображение

/*
                database.delete(DBHelper.TABLE_CONTACTS, null, null);
                    //удаление
  */
                            cursor.close();
                            dbHelper.close();

                            //ImageView it = (ImageView) getActivity().findViewById(R.id.it_cab);
                            //it.setVisibility(View.VISIBLE);
                        }
                        catch (Exception e) {
                            File file = new File(Environment.getExternalStorageDirectory() + "/Download/test.db/");
                            file.delete();
                            if(file.exists()){
                                try {
                                    file.getCanonicalFile().delete();
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                                if(file.exists()){
                                    getActivity().deleteFile(file.getName());
                                }
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("База Данных не найдена")
                                    .setMessage("Перейдите в раздел настроек и обновите Базу")
                                    .setCancelable(false)
                                    .setNegativeButton("ОК",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }
                });


            }
        });
        return root;
    }
}
