package com.example.morkince.okasyonv2.activities.common_activities;

import android.content.Intent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.morkince.okasyonv2.Custom_Progress_Dialog;
import com.example.morkince.okasyonv2.PopUpDialogs;
import com.example.morkince.okasyonv2.R;
import com.example.morkince.okasyonv2.RandomMessages;
import com.example.morkince.okasyonv2.activities.homepage_supplier_activities.ViewSupplierTransactionDetailsActivity;
import com.example.morkince.okasyonv2.activities.model.Transaction_Client;
import com.example.morkince.okasyonv2.activities.model.Transaction_Supplier;
import com.example.morkince.okasyonv2.activities.view_holders.NotificationViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import javax.annotation.Nullable;

public class NotificationsActivity extends AppCompatActivity {
    private String user_role = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView_notificationActivity);

        final Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog(this);
        final PopUpDialogs popUpDialogs = new PopUpDialogs(this);
        RandomMessages randomMessages = new RandomMessages();
        final GroupAdapter<ViewHolder> adapter = new GroupAdapter<>();

        //check the if user is supplier
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;

        custom_progress_dialog.showDialog("Loading", randomMessages.getRandomMessage());
        FirebaseFirestore.getInstance()
                .collection("User")
                .document(currentUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            if (task.getResult() != null){
                                user_role = task.getResult().getString("user_role");

                                if (!user_role.equals("supplier")){
                                    //load the none supplier notifications
                                    FirebaseFirestore.getInstance()
                                            .collection("Notification")
                                            .document(currentUser.getUid())
                                            .collection("buyer_notifications")
                                            .orderBy("notification_is_seen")
                                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                                    adapter.clear();

                                                    if (e != null){
                                                        popUpDialogs.errorDialog("Something went wrong", "Error");
                                                        custom_progress_dialog.dissmissDialog();
                                                        return;
                                                    }

                                                    if (!queryDocumentSnapshots.isEmpty() && queryDocumentSnapshots != null){
                                                        for (DocumentSnapshot doc : queryDocumentSnapshots){
                                                            String notificationUid = doc.getId();
                                                            adapter.add(new NotificationViewHolder(currentUser.getUid(), notificationUid, "buyer_notifications", NotificationsActivity.this));
                                                        }

                                                        recyclerView.setAdapter(adapter);
                                                        recyclerView.addItemDecoration(new DividerItemDecoration(NotificationsActivity.this, LinearLayoutManager.VERTICAL));
                                                        recyclerView.setLayoutManager(new LinearLayoutManager(NotificationsActivity.this));

                                                        custom_progress_dialog.dissmissDialog();
                                                    }else {
                                                        popUpDialogs.infoDialog("No item available", "INFO");
                                                        custom_progress_dialog.dissmissDialog();
                                                    }
                                                }
                                            });
//                                            .addOnSuccessListener(new OnE)
//
//                                                    (new OnCompleteListener<QuerySnapshot>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                    if(task.isSuccessful()){
//                                                        if (task.getResult() != null){
//                                                            for (DocumentSnapshot doc : task.getResult()){
//                                                                String notificationUid = doc.getId();
//                                                                adapter.add(new NotificationViewHolder(currentUser.getUid(), notificationUid, "buyer_notification", NotificationsActivity.this));
//                                                            }
//
//                                                            recyclerView.setAdapter(adapter);
//                                                            recyclerView.addItemDecoration(new DividerItemDecoration(NotificationsActivity.this, LinearLayoutManager.VERTICAL));
//                                                            recyclerView.setLayoutManager(new LinearLayoutManager(NotificationsActivity.this));
//
//                                                            custom_progress_dialog.dissmissDialog();
//
//                                                        }else {
//                                                            custom_progress_dialog.dissmissDialog();
//                                                        }
//                                                    }else {
//                                                        custom_progress_dialog.dissmissDialog();
//                                                    }
//                                                }
//                                            });
                                }else {
                                    //load the supplier notifications
                                    FirebaseFirestore.getInstance()
                                            .collection("Notification")
                                            .document(currentUser.getUid())
                                            .collection("supplier_notifications")
                                            .orderBy("notification_is_seen")
                                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                                    adapter.clear();

                                                    if (e != null){
                                                        popUpDialogs.errorDialog("Something went wrong", "Error");
                                                        custom_progress_dialog.dissmissDialog();
                                                        return;
                                                    }

                                                    if (!queryDocumentSnapshots.isEmpty() && queryDocumentSnapshots != null){
                                                        for (DocumentSnapshot doc : queryDocumentSnapshots){
                                                            String notificationUid = doc.getId();
                                                            adapter.add(new NotificationViewHolder(currentUser.getUid(), notificationUid,
                                                                    "supplier_notifications", NotificationsActivity.this));
                                                        }

                                                        recyclerView.setAdapter(adapter);
                                                        recyclerView.addItemDecoration(new DividerItemDecoration(NotificationsActivity.this, LinearLayoutManager.VERTICAL));
                                                        recyclerView.setLayoutManager(new LinearLayoutManager(NotificationsActivity.this));

                                                        custom_progress_dialog.dissmissDialog();
                                                    }else {
                                                        popUpDialogs.infoDialog("No item available", "INFO");
                                                        custom_progress_dialog.dissmissDialog();
                                                    }
                                                }
                                            });
                                }
                            }else {
                                custom_progress_dialog.dissmissDialog();
                            }
                        }else {
                            custom_progress_dialog.dissmissDialog();
                        }
                    }
                });

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull final View view) {
                final Custom_Progress_Dialog custom_progress_dialog = new Custom_Progress_Dialog(view.getContext());
                PopUpDialogs popUpDialogs = new PopUpDialogs(view.getContext());
                RandomMessages randomMessages = new RandomMessages();
                NotificationViewHolder notificationViewHolder = (NotificationViewHolder) item;

                String notificationUid = notificationViewHolder.getNotificationUid();

                custom_progress_dialog.showDialog("Loading", randomMessages.getRandomMessage());


                if (!user_role.equals("supplier") && !user_role.equals("")){

                    //update the notfication to seen
                    FirebaseFirestore.getInstance()
                            .collection("Notification")
                            .document(currentUser.getUid())
                            .collection("buyer_notifications")
                            .document(notificationUid)
                            .update("notification_is_seen", true);


                    ///
                    FirebaseFirestore.getInstance()
                            .collection("Notification")
                            .document(currentUser.getUid())
                            .collection("buyer_notifications")
                            .document(notificationUid)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult() != null) {
                                            String transactionUid = task.getResult().getString("notification_transaction_uid");
                                            String buyerUid = task.getResult().getString("notification_buyer_uid");
                                            String eventUid = task.getResult().getString("notification_event_uid");

                                            FirebaseFirestore.getInstance()
                                                    .collection("Transaction_Client")
                                                    .document(buyerUid)
                                                    .collection("events")
                                                    .document(eventUid)
                                                    .collection("transaction_client")
                                                    .document(transactionUid)
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                Transaction_Client transaction_client = task.getResult()
                                                                        .toObject(Transaction_Client.class);

                                                                if (transaction_client != null) {
                                                                    Intent intent = new Intent(view.getContext(), ViewTransactionDetailsActivity.class);
                                                                    intent.putExtra("transactionItem", transaction_client);
                                                                    startActivity(intent);
                                                                    custom_progress_dialog.dissmissDialog();
                                                                } else {
                                                                    custom_progress_dialog.dissmissDialog();
                                                                }
                                                            } else {
                                                                custom_progress_dialog.dissmissDialog();
                                                            }
                                                        }
                                                    });

                                        } else {
                                            custom_progress_dialog.dissmissDialog();
                                        }
                                    } else {
                                        custom_progress_dialog.dissmissDialog();
                                    }
                                }
                            });

                }else {

                    //update the notfication to seen
                    FirebaseFirestore.getInstance()
                            .collection("Notification")
                            .document(currentUser.getUid())
                            .collection("supplier_notifications")
                            .document(notificationUid)
                            .update("notification_is_seen", true);

                    FirebaseFirestore.getInstance()
                            .collection("Notification")
                            .document(currentUser.getUid())
                            .collection("supplier_notifications")
                            .document(notificationUid)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult() != null) {
                                            String transactionUid = task.getResult().getString("notification_transaction_uid");
                                            String buyerUid = task.getResult().getString("notification_buyer_uid");
                                            String eventUid = task.getResult().getString("notification_event_uid");

                                            FirebaseFirestore.getInstance()
                                                    .collection("Transaction_Supplier")
                                                    .document(currentUser.getUid())
                                                    .collection("transaction_supplier")
                                                    .document(transactionUid)
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                Transaction_Supplier transaction_client = task.getResult()
                                                                        .toObject(Transaction_Supplier.class);

                                                                if (transaction_client != null) {
                                                                    Intent intent = new Intent(view.getContext(), ViewSupplierTransactionDetailsActivity.class);
                                                                    intent.putExtra("transactionItem", transaction_client);
                                                                    startActivity(intent);
                                                                    custom_progress_dialog.dissmissDialog();
                                                                } else {
                                                                    custom_progress_dialog.dissmissDialog();
                                                                }
                                                            } else {
                                                                custom_progress_dialog.dissmissDialog();
                                                            }
                                                        }
                                                    });

                                        } else {
                                            custom_progress_dialog.dissmissDialog();
                                        }
                                    } else {
                                        custom_progress_dialog.dissmissDialog();
                                    }
                                }
                            });
                }
            }
        });


    }
}
