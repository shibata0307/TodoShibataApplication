package com.example.todoshibataapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditTodoActivity extends AppCompatActivity {

//  Button backButton = findViewById(R.id.back_button);
    public static final String TAG = "EditTodoActivity";

    private EditText editTitle;
    private EditText editDetails;
    private EditText editDueDate;
    private Button saveButton;
    private  Button canselButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        editTitle = findViewById(R.id.edit_todo_title);
        editDetails = findViewById(R.id.edit_todo_details);
        editDueDate = findViewById(R.id.edit_todo_due_date);
        saveButton = findViewById(R.id.save_button);
        canselButton = findViewById(R.id.cansel_button);

        
        Log.i(TAG, "onCreate Start");

        // TodoアイテムのデータをIntentから取得
        TodoItem todoItem = (TodoItem) getIntent().getSerializableExtra("todoItem");
        int position = getIntent().getIntExtra("position", -1);

        if (todoItem != null) {
            // EditTextにデータをセット
            editTitle.setText(todoItem.getTitle());
            editDetails.setText(todoItem.getDetails());
            editDueDate.setText(todoItem.getDueDate());
        }

        editDueDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 入力された日付形式をチェック
                if(!isValidDate(editable.toString())) {
                    editDueDate.setError("日付はYYYY-MM-DD形式で入力してください");
                }
            }
        });

        saveButton.setOnClickListener(v -> {
            if (position != -1) {
                // Todoアイテムを更新
                todoItem.setTitle(editTitle.getText().toString());
                todoItem.setDetails(editDetails.getText().toString());

                String dueDate = editDueDate.getText().toString();
                if(!isValidDate(dueDate)) {
                    Toast.makeText(this, "日付はYYYY-MM-DD形式で入力してください", Toast.LENGTH_SHORT).show();
                    return;
                }
                todoItem.setDueDate(dueDate);

                // 更新したTodoアイテムをMainActivityに返す
                Intent resultIntent = new Intent();
                resultIntent.putExtra("updatedTodo", todoItem);
                resultIntent.putExtra("position", position);
                setResult(RESULT_OK, resultIntent);
                finish(); // Activityを終了
            }
        });

        canselButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // 日付形式をチェックするメソッド
    private boolean isValidDate(String date) {
        String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";
        Pattern pattern = Pattern.compile(datePattern);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }
}
