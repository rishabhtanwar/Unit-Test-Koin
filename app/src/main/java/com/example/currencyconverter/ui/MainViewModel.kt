package com.example.currencyconverter.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.model.CountryWisePrice
import com.example.currencyconverter.data.model.CurrencyListResponse
import com.example.currencyconverter.data.model.Resource
import com.example.currencyconverter.data.repository.CurrencyRepo
import com.example.currencyconverter.util.DispatcherProvider
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainViewModel(
    private val currencyRepo: CurrencyRepo,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _currencyListResponse = MutableLiveData<Resource<CurrencyListResponse>>()

    val currencyListResponse: LiveData<Resource<CurrencyListResponse>> get() = _currencyListResponse

    val countryList: ArrayList<String> = arrayListOf()

    val countryWisePriceList: ArrayList<CountryWisePrice> = arrayListOf()

    private var rateListMap: HashMap<String, Double?> = hashMapOf()


    fun getCurrencyListResponse() {
        viewModelScope.launch {
            currencyRepo.getLatestCurrencyResponse().flowOn(dispatcherProvider.io).collect() {
                when (it) {
                    is Resource.Success -> {
                        //Log.e("TAG", "getCurrencyListResponse: ${it.data?.rates}")
                        it.data?.let {
                            it.rates?.let {
                                rateListMap = it
                                setCountryList(it)
                            }
                        }
                    }

                    else -> {
                        //Log.e("TAG", "getCurrencyListResponse: else")
                    }
                }
                _currencyListResponse.value = it
            }
        }
    }

    private fun setCountryList(listHashMap: HashMap<String, Double?>) {
        countryList.add("USD")
        listHashMap.forEach { (t, u) ->
            countryList.add(t)
            countryWisePriceList.add(CountryWisePrice(price = u, name = t))
        }
    }

    fun getUpdatedListByCountryAndPrice(selectedCountry: String, amount: Float,list:()->Unit) {
        countryWisePriceList.clear()
        var selectedCountryRate = 1.0
        if (!selectedCountry.equals("USD", true)) {
            selectedCountryRate = rateListMap.get(selectedCountry) ?: 1.0
        }
        rateListMap.forEach { t, u ->
            val updatedPrice = ((u?:1.0)/selectedCountryRate)*amount
            countryWisePriceList.add(CountryWisePrice(price = updatedPrice, name = t))
        }
        list.invoke()
    }

}