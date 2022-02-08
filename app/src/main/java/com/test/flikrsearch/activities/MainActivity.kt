package com.test.flikrsearch.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.flikrsearch.R
import com.test.flikrsearch.adapters.MyImageAdapter
import com.test.flikrsearch.databinding.ActivityMainBinding
import com.test.flikrsearch.pojo.PhotoItem
import com.test.flikrsearch.utils.GridSpacingItemDecoration
import com.test.flikrsearch.utils.LoadStates
import com.test.flikrsearch.utils.Utils
import com.test.flikrsearch.viewmodels.MainActivityModel
import com.test.flikrsearch.viewmodels.MainActivityModel.Companion.oldSize
import com.test.flikrsearch.viewmodels.MainActivityModel.Companion.searchTerm


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityModel
    private lateinit var context: MainActivity

    private val list = ArrayList<PhotoItem>()

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        context = this

        viewModel = ViewModelProviders.of(this)[MainActivityModel::class.java]

        setContentView(binding.root)

        binding.rvImages.let {
            it.addItemDecoration(
                GridSpacingItemDecoration(
                    3,
                    30,
                    true
                )
            )
            it.layoutManager = GridLayoutManager(context, 3)
        }

        val myImageAdapter = MyImageAdapter(context, list)

        binding.rvImages.adapter = myImageAdapter

        viewModel.mutablePhotos.observe(context) { photos ->
            Log.e(TAG, "Images API - $photos")
            if (photos != null && photos.size > 0) {
                binding.tvNoResult.visibility = View.GONE
                list.clear()
                list.addAll(photos)
                if (photos.size > oldSize)
                    myImageAdapter.notifyItemInserted(oldSize)
                else
                    myImageAdapter.notifyItemRangeRemoved(0, oldSize)
                oldSize = photos.size
            } else {
                binding.tvNoResult.text = getString(R.string.no_result)
                binding.tvNoResult.visibility = View.VISIBLE
            }
        }

        binding.rvImages.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!binding.rvImages.canScrollVertically(1)) {
                    viewModel.fetchMovieByQuery(searchTerm)
                }
            }
        })

        viewModel.loadingStates.observe(context) {
            Log.e(TAG, "observer: Load State - $it")
            when (it) {
                LoadStates.LOADING -> {
                    binding.ltSrl.isRefreshing = true
                }
                LoadStates.LOADED -> {
                    binding.ltSrl.isRefreshing = false
                }
                else -> {
                    binding.ltSrl.isRefreshing = false
                    Toast.makeText(context, "Unable to refresh the feed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                performSearch()
                true
            } else false
        }

        binding.btnSearch.setOnClickListener {

            performSearch()

        }

        binding.ltSrl.setOnRefreshListener { performSearch() }
    }

    private fun performSearch() {
        val str = binding.etSearch.text.toString()
        if (str.isEmpty()) {
            Toast.makeText(context, "Enter search term", Toast.LENGTH_SHORT).show()
        } else {
            searchTerm = str
            Utils.hideKeyboard(context)
            viewModel.fetchMovieByQuery(str, 1)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.nav_about){
            Toast.makeText(context, "Made by Deepak Kumar for ePayLater", Toast.LENGTH_LONG).show()
        }

        return super.onOptionsItemSelected(item)
    }
}
