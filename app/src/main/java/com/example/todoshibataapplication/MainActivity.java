package com.example.todoshibataapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_TODO_REQUEST = 1;
    public static final String TAG = "MainActivity";
    private List<TodoItem> todoList;
    private LinearLayout todoListLayout;

 /*
    private RecyclerView recyclerView;
    private TodoAdapter todoAdapter;
    private List<String> todoList;
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      recyclerView = findViewById(R.id.recycler_view);
        todoListLayout = findViewById(R.id.todo_list_layout);
        todoList = new ArrayList<>();
        Button addButton = findViewById(R.id.add_button);
        Button editButton = findViewById(R.id.edit_button);

//      todoAdapter = new TodoAdapter(todoList);
//      recyclerView.setLayoutManager(new LinearLayoutManager(this));
//      recyclerView.setAdapter(todoAdapter);

       addButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent= new Intent(MainActivity.this, AddTodoActivity.class);
               startActivityForResult(intent, ADD_TODO_REQUEST);
           }
       });

       editButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Log.i(TAG, "onClick Start");
               Intent intent = new Intent(MainActivity.this, EditTodoActivity.class);
               startActivity(intent);
               Log.i(TAG, "intent end");
           }
       });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TODO_REQUEST && resultCode == RESULT_OK) {
            // 新しいTodoが返された場合
            TodoItem newTodo = (TodoItem) data.getSerializableExtra("newTodo");
            if (newTodo != null) {
                todoList.add(newTodo); // Todoリストに追加
                addTodoToLayout(newTodo);

            }
        }
    }

    private void addTodoToLayout(TodoItem todo) {
        // 新しいチェックボックスとテキストビューを作成
        LinearLayout todoLayout = new LinearLayout(this);
        todoLayout.setOrientation(LinearLayout.HORIZONTAL);

        todoLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        CheckBox todoCheckbox = new CheckBox(this);
        todoCheckbox.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // タイトルと内容、期日を表示するTextViewを作成
        TextView todoText = new TextView(this);
        String displayText = todo.getTitle() + " - " + todo.getDetails() + " (" + todo.getDueDate() + ")";
        todoText.setText(displayText);
        todoText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // 削除ボタンを作成
        Button deleteButton = new Button(this);
        deleteButton.setText("削除");
        deleteButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // 削除ボタンのクリックリスナー
        deleteButton.setOnClickListener(v -> {
            todoListLayout.removeView(todoLayout); // リストからTodoを削除
            todoList.remove(todo); // 内部リストからも削除
            Toast.makeText(MainActivity.this, "Todoが削除されました", Toast.LENGTH_SHORT).show();
        });

        // チェックボックスの状態を設定
        todoCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            todo.setCompleted(isChecked);
        });

        // レイアウトに追加
        todoLayout.addView(todoCheckbox);
        todoLayout.addView(todoText);
        todoLayout.addView(deleteButton);

        // Todoリストレイアウトに追加
        todoListLayout.addView(todoLayout);
    }


}