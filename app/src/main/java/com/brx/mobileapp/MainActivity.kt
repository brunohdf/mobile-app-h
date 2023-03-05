package com.brx.mobileapp

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        validateParameterDefinition()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
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
}
