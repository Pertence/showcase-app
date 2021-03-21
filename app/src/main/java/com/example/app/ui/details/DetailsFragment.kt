package com.example.app.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.app.R
import com.example.app.databinding.FragmentDetailsBinding
import com.example.app.model.Images
import com.example.app.utils.Status
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()

    private lateinit var binding: FragmentDetailsBinding

    private var idDetail = 0

    private lateinit var arrayOfImagesDetails: Images

    private var oneImage: String? = null

    private var adapter: ImagesAdapter = ImagesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val args = DetailsFragmentArgs.fromBundle(requireArguments())

        idDetail = args.id

        arrayOfImagesDetails = args.imageDetails

        oneImage = args.oneImage

        adapter.imagesArray = arrayOfImagesDetails

        viewModel.getDetailsLocation(idDetail)

        //Setup view
        viewModel.detailLocation.observe(viewLifecycleOwner, {
            binding.nameDetails.text = it.name
            binding.reviewDetails.text = it.review.toString()
            binding.phoneDetails.text = it.phone
            binding.aboutDetails.text = it.about
            binding.addressDetails.text = it.address
            binding.ratingBarDetails.rating = it.review
            val imageUri = (oneImage ?: "").toUri().buildUpon().scheme("https").build()
            Picasso.get()
                .load(imageUri)
                .into(binding.detailImage)
        })

        //Setup Recycler View
        binding.recyclerHorizontal.layoutManager =
            GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
        binding.recyclerHorizontal.adapter = adapter

        setSchedule()

        //Setup Toolbar
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        //Setup progress bar
        viewModel.statusRequestDetail.observe(viewLifecycleOwner, {
            when (it) {
                Status.ERROR -> {
                    viewModel.messageDetail?.let { message ->
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

    private fun setSchedule(){
        viewModel.detailLocation.observe(viewLifecycleOwner, {
            val data = it.schedule
            val sunday = resources.getString(R.string.sunday_format, data.monday.open, data.monday.close)
            val monday = resources.getString(R.string.monday_format, data.monday.open, data.monday.close)
            val tuesday = resources.getString(R.string.tuesday_format, data.monday.open, data.monday.close)
            val wednesday = resources.getString(R.string.wednesday_format, data.monday.open, data.monday.close)
            val thursday = resources.getString(R.string.thursday_format, data.monday.open, data.monday.close)
            val friday = resources.getString(R.string.friday_format, data.monday.open, data.monday.close)
            val saturday = resources.getString(R.string.saturday_format, data.monday.open, data.monday.close)
            binding.schedulesDetails.text = "$monday$tuesday$wednesday$thursday$friday$saturday$sunday"
        })
    }
}