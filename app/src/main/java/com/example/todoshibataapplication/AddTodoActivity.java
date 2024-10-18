package com.example.todoshibataapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddTodoActivity extends AppCompatActivity {
    private EditText todoTitleEditText;
    private EditText todoDetailsEditText;
    private EditText todoDueDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_todo);

        todoTitleEditText = findViewById(R.id.todo_title);
        todoDetailsEditText = findViewById(R.id.todo_details);
        todoDueDateEditText = findViewById(R.id.todo_due_date);
        Button saveTodoButton = findViewById(R.id.save_todo_button);
        Button selectDueDateButton = findViewById(R.id.select_due_date_button);
        Button canselButton = findViewById(R.id.cancel_button);

        saveTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 入力されたデータを取得
                String title = todoTitleEditText.getText().toString();
                String details = todoDetailsEditText.getText().toString();
                String dueDate = todoDueDateEditText.getText().toString();

                // 入力が空でないことをチェック
                if (title.isEmpty() || details.isEmpty() || dueDate.isEmpty()) {
                    Toast.makeText(AddTodoActivity.this, "すべてのフィールドを入力してください", Toast.LENGTH_SHORT).show();
                    return; // 何も追加せずに終了
                }

                // TODOアイテムを作成
                TodoItem newTodo = new TodoItem(title, details, dueDate);

                // MainActivityに結果を返す
                Intent resultIntent = new Intent();
                resultIntent.putExtra("newTodo", newTodo);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        selectDueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // DatePickerDialogを表示
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTodoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        String dueDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                        todoDueDateEditText.setText(dueDate);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        canselButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
