package com.jiachian.cards.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jiachian.cards.ui.list.CardListScreen
import com.jiachian.cards.ui.list.CardListViewModel
import com.jiachian.cards.ui.route.CardsRoute

@Composable
fun CardsScreen(
    state: CardsState,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = state.route
    ) {
        composable<CardsRoute.List> {
            val viewModel = hiltViewModel<CardListViewModel>()
            val listState by viewModel.state.collectAsStateWithLifecycle()
            CardListScreen(
                modifier = Modifier.fillMaxSize(),
                state = listState,
            )
        }
    }
}