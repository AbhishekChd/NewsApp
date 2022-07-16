package com.example.abhishek.newsapp.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;

import com.example.abhishek.newsapp.data.dao.HeadlinesDao;
import com.example.abhishek.newsapp.data.dao.SavedDao;
import com.example.abhishek.newsapp.data.dao.SourcesDao;
import com.example.abhishek.newsapp.models.Article;
import com.example.abhishek.newsapp.models.SavedArticle;
import com.example.abhishek.newsapp.models.Source;

@Database(entities = {Article.class, Source.class, SavedArticle.class},
        version = 1,
        exportSchema = false)
@TypeConverters(DatabaseConverters.class)
public abstract class NewsDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "news";
    private static NewsDatabase sInstance;

    public static NewsDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        NewsDatabase.class,
                        DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract HeadlinesDao headlinesDao();

    public abstract SourcesDao sourcesDao();

    public abstract SavedDao savedDao();
}
