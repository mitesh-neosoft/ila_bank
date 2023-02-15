package com.ilabank.practical.mvvm.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.MarginPageTransformer
import com.ilabank.practical.mvvm.ui.main.adapter.ListAdapter
import com.ilabank.practical.mvvm.ui.main.adapter.PagerAdapter
import com.ilabank.practical.mvvm.ui.main.viewmodel.MainViewModel
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.ilabank.practical.mvvm.R
import com.ilabank.practical.mvvm.data.model.Animal
import com.ilabank.practical.mvvm.databinding.ActivityMainBinding
import com.ilabank.practical.mvvm.dpToPx

class MainActivity : BaseActivity<ActivityMainBinding>(), SearchView.OnQueryTextListener {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    private lateinit var mainViewModel: MainViewModel
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var listAdapter: ListAdapter

    private var pagerList: MutableList<Animal> = mutableListOf()
    private var imageList: MutableList<Animal> = mutableListOf()

    override fun onViewBindingCreated(savedInstanceState: Bundle?) {
        binding.searchView.setOnQueryTextListener(this)

        setUpBannerData()
        setupListData()
        setupObserver()
    }

    fun setUpBannerData() {
        pagerAdapter = PagerAdapter(arrayListOf())
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.offscreenPageLimit = 1

        binding.viewPager.apply {
            clipToPadding = false   // allow full width shown with padding
            clipChildren = false    // allow left/right item is not clipped
            offscreenPageLimit = 1  // make sure left/right item is rendered
        }

        val offsetPx = 30.dpToPx(resources.displayMetrics)
        binding.viewPager.setPadding(0, 0, offsetPx, 0)

        val pageMarginPx = 10.dpToPx(resources.displayMetrics)
        val marginTransformer = MarginPageTransformer(pageMarginPx)
        binding.viewPager.setPageTransformer(marginTransformer)

        TabLayoutMediator(binding.intoTabLayout, binding.viewPager) { tab, position -> }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.searchView.setQuery("", false)
                binding.searchView.clearFocus()
                val tabList = mutableListOf<Animal>()
                imageList.forEach {
                    if (it.type == pagerList[position].type)
                        tabList.add(it)
                }
                renderAnimalBreedsList(tabList)
            }
        })

    }

    private fun setupListData() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        listAdapter = ListAdapter(arrayListOf())
        binding.recyclerView.adapter = listAdapter
    }

    private fun setupObserver() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.animal.observe(this) {
            pagerList = it
            renderAnimalList(pagerList)
        }
        mainViewModel.animalBreeds.observe(this) {
            imageList = it
        }
    }

    private fun renderAnimalList(users: MutableList<Animal>) {
        pagerAdapter.addData(users)
    }

    private fun renderAnimalBreedsList(users: MutableList<Animal>) {
        listAdapter.addData(users)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        listAdapter.filter.filter(newText)
        return false
    }
}
