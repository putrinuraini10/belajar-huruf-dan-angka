package com.example.belajarhurufdanangka

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.belajarhurufdanangka.databinding.ItemAngkaBinding

class AngkaAdapter(private val listAngka: List<Angka>) :
    RecyclerView.Adapter<AngkaAdapter.AngkaViewHolder>() {

    inner class AngkaViewHolder(val binding: ItemAngkaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AngkaViewHolder {
        val binding = ItemAngkaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AngkaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AngkaViewHolder, position: Int) {
        val data = listAngka[position]
        holder.binding.txtAngka.text = data.angka
        holder.binding.imgAngka.setImageResource(data.gambarResId)
    }

    override fun getItemCount() = listAngka.size
}
