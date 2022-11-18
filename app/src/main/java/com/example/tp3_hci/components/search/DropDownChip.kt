package com.example.tp3_hci.components.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tp3_hci.R
import com.example.tp3_hci.data.DropDownItem
import com.example.tp3_hci.ui.theme.FitiBlackText


// -----------------------------------------------------------------------
// placeholder: Texto identificador de items del drop down
// items: Lista de items
// modifier: Modifier sobre el Row
// onItemChange: Funcion a ejecutar al cambiar el valor del drop down
//               Por el primer parametro pasa el placeholder y por el segundo el nuevo valor
// -----------------------------------------------------------------------
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DropDownChip(
    placeholder: DropDownItem,
    items: List<DropDownItem>,
    modifier: Modifier = Modifier,
    itemSelected: DropDownItem?,
    onItemChange: ((DropDownItem?) -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(0.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ){
            AssistChip(
                onClick = {
                    expanded = true
                },
                label = {
                    Text(
                        text = itemSelected?.getString() ?: placeholder.getString(),
                        style = MaterialTheme.typography.h5
                    )
                },
                trailingIcon = {
                    if(itemSelected != null){
                        IconButton(
                            onClick = {
                                if (onItemChange != null) {
                                    onItemChange(null)
                                }
                                expanded = false
                            },
                            modifier = Modifier.padding(0.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(id = R.string.delete_filter, placeholder.getString()),
                                tint = FitiBlackText
                            )
                        }
                    } else {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded,
                            onIconClick = {
                                expanded = !expanded
                            }
                        )
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = Color.White,
                    labelColor = FitiBlackText,
                    trailingIconContentColor = FitiBlackText,
                    disabledTrailingIconContentColor = FitiBlackText,
                    disabledContainerColor = Color.White,
                    leadingIconContentColor = FitiBlackText,
                    disabledLeadingIconContentColor = FitiBlackText
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                items.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            if (onItemChange != null) {
                                onItemChange(option)
                            }
                            expanded = false
                        }
                    ) {
                        Text(
                            text = option.getString(),
                            style = MaterialTheme.typography.h5,
                            color = FitiBlackText
                        )
                    }
                }
            }
        }
    }
}


/*
@Preview(showBackground = true)
@Composable
fun DropDownChipPreview(){
    TP3_HCITheme {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            item {
                DropDownChip(
                    placeholder = DropDownItem(R.string.difficulty),
                    items = DifficultyItem::class.sealedSubclasses.map {
                        subclass -> subclass.objectInstance as DropDownItem
                    }
                )
            }

            item {
                DropDownChip(
                    placeholder = DropDownItem(R.string.category),
                    items = CategoryItem::class.sealedSubclasses.map {
                            subclass -> subclass.objectInstance as DropDownItem
                    }
                )
            }

            item {
                DropDownChip(
                    placeholder = DropDownItem(R.string.rating),
                    items = RatingItem::class.sealedSubclasses.map {
                            subclass -> subclass.objectInstance as DropDownItem
                    }
                )
            }
        }
    }
}
*/