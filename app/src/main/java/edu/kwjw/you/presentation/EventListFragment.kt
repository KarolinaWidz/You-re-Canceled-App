package edu.kwjw.you.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import edu.kwjw.you.R
import edu.kwjw.you.databinding.FragmentEventListBinding
import edu.kwjw.you.presentation.adapter.EventListAdapter
import edu.kwjw.you.presentation.viewModel.EventListViewModel

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initList() {
        adapter = EventListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        viewModel.eventsState.observe(viewLifecycleOwner) { result -> setDataForList(result) }
    }

    private fun setDataForList(eventState: EventsForUserHolder) {
        adapter.submitList(eventState.data)
        when (eventState.state) {
            EventsForUserHolder.EventState.LOADING -> changeLayout(View.VISIBLE, R.drawable.ic_wait)
            EventsForUserHolder.EventState.SUCCESS -> changeLayout(View.GONE, null)
            EventsForUserHolder.EventState.ERROR -> changeLayout(View.VISIBLE, R.drawable.ic_error)
        }
    }

    private fun changeLayout(visibility: Int, drawable: Int?) {
        drawable?.let { binding.statusImage.setImageResource(drawable) }
        binding.statusImage.visibility = visibility
    }

    private fun navigateToAddEventDialog() {
        findNavController().navigate(EventListFragmentDirections.actionEventListFragmentToAddEventDialogFragment())
    }
}