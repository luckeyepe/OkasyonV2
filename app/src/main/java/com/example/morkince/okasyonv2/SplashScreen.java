package com.example.morkince.okasyonv2;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.example.morkince.okasyonv2.activities.client_activities.ClientHomePage;
import com.example.morkince.okasyonv2.activities.homepage_supplier_activities.SupplierHomePage;
import com.example.morkince.okasyonv2.activities.login_activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_OUT = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

       ImageView view = findViewById(R.id.imageView4);


        Animation animation = AnimationUtils.loadAnimation(this,R.anim.forsplash);
        view.startAnimation(animation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                if (currentUser != null) {
                    FirebaseFirestore
                            .getInstance()
                            .collection("User")
                            .document(currentUser.getUid())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            String userRole = task.getResult().getString("user_role");
                            String userFullName = task.getResult().getString("user_first_name") + " " + task.getResult().getString("user_last_name");

                            switch (userRole) {
                                case "client":
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(userFullName)
                                            .build();

                                    currentUser.updateProfile(profileUpdates);

                                    startActivity(new Intent(SplashScreen.this, ClientHomePage.class));
                                    finish();
                                    break;
                                case "supplier":
                                    UserProfileChangeRequest profileUpdates2 = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(userFullName)
                                            .build();

                                    currentUser.updateProfile(profileUpdates2);

                                    startActivity(new Intent(SplashScreen.this, SupplierHomePage.class));
                                    finish();
                                    break;
                                case "organizer":
                                    UserProfileChangeRequest profileUpdates3 = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(userFullName)
                                            .build();

                                    currentUser.updateProfile(profileUpdates3);

                                    startActivity(new Intent(SplashScreen.this, ClientHomePage.class));
                                    finish();
                                    break;
                            }
                        }
                    });

                }
                else {
                    ////////////////////////////
                    Intent homeIntent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        },SPLASH_OUT);
    }
}
