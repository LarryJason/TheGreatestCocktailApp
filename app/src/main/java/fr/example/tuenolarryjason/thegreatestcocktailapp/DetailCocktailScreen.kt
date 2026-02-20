package fr.example.tuenolarryjason.thegreatestcocktailapp

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCocktailScreen(modifier: Modifier = Modifier) {
    val lightViolet = Color(0xFFE1BEE7) // Violet un peu plus foncé (Purple 100)
    val context = LocalContext.current
    var isLiked by remember { mutableStateOf(false) }

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
                        isLiked = !isLiked
                        if (isLiked) {
                            Toast.makeText(context, "ajouté aux favoris", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Icon(
                            imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (isLiked) Color.Red else Color.Gray
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image du cocktail au centre
            Image(
                painter = painterResource(id = R.drawable.cocktail),
                contentDescription = "Cocktail Image",
                modifier = Modifier
                    .size(250.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nom du cocktail
            Text(
                text = "Margarita",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Catégories
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CategoryTag(text = "Other/Unknown", backgroundColor = Color(0xFFBBDEFB)) // Bleu léger
                CategoryTag(text = "Non Alcoholic", backgroundColor = Color(0xFFC8E6C9)) // Vert léger
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Note de verre
            Text(
                text = "Highball glass",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Liste d'ingrédients dans un cadre
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
                    IngredientRow(name = "Yogurt", amount = "1 cup")
                    IngredientRow(name = "Fruit", amount = "1 cup")
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
