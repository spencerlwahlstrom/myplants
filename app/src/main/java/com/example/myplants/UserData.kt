package com.example.myplants

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.datastore.generated.model.PlantData

object UserData {

    private const val TAG = "UserData"

    private val _isSignedIn = MutableLiveData<Boolean>(false)
    var isSignedIn: LiveData<Boolean> = _isSignedIn

    fun setSignedIn(newValue : Boolean) {
        _isSignedIn.postValue(newValue)
    }

    private val _plants = MutableLiveData<MutableList<Plant>>(mutableListOf())

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.postValue(this.value)
    }
    fun notifyObserver() {
        this._plants.notifyObserver()
    }

    fun plants() : LiveData<MutableList<Plant>>  = _plants

    fun addPlant(n : Plant) {
        val plants = _plants.value
        if (plants != null) {
            plants.add(n)
            _plants.notifyObserver()
        } else {
        }
    }
    fun deletePlant(at: Int) : Plant?  {
        val note = _plants.value?.removeAt(at)
        _plants.notifyObserver()
        return note
    }

    fun resetPlants() {
        this._plants.value?.clear()
        _plants.notifyObserver()
    }

    data class Plant(val id: String, val name: String, val description: String, var lastWatered: String, var lastFertilized: String, var waterInterval: Int, var fertilizeInterval: Int, var imageName: String? = null) {
        override fun toString(): String = name

        var image : Bitmap? = null

        val data : PlantData
            get() = PlantData.builder()
                .name(this.name)
                .description(this.description)
                .image(this.imageName)
                .lastWatered(this.lastWatered)
                .lastFertilized(this.lastFertilized)
                .waterInterval(this.waterInterval)
                .fertilizeInterval(this.fertilizeInterval)
                .id(this.id)
                .build()

        companion object {
            fun from(plantData : PlantData) : Plant {
                val result = Plant(plantData.id, plantData.name, plantData.description, plantData.lastWatered, plantData.lastFertilized, plantData.waterInterval, plantData.fertilizeInterval, plantData.image)
                if (plantData.image != null) {
                    Backend.retrieveImage(plantData.image!!) {
                        result.image = it
                        with(UserData) { notifyObserver() }
                    }
                }
                return result
            }
        }

    }
}