package com.example.todoshibataapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CompletedTodoAdapter extends RecyclerView.Adapter<CompletedTodoAdapter.CompletedTodoViewHolder> {
    private List<TodoItem> completedTodoList;

    public static class CompletedTodoViewHolder extends RecyclerView.ViewHolder {
        public TextView completedTodoText;

        public CompletedTodoViewHolder(View view) {
            super(view);
            completedTodoText = view.findViewById(R.id.completed_todo_text);
        }
    }

    public CompletedTodoAdapter(List<TodoItem> completedTodoList) {
        this.completedTodoList = completedTodoList;
    }

    @Override
    public CompletedTodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.completed_todo_item, parent, false);
        return new CompletedTodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CompletedTodoViewHolder holder, int position) {
        TodoItem todo = completedTodoList.get(position);
        holder.completedTodoText.setText(todo.getTitle() + " - " + todo.getDetails());
    }

    @Override
    public int getItemCount() {
        return completedTodoList.size();
    }
}
