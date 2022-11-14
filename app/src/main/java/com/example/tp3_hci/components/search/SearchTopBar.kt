package com.example.tp3_hci.components.search

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp3_hci.R
import com.example.tp3_hci.ui.theme.*
import com.example.tp3_hci.utilities.SpeechRecognizerContract
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


// -----------------------------------------------------------------------
// input: Valor a mostrar en el searchbox
// onCloseSearchBarState: Funcion a ejecutar al cancelar la busqueda
// onInputChange: Funcion a ejecutar al cambiar el input
// onSearchClicked: Funcion a ejecutar al buscar
// -----------------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun SearchTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    input: String = "",
    onCloseSearchBarState: (() -> Unit)? = null,
    onInputChange: ((String)->Unit)? = null,
    onSearchClicked: ((String)->Unit)? = null
) {

    // Recuerda si tiene permiso para usar el microfono
    val permissionState = rememberPermissionState(permission = Manifest.permission.RECORD_AUDIO)

    // Ejecuta el intent (contract) y recibe la respuesta (onResult) de la busqueda por microfono
    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = SpeechRecognizerContract(),
        onResult = {
            if(it != null && onInputChange != null){
                onInputChange(it[0])
                if (onSearchClicked != null) {
                    onSearchClicked(it[0])
                }
            }
        }
    )

    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(
                onClick = {
                    if(onCloseSearchBarState != null){
                        onCloseSearchBarState()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.cancel_search),
                    tint = FitiWhiteText,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    // Si tiene permiso para usar el microfono, usara el widget de Google para speech to text
                    if(permissionState.status.isGranted){
                        speechRecognizerLauncher.launch(Unit)
                    } else {
                        permissionState.launchPermissionRequest()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Mic,
                    contentDescription = stringResource(id = R.string.voice_search),
                    tint = FitiWhiteText,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        title = {
            SearchBox(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .height(55.dp)
                    .fillMaxWidth(),
                input = input,
                onInputChange = onInputChange,
                onSearchClicked = onSearchClicked
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = FitiBlue,
            scrolledContainerColor = FitiBlue,
            navigationIconContentColor = FitiWhiteText,
            titleContentColor = FitiWhiteText,
            actionIconContentColor = FitiWhiteText
        )
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBox(
    modifier: Modifier = Modifier,
    input: String = "",
    onInputChange: ((String)->Unit)? = null,
    onSearchClicked: ((String)->Unit)? = null
){
    OutlinedTextField(
        value = input,
        onValueChange = {
            if(onInputChange != null){
                onInputChange(it)
            }
        },
        modifier = modifier,
        textStyle = MaterialTheme.typography.h3.copy(
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        ),
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_routines),
                style = MaterialTheme.typography.h3.copy(
                    fontSize = 15.sp
                )
            )
        },
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        trailingIcon = {
            if(input.isNotEmpty()){
                IconButton(
                    onClick = {
                        if (onInputChange != null) {
                            onInputChange("")
                        }
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
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                if(onSearchClicked != null){
                    onSearchClicked(input)
                }
            }
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = FitiWhiteText,
            disabledTextColor = FitiWhiteText,
            containerColor = FitiGreyButton.copy(alpha = 0.6f),
            cursorColor = FitiWhiteText,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedTrailingIconColor = FitiWhiteText,
            unfocusedTrailingIconColor = FitiWhiteText,
            disabledTrailingIconColor = FitiWhiteText,
            placeholderColor = FitiWhiteText.copy(alpha = 0.6f),
            disabledPlaceholderColor = FitiWhiteText.copy(alpha = 0.6f)
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SearchTopBarPreview(){
    TP3_HCITheme {
        Scaffold(
            topBar = {
                var input by remember { mutableStateOf("") }
                SearchTopBar(
                    scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                    input = input,
                    onCloseSearchBarState = {
                        input = ""
                    },
                    onInputChange = { newInput ->
                        input = newInput
                    },
                    onSearchClicked = { inputSearched ->
                        println("Se busco $inputSearched")
                    }
                )
            }
        ){}
    }
}