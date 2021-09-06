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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lleotraas on 18.
 */
@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class ProjectDatabase extends RoomDatabase {

    private static volatile ProjectDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    public static ProjectDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (ProjectDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ProjectDatabase.class, "TodocDatabase.db")
                                                    .addCallback(sRoomDatabaseCallback)
                                                    .build();
                }
            }
        }
        return INSTANCE;
    }

//    private static Callback prepopulateDatabase() {
//        return new Callback() {
//            @Override
//            public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                super.onCreate(db);
//
//                ContentValues tartampion = new ContentValues();
//                tartampion.put("id", 1L);
//                tartampion.put("name", "Projet Tartampion");
//                tartampion.put("color", 0xFFEADAD1);
//
//                ContentValues lucidia = new ContentValues();
//                lucidia.put("id", 2L);
//                lucidia.put("name", "Projet Lucidia");
//                lucidia.put("color", 0xFFB4CDBA);
//
//                ContentValues circus = new ContentValues();
//                circus.put("id", 3L);
//                circus.put("name", "Projet Circus");
//                circus.put("color", 0xFFA3CED2);
//            }
//        };
//    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(()->{
                ProjectDao projectDao = INSTANCE.projectDao();
                projectDao.createProject(Project.getProjectById(1));
                projectDao.createProject(Project.getProjectById(2));
                projectDao.createProject(Project.getProjectById(3));
            });
        }
    };
}
