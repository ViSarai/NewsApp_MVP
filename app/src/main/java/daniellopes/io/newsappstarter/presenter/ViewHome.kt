package daniellopes.io.newsappstarter.presenter

import daniellopes.io.newsappstarter.model.Article

interface ViewHome {

    interface  View{
        fun showProgressBar()
        fun showFailure (message : String)
        fun hideProgressBar()
        fun showArticles(article:List<Article>)
    }


    interface Favorite {
        fun showArticles(article:List<Article>)
    }
}
