package com.ilabank.practical.mvvm.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.ilabank.practical.mvvm.data.model.Animal
import com.ilabank.practical.mvvm.databinding.ListItemLayoutBinding
import java.util.*
import kotlin.collections.ArrayList

class ListAdapter(
    private val dataList: MutableList<Animal>,
) : RecyclerView.Adapter<ListAdapter.ViewHolder>(), Filterable {

    var filterList: MutableList<Animal> = arrayListOf()

    inner class ViewHolder(val binding: ListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(filterList[position]) {
                binding.txtTitle.text = this.title
                binding.imgView.setImageResource(this.image)
            }
        }
    }

    override fun getItemCount(): Int = filterList.size

    fun addData(list: MutableList<Animal>) {
        dataList.clear()
        dataList.addAll(list)
        filterList = dataList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) filterList = dataList else {
                    val filteredList = mutableListOf<Animal>()
                    dataList
                        .filter {
                            (it.title.lowercase().contains(constraint!!)) or
                                    (it.title.lowercase().contains(constraint))

                        }
                        .forEach { filteredList.add(it) }
                    filterList = filteredList

                }
                return FilterResults().apply { values = filterList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                filterList = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Animal>
                notifyDataSetChanged()
            }
        }
    }
}