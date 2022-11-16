package com.example.tp3_hci

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tp3_hci.components.navigation.BottomNavItem
import com.example.tp3_hci.components.navigation.BottomNavigationBar
import com.example.tp3_hci.components.navigation.TopNavigationBar
import com.example.tp3_hci.data.model.RoutineOverview
import com.example.tp3_hci.data.model.Sport
import com.example.tp3_hci.data.model.User
import com.example.tp3_hci.ui.theme.FitiWhiteText
import com.example.tp3_hci.ui.theme.TP3_HCITheme
import com.example.tp3_hci.util.getViewModelFactory
import com.example.tp3_hci.utilities.MyNavHost
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tp3_hci.data.model.RoutineDetail
import kotlin.random.Random

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
//                        MainScreen()
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
fun ActionButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        enabled = enabled,
        onClick = onClick,
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(8.dp))
    }
}
@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(factory = getViewModelFactory())
) {
    val uiState = viewModel.uiState

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!uiState.isAuthenticated) {
            ActionButton(
                text = "login",
                onClick = {
                    viewModel.login("test", "1234")
                })
        } else {
            ActionButton(
                text = "logout",
                onClick = {
                    viewModel.logout()
                })
        }

        ActionButton(
            text = "getCurrentUser",
            enabled = uiState.canGetCurrentUser,
            onClick = {
                viewModel.getCurrentUser()
            })
        ActionButton(
            text = "getRoutines",
            enabled = uiState.canGetCurrentUser,
            onClick = {
                viewModel.getRoutines()
            })
        ActionButton(
            text = "getRoutineDetail",
            enabled = uiState.canGetCurrentUser,
            onClick = {
                viewModel.getDetailedRoutine()
            })
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val currentUserData = uiState.currentUser?.let {
                "Current User: ${it.firstName} ${it.lastName} (${it.email})"
            }
            Text(
                text = currentUserData?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                fontSize = 18.sp
            )
            if(uiState.routines!=null){
                Text(uiState.routines.size.toString())
                for(routine in uiState.routines){
                    Text(routine.name)
                }
            }else{
                Text("no estan disponibles las rutinas ")
                if(uiState.message!=null){
                    Text(text = uiState.message)
                }
            }
            if(uiState.detailedRoutine!=null){
                Text(uiState.detailedRoutine.name)
                Text(uiState.detailedRoutine.creator)
                Text(uiState.detailedRoutine.difficulty.toString())
                Text(uiState.detailedRoutine.isFavourite.toString())
                Text(uiState.detailedRoutine.rating.toString())
                Text(uiState.detailedRoutine.tags.toString())
                Text(uiState.detailedRoutine.votes.toString())
                LazyColumn(){
                    items(uiState.detailedRoutine.cycles){
                        Text(it.name.toString())
                        Text(it.order.toString())
                        Text(it.repetitions.toString())
                        Column() {
                            for(exercise in it.exercises){
                                Text(exercise.name)
                                Text(exercise.order.toString())
                                Text(exercise.time.toString())
                                Text(exercise.repetitions.toString())
                                Text(exercise.image)
                            }
                        }
                    }
                }
            }else{
                Text("el detalle de la rutina no esta disponible")
            }

        }
    }
}
data class MainUiState(
    val isAuthenticated: Boolean = false,
    val isFetching: Boolean = false,
    val currentUser: User? = null,
    val message: String? = null,
    val routines: List<RoutineOverview>? = null,
    val detailedRoutine: RoutineDetail? = null
)

val MainUiState.canGetCurrentUser: Boolean get() = isAuthenticated
val MainUiState.canGetAllSports: Boolean get() = isAuthenticated

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TP3_HCITheme {
        MainScreen()
    }
}

