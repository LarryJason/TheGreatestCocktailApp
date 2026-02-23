package fr.example.tuenolarryjason.thegreatestcocktailapp

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("drinks") val categories: List<CategoryItem>
)

data class CategoryItem(
    @SerializedName("strCategory") val name: String
)

data class DrinkListResponse(
    @SerializedName("drinks") val drinks: List<DrinkListItem>
)

data class DrinkListItem(
    @SerializedName("strDrink") val name: String,
    @SerializedName("strDrinkThumb") val imageUrl: String,
    @SerializedName("idDrink") val id: String
)

data class DrinkDetailResponse(
    @SerializedName("drinks") val drinks: List<DrinkDetailItem>
)

data class DrinkDetailItem(
    @SerializedName("idDrink") val id: String,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strCategory") val category: String?,
    @SerializedName("strAlcoholic") val alcoholic: String?,
    @SerializedName("strGlass") val glass: String?,
    @SerializedName("strInstructions") val instructions: String?,
    @SerializedName("strDrinkThumb") val imageUrl: String?,
    
    @SerializedName("strIngredient1") val strIngredient1: String?,
    @SerializedName("strIngredient2") val strIngredient2: String?,
    @SerializedName("strIngredient3") val strIngredient3: String?,
    @SerializedName("strIngredient4") val strIngredient4: String?,
    @SerializedName("strIngredient5") val strIngredient5: String?,
    
    @SerializedName("strMeasure1") val strMeasure1: String?,
    @SerializedName("strMeasure2") val strMeasure2: String?,
    @SerializedName("strMeasure3") val strMeasure3: String?,
    @SerializedName("strMeasure4") val strMeasure4: String?,
    @SerializedName("strMeasure5") val strMeasure5: String?
) {
    fun getIngredients(): List<Pair<String, String>> {
        val ingredients = mutableListOf<Pair<String, String>>()
        if (!strIngredient1.isNullOrBlank()) ingredients.add(strIngredient1 to (strMeasure1 ?: ""))
        if (!strIngredient2.isNullOrBlank()) ingredients.add(strIngredient2 to (strMeasure2 ?: ""))
        if (!strIngredient3.isNullOrBlank()) ingredients.add(strIngredient3 to (strMeasure3 ?: ""))
        if (!strIngredient4.isNullOrBlank()) ingredients.add(strIngredient4 to (strMeasure4 ?: ""))
        if (!strIngredient5.isNullOrBlank()) ingredients.add(strIngredient5 to (strMeasure5 ?: ""))
        return ingredients
    }
}
