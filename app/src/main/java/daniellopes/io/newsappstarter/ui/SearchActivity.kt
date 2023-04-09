package daniellopes.io.newsappstarter.ui


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import daniellopes.io.newsappstarter.adapter.MainAdapter
import daniellopes.io.newsappstarter.databinding.ActivitySearchBinding
import daniellopes.io.newsappstarter.model.Article
import daniellopes.io.newsappstarter.model.data.NewsDataSource
import daniellopes.io.newsappstarter.presenter.ViewHome
import daniellopes.io.newsappstarter.presenter.search.SearchPresenter
import daniellopes.io.newsappstarter.util.UtilQueryTextListener


class SearchActivity : AppCompatActivity(), ViewHome.View {


    private val mainAdapter by lazy {
        MainAdapter()
    }
    private lateinit var binding: ActivitySearchBinding
    private  lateinit var presenter: SearchPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dataSource = NewsDataSource(this)
        presenter = SearchPresenter(this, dataSource)
        search()
        configRecycle()
        clickAdapter()


    }
    private  fun search(){
         binding.searchView.setOnQueryTextListener(
             UtilQueryTextListener(this.lifecycle) { newText ->
                 newText?.let { query ->
                     if (query.isNotEmpty()) {
                         presenter.search(query)
                         binding.rvProgressBarSearch.visibility = View.VISIBLE
                     }

                 }
             }
         )
        }


    private fun configRecycle() {
        with(binding.rvSearch) {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@SearchActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@SearchActivity, DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun clickAdapter(){
        mainAdapter.setOnClickListener { article ->
            val intent = Intent(this, ArticleActivity::class.java)
            intent.putExtra("article",article)
            startActivity(intent)

        }
    }


    override fun showProgressBar() {
        binding.rvProgressBarSearch.visibility = View.VISIBLE
    }

    override fun showFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun hideProgressBar() {
        binding.rvProgressBarSearch.visibility = View.INVISIBLE
    }



    override fun showArticles(article: List<Article>) {
        mainAdapter.differ.submitList(article.toList())
    }
}