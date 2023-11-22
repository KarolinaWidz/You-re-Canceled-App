package edu.kwjw.you.presentation


import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import edu.kwjw.you.R
import edu.kwjw.you.databinding.FragmentAddEventDialogBinding
import edu.kwjw.you.domain.model.PlainEventData
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class AddEventDialogFragment : DialogFragment() {
    private var _binding: FragmentAddEventDialogBinding? = null
    private val binding get() = _binding!!

    private val dateFormatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    internal lateinit var addEventListener: (PlainEventData) -> Unit

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
        binding.toolbar.setOnMenuItemClickListener { createEvent() }
        binding.eventDateEditText.setOnClickListener { configureDatePicker() }
        binding.eventTimeEditText.setOnClickListener { configureTimePicker() }
    }

    private fun createEvent(): Boolean {
        val name = getStringFromTextInput(binding.eventNameTextInput)
        val date = getStringFromTextInput(binding.eventDateTextInput)
        val time = getStringFromTextInput(binding.eventTimeTextInput)
        val eventToAdd = PlainEventData(name, date, time)
        addEventListener(eventToAdd)
        return true
    }

    private fun getStringFromTextInput(textInput: TextInputLayout): String {
        return textInput.editText?.text.toString()
    }

    private fun configureDatePicker() {
        val picker = MaterialDatePicker.Builder
            .datePicker()
            .setTextInputFormat(dateFormatter)
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .setCalendarConstraints(futureDateConstraint())
            .build()

        picker.show(parentFragmentManager, DATE_PICKER_TAG)

        picker.addOnPositiveButtonClickListener {
            val date = dateFormatter.format(it)
            binding.eventDateEditText.setText(date)
        }
    }

    private fun futureDateConstraint(): CalendarConstraints {
        return CalendarConstraints.Builder()
            .setStart(getStartDate())
            .setValidator(DateValidatorPointForward.now())
            .build()
    }

    private fun getStartDate(): Long {
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(UTC_TIME_ZONE))
        calendar.timeInMillis = today
        return calendar.timeInMillis
    }

    private fun configureTimePicker() {
        val picker = MaterialTimePicker.Builder()
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .build()

        picker.show(parentFragmentManager, TIME_PICKER_TAG)

        picker.addOnPositiveButtonClickListener {
            val time = String.format(TIME_FORMAT, picker.hour, picker.minute)
            binding.eventTimeEditText.setText(time)
        }
    }

    companion object {
        const val DATE_PICKER_TAG = "DatePicker"
        const val TIME_PICKER_TAG = "TimePicker"
        const val UTC_TIME_ZONE = "UTC"
        const val DATE_FORMAT = "dd/MM/yyyy"
        const val TIME_FORMAT = "%02d:%02d"
    }
}