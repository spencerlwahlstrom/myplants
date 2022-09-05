package com.example.myplants

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {
    private var CHANNEL_ID = "CHANNEL_ID"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupRecyclerView(item_list)
        setupLoginButton(UserData)
        createNotificationChannel()

        UserData.isSignedIn.observe(this, Observer<Boolean> { isSignedUp ->
            Log.i(TAG, "isSignedIn changed : $isSignedUp")

            if (isSignedUp) {
                login.text=getString(R.string.signout)
                add.visibility =  android.view.View.VISIBLE
                getNotifications.visibility =  android.view.View.VISIBLE
            } else {
                login.text=getString(R.string.login)
                add.visibility =  android.view.View.INVISIBLE
                getNotifications.visibility =  android.view.View.INVISIBLE
            }
        })
        add.setOnClickListener {
            startActivity(Intent(this, AddPlantActivity::class.java))
        }
        getNotifications.setOnClickListener {
            sendNotifications()
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        UserData.plants().observe(this, Observer<MutableList<UserData.Plant>> { plants ->
            Log.d(TAG, "observer received ${plants.size} plants")
            val itemTouchHelper = ItemTouchHelper(SwipeCallback(this))
            itemTouchHelper.attachToRecyclerView(recyclerView)
            recyclerView.adapter = PlantRecyclerViewAdapter(plants)
        })
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    private fun setupLoginButton(userData: UserData) {
        login.setOnClickListener { view ->

            if (userData.isSignedIn.value!!) {
                Backend.signOut()
            } else {
                Backend.signIn(this)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Backend.handleWebUISignInResponse(requestCode, resultCode, data)
    }

    private fun createNotificationChannel() {
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        }

    private fun sendNotifications() {
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0,
            Intent(), PendingIntent.FLAG_IMMUTABLE)
        val water = getPlantsToWater().joinToString()
        val fertilize = getPlantsToFertilize().joinToString()
        val waterNotification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notification_icon)
            .setContentTitle("Plants to Water Today")
            .setContentText(water)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        val fertilizeNotification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notification_icon)
            .setContentTitle("Plants to Fertilize Today")
            .setContentText(fertilize)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        val waterID = 0
        val fertilizeID = 1
        with(NotificationManagerCompat.from(this)) {
            notify(waterID, waterNotification.build())
            notify(fertilizeID, fertilizeNotification.build())
        }

    }


    private fun getPlantsToWater(): MutableList<String> {
        val c = Calendar.getInstance()
        val format =  SimpleDateFormat("yyyy-MM-dd")
        val todaysDate = format.format(c.time)
        val plants = UserData.plants().value
        val plantList: MutableList<String> = mutableListOf()
        if (plants != null ) {
            for (plant in plants) {
                val temp = LocalDate.parse(plant.lastWatered)
                val nextWatering = temp.plusDays(plant.waterInterval.toLong())
                if (nextWatering.toString() == todaysDate) {
                    plantList.add(plant.name)
                }
            }
        }
        return plantList
    }

    private fun getPlantsToFertilize(): MutableList<String> {
        val c = Calendar.getInstance()
        val format =  SimpleDateFormat("yyyy-MM-dd")
        val todaysDate = format.format(c.time)
        val plants = UserData.plants().value
        val plantList: MutableList<String> = mutableListOf()
        if (plants != null ) {
            for (plant in plants) {
                val temp = LocalDate.parse(plant.lastFertilized)
                val nextFertilizing = temp.plusDays(plant.fertilizeInterval.toLong())
                if (nextFertilizing.toString() == todaysDate) {
                    plantList.add(plant.name)
                }
            }
        }
        return plantList
    }





}