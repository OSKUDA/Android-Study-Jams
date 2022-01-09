package com.oskuda.mynote.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oskuda.mynote.BaseApplication
import com.oskuda.mynote.R
import com.oskuda.mynote.databinding.FragmentAddNoteBinding
import com.oskuda.mynote.model.Note
import com.oskuda.mynote.ui.viewmodel.NoteViewModel
import com.oskuda.mynote.ui.viewmodel.NoteViewModelFactory

class AddNoteFragment : Fragment() {

    private val navigationArgs: AddNoteFragmentArgs by navArgs()

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var note: Note

    private val viewModel: NoteViewModel by activityViewModels() {
        NoteViewModelFactory(
            (activity?.application as BaseApplication).database.noteDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        if (id > 0) {
            viewModel.retrieveNote(id).observe(this.viewLifecycleOwner) { selectedNote ->
                note = selectedNote
                bindNote(note)
            }
            binding.deleteBtn.visibility = View.VISIBLE
            binding.deleteBtn.setOnClickListener {
                deleteNote(note)
            }
            binding.isCompletedSwitchContainer.visibility = View.VISIBLE
            binding.isCompletedSwitch.setOnCheckedChangeListener { _, b ->
                if (b) {
                    binding.isCompletedLabel.text = "Completed"
                } else {
                    binding.isCompletedLabel.text = "Pending"
                }
            }
        } else {
            binding.saveBtn.setOnClickListener {
                addNote()
            }
        }
    }

    private fun deleteNote(note: Note) {
        viewModel.deleteNote(note)
        findNavController().navigate(R.id.action_addNoteFragment_to_noteListFragment)
    }

    private fun addNote() {
        if (isValidEntry()) {
            viewModel.addNote(
                title = binding.titleInput.text.toString(),
                description = binding.descriptionInput.text.toString(),
                isCompleted = false
            )
            findNavController().navigate(R.id.action_addNoteFragment_to_noteListFragment)
        }else{
            val builder = AlertDialog.Builder(context)
                .setTitle("Empty fields")
                .setMessage("Please fill all the required fields")
                .setIcon(R.drawable.ic_warning)
                .setPositiveButton("Okay") { _, _ ->
                    binding.apply {
                        titleInput.setText("")
                        descriptionInput.setText("")
                    }
                }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

    }

    private fun updateNote() {
        if (isValidEntry()) {
            viewModel.updateNote(
                id = navigationArgs.id,
                title = binding.titleInput.text.toString(),
                description = binding.descriptionInput.text.toString(),
                isCompleted = binding.isCompletedSwitch.isChecked,
            )
            findNavController().navigate(R.id.action_addNoteFragment_to_noteListFragment)
        }
    }

    private fun isValidEntry() = viewModel.isValidEntry(
        binding.titleInput.text.toString(),
        binding.descriptionInput.text.toString()
    )

    private fun bindNote(note: Note) {
        binding.apply {
            titleInput.setText(note.title, TextView.BufferType.SPANNABLE)
            descriptionInput.setText(note.description, TextView.BufferType.SPANNABLE)
            // TODO for switch
            isCompletedSwitch.isChecked = note.isCompleted
            isCompletedLabel.text = if (note.isCompleted) "Completed" else "Pending"
            saveBtn.setOnClickListener {
                updateNote()
            }
        }
    }
}