package edu.towson.cosc435.vargashernandez.connectfour

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import edu.towson.cosc435.vargashernandez.connectfour.database.*
import kotlinx.coroutines.*
private var player1ChipColor = R.drawable.gold_chip
private var player1Name = ""
private val id = 0
private var wins = 0
private var losses = 0
var myProfile = ProfileItem(id, username = player1Name, wins, losses)

@ExperimentalFoundationApi
@Composable
fun LocalPlayScreen() {
    Column(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp),
            horizontalArrangement = Arrangement.SpaceEvenly){
            Column(){
                Image(painter = painterResource(id = R.drawable.doc),
                    contentDescription = "Doc The Tiger",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape))
               if(player1Name == ""){
                    player1Name = "Player 1"
                }
                Text(player1Name, fontSize=15.sp)
            }
            Column(){
                Image(painter = painterResource(id = R.drawable.doc),
                    contentDescription = "Doc The Tiger",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape))
                Text("Player 2", fontSize=15.sp)
            }
        }
        board()
    }
}

@ExperimentalFoundationApi
@Composable
fun OnlinePlayScreen() {
    Column(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp),
            horizontalArrangement = Arrangement.SpaceEvenly){
            Column(){
                Image(painter = painterResource(id = R.drawable.doc),
                    contentDescription = "Doc The Tiger",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape))
                if(player1Name == ""){
                    player1Name = "Player 1"
                }
                Text(player1Name, fontSize=15.sp)
            }
        }
        board()
    }
}

@ExperimentalFoundationApi
@Composable
fun board() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally){
        Box(modifier = Modifier
            .fillMaxSize()
        ){
            //player1Name = getPlayer1Name("Player 1")
            Log.d("TAG-USERNAME", "Name: $player1Name")
            Board().draw(player1Color = player1ChipColor, player1Name)
        }
    }
}


@ExperimentalFoundationApi
@Composable
fun NavigationDrawer(){
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope = scope, scaffoldState = scaffoldState)},
        drawerContent = {
            Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
        }
    ){
        Navigation(navController = navController)
    }
}

@Composable
fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {
    TopAppBar (
        backgroundColor = MaterialTheme.colors.primary,
        title = { Text(text = "Connect 4") },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Filled.Menu, "")
            }
        }
    )
}

@Composable
fun Drawer(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavController){
    val items = listOf (
        ScreenMenu.Home,
        ScreenMenu.MultiplayerScreen,
        ScreenMenu.OnlineScreen,
        ScreenMenu.Settings,
        ScreenMenu.UsernameScreen
    )

    Column (
        modifier = Modifier
            .background(color = Color.White)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment=Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            //something
        }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { items ->
            DrawerItems(item = items, selected = currentRoute == items.route, onItemClick ={
                navController.navigate(items.route){
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route){
                            saveState=true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            })
        }
    }
}

@Composable
fun DrawerItems(item: ScreenMenu, selected: Boolean, onItemClick: (ScreenMenu) -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .background(MaterialTheme.colors.primaryVariant)
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = item.title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}

@Composable
fun HomeScreen(navController: NavHostController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageComposable()
        Button(onClick = {
            navController.navigate(ScreenMenu.OnlineScreen.route){
                navController.graph.startDestinationRoute?.let { route ->
                    popUpTo(route){
                        saveState=true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            modifier = Modifier
                .padding(16.dp)
                .width(200.dp))
        {
            Text("Online Play")
        }
        Button(onClick = {
            navController.navigate(ScreenMenu.MultiplayerScreen.route){
                navController.graph.startDestinationRoute?.let { route ->
                    popUpTo(route){
                        saveState=true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            modifier = Modifier
                .padding(16.dp)
                .width(200.dp))
        {
            Text("Local Play")
        }
        Button(onClick = {
            navController.navigate(ScreenMenu.Settings.route){
                navController.graph.startDestinationRoute?.let { route ->
                    popUpTo(route){
                        saveState=true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            modifier = Modifier
                .padding(16.dp)
                .width(200.dp))
        {
            Text(text="Settings")
        }
    }
}

@Composable
fun SettingsScreen() {
    val textState = remember { mutableStateOf(TextFieldValue()) }
    Column(
        content = {
            Text(text = "Settings", fontSize = 26.sp, modifier = Modifier
                .padding(16.dp))
            Username()
            Dropdown()
            Spacer(modifier = Modifier.height(25.dp))
            HowTo()
        }, modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
}


@Composable
fun HowTo() {
    LazyColumn() {
        item{ Text("How To Play:", fontSize=45.sp, modifier = Modifier.padding(5.dp)) }
        // Rules are from https://www.gamesver.com/the-rules-of-connect-4-according-to-m-bradley-hasbro/
        item{ Text("1. Decide who goes first", fontSize=20.sp) }
        item{ Text("2. Decide which colors to use", fontSize=20.sp) }
        item{ Text("3. Players must alternate turns", fontSize=20.sp) }
        item{ Text("3.1. Only one chip can be dropped each turn", fontSize=16.sp) }
        item{ Text("4. On your turn, drop a chip into any of the 7 slots", fontSize=20.sp) }
        item{ Text("5. The game is over when there is 4-in-a-row or a stalemate", fontSize=20.sp) }
        item{ Text("5.1. 4-in-a-row can be achieved Horizontally, Vertically, or Diagonally", fontSize=16.sp) }
        item{ Text("5.2. A stalemate is when there are no more possible moves to make that would result in either player winning", fontSize=16.sp) }
        item{ Text("6. When playing a new game, the starter of the previous game goes second", fontSize=20.sp) }
        item{ Text("7. Enjoy playing !!", fontSize=25.sp, modifier = Modifier.padding(10.dp)) }

    }
}

@Composable
fun Username() {
    val textState = remember { mutableStateOf(TextFieldValue()) }
    val vm: ProfileViewModel = viewModel()
    val cs = rememberCoroutineScope()
    Text(text="Edit Player Name:", fontSize = 20.sp)
    TextField(
        value = textState.value,
        onValueChange = {
            textState.value = it
            player1Name = getPlayer1Name(textState.value.text)
            Log.d("TAG-USERNAME", "Name: $player1Name")
            myProfile = ProfileItem(id, player1Name, wins, losses)
            cs.launch {
                vm.update(myProfile)
            }
        }
    )
    val username = textState.value.text
    Text("Your name: " + username)
}

fun getPlayer1Name(name: String): String{
    return name
}

@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalFoundationApi
@Composable
fun UsernameScreen() {
    Column {
        Row(modifier = Modifier
            .fillMaxWidth()) {
            Text("Username: " + myProfile.username,
                modifier = Modifier
                    .padding(20.dp),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold)
        }
        Row(modifier = Modifier
            .fillMaxWidth()) {
            Text("Wins: " + myProfile.wins.toString(),
                modifier = Modifier
                    .padding(20.dp),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold)
        }
        Row(modifier = Modifier
            .fillMaxWidth()) {
            Text("Losses: " + myProfile.losses.toString(),
                modifier = Modifier
                    .padding(20.dp),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun start() {
    val vm: ProfileViewModel = viewModel()
    val cs= rememberCoroutineScope()
    cs.launch {
        vm.add(myProfile)
    }
}

@Composable
fun Dropdown() {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Red", "Orange", "Yellow", "Green", "Blue", "Purple")
    var selectedIndex by remember { mutableStateOf(0) }
    Text(text="Select Chip Color:", fontSize = 20.sp, modifier = Modifier
        .padding(top= 16.dp))
    Box(modifier = Modifier.wrapContentSize(Alignment.Center)) {
        Text(items[selectedIndex],modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(onClick = { expanded = true })
            .background(MaterialTheme.colors.primary),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White,
            textAlign = TextAlign.Center)
        //change the dropdown background color to equal the selected color?
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primaryVariant)
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    player1ChipColor = getPlayer1ChipColor(selectedIndex)
                    expanded = false
                }) {
                    Text(text = s, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

fun getPlayer1ChipColor(idx: Int): Int {
    return when(idx){
        0 -> R.drawable.red_chip
        1 -> R.drawable.orange_chip
        2 -> R.drawable.gold_chip
        3 -> R.drawable.green_chip
        4 -> R.drawable.blue_chip
        5 -> R.drawable.purple_chip
        else -> R.drawable.gold_chip
    }
}

@ExperimentalFoundationApi
@Composable
fun Navigation(navController: NavHostController){
    val vm: ProfileViewModel = viewModel()
    NavHost(navController, startDestination = ScreenMenu.Home.route){
        composable(ScreenMenu.Home.route){
            HomeScreen(navController)
        }

        composable(ScreenMenu.MultiplayerScreen.route){
            LocalPlayScreen()
            //online play
        }

        composable(ScreenMenu.OnlineScreen.route){
            OnlinePlayScreen()
            //local play
        }

        composable(ScreenMenu.Settings.route){
            SettingsScreen()
        }

        composable(ScreenMenu.UsernameScreen.route){
            UsernameScreen()
        }
    }
}

@Composable
fun ImageComposable() {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "",
        modifier = Modifier
            .padding(16.dp)
            .width(300.dp),
        contentScale = ContentScale.Crop
    )
}
