package com.example.morkince.okasyonv2.activities.client_activities;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.example.morkince.okasyonv2.R;

public class activity_filter extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_filter, container, false);
        ImageButton imageButton_modalUserTypeSelectionClose;
        imageButton_modalUserTypeSelectionClose = v.findViewById(R.id.imageButton_modalUserTypeSelectionClose);
        imageButton_modalUserTypeSelectionClose.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return v;



    }
}
