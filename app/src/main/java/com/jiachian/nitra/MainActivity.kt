package com.jiachian.nitra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jiachian.common.ui.NitraTheme
import com.jiachian.home.ui.HomeScreen
import com.jiachian.home.ui.HomeViewModel
import com.jiachian.nitraassignment.ui.route.MainRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NitraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        navController = navController,
                        startDestination = MainRoute.Home,
                    ) {
                        composable<MainRoute.Home> {
                            val viewModel = hiltViewModel<HomeViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            HomeScreen(
                                modifier = Modifier.fillMaxSize(),
                                state = state,
                                onEvent = viewModel::onEvent,
                            )
                        }
                    }
                }
            }
        }
    }
}