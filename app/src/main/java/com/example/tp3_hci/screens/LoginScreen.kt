package com.example.tp3_hci.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tp3_hci.data.view_model.LoginViewModel
import com.example.tp3_hci.ui.theme.*
import com.example.tp3_hci.util.getViewModelFactory
import com.example.tp3_hci.utilities.TopAppBarType
import com.example.tp3_hci.utilities.navigation.LoginNavigation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tp3_hci.MainViewModel
import com.example.tp3_hci.R
import com.example.tp3_hci.utilities.WindowInfo
import com.example.tp3_hci.utilities.rememberWindowInfo

@Composable
fun LoginView(
    setTopAppBar : ((TopAppBarType)->Unit),
    loginNavigation: LoginNavigation,
    loginViewModel: LoginViewModel = viewModel(factory = getViewModelFactory())
){
    var returned by remember { mutableStateOf(true) }
    if(returned){
        returned = false
        setTopAppBar(
            TopAppBarType(
                topAppBar = null,
            )
        )
    }
    if(loginViewModel.uiState.isAuthenticated){
        loginNavigation.getOnLoginScreen().invoke()
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            CircularProgressIndicator()
        }
    }else {
        Column(
            //modifier = Modifier.verticalScroll().fillMaxHeight(),
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val windowInfo = rememberWindowInfo()

            if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact ||
                windowInfo.screenHeightInfo is WindowInfo.WindowType.Compact
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fiti_logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 40.dp, end = 40.dp)
                )
            } else {
                AsyncImage(
                    model = R.drawable.fiti_logo,
                    contentDescription = "Logo",
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(top = 10.dp, start = 40.dp, end = 40.dp)
                )
            }
            BLockLogin(loginNavigation, loginViewModel)
        }
    }

}


/* Intentar "Composearlo" mas con la API */
@Composable
private fun BLockLogin(
    loginNavigation: LoginNavigation,
    viewModel: LoginViewModel
) {
    val UiState = viewModel.uiState
    var textError = remember { mutableStateOf("") }
    var required = remember { mutableStateOf(false) }
    var loading = false
    if( UiState.isAuthenticated == true){
        loginNavigation.getOnLoginScreen().invoke()
    }
    else if(UiState.isFetching == true && required.value){
        loading = true
    }
    else if(UiState.isAuthenticated == false && required.value){
        textError.value = stringResource(id = R.string.error_credentials)
    }
    Card(
        border = BorderStroke(2.dp, FitiBlack),
        modifier = Modifier.padding(20.dp),
        backgroundColor = White
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = stringResource(id = R.string.LogIn),
                style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Bold),
                color = FitiBlueText
            )


            val username = remember { mutableStateOf(TextFieldValue()) }

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = FitiGreyInputText
                    ),
                placeholder = { Text(stringResource(id = R.string.email)) },
                value = username.value,
                onValueChange = { username.value = it },
                isError = (required.value && username.value.text == ""))
            if (required.value && username.value.text == "") {
                Text(
                    text = stringResource(id = R.string.empty_field),
                    color = FitiRedError,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.height(20.dp))


            var password = remember { mutableStateOf(TextFieldValue()) }

            var passwordVisible = remember { mutableStateOf(false) }

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                placeholder = { Text(stringResource(id = R.string.password)) },
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(onGo = {
                    required.value = true;
                    viewModel.login(username = username.value.text, password = password.value.text)
                }),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = FitiGreyInputText
                ),
                isError = (required.value && password.value.text == ""),
                trailingIcon = {
                    val image = if (passwordVisible.value)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    // Please provide localized description for accessibility services
                    val description = if (passwordVisible.value) "Hide password" else "Show password"

                    IconButton(onClick = {passwordVisible.value = !passwordVisible.value}){
                        Icon(imageVector  = image, description)
                    }
                }
            )
            if (required.value && password.value.text == "") {
                Text(
                    text = stringResource(id = R.string.empty_field),
                    color = FitiRedError,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                )
            }

            Spacer(modifier = Modifier.height(10.dp))


            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.Start,
            ){
                Text(text = textError.value, color = FitiRedError)
            }


            Spacer(modifier = Modifier.height(20.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = FitiGreenButton,
                ),
                enabled = !loading,
                onClick = {
                    required.value = true;
                    viewModel.login(username = username.value.text, password = password.value.text)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                if(loading){
                    CircularProgressIndicator()
                }else{
                    Text(text = stringResource(id = R.string.singIn),
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.Bold,
                        color = White)
                }
            }
        }
    }
}

