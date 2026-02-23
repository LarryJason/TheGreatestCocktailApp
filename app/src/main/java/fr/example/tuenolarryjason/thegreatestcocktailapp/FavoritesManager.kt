package fr.example.tuenolarryjason.thegreatestcocktailapp

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FavoritesManager {
    private const val PREFS_NAME = "CocktailFavorites"
    private const val FAVORITES_KEY = "favorites"

    private fun getFavorites(context: Context): MutableList<DrinkListItem> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(FAVORITES_KEY, null)
        return if (json != null) {
            val type = object : TypeToken<MutableList<DrinkListItem>>() {}.type
            Gson().fromJson(json, type)
        } else {
            mutableListOf()
        }
    }

    private fun saveFavorites(context: Context, favorites: List<DrinkListItem>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        val json = Gson().toJson(favorites)
        prefs.putString(FAVORITES_KEY, json)
        prefs.apply()
    }

    fun isFavorite(context: Context, drinkId: String): Boolean {
        return getFavorites(context).any { it.id == drinkId }
    }

    fun addFavorite(context: Context, drink: DrinkListItem) {
        val favorites = getFavorites(context)
        if (favorites.none { it.id == drink.id }) {
            favorites.add(drink)
            saveFavorites(context, favorites)
        }
    }

    fun removeFavorite(context: Context, drinkId: String) {
        val favorites = getFavorites(context)
        favorites.removeAll { it.id == drinkId }
        saveFavorites(context, favorites)
    }

    fun getFavoriteDrinks(context: Context): List<DrinkListItem> {
        return getFavorites(context)
    }
}
