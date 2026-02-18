package com.example.pepoasistant.presentation

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.pepoasistant.R
import com.example.pepoasistant.databinding.BottomSheetMonthYearBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetMonthYear(
    private val initialMonth: Int,
    private val initialYear: Int,
    private val onSelected: (Int, Int) -> Unit
) : BottomSheetDialogFragment() {

    private var selectedMonth = initialMonth
    private var selectedYear = initialYear
    private lateinit var months: Array<String>
    private var _binding: BottomSheetMonthYearBinding? = null

    val binding: BottomSheetMonthYearBinding
        get() = _binding ?: throw RuntimeException("BottomSheetMonthYearBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        months = resources.getStringArray(R.array.months_fi)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetMonthYearBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateUI()

        binding.btnNextYear.setOnClickListener {
            selectedYear++
            updateUI()
        }

        binding.btnPrevYear.setOnClickListener {
            selectedYear--
            updateUI()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnConfim.setOnClickListener {
            onSelected(selectedYear, selectedMonth)
            dismiss()
        }

    }

    private fun updateUI() {
        binding.tvYear.text = selectedYear.toString()
        binding.monthsGrid.removeAllViews()

        for (i in 1..12) {
            binding.monthsGrid.addView(createMonthView(i, months[i - 1]))
        }
    }

    private fun createMonthView(index: Int, label: String): TextView {
        return TextView(requireContext()).apply {
            text = label
            textSize = 20f
            setPadding(8, 8, 8, 8)

            val isSelected = index == selectedMonth

            setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    if (isSelected) R.color.black else R.color.white
                )
            )

            background = ContextCompat.getDrawable(
                requireContext(),
                if (isSelected) R.drawable.bg_month_selected
                else R.drawable.bg_month_dialog_rounded
            )

            layoutParams = GridLayout.LayoutParams().apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                setMargins(8, 8, 8, 8)
            }

            gravity = Gravity.CENTER

            setOnClickListener {
                selectedMonth = index
                updateUI()

            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}