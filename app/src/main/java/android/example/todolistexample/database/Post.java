package android.example.todolistexample.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "task")
public class Post implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int id;

    public Post(String text) {
        this.text = text;
    }

    @SerializedName("body")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}





