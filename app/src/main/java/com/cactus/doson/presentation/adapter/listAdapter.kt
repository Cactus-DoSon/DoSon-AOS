package com.cactus.doson.presentation.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cactus.doson.R

class listAdapter(private val postTitle : ArrayList<String>,private val postDetail: ArrayList<String>,private val postDate: ArrayList<String>,private val postImg: ArrayList<Uri>) : RecyclerView.Adapter<listAdapter.ViewHolder>(){

    override fun getItemCount(): Int = postTitle.size

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener
    private lateinit var likedClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
    fun setLikedClickListener(likedClickListener: ItemClickListener){
        this.likedClickListener = likedClickListener
    }


    override fun onCreateViewHolder(parent : ViewGroup, viewType: Int): ViewHolder {
        val inflateView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(inflateView)
    }

    class ViewHolder(private val v: View): RecyclerView.ViewHolder(v){
        private var view : View = v
        var post_title = v.findViewById<TextView>(R.id.postTitle)
        var post_detail = v.findViewById<TextView>(R.id.postDetail)
        var post_date = v.findViewById<TextView>(R.id.postDate)
        var post_img = v.findViewById<ImageView>(R.id.postImg)

        fun d(uri: Uri){
            Glide.with(v.context)
                .load(uri)
                .centerCrop()
                .into(post_img)
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.post_title.text = postTitle[position]
        holder.post_detail.text = postDetail[position]
        holder.post_date.text = postDate[position]


    }



}