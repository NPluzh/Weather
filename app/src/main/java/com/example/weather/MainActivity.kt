package com.example.weather

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.weather.another.ThreadsFragment
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.model.room.WeatherHistoryListFragment
import com.example.weather.utils.SP_DB_NAME_IS_RUSSIAN
import com.example.weather.utils.SP_KEY_IS_RUSSIAN
import com.example.weather.view.contentprovider.ContentProviderFragment
import com.example.weather.view.maps.MapsFragment
import com.example.weather.view.weatherlist.CitiesListFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging


internal class MainActivity : AppCompatActivity() {




    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.myRoot)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CitiesListFragment.newInstance()).commit()
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("@@@", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.d("@@@", "$token")
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_threads -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .add(R.id.container, ThreadsFragment())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            R.id.menu_history -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, WeatherHistoryListFragment())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }

            R.id.menu_content_provider-> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, (ContentProviderFragment()))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }

            R.id.menu_google_maps-> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, (MapsFragment()))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}


