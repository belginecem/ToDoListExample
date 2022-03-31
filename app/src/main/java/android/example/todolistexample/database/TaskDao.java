package android.example.todolistexample.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    LiveData<List<Post>> loadAllTasks();

    @Insert
    void insertTask(Post post);

    @Delete
    void deleteTask(Post post);
}
