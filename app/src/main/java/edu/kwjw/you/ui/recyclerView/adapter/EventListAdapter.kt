package edu.kwjw.you.ui.recyclerView.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.kwjw.you.R
import edu.kwjw.you.databinding.EventItemBinding
import edu.kwjw.you.ui.recyclerView.model.Event


class EventListAdapter : ListAdapter<Event, EventListAdapter.ViewHolder>(EventDiff) {

    inner class ViewHolder(binding: EventItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.name
        val status = binding.status
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            EventItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder){
            name.text = name.toString()
            status.text = status.toString()
        }
    }



}

object EventDiff : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
    }
}