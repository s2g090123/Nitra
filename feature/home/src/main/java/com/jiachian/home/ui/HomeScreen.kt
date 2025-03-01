package com.jiachian.home.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jiachian.common.ui.DSTheme
import com.jiachian.home.R
import com.jiachian.home.ui.intents.HomeEvent
import com.jiachian.home.ui.routes.HomeRoute
import com.jiachian.home.ui.routes.TopLevelRoute
import com.jiachian.home.ui.states.HomeState

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
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            HomeBottomBar(
                currentDestination = state.route,
                onItemClick = { onEvent(HomeEvent.NavigateTo(it)) }
            )
        }
    ) { padding ->

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
    HomeScreen(
        state = HomeState(),
        onEvent = {},
    )
}