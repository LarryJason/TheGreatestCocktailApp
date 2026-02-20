package fr.example.tuenolarryjason.thegreatestcocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import fr.example.tuenolarryjason.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheGreatestCocktailAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(
        NavigationItem("Random", NavRoutes.RANDOM, Icons.Default.Casino),
        NavigationItem("List", NavRoutes.CATEGORIES, Icons.Default.List),
        NavigationItem("Favorites", NavRoutes.FAVORITES, Icons.Default.Favorite)
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = Color.Black,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavRoutes.CATEGORIES,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavRoutes.RANDOM) {
                // Pour l'instant on réutilise le DetailCocktailScreen comme écran random
                DetailCocktailScreen()
            }
            composable(NavRoutes.CATEGORIES) {
                CategoriesScreen(onCategoryClick = { category ->
                    navController.navigate(NavRoutes.drinkList(category))
                })
            }
            composable(NavRoutes.FAVORITES) {
                // Ecran favoris (à implémenter si besoin, vide pour l'instant)
                Text("Mes Favoris", modifier = Modifier.padding(innerPadding))
            }
            composable(NavRoutes.DRINK_LIST) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: ""
                DrinksListScreen(
                    category = category,
                    onDrinkClick = { drinkId ->
                        navController.navigate(NavRoutes.drinkDetail(drinkId))
                    },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(NavRoutes.DRINK_DETAIL) {
                DetailCocktailScreen()
            }
        }
    }
}

data class NavigationItem(val label: String, val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
