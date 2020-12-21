package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.AsteroidFilter
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.repository.AsteroidRepository

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val repository = AsteroidRepository.from(requireContext().applicationContext)
        ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)
    }

    private lateinit var selectedAsteroid: Asteroid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val asteroidAdapter = AsteroidAdapter(AsteroidClickListener { asteroid ->
            selectedAsteroid = asteroid
            viewModel.asteroidClicked()
        })

        binding.asteroidRecycler.adapter = asteroidAdapter

        viewModel.eventAsteroidClicked.observe(viewLifecycleOwner, Observer { clicked ->
            if (clicked) {
                findNavController().navigate(
                    MainFragmentDirections.actionShowDetail(
                        selectedAsteroid
                    )
                )
                viewModel.asteroidClickEventHandled()
            }
        })

        viewModel.asteroids.observe(viewLifecycleOwner, Observer { asteroids ->
            asteroidAdapter.submitList(asteroids)
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId) {
                R.id.show_today_asteroids -> AsteroidFilter.CURRENT_DAY
                R.id.show_week_asteroids -> AsteroidFilter.CURRENT_WEEK
                R.id.show_saved_asteroids -> AsteroidFilter.SAVED
                else -> AsteroidFilter.SAVED
            }
        )
        return true
    }
}
