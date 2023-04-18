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

public class BaguniAdapter extends RecyclerView.Adapter<BaguniAdapter.CustomViewHolder> {

    private ArrayList<BaguniData> arrayList;

    //황호준 - 데이터베이스 변수
    SQLiteDatabase sqlDB;
    //황호준 - 데이터베이스 삭제 연관 변수
    static String name="";

    public BaguniAdapter(ArrayList<BaguniData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public BaguniAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.baguni_item_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaguniAdapter.CustomViewHolder holder, int position) {
        name=arrayList.get(position).getTv_name();

        holder.tv_name.setText(arrayList.get(position).getTv_name());

        // 클릭 시 구현
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curName = holder.tv_name.getText().toString();
                Toast.makeText(view.getContext(), "길게 누르면 삭제됩니다.",Toast.LENGTH_SHORT).show();
                //   Toast.makeText(view.getContext(), curName,Toast.LENGTH_SHORT).show();
            }
        });

        // 롱클릭 시 구현 (삭제)
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //황호준 - 데이터 삭제
                sqlDB = Baguni.mySQLiteHelper.getWritableDatabase();
                sqlDB.execSQL("DELETE FROM guni WHERE itemName='" + name+"';");
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

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);


        }
    }
}