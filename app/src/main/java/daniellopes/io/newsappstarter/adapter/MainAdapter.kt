package daniellopes.io.newsappstarter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import daniellopes.io.newsappstarter.databinding.ItensNewsBinding
import daniellopes.io.newsappstarter.model.Article


class MainAdapter() : RecyclerView.Adapter<MainAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val binding: ItensNewsBinding) : ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return  oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(
            ItensNewsBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        with(holder){
            with(differ.currentList[position]){
                Glide.with(holder.itemView.context).load(urlToImage).into(binding.ivArticleView)
                binding.tvTitle.text = author ?: source?.name
                binding.tvSource.text = source?.name ?: author
                binding.tvDescription.text = description
                binding.tvPublishedAt.text = publishedAt

                holder.itemView.setOnClickListener {
                  onItemOnClickListener?.let {click ->
                      click(this)
                  }
                }
            }
        }




    }

    private  var onItemOnClickListener : ((Article) -> Unit)? = null

    fun  setOnClickListener(listener:(Article) -> Unit){
        onItemOnClickListener = listener
    }

}




