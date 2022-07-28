package com.pruebas.prueba1.main.home

import androidx.activity.viewModels
import com.pruebas.prueba1.databinding.ActivityHomeBinding
import com.pruebas.prueba1.main.base.BaseActivity

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {

    override fun createActivityViewModel() : HomeViewModel {
        val vm : HomeViewModel by viewModels()
        return vm
    }

    override fun setUpViewDatBinding(): ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
}