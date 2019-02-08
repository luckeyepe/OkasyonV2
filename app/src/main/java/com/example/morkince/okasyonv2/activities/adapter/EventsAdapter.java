package com.example.morkince.okasyonv2.activities.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.morkince.okasyonv2.Events;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.client_activities.EventDetailsActivity;
import com.example.morkince.okasyonv2.activities.client_activities.FoundEventDetailsActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kd.dynamic.calendar.generator.ImageGenerator;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>{

    private ArrayList<Events> events;
    private Context mContext;
    private FirebaseUser user;
    private StorageReference mStorageRef;
    private String eventName,eventLocation;

  //  Calendar currentDate;
    Bitmap generatedateIcon;
    ImageGenerator imageGenerator;

    public EventsAdapter(ArrayList<Events> events, Context mContext) {
        this.events = events;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_top_events_to_be_recycle, parent, false);
        EventsAdapter.ViewHolder holder = new EventsAdapter.ViewHolder(view,mContext,events);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final EventsAdapter.ViewHolder holder, int position) {

        mStorageRef = FirebaseStorage
                .getInstance()
                .getReference()
                .child("event_images")
                .child(events.get(position)
                        .getEvent_event_uid());

        mStorageRef
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri.toString()).error(R.mipmap.ic_launcher).into(holder.ImageView_clientTopEventsPicture);
                    }
                });

        eventName = events.get(position).getEvent_name();
        eventLocation = events.get(position).getEvent_location() + "";
        holder.textView_clientTopEventsToBeRecycleNameoftheEvent.setText(eventName);
        holder.textView_clientTopEventsToBeRecycleLocationoftheEvent.setText(eventLocation);


        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);
        try {
            currentDate.setTime(sdf.parse(events.get(position).getEvent_date()));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // .set(selectedYear,selectedMonth,selectedDay);
        imageGenerator = new ImageGenerator(mContext);


        imageGenerator.setIconSize(100, 100);
        imageGenerator.setDateSize(40);
        imageGenerator.setMonthSize(20);

        imageGenerator.setDatePosition(80);
        imageGenerator.setMonthPosition(24);

        imageGenerator.setDateColor(Color.parseColor("#D81B60"));
        imageGenerator.setMonthColor(Color.parseColor("#ffffff"));

        imageGenerator.setStorageToSDCard(true);

        generatedateIcon = imageGenerator.generateDateImage(currentDate, R.drawable.calendar_icon);
        holder.imageView_topEventsToBeRecycled.setImageBitmap(generatedateIcon);

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView_clientTopEventsToBeRecycleNameoftheEvent;
        TextView textView_clientTopEventsToBeRecycleLocationoftheEvent;
        ImageView imageView_topEventsToBeRecycled,ImageView_clientTopEventsPicture;
        LinearLayout eventsToBeRecycled_layout;
        ArrayList<Events> events;
        Context mContext;

        public ViewHolder(View eventsView, Context mContext,ArrayList<Events> events){
            super(eventsView);
            this.events=events;
            this.mContext=mContext;
            eventsView.setOnClickListener(this);

            textView_clientTopEventsToBeRecycleNameoftheEvent = itemView.findViewById(R.id.textView_clientTopEventsToBeRecycleNameoftheEvent);
            textView_clientTopEventsToBeRecycleLocationoftheEvent = itemView.findViewById(R.id.textView_clientTopEventsToBeRecycleLocationoftheEvent);
            imageView_topEventsToBeRecycled = itemView.findViewById(R.id.imageView_topEventsToBeRecycled);
            eventsToBeRecycled_layout=itemView.findViewById(R.id.eventsToBeRecycled_layout);
            ImageView_clientTopEventsPicture=itemView.findViewById(R.id.ImageView_clientTopEventsPicture);
        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            user = FirebaseAuth.getInstance().getCurrentUser();

            Log.e("EVENT Adapter: ", "Creator: "+events.get(position).getEvent_creator_id());
            Log.e("EVENT Adapter: ",  "Org"+events.get(position).getEvent_event_organizer_uid());

            if (events.get(position).getEvent_creator_id().equalsIgnoreCase(user.getUid() + "") ||
                    events.get(position).getEvent_event_organizer_uid().equalsIgnoreCase(user.getUid()))
            {
                Intent intent = new Intent(mContext,EventDetailsActivity.class);
                intent.putExtra("event_id",events.get(position).getEvent_event_uid());

                mContext.startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(mContext,FoundEventDetailsActivity.class);
                intent.putExtra("event_id",events.get(position).getEvent_event_uid());

                mContext.startActivity(intent);
            }
        }
    }
}

