package com.bitlasoft.trackingo.activity

import MainViewModel
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bitlasoft.trackingo.R
import com.bitlasoft.trackingo.databinding.ActivityMainBinding
import com.bitlasoft.trackingo.utils.InternetConnectivity
import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var internetConnectivity: InternetConnectivity? = null
    private val mainViewModel: MainViewModel by viewModel()

    companion object {
        var isInternet: MutableSharedFlow<Boolean> = MutableSharedFlow<Boolean>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize NavHostFragment and NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController

        if (savedInstanceState == null) {
            navController.navigate(mainViewModel.currentFragment)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            mainViewModel.currentFragment = destination.id
        }
    }

    override fun onResume() {
        super.onResume()
        internetConnectivity = InternetConnectivity()
        registerReceiver(
            internetConnectivity,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onPause() {
        super.onPause()
        if (internetConnectivity != null)
            unregisterReceiver(internetConnectivity)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}