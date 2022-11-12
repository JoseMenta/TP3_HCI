package com.example.tp3_hci


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import com.example.tp3_hci.ui.theme.*

@Composable
fun LoginView(){
    Column(
        //modifier = Modifier.verticalScroll().fillMaxHeight(),
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        AsyncImage(
            model = "https://cdn.discordapp.com/attachments/1008117588762579067/1024756087825645669/fiti-logo.png",
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 40.dp, end = 40.dp)
        )
        BLockLogin()
        BLockRegister()
    }

}


@Composable
fun BLockRegister() {
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
fun BLockLogin() {
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

            var required = remember { mutableStateOf(false) }
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
            var textError = remember { mutableStateOf("") }

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
                onClick = { required.value = true;
                    if(password.value.text == "fiti" && username.value.text == "fiti") textError.value = "ok" else textError.value = "Usuario y contraseña invalidos"},
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

