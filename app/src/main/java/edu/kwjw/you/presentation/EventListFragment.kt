package edu.kwjw.you.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import edu.kwjw.you.R
import edu.kwjw.you.domain.model.Event
import edu.kwjw.you.databinding.FragmentEventListBinding
import edu.kwjw.you.presentation.adapter.EventListAdapter
import edu.kwjw.you.presentation.viewModel.EventListViewModel
import edu.kwjw.you.util.Result

@AndroidEntryPoint
class EventListFragment : Fragment(R.layout.fragment_event_list) {

    private var _binding: FragmentEventListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: EventListAdapter
    private val viewModel: EventListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEventListBinding.bind(view)
        initList()
        binding.fab.setOnClickListener { navigateToAddEventDialog() }
        viewModel.getEventsForUser(1)

    }

    private fun navigateToAddEventDialog() {
        findNavController().navigate(EventListFragmentDirections.actionEventListFragmentToAddEventDialogFragment())
    }

    private fun initList() {
        adapter = EventListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        viewModel.events.observe(viewLifecycleOwner) { result -> setDataForList(result)}
    }
    private fun setDataForList(result: Result<List<Event>>){
        when (result) {
            is Result.HttpError -> setEventList(listOf(), View.VISIBLE, R.drawable.ic_error)
            Result.Loading -> setEventList(listOf(), View.VISIBLE, R.drawable.ic_wait)
            is Result.NetworkError -> setEventList(listOf(), View.VISIBLE, R.drawable.ic_error)
            is Result.Success -> setEventList(result.data, View.GONE, null)
        }
    }

    private fun setEventList(eventList: List<Event>, visibility: Int, drawable: Int?) {
        adapter.submitList(eventList)
        drawable?.let { binding.statusImage.setImageResource(drawable) }
        binding.statusImage.visibility = visibility
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}