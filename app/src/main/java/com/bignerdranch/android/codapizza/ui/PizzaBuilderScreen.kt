package com.bignerdranch.android.codapizza.ui

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.codapizza.R
import com.bignerdranch.android.codapizza.model.Pizza
import com.bignerdranch.android.codapizza.model.PizzaSizeChooser
import com.bignerdranch.android.codapizza.model.Topping
import java.text.NumberFormat

private const val TAG = "PizzaBuilderScreen"

@Preview
@Composable
fun PizzaBuilderScreen(
    modifier: Modifier = Modifier
) {
    var pizza by rememberSaveable { mutableStateOf(Pizza()) }
    var toRotatePizza by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) }
            )
        },
        content = { innerPadding ->
            Column(modifier = modifier.padding(innerPadding)) {
                ToppingsList(
                    pizza = pizza,
                    onEditPizza = { pizza = it },
                    toRotate = toRotatePizza,
                    rotationFinished = { toRotatePizza = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = true)
                )

                OrderButton(
                    pizza = pizza,
                    rotate = { toRotatePizza = true },
                    toRotatePizza = toRotatePizza,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    )
}


@Composable
private fun ToppingsList(
    pizza: Pizza,
    onEditPizza: (Pizza) -> Unit,
    toRotate: Boolean,
    rotationFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    var toppingBeingAdded by rememberSaveable { mutableStateOf<Topping?>(null) }

    toppingBeingAdded?.let { topping ->
        ToppingPlacementDialog(
            topping = topping,
            onSetToppingPlacement = { placement ->
                onEditPizza(pizza.withTopping(topping, placement))
            },
            onDismissRequest = {
                toppingBeingAdded = null
            }
        )
    }



    LazyColumn(
        modifier = modifier
    ) {
        item {

            var isFinished by remember { mutableStateOf(false) }

            Crossfade(targetState = pizza, label = "", animationSpec = tween(2000)) { pizza ->

                val rotationDegree: Float by animateFloatAsState(
                    if (toRotate) {
                        isFinished = false
                        360f
                    }
                    else {
                        0f
                    },
                    tween(1500),
                    label = ""
                ) {
                    isFinished = true
                    rotationFinished()
                }

                PizzaHeroImage(
                    pizza = pizza,
                    modifier = Modifier
                        .padding(16.dp)
                        .rotate(if (isFinished) 0f else rotationDegree)
                )
            }
        }

        item {
            PizzaSizeChooser(
                pizza = pizza,
                onEditPizza = onEditPizza,
                modifier = Modifier
                    .wrapContentSize(Alignment.TopStart)
            )
        }
        items(Topping.entries) { topping ->
            ToppingCell(
                topping = topping,
                placement = pizza.toppings[topping],
                onClickTopping = {
                    toppingBeingAdded = topping
                }
            )
        }
    }
}

@Composable
private fun OrderButton(
    pizza: Pizza,
    toRotatePizza: Boolean,
    rotate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var enabled by remember{ mutableStateOf(true)}

    Button(
        modifier = modifier,
        enabled = !toRotatePizza,
        onClick = {
            rotate()
            enabled = false
            Toast.makeText(context, R.string.order_placed_toast, Toast.LENGTH_LONG)
                .show()
        }
    ) {
        val currencyFormatter = remember { NumberFormat.getCurrencyInstance() }
        val price = currencyFormatter.format(pizza.price)

        Text(
            text = stringResource(R.string.place_order_button, price)
                .toUpperCase(Locale.current)
        )
    }
}