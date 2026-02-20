package fr.example.tuenolarryjason.thegreatestcocktailapp

object NavRoutes {
    const val RANDOM = "random"
    const val CATEGORIES = "categories"
    const val FAVORITES = "favorites"
    const val DRINK_LIST = "drink_list/{category}"
    const val DRINK_DETAIL = "drink_detail/{drinkId}"

    fun drinkList(category: String) = "drink_list/$category"
    fun drinkDetail(drinkId: String) = "drink_detail/$drinkId"
}