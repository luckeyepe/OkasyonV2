package com.example.morkince.okasyonv2.activities.homepage_supplier_activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.HomePage_Client_activity.Client_Viewitems
import com.example.morkince.okasyonv2.activities.HomePage_Client_activity.Transaction_ClientView
import com.example.morkince.okasyonv2.activities.HomePage_Client_activity.activity_ViewCart
import com.example.morkince.okasyonv2.activities.Homepage_organizer_activities.Activity_Vieworganizer
import com.example.morkince.okasyonv2.activities.adapter.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_home_page__supplier_.*
import kotlinx.android.synthetic.main.app_bar_home_page__supplier_.*
import kotlinx.android.synthetic.main.content_home_page__supplier_.*

class HomePage_Supplier_Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var viewAdapter: androidx.recyclerview.widget.RecyclerView.Adapter<*>
    private lateinit var viewManager: androidx.recyclerview.widget.RecyclerView.LayoutManager
    private lateinit var myDataset: ArrayList<String>
    internal val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page__supplier_)
        var myDataset = ArrayList<String>()
        myDataset.add("Smiles")
        myDataset.add("Dead")
        var viewManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        var viewAdapter = RecyclerAdapter(myDataset, this)


        supplierHomePage_recyclerView.adapter = viewAdapter
        supplierHomePage_recyclerView.layoutManager = viewManager


        val mstartActBtn = findViewById<Button>(R.id.supplierHomepage_viewItemsBtn)
        mstartActBtn.setOnClickListener {
            startActivity(Intent(this, AddItem_Supplier_activity::class.java))
        }

        setSupportActionBar(toolbar)
//       Transaction.setOnClickListener {
//            val intent = Intent(this,Transaction_Supplier_Activity::class.java)
//            // start your next activity
//            startActivity(intent)
//        }

//
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

    }



        override fun onBackPressed() {
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                super.onBackPressed()
            }
        }

        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            // Inflate the menu; this adds items to the action bar if it is present.
            menuInflater.inflate(R.menu.home_page__supplier_, menu)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            when (item.itemId) {
                R.id.action_settings -> return true
                else -> return super.onOptionsItemSelected(item)
            }
        }

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            // Handle navigation view item clicks here.
            when (item.itemId) {
                R.id.nav_camera -> {
                    // Handle the camera action
                    val intent = Intent(this,Activity_Vieworganizer::class.java)
                    // start your next activity
                    startActivity(intent)
                }
                R.id.nav_Transaction -> {
                    val intent = Intent(this,Transaction_Supplier_Activity::class.java)
                    // start your next activity
                    startActivity(intent)
                }
                R.id.nav_slideshow -> {
                    val intent = Intent(this,Client_Viewitems::class.java)
                    // start your next activity
                    startActivity(intent)
                }
                R.id.nav_manage -> {

                    val intent = Intent(this, activity_ViewCart::class.java)
                    // start your next activity
                    startActivity(intent)
                }
                R.id.nav_share -> {
                    val view = LayoutInflater.from(this).inflate(R.layout.modal_rateitemstore_client, null)
                    val dialog = Dialog(context)
                    dialog.setContentView(view)
                    dialog.show()
                    val window = dialog.window
                    val wlp = window!!.attributes
                    wlp.gravity = Gravity.CENTER
                    window.attributes = wlp
//                wlp.gravity = Gravity.RIGHT;
//                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    window.attributes = wlp


                }
                R.id.nav_send -> {
                    val intent = Intent(this,Transaction_ClientView::class.java)
                    // start your next activity
                    startActivity(intent)
                }
                R.id.nav_messages -> {

                }
            }

            drawer_layout.closeDrawer(GravityCompat.START)
            return true
        }
    }

