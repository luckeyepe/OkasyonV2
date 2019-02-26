package com.example.morkince.okasyonv2.activities.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.activities.Homepage_organizer_activities.CartItemDetailsActivity;
import com.example.morkince.okasyonv2.activities.model.Cart_Item;

import java.util.ArrayList;

public class ViewCartItemAdapter extends RecyclerView.Adapter<ViewCartItemAdapter.ViewHolder>{
    public ArrayList<Cart_Item> cartitem;
    public  ArrayList<Cart_Item> checkedItem=new ArrayList<>();
    private Context mContext;
    //  private StorageReference mStorageRef;

    public ViewCartItemAdapter(ArrayList<Cart_Item> cartitem, Context mContext) {
        this.cartitem = cartitem;
        this.mContext = mContext;
        Log.e("CART"," LOLAA" );
    }

    @Override
    public ViewCartItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_viewcartcontent_client, parent, false);
        ViewCartItemAdapter.ViewHolder holder = new ViewCartItemAdapter.ViewHolder(view, mContext,cartitem);
        return holder;
    }
//public void deleteItem(int position){
//
//}
    @Override
    public void onBindViewHolder(final ViewCartItemAdapter.ViewHolder holder, final int position) {

        holder.CartproductName2.setText(cartitem.get(position).getcart_item_name());
        holder.Price2.setText(cartitem.get(position).getcart_item_order_cost() + "");
        holder.Productrate2.setRating(cartitem.get(position).getcart_item_Rating());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
//                if (v.getId() == holder.chk.getId()){
//                    CheckBox checkbox= (CheckBox) v;
//                    if(checkbox.isChecked())
//                    {
//                        checkedItem.add(cartitem.get(pos));
//                    }else if(!checkbox.isChecked()){
//                        checkedItem.remove (cartitem.get(pos));
//                    }
//                }
            }
});
        //dari kiri


    }
    @Override
    public int getItemCount () {
        return cartitem.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        TextView CartproductName2;
        TextView Price2;
        RatingBar Productrate2;
        CheckBox chk;
        ConstraintLayout parentLayout2;
        private ArrayList<Cart_Item> cartitem;
        private Context mContext;
        ItemClickListener itemClickListener;

        public ViewHolder(View itemView,Context mContext, ArrayList<Cart_Item> cartitem) {
            super(itemView);
            this.cartitem = cartitem;
            this.mContext = mContext;
            itemView.setOnClickListener(this);
            CartproductName2= itemView.findViewById(R.id.textView_ItemName3);
            Price2 = itemView.findViewById(R.id.textView_ItemPrice2);
            Productrate2 = itemView.findViewById(R.id.ratingBar_storeRate);
            parentLayout2 = itemView.findViewById(R.id.ParentLayout);
            chk=(CheckBox) itemView.findViewById(R.id.checkBox_itemtoelacetoorder);
            chk.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == chk.getId()){
                int position = getAdapterPosition();

                if(chk.isChecked())
                {
                    checkedItem.add(cartitem.get(position));
                }else if(!chk.isChecked()){
                    checkedItem.remove (cartitem.get(position));
                }

            }else {
                int position = getAdapterPosition();

                Intent intent = new Intent(mContext, CartItemDetailsActivity.class);
                intent.putExtra("Cart_item_id", cartitem.get(position).getCart_item_id());
                intent.putExtra("Cart_item_group_id", cartitem.get(position).getCart_item_group_uid());
                intent.putExtra("Cart_item_item_id", cartitem.get(position).getCart_item_item_uid());
                this.mContext.startActivity(intent);
                this.itemClickListener.onItemClick(view, getLayoutPosition());
            }
        }

        public void setItemClickListener(ItemClickListener ic)
        {
            this.itemClickListener=ic;
        }

    }
}
