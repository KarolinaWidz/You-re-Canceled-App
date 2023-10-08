package edu.kwjw.you.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import edu.kwjw.you.databinding.FragmentEventListBinding
import edu.kwjw.you.ui.recyclerView.adapter.EventListAdapter
import edu.kwjw.you.ui.viewModel.EventListViewModel

@AndroidEntryPoint
class EventListFragment : Fragment(edu.kwjw.you.R.layout.fragment_event_list) {

    private var _binding: FragmentEventListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: EventListAdapter
    private val viewModel: EventListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEventListBinding.bind(view)
        initList()
        binding.fab.setOnClickListener {
            val bottomSheetFragment = AddEventDialogFragment()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
        viewModel.getEventsForUser(1)
    }

    private fun initList() {
        adapter = EventListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}