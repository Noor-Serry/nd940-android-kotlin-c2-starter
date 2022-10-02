package com.udacity.asteroidradar.ui.main

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data.room.Asteroid
import com.udacity.asteroidradar.databinding.CustomRecyclerViewBinding


class RecyclerAdapter (var asteroids: List<Asteroid>): RecyclerView.Adapter<RecyclerAdapter.Holder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var binding = DataBindingUtil.inflate<CustomRecyclerViewBinding>(LayoutInflater.from(parent.context),
            R.layout.custom_recycler_view,parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.asteroid = asteroids[position]
    }

    override fun getItemCount(): Int {
       return asteroids.size
    }

    class Holder(var binding : CustomRecyclerViewBinding) :RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener(this::onClickListener)
        }

        fun onClickListener(view : View){
            var destination = MainFragmentDirections.actionShowDetail(binding.getAsteroid()!!)
            Navigation.findNavController(binding.root).navigate(destination)
        }
    }
}