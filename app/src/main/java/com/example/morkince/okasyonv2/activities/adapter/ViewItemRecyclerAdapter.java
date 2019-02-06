package com.example.morkince.okasyonv2.activities.adapter;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.model.Store;

import java.util.ArrayList;

public class ViewItemRecyclerAdapter extends RecyclerView.Adapter<ViewItemRecyclerAdapter.ViewHolder> {
    private ArrayList<Store> store;
    private Context mContext;
    //  private StorageReference mStorageRef;

    public ViewItemRecyclerAdapter(ArrayList<Store> store, Context mContext) {
        this.store = store;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_viewitemcontent_client, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.Storetitle.setText(store.get(position). getStoreName());
        holder.Location.setText(store.get(position).getLocation());
        holder.Price.setText(store.get(position).getPrice()+"");
        holder. storerate.setRating(store.get(position).getRating());




        // mStorageRef = FirebaseStorage.getInstance().getReference().child("images/").child(StudEntry.get(position).getIdNum()+"");

       /* mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).error(R.mipmap.ic_launcher).into(holder.imgthis);
            }
        });*/

       /* holder.sName.setText(StudEntry.get(position).getfName() + " " +StudEntry.get(position).getmName() + " " + StudEntry.get(position).getlName());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(mContext, "MAMA", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, view_student.class);
                intent.putExtra("studID", StudEntry.get(position).getIdNum()+"");
                intent.putExtra("studFname", StudEntry.get(position).getfName());
                intent.putExtra("studMname",StudEntry.get(position).getmName());
                intent.putExtra("studLname",StudEntry.get(position).getlName());
                intent.putExtra("course",StudEntry.get(position).getCourse());
                intent.putExtra("yearLevel", StudEntry.get(position).getYearLevel()+"");
                mContext.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return store.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Storetitle;
        TextView Location;
        TextView Price;
        RatingBar storerate;
        ConstraintLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            Storetitle = itemView.findViewById(R.id.textView_storeTitle);
            Location = itemView.findViewById(R.id.textView_rowViewItemContentItemName);
            Price = itemView.findViewById(R.id.textView_ItemPrice);
            storerate = itemView.findViewById(R.id.RatingBar_storeRate);
            parentLayout=itemView.findViewById(R.id.ParentLayout);
        }
    }
}
