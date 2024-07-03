package com.bitlasoft.trackingo.activity

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.bitlasoft.trackingo.R
import com.bitlasoft.trackingo.databinding.ActivityMainBinding
import com.bitlasoft.trackingo.utils.InternetConnectivity
import kotlinx.coroutines.flow.MutableSharedFlow


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var internetConnectivity: InternetConnectivity? = null

    companion object{
        var isInternet: MutableSharedFlow<Boolean> = MutableSharedFlow<Boolean>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        internetConnectivity = InternetConnectivity()
        registerReceiver(internetConnectivity, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        // Initialize NavHostFragment and NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(internetConnectivity)
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}