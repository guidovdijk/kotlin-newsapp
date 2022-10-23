package nl.vandijk.guido.data

import nl.vandijk.guido.presentation.models.ArticleResultModel
import nl.vandijk.guido.presentation.models.NewsCardModel

class ArticleMapper {
    fun mapListPager(entity: ArticleResultEntity): Result<ArticleResultModel> = runCatching {
        with(entity) {
            ArticleResultModel(
                Results = mapResults(Results).getOrThrow(),
                NextId = NextId
            )
        }
    }

    fun mapList(entityList: List<ArticleEntity>): Result<List<NewsCardModel>> = runCatching {
        entityList.map {
            map(it).getOrThrow()
        }
    }

    fun mapResults(entities: List<ArticleEntity>): Result<List<NewsCardModel>> = runCatching {
        entities.map {
            with(it) {
                NewsCardModel(
                    Id = Id!!,
                    Title = Title!!,
                    IsLiked = IsLiked!!,
                    Image = Image!!,
                    Summary = Summary!!,
                    Url = Url!!,
                )
            }
        }
    }

    fun map(entity: ArticleEntity): Result<NewsCardModel> = runCatching {
        with(entity) {
            NewsCardModel(
                Id = Id!!,
                Title = Title!!,
                IsLiked = IsLiked!!,
                Image = Image!!,
                Summary = Summary!!,
                Url = Url!!,
            )
        }
    }
}