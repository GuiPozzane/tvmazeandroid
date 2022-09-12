package com.jobisity.myserieslist.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.SearchView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jobisity.myserieslist.R
import com.jobisity.myserieslist.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var homeFragment: HomeFragment
    private var currentPage : Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view);
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        setLayoutVars(view);

        super.onViewCreated(view, savedInstanceState)
    }
    fun setLayoutVars(view: View){
        homeFragment = this;
        setListObserver();
        searchHandle();
        buttonHandle();
    }
    private fun buttonHandle(){
        binding.homePreviousButton.setOnClickListener(View.OnClickListener {
            viewModel.backPage()
        })
        binding.homeNextButton.setOnClickListener(View.OnClickListener {
            viewModel.nextPage()
        })
        viewModel.currentPage.observe(viewLifecycleOwner, Observer
        {
            if(it < 1)
               binding.homePreviousButton.visibility = View.GONE
            else
                binding.homePreviousButton.visibility = View.VISIBLE
        })
    }
    private fun setListObserver(){
        viewModel.shows.observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()) {
                binding.homeNoContent.isVisible = true;
                binding.homeGridView.isVisible = false;
            }
            else {
                binding.homeNoContent.isVisible = false;
                binding.homeGridView.isVisible = true;
                var adapter = ShowGridAdapter(requireContext(), it)
                adapter.notifyDataSetChanged()
                binding.homeGridView.adapter = adapter
                binding.homeGridView.setOnItemClickListener(AdapterView.OnItemClickListener { parent, v, position, id ->
                    var idShow = adapter.getItemId(position);
                    val bundle = bundleOf("idShow" to idShow)
                    homeFragment.findNavController().navigate(R.id.navigation_showDetail,bundle)
                })
            }
        });
    }
    private fun searchHandle(){
        binding.homeSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String): Boolean {
        viewModel.searchShow(query);
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        if(newText.length > 0)
            return false;
        viewModel.loadList(1);
        return false
    }
}     )
    }

}