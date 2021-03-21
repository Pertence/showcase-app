package com.example.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.app.databinding.FragmentHomeBinding
import com.example.app.model.Images
import com.example.app.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()
    private var adapter: LocationsAdapter = LocationsAdapter()

    private var quantityItemsLocations: Int = 0
    var arrayOfImages = Images()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Setting images
        viewModel.locations.observe(this, {
            adapter.data = it.listLocations
            quantityItemsLocations = it.listLocations.size
            viewModel.getImages(quantityItemsLocations)
        })

        viewModel.images.observe(this, {
            arrayOfImages = it
            adapter.data.forEachIndexed { index, locations ->
                locations.img = it.hits[index].webformatURL
            }
            adapter.notifyDataSetChanged()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        //Setup RecyclerView
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.adapter = adapter

        //Setup click
        adapter.onItemClicked = object : OnItemClicked {
            override fun onClick(id: Int, oneImage: String?) {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                        id, arrayOfImages, oneImage
                    )
                )
            }
        }

        //Setup progress bar
        viewModel.statusRequest.observe(viewLifecycleOwner, {
            when (it) {
                Status.ERROR -> {
                    viewModel.messageHome?.let { message ->
                        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.GONE
                    }
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })

        return binding.root
    }

}