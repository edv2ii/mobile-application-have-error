package juta.java.mobile_application;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
public class MyViewHolder extends RecyclerView.ViewHolder {

    // ประกาศตัวแปรสำหรับ View ต่าง ๆ ตาม id ใน XML
    ImageView isTodoImageView;
    TextView todoTextView;
    ImageView todoDeleteImageView;

    // Constructor ที่รับ View และทำการเชื่อมโยง ID กับตัวแปร
    public MyViewHolder(View itemView) {
        super(itemView);

        // เชื่อมโยง View กับ ID ที่ประกาศใน XML
        isTodoImageView = itemView.findViewById(R.id.isTodo);
        todoTextView = itemView.findViewById(R.id.todo_text);
        todoDeleteImageView = itemView.findViewById(R.id.todo_delete);
    }
}
