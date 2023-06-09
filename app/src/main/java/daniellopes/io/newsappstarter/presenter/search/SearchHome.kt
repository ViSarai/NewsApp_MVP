package daniellopes.io.newsappstarter.presenter.search

import daniellopes.io.newsappstarter.model.NewsResponse

interface SearchHome {

    interface Presenter{
        fun search(term: String )
        fun onSucess(newsResponse: NewsResponse)
        fun onError(message : String)
        fun onComplete()
    }

}