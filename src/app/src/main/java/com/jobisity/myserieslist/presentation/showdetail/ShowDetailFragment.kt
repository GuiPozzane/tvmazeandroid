package com.jobisity.myserieslist.presentation.showdetail

import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jobisity.myserieslist.R
import com.jobisity.myserieslist.databinding.FragmentShowDetailBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowDetailFragment : Fragment(R.layout.fragment_show_detail) {

    private lateinit var binding: FragmentShowDetailBinding
    private lateinit var viewModel: ShowDetailViewModel
    private lateinit var showDetailFragment: ShowDetailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.getItemId()) {
            android.R.id.home -> {
                requireActivity()!!.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showDetailFragment = this;
        var idShow = arguments?.getLong("idShow");
        binding = FragmentShowDetailBinding.bind(view);
        viewModel = ViewModelProvider(this).get(ShowDetailViewModel::class.java)
        viewModel.loadDetail(idShow!!.toInt());
        populatePage();
        super.onViewCreated(view, savedInstanceState)
    }
    private fun populatePage(){
        if(viewModel.show == null)
            return;
        viewModel.show.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                if(!it.image?.original.isNullOrEmpty())
                    Picasso.get().load(Uri.parse(it.image?.original)).into(binding.showDetailImg);
                binding.showDetailTitle.setText(it.name);

                if(it.status == "Running" && it.schedule != null){
                    binding.showDetailReleaseDate.text = "${it.schedule.days.joinToString("|")} at ${it.schedule.time}"
                    binding.showDetailReleaseDate.visibility = View.VISIBLE
                }
                else
                    binding.showDetailReleaseDate.visibility = View.GONE
                if(!it.summary.isNullOrEmpty())
                    binding.showDetailSummary.text = Html.fromHtml(it.summary, Html.FROM_HTML_MODE_COMPACT)
                var genreLayoutManager = LinearLayoutManager(context)
                genreLayoutManager.orientation = LinearLayoutManager.HORIZONTAL;
                binding.showDetailGenresList.setLayoutManager(genreLayoutManager);
                //Genre adapter
                var genreAdapter = GenreListAdapter(it.genres)
                genreAdapter.notifyDataSetChanged()
                binding.showDetailGenresList.adapter = genreAdapter


                //Seasons adapter
                var seasonAdapter = ArrayAdapter<String>(requireContext(), R.layout.season_spinner,(it._embedded.episodes.map{ "Season ${it.season}" }).distinct())
                genreAdapter.notifyDataSetChanged()
                binding.showDetailSeasonSpinner.adapter = seasonAdapter
                binding.showDetailSeasonSpinner.setOnItemSelectedListener (object :
                    OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        binding.showDetailEpisodesList.visibility = View.VISIBLE

                        var episodeAdapter = EpisodeListAdapter(it._embedded.episodes.filter {it.season == position+1 },showDetailFragment)
                        episodeAdapter.notifyDataSetChanged()
                        var horizontalLayoutManager = LinearLayoutManager(context)
                        horizontalLayoutManager.orientation = LinearLayoutManager.HORIZONTAL;
                        binding.showDetailEpisodesList.setLayoutManager(horizontalLayoutManager);
                        binding.showDetailEpisodesList.adapter = episodeAdapter
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                })

            }

        });
    }


}