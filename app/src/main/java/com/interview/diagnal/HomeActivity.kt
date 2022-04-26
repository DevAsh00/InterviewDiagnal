package com.interview.diagnal

import android.content.res.Configuration
import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import java.lang.Exception

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val viewModel: MovieViewModel by viewModels()
    private val moviesAdapter = MoviesAdapter()
    private var currentPage = 1
    private var isLoading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerview.layoutManager = GridLayoutManager(this, getSpanCount())
        recyclerview.adapter = moviesAdapter
        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val pastVisibleItems =
                    (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()

                if (pastVisibleItems / getSpanCount() == (moviesAdapter.itemCount / getSpanCount()) - 4 && !isLoading) {
                    isLoading = true
                    currentPage++
                    viewModel.loadData(currentPage)
                }
            }
        })

        viewModel.dataMovies.observe(this, Observer {
            isLoading = false
            moviesAdapter.setList(it)
        })

        viewModel.loadData(currentPage)
        setListener()
    }

    private fun setListener() {
        img_back.setOnClickListener { v -> onBackPressed() }
        img_search.setOnClickListener { v ->
            if (edt_search.visibility == View.VISIBLE) {
                isLoading = false
                edt_search.setText("")
                edt_search.visibility = View.GONE
                img_search.setImageResource(R.drawable.img_search)
                img_back.visibility = View.VISIBLE
                txt_title.visibility = View.VISIBLE
            } else {
                isLoading = true
                edt_search.visibility = View.VISIBLE
                img_search.setImageResource(R.drawable.img_search_cancel)
                img_back.visibility = View.GONE
                txt_title.visibility = View.GONE
            }
        }
        edt_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (moviesAdapter.getTotalItemCount() == 0) return
                var filterBY = s.toString()
                moviesAdapter.searchRecord(if (filterBY.length<3) "" else filterBY)
            }
        })
    }

    private fun getSpanCount(): Int {
        return if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 3
        else 6
    }
}