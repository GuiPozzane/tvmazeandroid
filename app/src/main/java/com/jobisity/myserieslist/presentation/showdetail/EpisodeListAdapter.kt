package com.jobisity.myserieslist.presentation.showdetail

import android.net.Uri
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jobisity.myserieslist.R
import com.jobisity.myserieslist.common.Utils
import com.jobisity.myserieslist.domain.model.show.Episode
import com.squareup.picasso.Picasso


class EpisodeListAdapter(private val episodes: List<Episode>,private val fragment: ShowDetailFragment) :
    RecyclerView.Adapter<EpisodeListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView
        var summaryView: TextView
        var nameView: TextView
        var numberView: TextView

        init {
            // Define click listener for the ViewHolder's View.
            imageView = view.findViewById(R.id.listItemEpisodeImg);
            summaryView = view.findViewById(R.id.listItemEpisodeSummary);
            nameView = view.findViewById(R.id.listItemEpisodeName);
            numberView = view.findViewById(R.id.listItemEpisodeNumber);

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.episode_listitem, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        var item = episodes[position]

        if(!item.image?.original.isNullOrEmpty())
            Picasso.get().load(Uri.parse(item.image?.original)).into(viewHolder.imageView);
        viewHolder.nameView.text = item.name;
        if(!item.summary.isNullOrEmpty())
            viewHolder.summaryView.text = Html.fromHtml(item.summary, Html.FROM_HTML_MODE_COMPACT);
        viewHolder.numberView.text = "#${item.number.toString()}";

        viewHolder.itemView.setOnClickListener(View.OnClickListener {


            val bundle = bundleOf("episode" to Utils().getGsonParser()!!.toJson(item))
            fragment.findNavController().navigate(R.id.navigation_episodeDetail,bundle)
        })
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = episodes.size

}