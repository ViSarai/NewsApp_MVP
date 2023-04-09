package daniellopes.io.newsappstarter.model.data

import daniellopes.io.newsappstarter.model.Article
import daniellopes.io.newsappstarter.model.db.ArticleDataBase

class NewsRepository (private val db : ArticleDataBase) {

    suspend fun updateInsert(article: Article) = db.getArticleDao().updateInsert(article)


    fun getAll(): List<Article> = db.getArticleDao().getAll()

    suspend fun delete(article: Article) = db.getArticleDao().delete(article)

}