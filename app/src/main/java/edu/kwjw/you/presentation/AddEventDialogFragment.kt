package edu.kwjw.you.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import edu.kwjw.you.R
import edu.kwjw.you.data.remote.dto.AddEventDto
import edu.kwjw.you.databinding.FragmentAddEventDialogBinding
import edu.kwjw.you.presentation.viewModel.AddEventDialogViewModel
import java.time.LocalDateTime

@AndroidEntryPoint
class AddEventDialogFragment : DialogFragment() {
    private var _binding: FragmentAddEventDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddEventDialogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Theme_App_FullScreenDialog)
    }

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
        configureDialog()
    }

    private fun configureDialog() {
        binding.toolbar.setNavigationOnClickListener { dismiss() }
        binding.toolbar.setTitle(R.string.add_new_event)
        binding.toolbar.inflateMenu(R.menu.toolbar_menu)
        binding.toolbar.setOnMenuItemClickListener {
            viewModel.addNewEvent(
                AddEventDto(
                    1, "test event", LocalDateTime.now()
                )
            )
            dismiss()
            true
        }
        binding.eventDateEditText.setOnClickListener { configureDatePicker() }
    }

    private fun configureDatePicker() {
        val picker = MaterialDatePicker.Builder
            .datePicker()
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .build()

        picker.show(parentFragmentManager, DATE_PICKER_TAG)
    }

    companion object {
        const val DATE_PICKER_TAG = "DatePicker"
    }
}