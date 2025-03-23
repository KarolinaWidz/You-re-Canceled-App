package edu.kwjw.you.presentation

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import edu.kwjw.you.R
import edu.kwjw.you.databinding.FragmentEventListBinding
import edu.kwjw.you.presentation.ui.eventlist.EventItem
import edu.kwjw.you.presentation.ui.eventlist.EventList
import edu.kwjw.you.presentation.ui.theme.AppTheme
import edu.kwjw.you.presentation.uiState.UiEvent
import edu.kwjw.you.presentation.viewModel.EventListViewModel
import kotlinx.collections.immutable.toImmutableList


@AndroidEntryPoint
class EventListFragment : Fragment(R.layout.fragment_event_list) {

    private var _binding: FragmentEventListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EventListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEventListBinding.bind(view)
        binding.eventList.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppTheme {
                    EventList()
                }
            }
        }
        viewModel.getEvents(1)
        viewModel.uiEvent.observe(viewLifecycleOwner) { handleEvent(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    private fun handleEvent(event: UiEvent) {
        when (event) {
            is UiEvent.ShowDialogSnackbar -> {}

            is UiEvent.ShowListSnackbar -> Snackbar.make(
                requireView(),
                event.message,
                Snackbar.LENGTH_SHORT
            ).show()

            is UiEvent.EventListUpdate -> setDataForList(event)
            UiEvent.DismissDialog -> dismissDialogIfSuccess()
        }
    }

    private fun setDataForList(event: UiEvent.EventListUpdate) {
        binding.eventList.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppTheme {
                    EventList(events = event.data.map {
                        EventItem(
                            name = it.name,
                            date = it.date,
                            status = it.status
                        )
                    }.toImmutableList())
                }
            }
        }
        when (event.state) {
            UiEvent.EventListUpdate.EventState.LOADING -> changeLayout(
                View.GONE,
                View.VISIBLE,
                null
            )

            UiEvent.EventListUpdate.EventState.SUCCESS -> changeLayout(View.GONE, View.GONE, null)
            UiEvent.EventListUpdate.EventState.ERROR -> changeLayout(
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

    private fun dismissDialogIfSuccess() {
        viewModel.getEvents(1)
    }
}
