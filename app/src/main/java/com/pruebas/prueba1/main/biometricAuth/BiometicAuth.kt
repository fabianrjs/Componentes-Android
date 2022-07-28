package com.pruebas.prueba1.main.biometricAuth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.activity.viewModels
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.pruebas.prueba1.databinding.ActivityBiometricAuthBinding
import com.pruebas.prueba1.main.base.BaseActivity

class BiometricAuth : BaseActivity<ActivityBiometricAuthBinding, BiometricAuthViewModel>() {

    override fun setUpView(savedInstanceState: Bundle?) {
        viewDataBinding.button.isEnabled = false
        viewDataBinding.imgFinger.setOnClickListener {
            checkDeviceHasBiometric()
        }

        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence,
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    showToast("Authentication error: $errString")
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult,
                ) {
                    super.onAuthenticationSucceeded(result)
                    showToast("Authentication succeeded!")
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    showToast("Authentication failed")
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.

        viewDataBinding.button.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    private fun checkDeviceHasBiometric() {
        var info = ""
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                showToast("App can authenticate using biometrics.")
                info = "App can authenticate using biometrics."
                viewDataBinding.button.isEnabled = true
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                showToast("No biometric features available on this device.")
                info = "No biometric features available on this device."
                viewDataBinding.button.isEnabled = false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                showToast("Biometric features are currently unavailable.")
                info = "Biometric features are currently unavailable."
                viewDataBinding.button.isEnabled = false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // Prompts the user to create credentials that your app accepts.
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                }
                viewDataBinding.button.isEnabled = false

                startActivityForResult(enrollIntent, 100)
            }
        }
        viewDataBinding.tvShowMsg.text = info
    }

    override fun createActivityViewModel(): BiometricAuthViewModel {
        val viewModel : BiometricAuthViewModel by viewModels()
        return viewModel
    }

    override fun setUpViewDatBinding(): ActivityBiometricAuthBinding
        = ActivityBiometricAuthBinding.inflate(layoutInflater)

    companion object {
        fun getInstance(context: Context) : Intent {
            return Intent(context, BiometricAuth::class.java)
        }
    }
}