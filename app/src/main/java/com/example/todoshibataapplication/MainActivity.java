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

    private static final int ADD_TODO_REQUEST = 1; // Todoを追加するリクエストコード
    public static final int EDIT_TODO_REQUEST = 2; // Todoを編集するリクエストコード
    public static final String TAG = "MainActivity";

    // Todoアイテムのリスト
    private List<TodoItem> todoList;
    // RecyclerView用のアダプタ
    private TodoAdapter todoAdapter;
    // 完了済みTodoアイテムのリスト
    private  List<TodoItem> completedTodoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // レイアウトを設定

        // RecyclerViewの設定
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView completedRecyclerView = findViewById(R.id.completed_recycler_view);
        todoList = new ArrayList<>(); // Todoリストの初期化
        // サンプルデータの追加
        todoList.add(new TodoItem("Todo1", "test1", "2024-10-10"));
        todoList.add(new TodoItem("Todo2", "test2", "2024-11-11"));
        todoList.add(new TodoItem("Todo3", "test3", "2024-12-12"));

        completedTodoList = new ArrayList<>();

        // ボタンの取得
        Button addButton = findViewById(R.id.add_button);
        Button completeButton = findViewById(R.id.complete_button);
        Button deleteButton = findViewById(R.id.delete_button);

        // RecyclerViewにアダプタを設定
        todoAdapter = new TodoAdapter(todoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //リニアレイアウトマネージャを設定
        recyclerView.setAdapter(todoAdapter); // アダプタをRecyclerViewに設定

        // 完了済みタスクのアダプタを設定
        CompletedTodoAdapter completedTodoAdapter = new CompletedTodoAdapter(completedTodoList);
        completedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        completedRecyclerView.setAdapter(completedTodoAdapter);

        // DeviderItemDecorationの追加(アイテム間に区切り線を表示するため)
        recyclerView.addItemDecoration(new DividerItemDecoration(this));

        // 追加ボタンのクリックリスナーを設定
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Todo追加画面に遷移
                Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
                startActivityForResult(intent, ADD_TODO_REQUEST); // 結果を受け取るためのリクエスト
            }
        });

        // 完了ボタンのクリックリスナー
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // チェックされているTodoアイテムを完了済みリストに移動
                List<TodoItem> completedItems = todoAdapter.getCompletedItems();

                if(completedItems.isEmpty()) { // completedItemsが空ならメッセージを表示し、そのままreturnする
                    Toast.makeText(MainActivity.this, "完了したTodoを選択してください", Toast.LENGTH_SHORT).show();
                    return;
                }

                completedTodoList.addAll(completedItems);
                todoList.removeAll(completedItems);
                todoAdapter.notifyDataSetChanged();
                completedTodoAdapter.notifyDataSetChanged();
            }
        });

        // 削除ボタンのクリックリスナー
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // チェックされているTodoアイテムを削除
                List<TodoItem> itemsDelete = todoAdapter.getCompletedItems();
                if(itemsDelete.isEmpty()) { // itemsDeleteが空ならメッセージを表示し、そのままreturnする
                    Toast.makeText(MainActivity.this, "削除するTodoを選択してください", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Todoリストから削除
                todoList.removeAll(itemsDelete);
                todoAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Todoを削除しました", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 追加・編集
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TODO_REQUEST && resultCode == RESULT_OK) { // Todo追加の結果を処理
            TodoItem newTodo = (TodoItem) data.getSerializableExtra("newTodo"); // 追加されたTodoを取得
            if (newTodo != null) {
                todoList.add(newTodo); // Todoリストに追加
                todoAdapter.notifyItemInserted(todoList.size() - 1); // アダプタに新しいアイテムの追加を通知
            }
        }else if (requestCode == EDIT_TODO_REQUEST && resultCode == RESULT_OK) { // Todo編集の結果を処理
            TodoItem updatedTodo = (TodoItem) data.getSerializableExtra("updatedTodo"); // 更新されたTodoを取得
            int position = data.getIntExtra("position", -1); // 更新されたアイテムの位置を取得
            if (updatedTodo != null && position != -1) {
                todoList.set(position, updatedTodo); // 指定位置のTodoを更新
                todoAdapter.notifyItemChanged(position); // アダプタにアイテムの変更を通知
            }
        }
    }
}
