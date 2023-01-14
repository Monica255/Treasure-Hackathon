package com.example.treasurehackathon.ui.home.donatur

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.treasurehackathon.core.domain.model.Product
import com.example.treasurehackathon.core.utils.DataMapper
import com.example.treasurehackathon.databinding.ItemDonateBinding

class ProductAdapter(private val onClick: ((Product) -> Unit)): RecyclerView.Adapter<ProductAdapter.ListViewHolder>() {
    private var listData = ArrayList<Product>()

    fun setData(data: List<Product>?) {
        if (data == null) return
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private var binding: ItemDonateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick?.invoke(listData[adapterPosition])
            }
        }
        fun bind(data: Product) {
            binding.tvSeller.text=data.username
            binding.tvLeftovers.text=data.qty.toString()+ " leftover(s)"
            binding.tvPrice.text= "Rp " +DataMapper.totalPriceToPrice(data.prize,data.qty).toString()

         /*Glide.with(itemView.context)
                .load(x)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .fallback(R.drawable.ic_launcher_foreground)
                .into(binding.imgProfile)*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemDonateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int=listData.size
}