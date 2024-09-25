package juta.java.mobile_application;

import android.os.Bundle; // Make sure to import the Bundle class
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import juta.java.mobile_application.controller.LocalStorageManage;
import juta.java.mobile_application.model.TodoItem;

public class AddTodos extends AppCompatActivity {

    private EditText inputTodo;
    private Button inputButton;
    private LocalStorageManage storage;

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<TodoItem> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todos);

        inputTodo = findViewById(R.id.input_todo);
        inputButton = findViewById(R.id.input_button);

        // Initialize LocalStorageManage class
        storage = new LocalStorageManage(this);

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Initialize the adapter and pass context (this)
        myAdapter = new MyAdapter(itemList);
        recyclerView.setAdapter(myAdapter);

        // Load data from localStorage
        loadTodos();

        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoText = inputTodo.getText().toString();
                boolean isTodo = false; // Set the initial status

                if (!todoText.isEmpty()) {
                    // Check if the item already exists
                    boolean exists = false;
                    for (TodoItem item : itemList) {
                        if (item.getTodo_text().equals(todoText)) {
                            exists = true;
                            break;
                        }
                    }

                    // If the item doesn't exist, add it
                    if (!exists) {
                        // Add new item to the list
                        itemList.add(new TodoItem(todoText, isTodo));

                        // Save the updated list to local storage
                        storage.saveTodoToLocalStorage(itemList);

                        // Update adapter
                        myAdapter.notifyDataSetChanged();
                    }

                    // Clear EditText after adding
                    inputTodo.setText("");
                }
            }
        });
    }


    private void loadTodos() {
            // Load saved todos
            itemList.clear(); // Clear existing items
            itemList.addAll(storage.loadTodosFromLocalStorage()); // Load the list of TodoItems

            // Notify adapter to refresh the data
            myAdapter.notifyDataSetChanged();
        }
}
