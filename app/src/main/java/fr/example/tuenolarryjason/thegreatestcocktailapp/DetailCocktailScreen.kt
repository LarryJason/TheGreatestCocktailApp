package fr.example.tuenolarryjason.thegreatestcocktailapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCocktailScreen(modifier: Modifier = Modifier, drinkId: String? = null) {
    val lightViolet = Color(0xFFE1BEE7)
    val context = LocalContext.current
    var drink by remember { mutableStateOf<DrinkDetailItem?>(null) }
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(drink) {
        drink?.let {
            isFavorite = FavoritesManager.isFavorite(context, it.id)
        }
    }

    LaunchedEffect(drinkId) {
        drink = if (drinkId != null) {
            NetworkManager.apiService.getDrinkById(drinkId).drinks.firstOrNull()
        } else {
            NetworkManager.apiService.getRandomCocktail().drinks.firstOrNull()
        }
    }

    Scaffold(
        containerColor = lightViolet,
        topBar = {
            TopAppBar(
                title = { },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                actions = {
                    IconButton(onClick = { /* TODO: Refresh Action */ }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Recharger",
                            tint = Color.Gray
                        )
                    }
                    IconButton(onClick = {
                        drink?.let {
                            if (isFavorite) {
                                FavoritesManager.removeFavorite(context, it.id)
                            } else {
                                FavoritesManager.addFavorite(context, DrinkListItem(it.name, it.imageUrl ?: "", it.id))
                            }
                            isFavorite = !isFavorite
                        }
                    }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (isFavorite) Color.Red else Color.Gray
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        drink?.let { drink ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = drink.imageUrl,
                    contentDescription = "Cocktail Image",
                    modifier = Modifier
                        .size(250.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = drink.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    drink.category?.let { CategoryTag(text = it, backgroundColor = Color(0xFFBBDEFB)) }
                    drink.alcoholic?.let { CategoryTag(text = it, backgroundColor = Color(0xFFC8E6C9)) }
                }

                Spacer(modifier = Modifier.height(8.dp))

                drink.glass?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Ingredients",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp),
                            color = Color.Black
                        )
                        drink.getIngredients().forEach { (ingredient, measure) ->
                            IngredientRow(name = ingredient, amount = measure)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryTag(text: String, backgroundColor: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.labelLarge, color = Color.Black)
    }
}

@Composable
fun IngredientRow(name: String, amount: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = name, style = MaterialTheme.typography.bodyLarge, color = Color.Black)
        Text(text = amount, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium, color = Color.Black)
    }
}
