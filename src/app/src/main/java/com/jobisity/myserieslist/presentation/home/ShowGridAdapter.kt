package com.jobisity.myserieslist.presentation.home

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.jobisity.myserieslist.R
import com.jobisity.myserieslist.domain.model.show.Show
import com.squareup.picasso.Picasso


internal class ShowGridAdapter(
    private var context: Context,
    private val shows: List<Show>
) : BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var currentView: View
    override fun getCount(): Int {
        return shows.size
    }

    override fun getItem(position: Int): Show? {
        return shows[position]
    }

    override fun getItemId(position: Int): Long {
        return shows[position].id.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View? {
        var item = shows[position];
        if(item == null)
            return null;
        var convertView : View? = convertView
        if (layoutInflater == null)
            layoutInflater = context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater;
        if(convertView == null)
            convertView = layoutInflater!!.inflate(R.layout.seriescard_listitem, null);
        convertView = View.inflate(context,R.layout.seriescard_listitem,null);
        currentView = convertView;
        imageView = convertView.findViewById(R.id.sc_bgImage);
        textView = convertView.findViewById(R.id.sc_name);
        if(!item.image?.original.isNullOrEmpty())
            Picasso.get().load(Uri.parse(item.image?.original)).into(imageView);
        textView.text = item.name;

        return convertView
    }

}