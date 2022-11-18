package com.example.tp3_hci.components.search

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.tp3_hci.R
import com.example.tp3_hci.data.SearchByItem
import com.example.tp3_hci.ui.theme.FitiBlue
import com.example.tp3_hci.ui.theme.FitiBlueText


// -----------------------------------------------------------------------
// modifier: Modifier sobre el Row
// onItemChange: Funcion a ejecutar al cambiar el tipo de busqueda
// -----------------------------------------------------------------------
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchByDropDown(
    modifier: Modifier = Modifier,
    itemSelected : SearchByItem,
    onItemChange : ((SearchByItem) -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = stringResource(id = itemSelected.stringId),
            onValueChange = { },
            singleLine = true,
            readOnly = true,
            label = {
                Text(
                    text = stringResource(id = R.string.search_by),
                    style = MaterialTheme.typography.caption,
                    color = FitiBlueText
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            textStyle = MaterialTheme.typography.body1,
            shape = MaterialTheme.shapes.small,
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
            onDismissRequest = {
                expanded = false
            }
        ) {
            SearchByItem::class.sealedSubclasses.forEach { selectedOption ->
                DropdownMenuItem(
                    onClick = {
                        if(onItemChange != null){
                            onItemChange(selectedOption.objectInstance as SearchByItem)
                        }
                        expanded = false
                    }
                ) {
                    Text(
                        text = stringResource(id = (selectedOption.objectInstance as SearchByItem).stringId),
                        style = MaterialTheme.typography.body1,
                        color = FitiBlueText
                    )
                }
            }
        }
    }
}


/*
@Preview(showBackground = true)
@Composable
fun SearchByDropDownPreview(){
    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        SearchByDropDown()
    }
}
 */


