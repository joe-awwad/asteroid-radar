package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.FragmentAsteroidItemBinding

class AsteroidAdapter(private val clickListener: AsteroidClickListener) :
    ListAdapter<Asteroid, AsteroidViewHolder>(AsteroidDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}

class AsteroidViewHolder(private val binding: FragmentAsteroidItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(asteroid: Asteroid, clickListener: AsteroidClickListener) {
        binding.asteroid = asteroid
        binding.clickListener = clickListener
    }

    companion object {
        fun from(parent: ViewGroup): AsteroidViewHolder {
            val binding =
                FragmentAsteroidItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return AsteroidViewHolder(binding)
        }
    }
}

class AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {

    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}

class AsteroidClickListener(private val clickHandler: (asteroid: Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = clickHandler(asteroid)
}