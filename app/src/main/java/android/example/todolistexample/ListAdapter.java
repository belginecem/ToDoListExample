package android.example.todolistexample;

import android.example.todolistexample.database.Post;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private List<Post> posts;
    private OnNoteListener mOnNoteListener;

    public ListAdapter(List<Post> posts, OnNoteListener onNoteListener) {
        this.posts = posts;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.todoTitle.setText(posts.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public List<Post> getTasks() {
        return posts;
    }

    public void updateItems(List<Post> posts) {
        this.posts.clear();
        this.posts.addAll(posts);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView todoTitle;
        OnNoteListener onNoteListener;

        MyViewHolder(View view, OnNoteListener onNoteListener) {
            super(view);
            todoTitle = view.findViewById(R.id.todo_title);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(posts.get(getAdapterPosition()));
        }
    }

    public interface OnNoteListener {
        void onNoteClick(Post post);
    }

}
