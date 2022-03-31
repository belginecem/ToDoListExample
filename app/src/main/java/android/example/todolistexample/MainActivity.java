package android.example.todolistexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.example.todolistexample.database.AppDatabase;
import android.example.todolistexample.database.Post;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListAdapter.OnNoteListener {
    public static RecyclerView listTodo;
    private ListAdapter listAdapter;
    EditText itemEditText;
    private TextView textViewResult;
    private List<Post> posts;
    private String newTodo;
    //public static final String EXTRA_TASK_ID = "extraTaskId";
    //public static final String INSTANCE_TASK_ID = "instanceTaskId";
    private static final int DEFAULT_TASK_ID = -1;
    private int mTaskId = DEFAULT_TASK_ID;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.fetched_data);
        listTodo = findViewById(R.id.list_todo);


        itemEditText = findViewById(R.id.newItem);

        posts = new ArrayList<>();
        mDb = AppDatabase.getInstance(getApplicationContext());

        listAdapter = new ListAdapter(posts, this);
        listTodo.setLayoutManager(new LinearLayoutManager(this));
        listTodo.setAdapter(listAdapter);

        mDb = AppDatabase.getInstance((getApplicationContext()));
        setupViewModel();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Post> posts = listAdapter.getTasks();
                        mDb.taskDao().deleteTask(posts.get(position));
                    }
                });
            }
        }).attachToRecyclerView(listTodo);
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                listAdapter.updateItems(posts);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void onAddButtonClicked(View view) {
        try {
            String text = itemEditText.getText().toString();
            final Post post = new Post(text);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.taskDao().insertTask(post);

                }
            });
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "The field is empty",
                    Toast.LENGTH_SHORT).show();
        }
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        itemEditText.setText("");
    }

    @Override
    public void onNoteClick(Post post) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.POST_KEY, post);
        startActivity(intent);
    }
}