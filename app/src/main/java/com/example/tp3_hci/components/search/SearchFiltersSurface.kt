package com.example.tp3_hci.components.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.tp3_hci.R
import com.example.tp3_hci.data.*
import com.example.tp3_hci.ui.theme.FitiBlackText
import com.example.tp3_hci.ui.theme.TP3_HCITheme
import com.example.tp3_hci.utilities.WindowInfo
import com.example.tp3_hci.utilities.rememberWindowInfo


// -----------------------------------------------------------------------
// onSearchByItemChange: Funcion a ejecutar al cambiar el tipo de busqueda
// onFilterItemChange: Funcion a ejecutar al cambiar el valor del drop down
//                     Por el primer parametro pasa el tipo de filtro y por el segundo el nuevo valor
// -----------------------------------------------------------------------
@Composable
fun SearchFiltersSurface(
    onSearchByItemChange: ((SearchByItem)->Unit)? = null,
    onFilterItemChange: ((DropDownItem, DropDownItem)->Unit)? = null
) {
    val windowInfo = rememberWindowInfo()
    var heightSize by remember { mutableStateOf(0.25f) }
    if(windowInfo.screenHeightInfo is WindowInfo.WindowType.Compact){
        heightSize = 0.70f
    } else {
        heightSize = 0.25f
    }

    Box(
        modifier = Modifier
            .zIndex(1f)
            .fillMaxWidth()
            .fillMaxHeight()
    ){
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = 0.6f),
            content = {}
        )
        Surface(
            modifier = Modifier
                .zIndex(1f)
                .fillMaxWidth(1f)
                .fillMaxHeight(heightSize),
            shape = MaterialTheme.shapes.medium.copy(topStart = CornerSize(0.dp), topEnd = CornerSize(0.dp)),
            color = Color.White,
            contentColor = FitiBlackText,
            elevation = 20.dp,
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
            ) {
                SearchByDropDown(
                    onItemChange = onSearchByItemChange,
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    item {
                        DropDownChip(
                            placeholder = DropDownItem(R.string.difficulty),
                            items = DifficultyItem::class.sealedSubclasses.map {
                                    subclass -> subclass.objectInstance as DropDownItem
                            },
                            onItemChange = onFilterItemChange
                        )
                    }

                    item {
                        DropDownChip(
                            placeholder = DropDownItem(R.string.category),
                            items = CategoryItem::class.sealedSubclasses.map {
                                    subclass -> subclass.objectInstance as DropDownItem
                            },
                            onItemChange = onFilterItemChange
                        )
                    }

                    item {
                        DropDownChip(
                            placeholder = DropDownItem(R.string.rating),
                            items = RatingItem::class.sealedSubclasses.map {
                                    subclass -> subclass.objectInstance as DropDownItem
                            },
                            onItemChange = onFilterItemChange
                        )
                    }
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun SearchFiltersSurfacePreview(){
    TP3_HCITheme {
        SearchFiltersSurface()
    }
}