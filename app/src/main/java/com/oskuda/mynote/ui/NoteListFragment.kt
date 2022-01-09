package com.oskuda.mynote.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.oskuda.mynote.BaseApplication
import com.oskuda.mynote.R
import com.oskuda.mynote.databinding.FragmentNoteListBinding
import com.oskuda.mynote.ui.adapter.NoteListAdapter
import com.oskuda.mynote.ui.viewmodel.NoteViewModel
import com.oskuda.mynote.ui.viewmodel.NoteViewModelFactory

class NoteListFragment : Fragment() {

    private val viewModel: NoteViewModel by activityViewModels(){
        NoteViewModelFactory(
            (activity?.application as BaseApplication).database.noteDao()
        )
    }

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NoteListAdapter { note ->
            val action =
                NoteListFragmentDirections.actionNoteListFragmentToNoteDetailFragment(note.id)
            findNavController().navigate(action)
        }

        viewModel.allNote.observe(this.viewLifecycleOwner) { note ->
            note.let {
                adapter.submitList(it)
            }
        }

        binding.apply {
            recyclerView.adapter = adapter
            addNoteFab.setOnClickListener {
                findNavController().navigate(R.id.action_noteListFragment_to_addNoteFragment)
            }
        }
    }
}