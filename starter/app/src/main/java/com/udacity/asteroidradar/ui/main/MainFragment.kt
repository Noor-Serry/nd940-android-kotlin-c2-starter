package com.udacity.asteroidradar.ui.main



import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data.room.Asteroid
import com.udacity.asteroidradar.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
   lateinit var  binding : FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       binding =  FragmentMainBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        viewModel.toDayAsteroids.observe(viewLifecycleOwner,this::sendDataToBinding)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setHasOptionsMenu(true)

        binding.asteroidRecycler.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

    private fun sendDataToBinding(asteroids:List<Asteroid>){
        if(asteroids.isNotEmpty())
        binding.statusLoadingWheel.visibility = View.GONE
        binding.asteroidRecycler.adapter =  RecyclerAdapter(asteroids)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.show_rent_menu ->
                viewModel.toDayAsteroids.observe(viewLifecycleOwner,this::sendDataToBinding)
            R.id.show_buy_menu ->
                viewModel.allAsteroids.observe(viewLifecycleOwner,this::sendDataToBinding)
            else ->
                viewModel.sevenDayAsteroid.observe(viewLifecycleOwner,this::sendDataToBinding)
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }












}
