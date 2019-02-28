package com.example.morkince.okasyonv2.activities.chat_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.morkince.okasyonv2.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_max_image.*

class MaxImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_max_image)

        val sourceUrl = intent.getStringExtra("sourceUrl")

        if (sourceUrl.isEmpty()){
            Picasso.get().load(R.drawable.ic_error_black_24dp).into(imageView_maxImageImage)
        }else {
            Picasso.get().load(sourceUrl).into(imageView_maxImageImage)
        }
    }
}
