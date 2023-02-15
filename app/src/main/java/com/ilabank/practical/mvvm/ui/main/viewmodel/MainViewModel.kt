package com.ilabank.practical.mvvm.ui.main.viewmodel

import android.text.TextUtils
import androidx.lifecycle.*
import com.ilabank.practical.mvvm.R
import com.ilabank.practical.mvvm.data.model.Animal
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val animalList = mutableListOf<Animal>()
    private val _animal = MutableLiveData<MutableList<Animal>>()
    val animal: LiveData<MutableList<Animal>>
        get() = _animal

    private val animalBreedsList = mutableListOf<Animal>()
    private val _animalBreeds = MutableLiveData<MutableList<Animal>>()
    val animalBreeds: LiveData<MutableList<Animal>>
        get() = _animalBreeds

    init {
        fetchAnimals()
    }

    private fun fetchAnimals() {
        viewModelScope.launch {
            animalList.add(Animal("Dog", R.drawable.dog, 1))
            animalList.add(Animal("Cat", R.drawable.cat, 2))
            animalList.add(Animal("Horse", R.drawable.horse, 3))
            animalList.add(Animal("Lion", R.drawable.download, 4))
            animalList.add(Animal("Tiger", R.drawable.tiger, 5))
            _animal.postValue(animalList)
        }
        animalList.forEach {
            fetchAnimalBreeds(it)
        }
    }

    fun fetchAnimalBreeds(data: Animal) {
        viewModelScope.launch {
            for (i in 1..20) {
                animalBreedsList.add(Animal("${data.title} $i", data.image, data.type))
            }
            _animalBreeds.postValue(animalBreedsList)
        }
    }
}