package com.example.todoshibataapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// TodoAdapterはRecyclerView.Adapterを拡張し、Todoアイテムのリストを表示する
public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<TodoItem> todoList;

    // ViewHolderクラスは、RecyclerViewの各アイテムのUIコンポーネントを保持する
    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        public CheckBox todoCheckbox;
        public TextView todoText;
        public  Button editButton;
//        public Button deleteButton;

        // ViewHolderのコンストラクタ
        public TodoViewHolder(View view) {
            super(view);
            // UIコンポーネントをViewから取得
            todoCheckbox = view.findViewById(R.id.todo_checkbox);
            todoText = view.findViewById(R.id.todo_text);
            editButton = view.findViewById(R.id.edit_button);
//            deleteButton = view.findViewById(R.id.delete_button);
        }
    }

    // コンストラクタでTodoアイテムのリストを受け取る
    public TodoAdapter(List<TodoItem> todoList) {
        this.todoList = todoList;
    }

    // ViewHolderを生成するメソッド
    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item, parent, false);
        return new TodoViewHolder(view);
    }

    // ViewHolderにデータをバインドするメソッド
    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        // 現在のアイテムを取得
        TodoItem todo = todoList.get(position);
        // Todoのタイトル、詳細、期日を表示
        holder.todoText.setText(todo.getTitle() + " - " + todo.getDetails() + " (" + todo.getDueDate() + ")");
        // チェックボックスの状態を設定
        holder.todoCheckbox.setChecked(todo.isCompleted());

        // チェックボックスの状態変更リスナーを設定
        holder.todoCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            todo.setCompleted(isChecked);
        });

        // 編集ボタンのクリックリスナーを設定
        holder.editButton.setOnClickListener(v -> {
            // 編集画面に遷移するためのIntentを作成
            Intent intent = new Intent(holder.itemView.getContext(), EditTodoActivity.class);
            intent.putExtra("todoItem", todo); // TodoItemを渡す
            intent.putExtra("position", position); // 位置も渡す
            ((MainActivity) holder.itemView.getContext()).startActivityForResult(intent, MainActivity.EDIT_TODO_REQUEST);
        });
/*
        // 削除ボタンのクリックリスナーを設定
        holder.deleteButton.setOnClickListener(v -> {
            todoList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, todoList.size());
        });

*/
    }

    // リストのアイテム数を返すメソッド
    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public List<TodoItem> getCompletedItems() {
        List<TodoItem> completedItems = new ArrayList<>();
        for (TodoItem todo : todoList) {
            if (todo.isCompleted()) {
                completedItems.add(todo);
            }
        }
        return completedItems;
    }
}

