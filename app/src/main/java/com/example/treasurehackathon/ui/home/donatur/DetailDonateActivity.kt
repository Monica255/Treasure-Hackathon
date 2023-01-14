package com.example.treasurehackathon .ui.home.donatur

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.treasurehackathon.core.data.Resource
import com.example.treasurehackathon.core.data.source.remote.model.RequestPurchase
import com.example.treasurehackathon.core.domain.model.Product
import com.example.treasurehackathon.core.utils.DataMapper
import com.example.treasurehackathon.databinding.ActivityDetailDonateBinding
import com.example.treasurehackathon.ui.home.HomeViewModel
import com.example.treasurehackathon.ui.splash.SplashActivity.Companion.SESSION_TOKEN
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailDonateActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailDonateBinding
    private val viewModel:DetailDonateViewModel by viewModels()
    private val viewModel2:HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailDonateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()
        val prefManager: SharedPreferences =
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)

        val data=intent.getParcelableExtra<Product>(EXTRA_DATA)
        data?.let {product->
            binding.tvSeller.text=product.username
            binding.tvLeftovers.text=product.qty.toString()
            viewModel.totalItem=product.qty
            val itemPrice=DataMapper.totalPriceToPrice(product.prize,product.qty)
            viewModel.itemCount.observe(this){
                binding.btPlus.isEnabled = it != viewModel.totalItem
                binding.btMinus.isEnabled=it>5
                binding.tvItemCount.text=it.toString()
                viewModel.itemPrice=calculatePrice(it,itemPrice)
                binding.btnPurcharse.text="Purchase Rp "+viewModel.itemPrice
            }
            binding.btnPurcharse.setOnClickListener {
                val req= viewModel.itemCount.value?.let { it1 ->
                    RequestPurchase(
                        it1,
                        viewModel.itemPrice,
                        product.id_seller,
                        product.id_product
                    )
                }
                req?.let{
                    val token=prefManager.getString(SESSION_TOKEN,"")?:""
                    purchase(token,it)
                }

            }
        }

        binding.btPlus.setOnClickListener {
            viewModel.itemCount.value=if(viewModel.itemCount.value?.plus(5) ?:0 >viewModel.totalItem) viewModel.totalItem else viewModel.itemCount.value?.plus(5)
        }

        binding.btMinus.setOnClickListener{
            viewModel.itemCount.value=if(viewModel.itemCount.value?.minus(5) ?:0 >0) viewModel.itemCount.value?.minus(5) else 0

        }



    }
    private fun showLoading(isShow:Boolean){
        binding.progressBar.visibility=if(isShow) View.VISIBLE else View.GONE
    }

    private fun purchase(id:String,requestPurchase: RequestPurchase) {
        viewModel.purchase(id,requestPurchase).observe(this){
            when(it){
                is Resource.Success->{
                    Toast.makeText(this,"Purchased!!",Toast.LENGTH_SHORT).show()
                    showLoading(false)
                    finish()
                }
                is Resource.Loading->{
                    showLoading(true)
                }
                is Resource.Error->{
                    showLoading(false)
                    Toast.makeText(this,"Failed to purchase items",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun calculatePrice(qty:Int,itemPrice:Int)=qty*itemPrice

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    companion object{
        const val EXTRA_DATA="extra_data"
    }
}