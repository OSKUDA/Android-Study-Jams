package com.oskuda.mynote.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oskuda.mynote.databinding.ListItemNoteBinding
import com.oskuda.mynote.model.Note

class NoteListAdapter(
    private val clickListener : (Note) -> Unit)
    :  ListAdapter<Note, NoteListAdapter.NoteViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NoteViewHolder(
            ListItemNoteBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.itemView.setOnClickListener{
            clickListener(note)
        }
        holder.bind(note)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    class NoteViewHolder (private val binding : ListItemNoteBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(note : Note){
            binding.note = note
            binding.executePendingBindings()
        }
    }
}