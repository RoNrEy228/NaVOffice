package com.example.navoffice.ui.back_call;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.Calendar;
import java.util.GregorianCalendar;
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
                    Set<String> subSet = new HashSet<String>();
                    Cursor cursor = database.rawQuery("SELECT * FROM main WHERE `Day_of_ week` = " + String.valueOf(7 - (8 - new GregorianCalendar().get(Calendar.DAY_OF_WEEK)) % 7), null);
                    if (cursor.moveToFirst()) {
                        int nameTeacher = cursor.getColumnIndex("Teacher_name");
                        int stime = cursor.getColumnIndex("Start_ time");
                        int etime = cursor.getColumnIndex("End_time");
                        do {
                            String[] st = cursor.getString(stime).split(":");
                            String[] end = cursor.getString(etime).split(":");
                            GregorianCalendar now = new GregorianCalendar();
                            GregorianCalendar calendar_start = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), Integer.parseInt(st[0]), Integer.parseInt(st[1]), now.get(Calendar.SECOND));
                            GregorianCalendar calendar_end = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), Integer.parseInt(end[0]), Integer.parseInt(end[1]), now.get(Calendar.SECOND));
                            if (now.compareTo(calendar_start) >= 0 && now.compareTo(calendar_end) < 0) {
                                subSet.add(cursor.getString(nameTeacher));
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
                    catNames.addAll(0, subSet);
                    adapter.notifyDataSetChanged();
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



                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        String group = null;
                        String room = null;
                        DBHelper dbHelper = new DBHelper(getActivity());
                        SQLiteDatabase database = new DBHelper(getActivity()).getWritableDatabase();
                        Cursor cursor = database.rawQuery("SELECT * FROM main WHERE `Teacher_name` = '" + String.valueOf(catNames.get(position)) + "' and `Day_of_ week` = " + String.valueOf(7 - (8 - new GregorianCalendar().get(Calendar.DAY_OF_WEEK)) % 7), null);
                        if (cursor.moveToFirst()) {
                            int nameRoom = cursor.getColumnIndex("Room");
                            int nameGroup = cursor.getColumnIndex("Group");
                            int stime = cursor.getColumnIndex("Start_ time");
                            int etime = cursor.getColumnIndex("End_time");
                            do {
                                String[] st = cursor.getString(stime).split(":");
                                String[] end = cursor.getString(etime).split(":");
                                GregorianCalendar now = new GregorianCalendar();
                                GregorianCalendar calendar_start = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), Integer.parseInt(st[0]), Integer.parseInt(st[1]), now.get(Calendar.SECOND));
                                GregorianCalendar calendar_end = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), Integer.parseInt(end[0]), Integer.parseInt(end[1]), now.get(Calendar.SECOND));
                                if (now.compareTo(calendar_start) >= 0 && now.compareTo(calendar_end) < 0) {
                                    group = cursor.getString(nameGroup);
                                    room = cursor.getString(nameRoom);
                                }
                            }
                            while (cursor.moveToNext());
                        }
                        if (room != null) {
                            switch (room) {
                                case "it_cab":
                                    room = "ИТ класс";
                                    break;
                                case "design_cab":
                                    room = "Класс Промышленного Дизайна";
                                    break;
                                case "aero_cab":
                                    room = "Аэро класс";
                                    break;
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Доступная информация о сотруднике:\n")
                                    .setMessage("ФИО: " + String.valueOf(catNames.get(position)) + "\nГруппа: " + group + "\nКабинет: " + room + "\n")
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
