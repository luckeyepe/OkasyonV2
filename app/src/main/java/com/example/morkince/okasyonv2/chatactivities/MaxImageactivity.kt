package com.example.morkince.okasyonv2.chatactivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.morkince.okasyonv2.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_max_imageactivity.*

class MaxImageactivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_max_imageactivity)

        val sourcUrl = intent.getStringExtra("sourceUrl")

        Picasso.get().load(sourcUrl).into(imageView_maxImageImage)
    }
}
