package edu.kwjw.you.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import edu.kwjw.you.databinding.FragmentEventListBinding


class EventListFragment : Fragment(edu.kwjw.you.R.layout.fragment_event_list) {

    private var _binding: FragmentEventListBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEventListBinding.bind(view)
        binding.fab.setOnClickListener {
            val bottomSheetFragment = AddEventDialogFragment()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}