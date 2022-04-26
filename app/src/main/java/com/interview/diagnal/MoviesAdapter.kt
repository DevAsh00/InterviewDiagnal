package com.interview.diagnal;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.interview.diagnal.utils.loadImage
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private var moviesList: ArrayList<MovieModel> = arrayListOf()
    private var filterBY: ArrayList<MovieModel> = arrayListOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_movie,
            parent,
            false
        )
        return ViewHolder(view)
    }

    fun setList(list: List<MovieModel>) {
        this.moviesList = list as ArrayList<MovieModel>
        this.filterBY.clear()
        searchRecord("")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.txt_title.text = filterBY[position].name
        holder.itemView.img_banner.loadImage(filterBY[position].posterImage)
    }

    override fun getItemCount() = filterBY.size

    fun getTotalItemCount() = moviesList.size

    fun searchRecord(strSearch: String) {
        this.filterBY.clear()
        if (strSearch.isNotEmpty()) {
            for (movObj in moviesList) {
                if (movObj.name.lowercase().contains(strSearch.lowercase())) {
                    filterBY.add(movObj)
                }
            }
        } else
            filterBY.addAll(moviesList)
        notifyDataSetChanged()
    }
}
