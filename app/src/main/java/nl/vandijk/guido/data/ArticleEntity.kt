package nl.vandijk.guido.data

data class ArticleEntity(
    val Id:Int,
    val Title:String,
    val Image:String,
    val IsLiked: Boolean,
    val Summary: String?,
    val Url:String?,
)