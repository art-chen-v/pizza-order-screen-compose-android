package com.bignerdranch.android.codapizza.ui

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.codapizza.model.Pizza
import com.bignerdranch.android.codapizza.model.PizzaSize

@Composable
fun PizzaSizeDropdownMenu(
    expanded: Boolean,
    onSizeChange: (PizzaSize) -> Unit,
    onDismissRequest: () -> Unit
) {
    DropdownMenu(expanded = expanded,
        offset = DpOffset(10.dp, 0.dp),
        onDismissRequest = onDismissRequest) {
        PizzaSize.entries.forEach {
            DropdownMenuItem(
                onClick = {
                    onSizeChange(it)
                    onDismissRequest()
                }
            ) {
                Text(stringResource(id = it.size))
            }
        }
    }
}