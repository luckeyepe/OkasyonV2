package com.example.morkince.okasyonv2.activities.Homepage_organizer_activities;

import android.app.Dialog;
import android.content.Context;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.*;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.adapter.ViewOrganizersAdapter;
import com.example.morkince.okasyonv2.activities.model.Organizer;

import java.util.ArrayList;

public class Activity_Vieworganizer extends AppCompatActivity {
    private ArrayList<Organizer> organizer = new ArrayList<>();
    ViewOrganizersAdapter adapter;
    RecyclerView recyclerView;
    int size = 0;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__vieworganizer);
        refs();


        // add button listener


        // custom dialog


//                // set the custom dialog components - text, image and button
////                TextView text = (TextView) dialog.findViewById(R.id.text);
////                text.setText("Android custom dialog example!");
////                ImageView image = (ImageView) dialog.findViewById(R.id.image);
////                image.setImageResource(R.drawable.ic_launcher);



        Organizer item1 = new Organizer();
        item1.setStoreName("Marc's Gown");
        item1.setLocation("Hernan Cortes");
        item1.setPrice(100.00);

        Organizer item2 = new Organizer();
        item2.setStoreName("Japhet's SoundSystems");
        item2.setLocation("Nasipit Talamban");
        item2.setPrice(200.00);

        Organizer item3 = new Organizer();
        item3.setStoreName("Mikay's Eatery");
        item3.setLocation("629 Gov. Cuenco Ave Banilad Cebu City");
        item3.setPrice(300.00);


        organizer.add(item1);
        organizer.add(item2);
        organizer.add(item3);

        adapter = new ViewOrganizersAdapter(organizer, Activity_Vieworganizer.this);
        recyclerView.setAdapter(adapter);


        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.clientviewitemsactionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if(id == R.id.Filter)
//        {
//            final Dialog dialog = new Dialog(context);
//            dialog.setContentView(R.layout.activity_filter);
//            dialog.setTitle("Title...");
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Fragment selectedFragment = null;
        // Handle item selection
        switch (item.getItemId()) {
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
            case R.id.Filter:
//                selectedFragment = new activity_filter();
//                break;
                View view = LayoutInflater.from(this).inflate(R.layout.activity_filter, null);
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(view);
                dialog.setTitle("FILTER");
                dialog.show();
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
                window.setAttributes(wlp);
//                wlp.gravity = Gravity.RIGHT;
//                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void refs()
    {

        recyclerView = findViewById(R.id.RecyclerView_viewOrganizer_activity);
    }
}