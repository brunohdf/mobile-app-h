package com.brx.mobileapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.brx.mobileapp.ui.main.MainFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        setupNavigationBar()
    }

    private fun setupNavigationBar() {
        bottom_navigation.itemIconTintList = null
        bottom_navigation.setOnNavigationItemSelectedListener {
            Toast.makeText(
                this, "TODO: ${when (it.itemId) {
                    R.id.home -> {
                        "Home"
                    }
                    R.id.map -> {
                        "Map"
                    }
                    else -> {
                        "Profile"
                    }
                }}", Toast.LENGTH_SHORT
            ).show()


            true
        }
    }

}
