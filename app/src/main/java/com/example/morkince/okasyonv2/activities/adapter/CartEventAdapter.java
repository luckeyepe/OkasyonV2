package com.example.morkince.okasyonv2.activities.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.Events;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.client_activities.ClientViewCartActivty;
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

public class CartEventAdapter extends RecyclerView.Adapter<CartEventAdapter.ViewHolder> {
    private ArrayList<Events> events;
    private Context mContext;
    private FirebaseUser user;
    private StorageReference mStorageRef;
    private String eventName, eventLocation;

//    Calendar currentDate;
    Bitmap generatedateIcon;
    ImageGenerator imageGenerator;

    public CartEventAdapter(ArrayList<Events> events, Context mContext) {
        this.events = events;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public CartEventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_clientcarteventlist_acttivity, parent, false);
        CartEventAdapter.ViewHolder holder = new CartEventAdapter.ViewHolder(view, mContext, events);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartEventAdapter.ViewHolder holder, int position) {

//        mStorageRef = FirebaseStorage
//                .getInstance()
//                .getReference()
//                .child("event_images")
//                .child(events.get(position)
//                        .getEvent_event_uid());
//
//        mStorageRef
//                .getDownloadUrl()
//                .addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        Picasso.get().load(uri.toString()).error(R.mipmap.ic_launcher).into(holder.ImageView_clientTopEventsPicture);
//                    }
//                });

        eventName = events.get(position).getEvent_name();
//        eventLocation = events.get(position).getEvent_location() + "";
        holder.textView_clientTopEventsToBeRecycleNameoftheEvent2.setText(eventName);
//        holder.textView_clientTopEventsToBeRecycleLocationoftheEvent.setText(eventLocation);


        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
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
        holder.imageView_topEventsToBeRecycled2.setImageBitmap(generatedateIcon);

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView_clientTopEventsToBeRecycleNameoftheEvent2;
//        TextView textView_clientTopEventsToBeRecycleLocationoftheEvent;
        ImageView imageView_topEventsToBeRecycled2;
        LinearLayout eventsToBeRecycled_layout;
        ArrayList<Events> events;
        Context mContext;

        public ViewHolder(View eventsView, Context mContext, ArrayList<Events> events) {
            super(eventsView);
            this.events = events;
            this.mContext = mContext;
            eventsView.setOnClickListener(this);
            textView_clientTopEventsToBeRecycleNameoftheEvent2 = itemView.findViewById(R.id.textView_clientCartEventsToBeRecycleNameoftheEvent);
            imageView_topEventsToBeRecycled2 = itemView.findViewById(R.id.imageView_carttopEventsToBeRecycled);
            eventsToBeRecycled_layout = itemView.findViewById(R.id.eventsToBeRecycled_layout);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            user = FirebaseAuth.getInstance().getCurrentUser();

            Log.e("EVENT Adapter: ", "Creator: " + events.get(position).getEvent_creator_id());
            Log.e("EVENT Adapter: ", "Org" + events.get(position).getEvent_event_organizer_uid());

            if (events.get(position).getEvent_creator_id().equalsIgnoreCase(user.getUid() + "") ||
                    events.get(position).getEvent_event_organizer_uid().equalsIgnoreCase(user.getUid())) {
                Intent intent = new Intent(mContext, ClientViewCartActivty.class);
                intent.putExtra("event_event_uid", events.get(position).getEvent_event_uid());
                intent.putExtra("event_category_id", events.get(position).getEvent_category_id());
                intent.putExtra("event_cart_group_uid", events.get(position).getEvent_cart_group_uid());


                mContext.startActivity(intent);
            } else {
//                Intent intent = new Intent(mContext, FoundEventDetailsActivity.class);
//                intent.putExtra("event_event_uid", events.get(position).getEvent_event_uid());
//                intent.putExtra("event_category_id", events.get(position).getEvent_category_id());
//
//                mContext.startActivity(intent);
            }
        }
    }
}

