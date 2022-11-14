package com.example.tp3_hci.components.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import com.example.tp3_hci.R
import com.example.tp3_hci.ui.theme.*

@Composable
fun EditProfileDataPopUp(
    modifier: Modifier = Modifier,
    popUpTitle: String,
    popUpText: String,
    content: (@Composable ()->Unit),
    onConfirmButton: (()->Unit)? = null,
    onClosePopUp: ()->Unit
) {
    Box(
        modifier = Modifier
            .zIndex(1f)
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ){
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = 0.6f),
            content = {}
        )

        Popup(
            alignment = Alignment.Center,
            onDismissRequest = onClosePopUp,
            properties = PopupProperties(
                focusable = true
            )
        ){
            Surface(
                shape = MaterialTheme.shapes.medium,
                modifier = modifier,
                color = Color.White,
                contentColor = FitiBlackText,
                elevation = 20.dp,
                border = BorderStroke(
                    width = 2.dp,
                    color = Color.Black
                )
            ) {
                val componentsModifier = Modifier
                    .padding(horizontal = 15.dp)
                    .padding(bottom = 5.dp)

                Column(
                    modifier = Modifier.padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = popUpTitle,
                        style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Bold),
                        color = FitiBlackText,
                        modifier = componentsModifier.fillMaxWidth()
                    )

                    Divider(
                        color = Color.Black
                    )

                    Text(
                        text = popUpText,
                        style = MaterialTheme.typography.h4,
                        color = FitiBlackText,
                        modifier = componentsModifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    )

                    content()

                    Divider(
                        color = Color.Black
                    )

                    Row(
                        modifier = componentsModifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        PopUpButton(
                            text = "Cancelar",
                            icon = Icons.Filled.Delete,
                            iconContentDescription = "Cancelar",
                            buttonColor = FitiBlue,
                            modifier = Modifier
                                .padding(horizontal = 5.dp),
                            action = onClosePopUp
                        )

                        PopUpButton(
                            text = "Guardar",
                            icon = Icons.Filled.Check,
                            iconContentDescription = "Guardar",
                            buttonColor = FitiGreenButton,
                            modifier = Modifier
                                .padding(horizontal = 5.dp),
                            action = onConfirmButton
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun RegularEditProfileDataPopUpContent(
    input: String,
    placeholder: String,
    onInputChange: (String)->Unit
){
    OutlinedTextField(
        value = input,
        onValueChange = {
            onInputChange(it)
        },
        modifier = Modifier.padding(horizontal = 15.dp),
        readOnly = false,
        singleLine = true,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.h5,
                color = FitiGreyInputText
            )
        },
        textStyle = MaterialTheme.typography.h5,
        shape = MaterialTheme.shapes.medium,
        trailingIcon = {
            if(input.isNotEmpty()){
                IconButton(
                    onClick = {
                        onInputChange("")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = R.string.delete_text),
                        tint = FitiWhiteText,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = FitiWhiteText,
            disabledTextColor = FitiWhiteText,
            cursorColor = FitiWhiteText,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledTrailingIconColor = FitiWhiteText,
            placeholderColor = FitiWhiteText.copy(alpha = 0.6f),
            disabledPlaceholderColor = FitiWhiteText.copy(alpha = 0.6f)
        )
    )
}


@Composable
private fun PopUpButton(
    text : String,
    icon: ImageVector,
    iconContentDescription: String,
    buttonColor: Color,
    modifier: Modifier = Modifier,
    action : (()->Unit)? = null,
){
    Button(
        onClick = {
            if(action != null){
                action()
            }
        },
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonColor,
            contentColor = FitiWhiteText
        ),
        contentPadding = PaddingValues(horizontal = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = iconContentDescription,
                tint = FitiWhiteText,
                modifier = Modifier.size(15.dp)
            )

            Text(
                text = text,
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                ),
                color = FitiWhiteText
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun EditProfileDataPopUpPreview(){
    TP3_HCITheme {
        Column {
            EditProfileDataPopUp(
                popUpTitle = "Cambiar nombre",
                popUpText = "Introduzca su nombre y apellido",
                content = {
                    var input by remember { mutableStateOf("") }

                    OutlinedTextField(
                        value = input,
                        onValueChange = {
                            input = it
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                onClosePopUp = {},
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp),
                onConfirmButton = {}
            )
        }

    }
}