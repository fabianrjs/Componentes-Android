package com.pruebas.prueba1.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pruebas.prueba1.R
import com.pruebas.prueba1.main.biometricAuth.BiometricAuth
import java.lang.Thread.sleep

class WelcomeActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        sleep(2000)
        setTheme(R.style.WelcomeTheme)
        super.onCreate(savedInstanceState)
        startActivity(BiometricAuth.getInstance(this))
        finish()
    }
}
