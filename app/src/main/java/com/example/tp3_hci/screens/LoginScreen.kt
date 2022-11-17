package com.example.tp3_hci.screens


import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
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
import com.example.tp3_hci.utilities.WindowInfo
import com.example.tp3_hci.utilities.rememberWindowInfo

@Composable
fun LoginView(
    setTopAppBar : ((TopAppBarType)->Unit),
    loginNavigation: LoginNavigation,
){
    setTopAppBar(
        TopAppBarType(
            topAppBar = null,
        )
    )

    Column(
        //modifier = Modifier.verticalScroll().fillMaxHeight(),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        val windowInfo = rememberWindowInfo()

        if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact ||
            windowInfo.screenHeightInfo is WindowInfo.WindowType.Compact){
            AsyncImage(
                model = "https://cdn.discordapp.com/attachments/1008117588762579067/1024756087825645669/fiti-logo.png",
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 40.dp, end = 40.dp)
            )
        }
        else{
            AsyncImage(
                model = "https://cdn.discordapp.com/attachments/1008117588762579067/1024756087825645669/fiti-logo.png",
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(top = 10.dp, start = 40.dp, end = 40.dp)
            )
        }
        BLockLogin(loginNavigation)
        BLockRegister()
    }

}


@Composable
private fun BLockRegister() {
    Column(
            modifier = Modifier.padding(start = 40.dp, end = 40.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
        Text(
            text = "¿No tienes cuenta aún?",
            style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Bold),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = FitiGreenButton,
            ),
            onClick = {/* logica del register OPCIONAL */ },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Registrate",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                color = White )
        }
    }
}

/* Intentar "Composearlo" mas con la API */
@Composable
private fun BLockLogin(
    loginNavigation: LoginNavigation,
    viewModel: LoginViewModel = viewModel(factory = getViewModelFactory())
) {
    val UiState = viewModel.uiState
    var textError = remember { mutableStateOf("") }
    var required = remember { mutableStateOf(false) }
    if( UiState.isAuthenticated == true){
        loginNavigation.getOnLoginScreen().invoke()
    }
    else if(UiState.isFetching == true && required.value){
        textError.value = "Loding..."
    }
    else if(UiState.isAuthenticated == false && required.value){
        textError.value = "Usuario y contraseña invalidos"
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
                text = "Inicio de Sesion",
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
                placeholder = { Text("Correo Electronico") },
                value = username.value,
                onValueChange = { username.value = it },
                isError = (required.value && username.value.text == ""))
            if (required.value && username.value.text == "") {
                Text(
                    text = "Campo obligatorio",
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
                placeholder = { Text("Password") },
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(onGo = {
                    required.value = true;
                    viewModel.login(username = username.value.text, password = password.value.text)
                    //while(viewModel.uiState.isFetching == true){ }
                    //viewModel.login(username = "bb@mail.com", password = "1234")
                    //if( UiState.isAuthenticated == true){
                    //    loginNavigation.getOnLoginScreen().invoke()
                    //}
                    textError.value = "Loding..."
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
                    text = "Campo obligatorio",
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
                onClick = {
                    required.value = true;
                    viewModel.login(username = username.value.text, password = password.value.text)
                    //while(viewModel.uiState.isFetching == true){ }
                    //viewModel.login(username = "bb@mail.com", password = "1234")
                    //if( UiState.isAuthenticated == true){
                    //    loginNavigation.getOnLoginScreen().invoke()
                    //}
                        textError.value = "Loding..."
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Ingresar",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                    color = White)
            }
        }
    }
}

