package com.example.pepoasistant.presentation

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
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

    private var _binding: BottomSheetMonthYearBinding? = null

    val binding: BottomSheetMonthYearBinding
        get() = _binding ?: throw RuntimeException("BottomsheetMonthYearBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetMonthYearBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val months = listOf(
            "tammi", "helmi", "maalis", "huhti", "touko", "kesä",
            "heinä", "elo", "syys", "loka", "marras", "joulu"
        )

        fun updateUI() {
            binding.tvYear.text = selectedYear.toString()
            binding.monthsGrid.removeAllViews()

            for (i in 1..12) {
                val tv = TextView(requireContext()).apply {
                    text = months[i - 1]
                    textSize = 20f
                    setTextColor(ContextCompat.getColor(requireContext(),if (i == selectedMonth)
                    R.color.black else R.color.white))

                    setPadding(8, 8, 8, 8)

                    val params = GridLayout.LayoutParams().apply {
                        width = 0
                        height = GridLayout.LayoutParams.WRAP_CONTENT
                        columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                        setMargins(8, 8, 8, 8)
                    }

                    layoutParams = params
                    gravity = Gravity.CENTER
                    background = ContextCompat.getDrawable(
                        requireContext(),
                        if (i == selectedMonth)
                            R.drawable.bg_month_selected
                        else R.drawable.bg_month_dialog_rounded
                    )
                    setOnClickListener {
                        selectedMonth = i
                        updateUI()
                    }
                }
                binding.monthsGrid.addView(tv)
            }
        }

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

        updateUI()
    }
}