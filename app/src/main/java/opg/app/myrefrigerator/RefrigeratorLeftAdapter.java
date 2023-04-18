package opg.app.myrefrigerator;

import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Adapter
public class RefrigeratorLeftAdapter extends RecyclerView.Adapter<RefrigeratorLeftAdapter.CustomViewHolder> {

    //황호준 - 데이터베이스 변수
    SQLiteDatabase sqlDB;
    private ArrayList<RefrigeratorLeftData> arrayList;
    //황호준 - 데이터베이스 삭제 연관 변수
    static String name="";
    static String date="";

    public RefrigeratorLeftAdapter(ArrayList<RefrigeratorLeftData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RefrigeratorLeftAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.refrigerator_left_item_list,parent,false);
        RefrigeratorLeftAdapter.CustomViewHolder holder = new RefrigeratorLeftAdapter.CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RefrigeratorLeftAdapter.CustomViewHolder holder, int position) {
        //황호준 - 데이터베이스 삭제 연관 변수
        this.name=arrayList.get(position).getTv_name();
        this.date=arrayList.get(position).getTv_data();

        holder.tv_name.setText(arrayList.get(position).getTv_name());
        holder.tv_number.setText(arrayList.get(position).getTv_number());
        holder.tv_date.setText(arrayList.get(position).getTv_data());
        holder.tv_where.setText(arrayList.get(position).getTv_where());

        // 클릭 시 구현
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curName = holder.tv_name.getText().toString();
                Toast.makeText(view.getContext(), "길게 누르면 삭제됩니다.",Toast.LENGTH_SHORT).show();

            }
        });

        // 롱클릭 시 구현 (삭제)
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //황호준 - 데이터 삭제
                sqlDB = RefrigeratorLeft.mySQLiteHelper.getWritableDatabase();
                sqlDB.execSQL("DELETE FROM ItemDataLeft WHERE itemName='" + name +"' AND itemdate ='"+ date +"';");
                sqlDB.close();
                remove(holder.getAdapterPosition());
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int position){
        try{
            arrayList.remove(position);
            notifyItemRemoved(position); // 새로고침
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }

    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView tv_name;
        protected TextView tv_number;
        protected TextView tv_date;
        protected TextView tv_where;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            this.tv_number = (TextView) itemView.findViewById(R.id.tv_number);
            this.tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            this.tv_where = (TextView) itemView.findViewById(R.id.tv_where);


        }
    }
}