package com.pruebas.prueba1.main.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VDB : ViewDataBinding, AVM : BaseViewModel> : AppCompatActivity() {

    lateinit var viewDataBinding: VDB
    lateinit var activityViewModel: AVM

    abstract fun createActivityViewModel(): AVM
    abstract fun setUpViewDatBinding(): VDB
    open fun setUpView(savedInstanceState: Bundle?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityViewModel = createActivityViewModel()
        viewDataBinding = setUpViewDatBinding()
        setContentView(viewDataBinding.root)
        setUpView(savedInstanceState)
    }

    fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
