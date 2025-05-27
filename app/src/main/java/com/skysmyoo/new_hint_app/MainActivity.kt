package com.skysmyoo.new_hint_app

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skysmyoo.new_hint_app.ui.LoginScreen
import com.skysmyoo.new_hint_app.ui.StoreViewModel
import com.skysmyoo.new_hint_app.ui.hint.AgentAssistantScreen
import com.skysmyoo.new_hint_app.ui.hint.HintScreen
import com.skysmyoo.new_hint_app.ui.hint.HintViewModel
import com.skysmyoo.new_hint_app.ui.home.HomeScreen
import com.skysmyoo.new_hint_app.ui.home.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val storeViewModel: StoreViewModel by viewModels()
    private val hintViewModel: HintViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation(storeViewModel, hintViewModel)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val packageName = applicationContext.packageName
                val pm = applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager

                if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                    val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                        data = Uri.parse("package:$packageName")
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(intent)
                }
            }
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
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen {
                val nextRoute = if (storeViewModel.getStoreCode().isNullOrEmpty()) "login" else "home"
                navController.navigate(nextRoute) {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }

        composable("login") {
            LoginScreen(navController, storeViewModel)
        }

        composable("home") {
            HomeScreen(navController, storeViewModel)
        }

        composable("hintScreen/{title}") { backStackEntry ->
            HintScreen(
                navController = navController,
                viewModel = hintViewModel,
                title = backStackEntry.arguments?.getString("title") ?: ""
            )
        }

        composable("agentAssistant/{title}") { backStackEntry ->
            AgentAssistantScreen(
                navController = navController,
                viewModel = hintViewModel,
                title = backStackEntry.arguments?.getString("title") ?: ""
            )
        }
    }
}