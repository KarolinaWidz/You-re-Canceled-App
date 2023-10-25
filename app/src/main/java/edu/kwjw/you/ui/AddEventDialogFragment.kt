package edu.kwjw.you.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import edu.kwjw.you.R
import edu.kwjw.you.data.repository.network.request.AddEventRequest
import edu.kwjw.you.databinding.FragmentAddEventDialogBinding
import edu.kwjw.you.ui.viewModel.AddEventDialogViewModel
import java.time.LocalDateTime

@AndroidEntryPoint
class AddEventDialogFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAddEventDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddEventDialogViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_event_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddEventDialogBinding.bind(view)
        binding.addButton.setOnClickListener {
            viewModel.addNewEvent(
                AddEventRequest(
                    1, "test event", LocalDateTime.now()
                )
            )
        }
    }
}