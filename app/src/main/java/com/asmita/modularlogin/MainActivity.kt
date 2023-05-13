package com.asmita.modularlogin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.asmita.login.LoginActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest


class MainActivity : AppCompatActivity() {

    lateinit var splitInstallManager: SplitInstallManager
    lateinit var request: SplitInstallRequest
    private val onDemandFeature = "onDemand"
    private val customFeature = "custom"
    private lateinit var buttonOpenNewsModule : Button
    private lateinit var buttonCustomModule : Button
    private lateinit var buttonDeleteNewsModule : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDynamicModules()

        // initializing the button
        val modularText = findViewById<TextView>(R.id.modularTextView)
        val buttonClick = findViewById<Button>(R.id.buttonClick)
        buttonOpenNewsModule = findViewById(R.id.buttonOpenNewsModule)
        buttonCustomModule = findViewById(R.id.buttonCustomModule)
        buttonDeleteNewsModule = findViewById(R.id.buttonDeleteNewsModule)

        // adding listener to the button
        modularText.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    LoginActivity::class.java
                )
            )
        }

        buttonClick.setOnClickListener {
            if (!isDynamicFeatureDownloaded(onDemandFeature)) {
                downloadFeature()
            } else {
                buttonOpenNewsModule.visibility = View.VISIBLE
                buttonCustomModule.visibility = View.VISIBLE
                buttonDeleteNewsModule.visibility = View.VISIBLE
            }
        }

        buttonOpenNewsModule.setOnClickListener {
            val intent = Intent().setClassName(
                this,
                "com.asmita.ondemand.DemandActivity"
            )
            startActivity(intent)
        }

        buttonCustomModule.setOnClickListener {
            val intent = Intent().setClassName(
                this,
                "com.asmita.custom.CustomActivity"
            )
            startActivity(intent)
        }

        buttonDeleteNewsModule.setOnClickListener {
            val list = ArrayList<String>()
            list.add(onDemandFeature)
            list.add(customFeature)
            uninstallDynamicFeature(list)
        }
    }

    private fun initDynamicModules() {
        splitInstallManager = SplitInstallManagerFactory.create(this)
        request = SplitInstallRequest
            .newBuilder()
            .addModule(onDemandFeature)
            .addModule(customFeature)
            .build()
    }

    private fun isDynamicFeatureDownloaded(feature: String): Boolean =
        splitInstallManager.installedModules.contains(feature)

    private fun downloadFeature() {
        splitInstallManager.startInstall(request)
            .addOnFailureListener {
            }
            .addOnSuccessListener {
                buttonOpenNewsModule.visibility = View.VISIBLE
                buttonCustomModule.visibility = View.VISIBLE
                buttonDeleteNewsModule.visibility = View.VISIBLE
            }
            .addOnCompleteListener {
            }
    }

    private fun uninstallDynamicFeature(list: List<String>) {
        splitInstallManager.deferredUninstall(list)
            .addOnSuccessListener {
                buttonOpenNewsModule.visibility = View.GONE
                buttonCustomModule.visibility = View.GONE
                buttonDeleteNewsModule.visibility = View.GONE
            }
    }

}