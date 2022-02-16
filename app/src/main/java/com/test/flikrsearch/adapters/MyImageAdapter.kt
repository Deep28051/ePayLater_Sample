package com.test.flikrsearch.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.flikrsearch.R
import com.test.flikrsearch.databinding.LtImageBinding
import com.test.flikrsearch.pojo.PhotoItem

class MyImageAdapter(private val context: Context, var myVehicles: List<PhotoItem>) :
    RecyclerView.Adapter<MyImageAdapter.ViewHolder>() {

    private val TAG = "MyImageAdapter"

    override fun getItemCount(): Int {
        return myVehicles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LtImageBinding.inflate(
                LayoutInflater.from(
                    context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val image = myVehicles[position]

        val url = context.getString(
            R.string.image_url,
            image.farm.toString(),
            image.server,
            image.id,
            image.secret
        )

        //binding.url = url

        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.place)
            .error(R.drawable.place)
            .into(binding.ivImg)
        /*binding.root.setOnClickListener {
            Toast.makeText(context, "position - $position", Toast.LENGTH_SHORT).show()
        }*/
    }

    class ViewHolder(var binding: LtImageBinding) : RecyclerView.ViewHolder(binding.root)
}