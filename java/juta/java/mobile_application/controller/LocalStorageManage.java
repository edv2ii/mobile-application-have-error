package juta.java.mobile_application.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import juta.java.mobile_application.model.TodoItem;

public class LocalStorageManage {

    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS = "localStorage";
    private static final String TODO_KEY = "todo_list";

    // Constructor to initialize SharedPreferences
    public LocalStorageManage(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }

    // Method to save todo item
    public void saveTodoToLocalStorage(List<TodoItem> todoItems) {
        StringBuilder updatedTodos = new StringBuilder();

        for (TodoItem item : todoItems) {
            updatedTodos.append(item.getTodo_text()).append("|").append(item.getTodo()).append("\n");
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TODO_KEY, updatedTodos.toString().trim()); // บันทึกเฉพาะข้อมูลใหม่
        editor.apply();
    }


    public List<TodoItem> loadTodosFromLocalStorage() {
        String savedTodos = sharedPreferences.getString(TODO_KEY, "");
        List<TodoItem> todoItems = new ArrayList<>();

        if (!savedTodos.isEmpty()) {
            String[] todosArray = savedTodos.split("\n");
            for (String todoEntry : todosArray) {
                String[] parts = todoEntry.split("\\|"); // Split by pipe
                if (parts.length == 2) {
                    String todoText = parts[0];
                    boolean isTodo = Boolean.parseBoolean(parts[1].trim());
                    todoItems.add(new TodoItem(todoText, isTodo));
                }
            }
        }
        return todoItems;
    }


    public void clearLocalStorage() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TODO_KEY); // Remove the todo list
        editor.apply(); // Save changes
    }

    // Method to save the isTodo state for a specific todo item
    public void saveIsTodo(String todoText, boolean isTodo) {
        List<TodoItem> todoItems = loadTodosFromLocalStorage();
        boolean itemFound = false;

        // Search for the item and update its status
        for (TodoItem item : todoItems) {
            if (item.getTodo_text().equals(todoText)) {
                item.setTodo(isTodo); // Update the todo state
                itemFound = true;
                break;
            }
        }

        // If the item was not found, add a new one
        if (!itemFound) {
            todoItems.add(new TodoItem(todoText, isTodo));
        }

        // Log a message for debugging
        Log.d("LocalStorageManage", itemFound ? "Updated todo: " + todoText : "Added new todo: " + todoText);

        // Save the updated list back to local storage
        saveTodoToLocalStorage(todoItems);
    }






}
