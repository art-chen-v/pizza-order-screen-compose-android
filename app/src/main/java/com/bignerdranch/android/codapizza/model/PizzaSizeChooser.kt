package com.bignerdranch.android.codapizza.model

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.codapizza.ui.PizzaSizeDropdownMenu

@Composable
fun PizzaSizeChooser(
    pizza: Pizza,
    onEditPizza: (Pizza) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { expanded = true }
        ) {
            Text(
                text = stringResource(pizza.size.size)
            )
        }

        PizzaSizeDropdownMenu(
            expanded = expanded,
            onSizeChange = { size ->
                onEditPizza(pizza.changeSize(size))
            },
            onDismissRequest = { expanded = false }
        )

    }
}