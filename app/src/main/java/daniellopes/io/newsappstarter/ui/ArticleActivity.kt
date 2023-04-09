package daniellopes.io.newsappstarter.ui

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import daniellopes.io.newsappstarter.R
import daniellopes.io.newsappstarter.databinding.ActivityArticleBinding
import daniellopes.io.newsappstarter.databinding.ActivityMainBinding
import daniellopes.io.newsappstarter.model.Article
import daniellopes.io.newsappstarter.model.data.NewsDataSource
import daniellopes.io.newsappstarter.presenter.ViewHome
import daniellopes.io.newsappstarter.presenter.favorite.FavoritePresenter



class ArticleActivity : AppCompatActivity(), ViewHome.Favorite {
    private lateinit var binding: ActivityArticleBinding
    private lateinit var article: Article
    private lateinit var presenter: FavoritePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityArticleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getArticle()



        val dataSource = NewsDataSource(this)
        presenter = FavoritePresenter(this,dataSource)
        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let {url ->
                loadUrl(url)
            }
        }

        binding.fab.setOnClickListener{
            presenter.saveArticle(article)
            Snackbar.make(it,R.string.article_saved_successful,
                Snackbar.LENGTH_SHORT
            ).show()
        }

    }






    private fun getArticle() {
        intent.extras?.let { articleSend ->
            article = articleSend.get("article") as Article
        }
    }

    override fun showArticles(article: List<Article>) {}
}
