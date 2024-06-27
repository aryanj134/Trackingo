package com.bitlasoft.trackingo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bitlasoft.trackingo.databinding.ActivityMainBinding
import com.bitlasoft.trackingo.fragment.MapTrackingoFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToFragment(MapTrackingoFragment())

    }

    private fun navigateToFragment(fragment: Fragment) {
        // Replace the following line with your fragment transaction code
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}