package com.example.todoshibataapplication;

import java.io.Serializable;

public class TodoItem implements Serializable {
    private String title;
    private String details;
    private String dueDate;
    private boolean isCompleted;

    // コンストラクタ、ゲッタ、セッタを作成
    public TodoItem(String title, String details, String dueDate) {
        this.title = title;
        this.details = details;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    // ゲッタとセッタ
    public String getTitle() { return title; }
    public String getDetails() { return details; }
    public String getDueDate() { return dueDate; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
    public void setTitle(String title) { this.title = title; }
    public void setDetails(String details) { this.details = details; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
}
