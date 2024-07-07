package com.example.currencyconverter.ui

import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.currencyconverter.R
import com.example.currencyconverter.data.model.CurrencyListResponse
import com.example.currencyconverter.data.model.Resource
import com.example.currencyconverter.databinding.ActivityMainBinding
import com.example.currencyconverter.di.getSharePref
import com.example.currencyconverter.util.ConnectivityHelper
import com.example.currencyconverter.util.Constant
import com.example.currencyconverter.util.amountValidate
import com.example.currencyconverter.util.showToast
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()
    private lateinit var activityMainBinding: ActivityMainBinding
    private var dialog: ProgressDialog? = null
    private var rvAdapter:RvAdapter?=null
    private var selectedCountry = "USD"
    private lateinit var pref:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        pref=getSharePref(this)
        rvAdapter = RvAdapter()
        activityMainBinding.rv.adapter = rvAdapter
        setObserver()
        if (pref.getString(Constant.SAVED_API_RESPONSE,null).isNullOrEmpty() && !ConnectivityHelper.checkConnectivity(this)){
            showToast("Please check your internet connection and come back again!!")
            return
        }
        viewModel.getCurrencyListResponse()

    }


    private fun setObserver() {
        viewModel.currencyListResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {
                        saveApiData(it)
                    }
                    hidProgress()
                    setSpinnerAdapter()
                    updateRvAdapter()
                }

                is Resource.Error -> {
                    showToast(it.error)
                    hidProgress()
                }

                is Resource.Loading -> {
                    showProgress()
                }
            }
        }

        activityMainBinding.amountEt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
             s.toString().amountValidate {
                 if (it){
                     viewModel.getUpdatedListByCountryAndPrice(selectedCountry,s.toString().toFloat()){
                         updateRvAdapter()
                     }
                 }else{
                     showToast("Please enter valid amount")
                 }
             }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun setSpinnerAdapter() {

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.spinner_layout, viewModel.countryList
        )
        activityMainBinding.spinner.adapter = adapter

        activityMainBinding.spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCountry = viewModel.countryList.get(position)
                activityMainBinding.amountEt.text.toString().amountValidate {
                   if (it){
                       viewModel.getUpdatedListByCountryAndPrice(selectedCountry,activityMainBinding.amountEt.text.toString().toFloat()){
                           updateRvAdapter()
                       }
                   }else{
                       showToast("Please enter valid amount")
                   }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

    private fun updateRvAdapter(){
      rvAdapter?.updateList(viewModel.countryWisePriceList)
    }

    private fun showProgress() {
        dialog = ProgressDialog(this)
        dialog?.setMessage("Loading...")
        dialog?.show()
    }

    private fun hidProgress() {
        dialog?.hide()
        dialog?.cancel()
    }

    private fun saveApiData(responseBody:CurrencyListResponse){
        val responseToSave = Gson().toJson(responseBody)
        pref.edit {
            putString(Constant.SAVED_API_RESPONSE,responseToSave)
            putLong(Constant.SAVED_API_RESPONSE_TIME,System.currentTimeMillis())
            apply()
        }
    }

}