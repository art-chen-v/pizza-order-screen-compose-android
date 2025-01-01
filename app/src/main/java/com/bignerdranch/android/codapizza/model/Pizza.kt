package com.bignerdranch.android.codapizza.model

import android.os.Parcelable
import com.bignerdranch.android.codapizza.model.ToppingPlacement.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pizza(
    val toppings: Map<Topping, ToppingPlacement> = emptyMap(),
    var size: PizzaSize = PizzaSize.Large
) : Parcelable {
    val price: Double
        get() = 9.99 + toppings.asSequence()
            .sumOf { (_, toppingPlacement) ->
                when (toppingPlacement) {
                    Left, Right -> 0.5
                    All -> 1.0
                }
            } + when (size) {
            PizzaSize.Large -> 1.0
            PizzaSize.Medium -> 0.0
            PizzaSize.Small -> -1.0
        }

    fun withTopping(topping: Topping, placement: ToppingPlacement?): Pizza {
        return copy(
            toppings = if (placement == null) {
                toppings - topping
            } else {
                toppings + (topping to placement)
            }
        )
    }

    fun changeSize(size: PizzaSize): Pizza {
        return copy(
            size = size
        )
    }
}