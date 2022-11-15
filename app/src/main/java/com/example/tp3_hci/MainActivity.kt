package com.example.tp3_hci

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_hci.ui.theme.TP3_HCITheme
import com.example.tp3_hci.utilities.MyNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TP3_HCITheme {
//                 A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(modifier= Modifier
                        .width(400.dp)
                        .height(330.dp), horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        //Controler()
                        MyNavHost()
                        /*
                        RoutineDetail(RoutineDetailUiState("Futbol",3,"Jose",3,120000, listOf("Hola","Como","estas", "buenas","tardes","Futbol","Scaloneta","Messi"),cycles))
                        ratingView(Routine("Futbol",3,"Jose",3,120000, listOf("Hola","Como","estas", "buenas","tardes","Futbol","Scaloneta","Messi"),cycles),
                            "https://www.aquasportclubs.com/wp-content/uploads/2018/12/clase-dirigida-skillmill.jpg")
                        ExerciseCard()
                        LoginView()
                        Spacer(modifier = Modifier.height(20.dp))
                        ExerciseCard()
                        Spacer(modifier = Modifier.height(20.dp))
                        ExerciseCard()*/
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TP3_HCITheme {
        Greeting("Android")
    }
}

