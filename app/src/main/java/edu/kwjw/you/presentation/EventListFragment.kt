package edu.kwjw.you.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
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
    private lateinit var dialog: AddEventDialogFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEventListBinding.bind(view)
        initList()
        binding.fab.setOnClickListener { showAddEventDialog() }
        viewModel.getEvents(1)
        viewModel.uiEvent.observe(viewLifecycleOwner) { handleEvent(it) }
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
        dialog = AddEventDialogFragment()
        dialog.addEventListener = { data -> viewModel.addNewEvent(data) }
        dialog.show(parentFragmentManager, ADD_EVENT_TAG)
    }


    private fun handleEvent(event: UiEvent) {
        when (event) {
            is UiEvent.ShowDialogSnackbar -> Snackbar.make(
                dialog.requireView(),
                event.message,
                Snackbar.LENGTH_SHORT
            ).show()

            is UiEvent.ShowListSnackbar -> Snackbar.make(
                requireView(),
                event.message,
                Snackbar.LENGTH_SHORT
            ).show()

            UiEvent.DismissDialog -> dismissDialogIfSuccess()
        }
    }

    private fun dismissDialogIfSuccess() {
        viewModel.getEvents(1)
        dialog.dismiss()
    }

    companion object {
        const val ADD_EVENT_TAG = "AddEventDialog"
    }
}
