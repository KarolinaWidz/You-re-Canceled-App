package edu.kwjw.you.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import edu.kwjw.you.R
import edu.kwjw.you.databinding.FragmentEventListBinding
import edu.kwjw.you.presentation.adapter.EventListAdapter
import edu.kwjw.you.presentation.uiState.EventsForUserHolder
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
        binding.fab.setOnClickListener { showAddEventDialog() }
        viewModel.getEvents(1)
        viewModel.addEventApiResult.observe(viewLifecycleOwner) { viewModel.getEvents(1) }
        //todo: add snack bar for results
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
            EventsForUserHolder.EventState.LOADING -> changeLayout(View.GONE, View.VISIBLE, null)
            EventsForUserHolder.EventState.SUCCESS -> changeLayout(View.GONE, View.GONE, null)
            EventsForUserHolder.EventState.ERROR -> changeLayout(
                View.VISIBLE,
                View.GONE,
                R.drawable.ic_error
            )
        }
    }

    private fun changeLayout(visibility: Int, progressVisibility: Int, drawable: Int?) {
        drawable?.let { binding.statusImage.setImageResource(drawable) }
        binding.statusImage.visibility = visibility
        binding.progressCircular.visibility = progressVisibility
    }

    private fun showAddEventDialog() {
        val dialog = AddEventDialogFragment()
        dialog.addEventListener =
            { data ->
                if (viewModel.isNewEventValid(data)) {
                    viewModel.addNewEvent(data)
                    dialog.dismiss()
                }
                //todo: add snack bar if invalid
            }
        dialog.show(parentFragmentManager, ADD_EVENT_TAG)
    }

    companion object {
        const val ADD_EVENT_TAG = "AddEventDialog"
    }
}
