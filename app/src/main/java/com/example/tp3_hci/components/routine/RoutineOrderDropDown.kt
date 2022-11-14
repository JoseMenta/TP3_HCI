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
import com.example.tp3_hci.data.OrderByItem
import com.example.tp3_hci.data.OrderTypeItem
import com.example.tp3_hci.ui.theme.FitiBlue
import com.example.tp3_hci.ui.theme.FitiBlueText
import com.example.tp3_hci.utilities.WindowInfo
import com.example.tp3_hci.utilities.rememberWindowInfo

data class DropDownWeight(
    val orderByWeight: Float,
    val orderTypeWeight: Float
)

private val roundBorderValue = 4.dp

// ------------------------------------------------------------------------------------
// modifier: Estilo a aplicar al componente
// onOrderByChange: Funcion a ejecutar al cambiar el estado del dropdown (recibe el nuevo item seleccionado)
// onOrderTypeChange: Funcion a ejecutar al cambiar el tipo de orden
// ------------------------------------------------------------------------------------
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoutineOrderDropDown(
    modifier: Modifier = Modifier,
    onOrderByChange: ((OrderByItem) -> Unit)? = null,
    onOrderTypeChange: ((OrderTypeItem) -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        var expanded by remember { mutableStateOf(false) }
        var selectedOptionText by remember {
            mutableStateOf(OrderByItem.Name as OrderByItem)
        }

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
                value = stringResource(id = selectedOptionText.stringId),
                onValueChange = {
                    if(onOrderByChange != null){
                        onOrderByChange(selectedOptionText)
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
                OrderByItem::class.sealedSubclasses.forEach { selectedOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOptionText = selectedOption.objectInstance as OrderByItem
                            expanded = false
                        }
                    ) {
                        Text(
                            text = stringResource(id = (selectedOption.objectInstance as OrderByItem).stringId),
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
    onOrderTypeChange: ((OrderTypeItem) -> Unit)? = null,
){

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember {
        mutableStateOf(OrderTypeItem.Descending as OrderTypeItem)
    }

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
            value = getOrderTypeString(orderType = selectedOptionText),
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
            OrderTypeItem::class.sealedSubclasses.forEach { selectedOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectedOption.objectInstance as OrderTypeItem
                        expanded = false
                    }
                ) {
                    Text(
                        text = getOrderTypeString(orderType = (selectedOption.objectInstance as OrderTypeItem)),
                        style = MaterialTheme.typography.body1,
                        color = FitiBlueText
                    )
                }
            }
        }

    }
}


@Composable
private fun getOrderTypeString(
    orderType: OrderTypeItem
): String{
    val windowInfo = rememberWindowInfo()

    if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact){
        return when(orderType){
            is OrderTypeItem.Descending -> stringResource(id = R.string.descending_abrev)
            else -> stringResource(id = R.string.ascending_abrev)
        }
    }

    return when(orderType){
        is OrderTypeItem.Descending -> stringResource(id = R.string.descending)
        else -> stringResource(id = R.string.ascending)
    }
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
    Column(
        modifier = Modifier.
                padding(horizontal = 20.dp)
    ){
        RoutineOrderDropDown()
    }

}