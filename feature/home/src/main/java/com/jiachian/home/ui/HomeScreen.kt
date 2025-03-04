package com.jiachian.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jiachian.cards.ui.CardsScreen
import com.jiachian.common.ui.DSTheme
import com.jiachian.home.R
import com.jiachian.home.ui.routes.HomeRoute
import com.jiachian.home.ui.routes.TopLevelRoute

private val routes = listOf(
    TopLevelRoute(
        nameResId = R.string.route_transactions,
        route = HomeRoute.Transactions,
        iconResId = R.drawable.ic_route_transcations,
    ),
    TopLevelRoute(
        nameResId = R.string.route_cards,
        route = HomeRoute.Cards,
        iconResId = R.drawable.ic_route_cards,
    ),
    TopLevelRoute(
        nameResId = R.string.route_payments,
        route = HomeRoute.Payments,
        iconResId = R.drawable.ic_route_payments,
    ),
    TopLevelRoute(
        nameResId = R.string.route_statements,
        route = HomeRoute.Statements,
        iconResId = R.drawable.ic_route_statements,
    ),
    TopLevelRoute(
        nameResId = R.string.route_more,
        route = HomeRoute.More,
        iconResId = R.drawable.ic_route_more,
    ),
)

@Composable
private fun NavHostController.currentDestinationAsState(startDestination: HomeRoute): State<HomeRoute> {
    val navBackStackEntry by currentBackStackEntryAsState()
    return remember {
        derivedStateOf {
            navBackStackEntry?.destination?.let { destination ->
                when {
                    destination.hasRoute(HomeRoute.Transactions::class) -> HomeRoute.Transactions
                    destination.hasRoute(HomeRoute.Cards::class) -> HomeRoute.Cards
                    destination.hasRoute(HomeRoute.Payments::class) -> HomeRoute.Payments
                    destination.hasRoute(HomeRoute.Statements::class) -> HomeRoute.Statements
                    destination.hasRoute(HomeRoute.More::class) -> HomeRoute.More
                    else -> throw IllegalArgumentException("Route not found: ${destination.route}")
                }
            } ?: startDestination
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    startDestination: HomeRoute = HomeRoute.Cards,
) {
    val navController = rememberNavController()
    val currentDestination by navController.currentDestinationAsState(startDestination)

    Scaffold(
        modifier = modifier,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(with(LocalDensity.current) {
                        WindowInsets.statusBars.getTop(this).toDp()
                    })
                    .background(DSTheme.colors.background)
            )
        },
        bottomBar = {
            HomeBottomBar(
                currentDestination = currentDestination,
                onItemClick = {
                    navController.navigate(it) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { padding ->
        NavHost(
            modifier = Modifier.padding(padding),
            navController = navController,
            startDestination = startDestination,
        ) {
            composable<HomeRoute.Cards> {
                CardsScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable<HomeRoute.Transactions> { }
            composable<HomeRoute.Payments> { }
            composable<HomeRoute.Statements> { }
            composable<HomeRoute.More> { }
        }
    }
}

@Composable
private fun HomeBottomBar(
    currentDestination: HomeRoute,
    onItemClick: (HomeRoute) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
        containerColor = DSTheme.colors.background,
    ) {
        routes.forEach { route ->
            NavigationBarItem(
                selected = route.route == currentDestination,
                onClick = { onItemClick(route.route) },
                icon = {
                    Icon(
                        painter = painterResource(route.iconResId),
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = stringResource(route.nameResId),
                        style = DSTheme.fonts.regular10,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DSTheme.colors.white,
                    selectedTextColor = DSTheme.colors.white,
                    unselectedTextColor = DSTheme.colors.white40,
                    unselectedIconColor = DSTheme.colors.white40,
                    indicatorColor = DSTheme.colors.transparent,
                )
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}