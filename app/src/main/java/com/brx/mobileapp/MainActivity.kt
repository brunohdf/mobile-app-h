package com.brx.mobileapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
        validateParameterDefinition()
    }

    private fun validateParameterDefinition() {
        if (BuildConfig.KEY.isEmpty() || BuildConfig.CX.isEmpty()) {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.invalid_parameters))
                .setMessage(getString(R.string.please_configure_parameters))
                .setPositiveButton(getString(R.string.ok), null)
                .setOnDismissListener { finish() }
                .create().show()
        }
    }

    private fun setupNavigationBar() {
        bottom_navigation.itemIconTintList = null
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    // Paliativo para demostração
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance())
                        .commitNow()
                }
                R.id.map -> {
                    Toast.makeText(this, "TODO: Map ", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "TODO: Profile ", Toast.LENGTH_SHORT).show()
                }
            }

            true
        }
    }
}
