package com.example.navoffice.ui.map;

import android.app.DownloadManager;
import android.content.Context;
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

import com.example.navoffice.MainActivity;
import com.example.navoffice.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

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
                        Date data = new Date();
                        int hours = Calendar.HOUR_OF_DAY;

                        Log.i("ggeg", String.valueOf(hours));
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
