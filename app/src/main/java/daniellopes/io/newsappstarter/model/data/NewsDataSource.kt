package daniellopes.io.newsappstarter.model.data


import android.annotation.SuppressLint
import android.content.Context
import daniellopes.io.newsappstarter.model.Article
import daniellopes.io.newsappstarter.model.db.ArticleDataBase
import daniellopes.io.newsappstarter.network.RetrofitInstance
import daniellopes.io.newsappstarter.presenter.favorite.FavoriteHome
import daniellopes.io.newsappstarter.presenter.news.NewsPresenter
import daniellopes.io.newsappstarter.presenter.search.SearchHome
import kotlinx.coroutines.*

class NewsDataSource(context: Context) {

        private val db: ArticleDataBase = ArticleDataBase(context)
        private  var newsRepository: NewsRepository = NewsRepository(db)


    fun searchNews(term : String, callback : SearchHome.Presenter){
        GlobalScope.launch ( Dispatchers.Main ) {
            val response = RetrofitInstance.api.searchNews(term)
            if (response.isSuccessful) {
                response.body()?.let { newsResponse ->
                    callback.onSucess(newsResponse)

                }

                callback.onComplete()
            }else{
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }


        @SuppressLint("SuspiciousIndentation")
        fun getBreakingNews(callback: NewsPresenter){
            GlobalScope.launch ( Dispatchers.Main ){
             val response = RetrofitInstance.api.getBreakingNews("us")
                if(response.isSuccessful){
                    response.body()?.let {newsResponse ->
                        callback.onSucess(newsResponse)
                    }
                    callback.onComplete()
                }else{
                    callback.onError(response.message())
                    callback.onComplete()
                }
            }



        }


            fun saveArticle(article: Article){
                GlobalScope.launch(Dispatchers.Main){
                    newsRepository.updateInsert(article)
                }
            }


            fun getAllArticle(callback:FavoriteHome.Presenter){
                var allArticles: List<Article>
                CoroutineScope(Dispatchers.IO).launch {
                    allArticles = newsRepository.getAll()
                    withContext(Dispatchers.Main){
                        callback.onSuccess(allArticles)
                    }
                }
            }
            fun deleteArticle(article: Article?){
                GlobalScope.launch(Dispatchers.Main){
                    article?.let { articleSafe ->

                        newsRepository.delete(articleSafe)

                    }
                }
            }
                }
