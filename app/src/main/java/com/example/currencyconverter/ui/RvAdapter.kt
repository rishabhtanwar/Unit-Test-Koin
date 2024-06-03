package com.example.currencyconverter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.R
import com.example.currencyconverter.data.model.CountryWisePrice
import com.example.currencyconverter.databinding.CountryCurrencyConvertorLayoutBinding


class RvAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var countryPriceData:ArrayList<CountryWisePrice> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: CountryCurrencyConvertorLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.country_currency_convertor_layout, parent, false
        )
        return CountryVH(binding)
    }

    override fun getItemCount(): Int = countryPriceData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CountryVH).bindData(countryPriceData.get(position))
    }

    fun updateList(countryPriceData:List<CountryWisePrice>){
        this.countryPriceData.clear()
        this.countryPriceData.addAll(countryPriceData)
        notifyDataSetChanged()
    }

  inner  class CountryVH(private val binding: CountryCurrencyConvertorLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindData(countryWisePrice: CountryWisePrice){
            binding.price.text = "${countryWisePrice.price}"
            binding.countrySymbol.text = countryWisePrice.name
        }
    }
}