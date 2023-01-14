package com.example.treasurehackathon.ui.home.donatur

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.treasurehackathon.core.data.Resource
import com.example.treasurehackathon.core.domain.model.Product
import com.example.treasurehackathon.databinding.FragmentDonateBinding
import com.example.treasurehackathon.ui.home.HomeViewModel
import com.example.treasurehackathon.ui.splash.SplashActivity.Companion.SESSION_DATA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonateFragment : Fragment() {
    private var _binding: FragmentDonateBinding? = null
    private val viewModel:HomeViewModel by viewModels()
    private val binding get() = _binding!!
    override fun onStart() {
        super.onStart()
        if (isAdded) {
            val activity = activity as HomeDonaturActivity
            activity.state = Screen.DONATE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(activity!=null){
            val productAdapter = ProductAdapter{ selectedData ->
                val intent = Intent(activity, DetailDonateActivity::class.java)
                intent.putExtra(DetailDonateActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }
            viewModel.getAllProducts.observe(viewLifecycleOwner) { products ->
                if (products != null) {
                    when (products) {
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.tvErrorMsg.visibility=View.GONE
                            productAdapter.setData(products.data)
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.tvErrorMsg.visibility=View.VISIBLE
                        }
                    }
                }
            }

            with(binding.rvDonate) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = productAdapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDonateBinding.inflate(inflater, container, false)
        return binding.root

    }

}