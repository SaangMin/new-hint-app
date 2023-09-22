package com.skysmyoo.new_hint_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skysmyoo.new_hint_app.ui.LoginScreen
import com.skysmyoo.new_hint_app.ui.StoreViewModel
import com.skysmyoo.new_hint_app.ui.hint.HintScreen
import com.skysmyoo.new_hint_app.ui.hint.HintViewModel
import com.skysmyoo.new_hint_app.ui.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val storeViewModel: StoreViewModel by viewModels()
    private val hintViewModel: HintViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation(storeViewModel, hintViewModel)
        }
    }
}

@Composable
fun Navigation(
    storeViewModel: StoreViewModel,
    hintViewModel: HintViewModel,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (storeViewModel.getStoreCode().isNullOrEmpty()) "login" else "home"
    ) {
        composable("login") {
            LoginScreen(navController, storeViewModel)
        }

        composable("home") {
            HomeScreen(navController, storeViewModel)
        }

        composable("hintScreen/{themeId}") { backStackEntry ->
            HintScreen(
                navController = navController,
                viewModel = hintViewModel,
                themeId = backStackEntry.arguments?.getString("themeId") ?: ""
            )
        }
    }
}