package com.example.structure.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.structure.R
import com.example.structure.databinding.CustomFooterBinding

class CustomFooter @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr) {

    var binding: CustomFooterBinding = CustomFooterBinding.bind(LayoutInflater.from(context).inflate(
        R.layout.custom_footer, this))

    companion object { var selectedPosition: Int = 0 }

    init { changeBackground(selectedPosition) }

    fun changeBackground(position: Int) {
        selectedPosition = position

        binding.ivMap.setImageDrawable(if (selectedPosition == 0) ContextCompat.getDrawable(context, R.drawable.ic_map_fill) else ContextCompat.getDrawable(context, R.drawable.ic_map))
        binding.ivHistory.setImageDrawable(if (selectedPosition == 1) ContextCompat.getDrawable(context, R.drawable.ic_history_fill) else ContextCompat.getDrawable(context, R.drawable.ic_history))
        binding.ivAccount.setImageDrawable(if (selectedPosition == 2) ContextCompat.getDrawable(context, R.drawable.ic_account_fill) else ContextCompat.getDrawable(context, R.drawable.ic_account))

        binding.tvMap.setTextColor(if (selectedPosition == 0) ContextCompat.getColor(context, R.color.colorPrimary) else ContextCompat.getColor(context, R.color.colorText))
        binding.tvHistory.setTextColor(if (selectedPosition == 1) ContextCompat.getColor(context, R.color.colorPrimary) else ContextCompat.getColor(context, R.color.colorText))
        binding.tvAccount.setTextColor(if (selectedPosition == 2) ContextCompat.getColor(context, R.color.colorPrimary) else ContextCompat.getColor(context, R.color.colorText))

    }

}