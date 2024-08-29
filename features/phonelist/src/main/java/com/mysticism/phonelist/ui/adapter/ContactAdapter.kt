package com.mysticism.phonelist.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mysticism.data.models.Data
import com.mysticism.phonelist.R

class ContactAdapter(
    private val onFavoriteClick: (Data) -> Unit,
    private val onItemClick: (Data) -> Unit
) : ListAdapter<Data, ContactAdapter.ContactViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view, onFavoriteClick, onItemClick)
    }

    class ContactViewHolder(
        itemView: View,
        private val onFavoriteClick: (Data) -> Unit,
        private val onItemClick: (Data) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val favoriteIcon = itemView.findViewById<ImageView>(R.id.imageViewFavorite)
        private val contactImage = itemView.findViewById<ImageView>(R.id.imageView2)

        fun bind(contact: Data, position: Int) {
            itemView.findViewById<TextView>(R.id.textViewName).text =
                "${contact.firstName} ${contact.lastName}"
            itemView.findViewById<TextView>(R.id.textViewEmail).text = contact.email
            itemView.findViewById<TextView>(R.id.textViewIp).text = contact.ipAddress

            if (position % 2 == 0) {
                itemView.setBackgroundResource(R.drawable.custom_background)
            } else {
                itemView.setBackgroundResource(R.drawable.default_background)
            }

            val favoriteRes =
                if (contact.isFavorite) R.drawable.list_favorit_on_icon else R.drawable.list_favorit_off_icon
            favoriteIcon.setImageResource(favoriteRes)

            favoriteIcon.setOnClickListener {
                onFavoriteClick(contact)
            }

            itemView.setOnClickListener {
                onItemClick(contact)
            }

            contact.imagePath?.let {
                Glide.with(itemView.context)
                    .load(it)
                    .placeholder(R.drawable.list_avatar_bg)
                    .into(contactImage)
            }
        }
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Data, newItem: Data) =
                oldItem == newItem
        }
    }
}