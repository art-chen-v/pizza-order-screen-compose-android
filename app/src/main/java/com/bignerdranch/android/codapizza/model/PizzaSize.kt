package com.bignerdranch.android.codapizza.model

import androidx.annotation.StringRes
import com.bignerdranch.android.codapizza.R

enum class PizzaSize(
    @StringRes val size: Int
) {
    Small(R.string.size_small),
    Medium(R.string.size_medium),
    Large(R.string.size_large)
}