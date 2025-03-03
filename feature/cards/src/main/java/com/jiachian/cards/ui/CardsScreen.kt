package com.jiachian.cards.ui

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jiachian.cards.ui.form.CardFormScreen
import com.jiachian.cards.ui.form.CardFormViewModel
import com.jiachian.cards.ui.list.CardListScreen
import com.jiachian.cards.ui.list.CardListViewModel
import com.jiachian.cards.ui.route.CardsRoute

@Composable
fun CardsScreen(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = CardsRoute.List,
    ) {
        composable<CardsRoute.List>(
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
        ) {
            val viewModel = hiltViewModel<CardListViewModel>()
            val listState by viewModel.state.collectAsStateWithLifecycle()
            CardListScreen(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                onAddCardClick = { navController.navigate(CardsRoute.Form) }
            )
        }
        composable<CardsRoute.Form>(
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) },
        ) {
            val viewModel = hiltViewModel<CardFormViewModel>()
            val formState by viewModel.state.collectAsStateWithLifecycle()
            CardFormScreen(
                modifier = Modifier.fillMaxSize(),
                state = formState,
                onEvent = viewModel::onEvent,
                onBackClick = { navController.popBackStack() },
                goToList = {
                    navController.navigate(CardsRoute.List) {
                        popUpTo(CardsRoute.List) {
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}