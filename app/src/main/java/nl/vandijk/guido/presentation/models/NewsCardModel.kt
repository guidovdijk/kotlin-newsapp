package nl.vandijk.guido.presentation.models

data class NewsCardModel(
    val Id:Int,
    val Title:String,
    val Image:String,
    val IsLiked: Boolean,
    val Url:String,
//    val categories: List<NewsCategories>,
//    val publishDate:String,
    val Summary:String?,
)