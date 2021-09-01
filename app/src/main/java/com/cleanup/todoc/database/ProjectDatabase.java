package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

/**
 * Created by lleotraas on 18.
 */
@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class ProjectDatabase extends RoomDatabase {

    private static volatile ProjectDatabase INSTANCE;

    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    public static ProjectDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (ProjectDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ProjectDatabase.class, "TodocDatabase.db")
                                                    .addCallback(prepopulateDatabase())
                                                    .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues tartampion = new ContentValues();
                tartampion.equals(Project.getProjectById(1));

                ContentValues lucidia = new ContentValues();
                lucidia.put("id", Project.getProjectById(2).getId());
                lucidia.put("name", Project.getProjectById(2).getName());
                lucidia.put("color", Project.getProjectById(2).getColor());

                ContentValues circus = new ContentValues();
                circus.put("id", Project.getProjectById(3).getId());
                circus.put("name", Project.getProjectById(3).getName());
                circus.put("color", Project.getProjectById(3).getColor());
            }
        };
    }
}
