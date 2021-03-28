package com.example.navoffice.ui.back_call;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BackCallFragment extends Fragment {

    private BackCallViewModel backCallViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        backCallViewModel = new ViewModelProvider(this).get(BackCallViewModel.class);

        View root = inflater.inflate(R.layout.fragment_back_call, container, false);

        backCallViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                ListView listView = (ListView) getActivity().findViewById(R.id.listView);
                final ArrayList<String> catNames = new ArrayList<>();

                final ArrayAdapter<String> adapter;
                adapter = new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_list_item_1, catNames);
                listView.setAdapter(adapter);

                try {
                    DBHelper dbHelper = new DBHelper(getActivity());

                    SQLiteDatabase database = new DBHelper(getActivity()).getWritableDatabase();


                    //ContentValues contentValues = new ContentValues();


                    //contentValues.put(DBHelper.KEY_NAME, "hello");
                    //contentValues.put(DBHelper.KEY_MAIL, "maillll");

                    //database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                    //добавление

                    Set<String> subSet = new HashSet<String>();
                    Cursor cursor = database.rawQuery("SELECT * FROM main", null);
                    if (cursor.moveToFirst()) {
                        int nameRoom = cursor.getColumnIndex("Room");
                        int nameDay = cursor.getColumnIndex(DBHelper.KEY_DAY);
                        do {
                            subSet.add(cursor.getString(nameRoom));

                            //Toast.makeText(getActivity(), cursor.getString(nameRoom) + cursor.getString(nameDay), Toast.LENGTH_LONG).show();
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
                    catNames.addAll(0, subSet);
                    //catNames.add(0, cursor.getString(nameRoom));
                    adapter.notifyDataSetChanged();
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
        return root;
    }
}
