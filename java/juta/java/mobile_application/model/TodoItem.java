package juta.java.mobile_application.model;


public class TodoItem {
    String todo_text;
    Boolean isTodo;

    public TodoItem(String todo_text, Boolean isTodo) {
        this.todo_text = todo_text;
        this.isTodo = isTodo;
    }

    public String getTodo_text() {
        return todo_text;
    }

    public void setTodo_text(String todo_text) {
        this.todo_text = todo_text;
    }

    public Boolean getTodo() {
        return isTodo;
    }

    public void setTodo(Boolean todo) {
        isTodo = todo;
    }
}
