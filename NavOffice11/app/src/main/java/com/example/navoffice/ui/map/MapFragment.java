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
import java.util.Calendar;
import java.util.GregorianCalendar;
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
                        //Toast.makeText(getActivity(), String.valueOf(7 - (8 - new GregorianCalendar().get(Calendar.DAY_OF_WEEK)) % 7), Toast.LENGTH_LONG).show();
                        try {
                            DBHelper dbHelper = new DBHelper(getActivity());
                            SQLiteDatabase database = new DBHelper(getActivity()).getWritableDatabase();
                            Cursor cursor = database.rawQuery("SELECT * FROM main WHERE `Day_of_ week` = " + String.valueOf(7 - (8 - new GregorianCalendar().get(Calendar.DAY_OF_WEEK)) % 7), null);
                            if (cursor.moveToFirst()) {
                                int nameRoom = cursor.getColumnIndex("Room");
                                int stime = cursor.getColumnIndex("Start_ time");
                                int etime = cursor.getColumnIndex("End_time");
                                do {

                                    String[] st = cursor.getString(stime).split(":");
                                    String[] end = cursor.getString(etime).split(":");
                                    GregorianCalendar now = new GregorianCalendar();
                                    GregorianCalendar calendar_start = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), Integer.parseInt(st[0]), Integer.parseInt(st[1]), now.get(Calendar.SECOND));
                                    GregorianCalendar calendar_end = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), Integer.parseInt(end[0]), Integer.parseInt(end[1]), now.get(Calendar.SECOND));
                                    if (now.compareTo(calendar_start) >= 0 && now.compareTo(calendar_end) < 0) {
                                        switch (cursor.getString(nameRoom)) {
                                            case "it_cab":
                                                getActivity().findViewById(R.id.it_cab).setVisibility(View.VISIBLE);
                                                getActivity().findViewById(R.id.it_way).setVisibility(View.VISIBLE);
                                                break;
                                            case "design_cab":
                                                getActivity().findViewById(R.id.design_cab).setVisibility(View.VISIBLE);
                                                getActivity().findViewById(R.id.design_way).setVisibility(View.VISIBLE);
                                                break;
                                            case "aero_cab":
                                                getActivity().findViewById(R.id.aero_cab).setVisibility(View.VISIBLE);
                                                break;
                                        }
                                    }
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
                            cursor.close();
                            dbHelper.close();
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
