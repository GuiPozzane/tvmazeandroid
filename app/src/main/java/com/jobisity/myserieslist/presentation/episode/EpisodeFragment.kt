package com.jobisity.myserieslist.presentation.episode

import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jobisity.myserieslist.R
import com.jobisity.myserieslist.common.Utils
import com.jobisity.myserieslist.databinding.FragmentEpisodeBinding
import com.jobisity.myserieslist.domain.model.show.Episode
import com.squareup.picasso.Picasso


class EpisodeFragment: Fragment(R.layout.fragment_episode) {
    private lateinit var binding: FragmentEpisodeBinding
    private lateinit var viewModel: EpisodeViewModel
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
        var episodeStringfied = arguments?.getString("episode");
        binding = FragmentEpisodeBinding.bind(view);
        viewModel = ViewModelProvider(this).get(EpisodeViewModel::class.java)
        viewModel.episode.postValue(Utils().getGsonParser()!!.fromJson(episodeStringfied,
            Episode::class.java))

        populatePage()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun populatePage(){
        viewModel.episode.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                if(!it.image?.original.isNullOrEmpty())
                    Picasso.get().load(Uri.parse(it.image?.original)).into(binding.fragmentEpisodeImg);
                binding.fragmentEpisodeName.text = it.name;
                if(!it.summary.isNullOrEmpty())
                    binding.fragmentEpisodeSummary.text = Html.fromHtml(it.summary, Html.FROM_HTML_MODE_COMPACT);
                binding.fragmentEpisodeNumber.text = "#${it.number.toString()}";
            }

        });
    }

}