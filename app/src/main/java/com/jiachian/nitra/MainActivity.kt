package com.jiachian.nitra

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jiachian.common.ui.NitraTheme
import com.jiachian.home.ui.HomeScreen
import com.jiachian.lock.util.BiometricPromptManager
import com.jiachian.nitraassignment.ui.route.MainRoute
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val biometricManager by lazy { BiometricPromptManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NitraTheme {
                val viewModel = hiltViewModel<MainViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()

                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = MainRoute.Home,
                    ) {
                        composable<MainRoute.Home> {
                            HomeScreen()
                        }
                    }
                }

                LockAppDetector(
                    enabled = state.lockEnabled,
                    onFailed = { finish() }
                )
            }
        }
    }

    @Composable
    private fun LockAppDetector(
        enabled: Boolean,
        onFailed: () -> Unit,
    ) {
        val lifecycleOwner = LocalLifecycleOwner.current
        val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

        if (enabled && lifecycleState == Lifecycle.State.RESUMED) {
            biometricManager.showBiometricPrompt(
                title = getString(com.jiachian.more.R.string.verify_identity_title),
                description = getString(com.jiachian.more.R.string.verify_identity_description),
            )
        }

        LaunchedEffect(Unit) {
            biometricManager.promptResults.collect {
                if (
                    it is BiometricPromptManager.BiometricResult.AuthenticationError ||
                    it is BiometricPromptManager.BiometricResult.AuthenticationFailed
                ) {
                    onFailed()
                }
            }
        }
    }
}