package com.example.tp3_hci.components.routine

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp3_hci.R
import com.example.tp3_hci.ui.theme.FitiBlue
import com.example.tp3_hci.ui.theme.FitiBlueText
import com.example.tp3_hci.utilities.WindowInfo
import com.example.tp3_hci.utilities.rememberWindowInfo

data class DropDownItem(
    val value: String
)

data class DropDownWeight(
    val orderByWeight: Float,
    val orderTypeWeight: Float
)

private val roundBorderValue = 4.dp

// ------------------------------------------------------------------------------------
// modifier: Estilo a aplicar al componente
// items: Lista de items a utilizar en el dropdown
// default: Valor inicial a elegir en el dropdown
// onItemChange: Funcion a ejecutar al cambiar el estado del dropdown (recibe el nuevo item seleccionado)
// onOrderTypeChange: Funcion a ejecutar al cambiar el tipo de orden
// ------------------------------------------------------------------------------------
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoutineOrderDropDown(
    modifier: Modifier = Modifier,
    items: List<DropDownItem>,
    default: Int = 0,
    onItemChange: ((DropDownItem) -> Unit)? = null,
    onOrderTypeChange: ((DropDownItem) -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        var expanded by remember { mutableStateOf(false) }
        var selectedOptionText by remember { mutableStateOf(items[default]) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
            modifier = Modifier.
                weight(getDropDownWeight().orderByWeight)
        ) {
            OutlinedTextField(
                singleLine = true,
                readOnly = true,
                value = selectedOptionText.value,
                onValueChange = {
                    if(onItemChange != null){
                        onItemChange(selectedOptionText)
                    }
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.order_by),
                        style = MaterialTheme.typography.caption,
                        color = FitiBlueText
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                textStyle = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                shape = RoundedCornerShape(topStart = roundBorderValue, bottomStart = roundBorderValue),
                modifier = Modifier.fillMaxWidth(),
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    textColor = FitiBlueText,
                    disabledTextColor = FitiBlueText,
                    backgroundColor = Color.White,
                    placeholderColor = FitiBlue,
                    disabledPlaceholderColor = FitiBlue,
                    cursorColor = FitiBlue,
                    trailingIconColor = FitiBlue,
                    disabledTrailingIconColor = FitiBlue,
                    focusedIndicatorColor = FitiBlue,
                    disabledIndicatorColor = FitiBlue,
                    unfocusedIndicatorColor = FitiBlue,
                    focusedTrailingIconColor = FitiBlue,
                    focusedLabelColor = FitiBlueText,
                    unfocusedLabelColor = FitiBlueText,
                    disabledLabelColor = FitiBlueText
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                items.forEach { selectedOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOptionText = selectedOption
                            expanded = false
                        }
                    ) {
                        Text(
                            text = selectedOption.value,
                            style = MaterialTheme.typography.body1,
                            color = FitiBlueText
                        )
                    }
                }
            }

        }

        OrderTypeDropDown(
            onOrderTypeChange = onOrderTypeChange,
            modifier = Modifier.weight(getDropDownWeight().orderTypeWeight)
        )
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun OrderTypeDropDown(
    modifier: Modifier = Modifier,
    onOrderTypeChange: ((DropDownItem) -> Unit)? = null,
){
    val items = getOrderTypeStrings()

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(items[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = modifier
    ) {
        OutlinedTextField(
            singleLine = true,
            readOnly = true,
            value = selectedOptionText.value,
            onValueChange = {
                if(onOrderTypeChange != null){
                    onOrderTypeChange(selectedOptionText)
                }
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            textStyle = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
            shape = RoundedCornerShape(topEnd = roundBorderValue, bottomEnd = roundBorderValue),
            modifier = Modifier.fillMaxWidth(),
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                textColor = FitiBlueText,
                disabledTextColor = FitiBlueText,
                backgroundColor = Color.White,
                placeholderColor = FitiBlue,
                disabledPlaceholderColor = FitiBlue,
                cursorColor = FitiBlue,
                trailingIconColor = FitiBlue,
                disabledTrailingIconColor = FitiBlue,
                focusedIndicatorColor = FitiBlue,
                disabledIndicatorColor = FitiBlue,
                unfocusedIndicatorColor = FitiBlue,
                focusedTrailingIconColor = FitiBlue,
                focusedLabelColor = FitiBlueText,
                unfocusedLabelColor = FitiBlueText,
                disabledLabelColor = FitiBlueText
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEach { selectedOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectedOption
                        expanded = false
                    }
                ) {
                    Text(
                        text = selectedOption.value,
                        style = MaterialTheme.typography.body1,
                        color = FitiBlueText
                    )
                }
            }
        }

    }
}


@Composable
private fun getOrderTypeStrings(): List<DropDownItem>{
    val windowInfo = rememberWindowInfo()

    if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact){
        return listOf(
            DropDownItem(stringResource(id = R.string.descending_abrev)),
            DropDownItem(stringResource(id = R.string.ascending_abrev))
        )
    }

    return listOf(
        DropDownItem(stringResource(id = R.string.descending)),
        DropDownItem(stringResource(id = R.string.ascending))
    )
}


@Composable
private fun getDropDownWeight(): DropDownWeight{
    val windowInfo = rememberWindowInfo()
    if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact){
        return DropDownWeight(0.6f, 0.4f)
    }
    return DropDownWeight(0.5f, 0.5f)
}



@Preview(showBackground = true)
@Composable
fun DropDownPreview(){
    val items = listOf(
        DropDownItem(stringResource(id = R.string.name)),
        DropDownItem(stringResource(id = R.string.creation_date)),
        DropDownItem(stringResource(id = R.string.rating)),
        DropDownItem(stringResource(id = R.string.difficulty)),
        DropDownItem(stringResource(id = R.string.category))
    )
    Column(
        modifier = Modifier.
                padding(horizontal = 20.dp)
    ){
        RoutineOrderDropDown(items = items)
    }

}