package daniellopes.io.newsappstarter.model.db

import android.content.Context
import androidx.room.*
import daniellopes.io.newsappstarter.model.Article


@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticleDataBase : RoomDatabase() {

    abstract fun  getArticleDao(): ArticleDao

    companion object{

        @Volatile
        private var instance: ArticleDataBase? = null
        private val lock = Any()



        operator fun invoke(context: Context) = instance ?: synchronized(lock){
             instance ?: createDataBase(context).also { articleDataBase ->
             instance = articleDataBase

             }
        }

        private fun createDataBase(context: Context) =
            Room.databaseBuilder(

                context.applicationContext,
                ArticleDataBase ::class.java,
                "article_db.db")
                    .build()


        }
    }

