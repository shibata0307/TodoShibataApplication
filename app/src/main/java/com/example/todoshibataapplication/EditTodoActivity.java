package com.example.todoshibataapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EditTodoActivity extends AppCompatActivity {

//  Button backButton = findViewById(R.id.back_button);
    public static final String TAG = "EditTodoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        Button backButton = findViewById(R.id.back_button);
        Log.i(TAG, "onCreate Start");
        // Todo編集のロジックをここに追加








       if (backButton != null) {
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "finish");
                    finish();
                }
            });
        } else {
            Log.e(TAG, "Back button is null");
        }


    }


}
