package android.example.todolistexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.todolistexample.database.Post;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    static final String POST_KEY = "text";

    TextView todoBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        todoBody = findViewById(R.id.todo_body);

        Intent intent = getIntent();
        Post post = (Post) intent.getSerializableExtra(POST_KEY);

        todoBody.setText(post.getText());
    }
}
