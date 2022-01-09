package com.oskuda.mynote.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inspector.IntFlagMapping
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oskuda.mynote.BaseApplication
import com.oskuda.mynote.R
import com.oskuda.mynote.databinding.FragmentNoteDetailBinding
import com.oskuda.mynote.model.Note
import com.oskuda.mynote.ui.viewmodel.NoteViewModel
import com.oskuda.mynote.ui.viewmodel.NoteViewModelFactory


class NoteDetailFragment : Fragment() {

    private val navigationArgs : NoteDetailFragmentArgs by navArgs()

    private val viewModel : NoteViewModel by activityViewModels(){
        NoteViewModelFactory(
            (activity?.application as BaseApplication).database.noteDao()
        )
    }

    private lateinit var note : Note

    private var _binding : FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        viewModel.retrieveNote(id).observe(this.viewLifecycleOwner){
            selectedNote ->note =selectedNote
            bindNote()
        }
    }

    private fun bindNote(){
        binding.apply {
            title.text = note.title
            description.text = note.description
            isCompletedSwitch.isChecked = note.isCompleted
            isCompletedSwitch.isEnabled = false
            isCompletedLabel.text = if(note.isCompleted) "Completed" else "Pending"
            editNoteFab.setOnClickListener {
                val action = NoteDetailFragmentDirections.actionNoteDetailFragmentToAddNoteFragment(note.id)
                findNavController().navigate(action)
            }
            emailTaskButton.setOnClickListener{
                sendNote()
            }
        }
    }

    private fun sendNote(){
            val taskSummary = getTaskSummary(note.title, note.description, note.isCompleted)
            val intent = Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_SUBJECT, "MyNote")
                .putExtra(Intent.EXTRA_TEXT, taskSummary)
            if (activity?.packageManager?.resolveActivity(intent, 0) != null) {
                startActivity(intent)
            }
    }

    private fun getTaskSummary(title : String, description : String, isCompleted: Boolean): String{
        return "Task title : $title\nDescription: $description\nStatus: $isCompleted"
    }
}