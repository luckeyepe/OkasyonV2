package com.example.morkince.okasyonv2.activities.homepage_supplier_activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.example.morkince.okasyonv2.R
import com.example.morkince.okasyonv2.activities.client_activities.ClientViewItemsActivity
import com.example.morkince.okasyonv2.activities.adapter.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_home_page__supplier_.*
import kotlinx.android.synthetic.main.app_bar_home_page__supplier_.*
import kotlinx.android.synthetic.main.content_home_page__supplier_.*

class HomePage_Supplier_Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var myDataset: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page__supplier_)
        var myDataset = ArrayList<String>()
        myDataset.add("Smiles")
        myDataset.add("Dead")
        var viewManager = LinearLayoutManager(this)
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
                }
                R.id.nav_Transaction -> {
                    val intent = Intent(this,Transaction_Supplier_Activity::class.java)
                    // start your next activity
                    startActivity(intent)
                }
                R.id.nav_slideshow -> {
                    val intent = Intent(this,
                        ClientViewItemsActivity::class.java)
                    // start your next activity
                    startActivity(intent)
                }
                R.id.nav_manage -> {

                }
                R.id.nav_share -> {

                }
                R.id.nav_send -> {

                }
            }

            drawer_layout.closeDrawer(GravityCompat.START)
            return true
        }
    }

