package com.example.weather

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weather.another.BUNDLE_KEY
import com.example.weather.another.MyBroadCastReceiver
import com.example.weather.another.MyService
import com.example.weather.another.ThreadsFragment
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.model.room.WeatherHistoryListFragment
import com.example.weather.utils.SP_DB_NAME_IS_RUSSIAN
import com.example.weather.utils.SP_KEY_IS_RUSSIAN
import com.example.weather.view.weatherlist.CitiesListFragment

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

        val sp = getSharedPreferences(SP_DB_NAME_IS_RUSSIAN,Context.MODE_PRIVATE)
        Log.d("@@@", localClassName)
        val spActivity = getPreferences(Context.MODE_PRIVATE)// аналог getSharedPreferences("MainActivity.class",Context.MODE_PRIVATE)
        val spApp =
            PreferenceManager.getDefaultSharedPreferences(this)// аналог getSharedPreferences(getPackageName(),Context.MODE_PRIVATE)


        val isRussian = sp.getBoolean(SP_KEY_IS_RUSSIAN,true)
        val editor = sp.edit()
        editor.putBoolean(SP_KEY_IS_RUSSIAN,isRussian)
        editor.apply()

        sp.edit().apply {
            putBoolean(SP_KEY_IS_RUSSIAN, isRussian)
            apply()
        }




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
            else -> super.onOptionsItemSelected(item)
        }
    }


}

