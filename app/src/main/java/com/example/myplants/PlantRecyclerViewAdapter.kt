package com.example.myplants


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate


class PlantRecyclerViewAdapter(
    private val values: MutableList<UserData.Plant>?) :
    RecyclerView.Adapter<PlantRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.content_plant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)
        if (item!= null){
            val water = LocalDate.parse(item.lastWatered)
            val fertilize = LocalDate.parse(item.lastFertilized)
            val nextWater = water.plusDays(item.waterInterval.toLong() )
            val nextFertilize = fertilize.plusDays(item.fertilizeInterval.toLong())
            holder.nameView.text = item.name
            holder.descriptionView.text = item.description
            holder.lastWView.text = "last watered:     "+item.lastWatered
            holder.lastFView.text = "last fertilized:    "+item.lastFertilized
            holder.nextWView.text = "Next watering:      "+nextWater.toString()
            holder.nextFView.text = "Next fertilizing:    "+nextFertilize.toString()
        }
        if (item?.image != null) {
            holder.imageView.setImageBitmap(item.image)
        }
    }

    override fun getItemCount() = values?.size ?: 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image)
        val nameView: TextView = view.findViewById(R.id.name)
        val descriptionView: TextView = view.findViewById(R.id.description)
        val lastWView: TextView = view.findViewById(R.id.lastWatered)
        val lastFView: TextView = view.findViewById(R.id.lastFertilized)
        val nextWView: TextView = view.findViewById(R.id.nextWatered)
        val nextFView: TextView = view.findViewById(R.id.nextFertilized)
    }
}