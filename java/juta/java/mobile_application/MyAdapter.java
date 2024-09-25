package juta.java.mobile_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import juta.java.mobile_application.controller.LocalStorageManage;
import juta.java.mobile_application.model.TodoItem;
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<TodoItem> items;
    private LocalStorageManage storage;

    // เพิ่ม Context ใน constructor เพื่อใช้สร้าง LocalStorageManage
    public MyAdapter(List<TodoItem> items) {
        this.items = items;
        this.storage = storage;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TodoItem currentItem = items.get(position);
        holder.textView.setText(currentItem.getTodo_text());


        // ตรวจสอบสถานะของ todo แล้วเปลี่ยนไอคอนตามสถานะ
        if (currentItem.getTodo()) {
            holder.isTodoImageView.setImageResource(R.drawable.heart_after); // ไอคอนเมื่อเสร็จ
        } else {
            holder.isTodoImageView.setImageResource(R.drawable.heart_before); // ไอคอนเมื่อยังไม่เสร็จ
        }

        // เมื่อคลิกที่ isTodoImageView ให้เปลี่ยนสถานะของงาน
        holder.isTodoImageView.setOnClickListener(v -> {
            boolean newStatus = !currentItem.getTodo();
            currentItem.setTodo(newStatus);

            // บันทึกสถานะใหม่ลงใน LocalStorageManage
            storage.saveIsTodo(currentItem.getTodo_text(), newStatus);

            // อัปเดต UI เพื่อเปลี่ยนไอคอน
            notifyItemChanged(position);
        });

        // การลบรายการเมื่อกดปุ่ม deleteImageView
        holder.deleteImageView.setOnClickListener(v -> {
            items.remove(position);
            notifyItemRemoved(position);

            // บันทึกการเปลี่ยนแปลงลงใน LocalStorageManage หลังจากลบ
            storage.saveTodoToLocalStorage(items);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView isTodoImageView;
        TextView textView;
        ImageView deleteImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            isTodoImageView = itemView.findViewById(R.id.isTodo);
            textView = itemView.findViewById(R.id.todo_text);
            deleteImageView = itemView.findViewById(R.id.todo_delete);
        }
    }
}
