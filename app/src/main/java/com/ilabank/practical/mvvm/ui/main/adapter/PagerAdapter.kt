package com.ilabank.practical.mvvm.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilabank.practical.mvvm.data.model.Animal
import com.ilabank.practical.mvvm.databinding.PagerItemLayoutBinding

class PagerAdapter(
    private val users: ArrayList<Animal>,
) : RecyclerView.Adapter<PagerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: PagerItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PagerItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(users[position]) {
                binding.imgView.setImageResource(image)
            }
        }
    }

    override fun getItemCount(): Int = users.size

    fun addData(list: MutableList<Animal>) {
        users.addAll(list)
        notifyDataSetChanged()
    }

    fun getData(position: Int): String {
        return users[position].title
    }
}