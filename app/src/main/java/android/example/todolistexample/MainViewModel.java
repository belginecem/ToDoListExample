package android.example.todolistexample;

import android.app.Application;
import android.example.todolistexample.database.AppDatabase;
import android.example.todolistexample.database.Post;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<Post>> posts;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        posts = database.taskDao().loadAllTasks();
    }

    public LiveData<List<Post>> getPosts(){
        return posts;
    }
}
