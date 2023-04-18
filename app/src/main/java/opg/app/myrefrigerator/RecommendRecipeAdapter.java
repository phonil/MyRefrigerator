package opg.app.myrefrigerator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecommendRecipeAdapter extends RecyclerView.Adapter<RecommendRecipeAdapter.CustomViewHolder> {

    private ArrayList<RecommendRecipeData> arrayList;

    public RecommendRecipeAdapter(ArrayList<RecommendRecipeData> arrayList) {
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public RecommendRecipeAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_recipe_item_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.ingredientName_tv.setText(arrayList.get(position).getIngredientName_tv());
        holder.ingredientNumber_tv.setText(arrayList.get(position).getIngredientNumber_tv());
        holder.ingredientDate_tv.setText(arrayList.get(position).getIngredientDate_tv());
        // holder.select_cb.setText(arrayList.get(position).getIngredientName_tv());

        // 클릭 시 구현
        holder.itemView.setTag(holder.getAdapterPosition());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = holder.select_cb.isChecked();
                if(checked){
                    holder.select_cb.setChecked(false);
                    for(int i=0;i<arrayList.size();i++){
                        if(!arrayList.get(i).isSelect_cb()){
                            notifyItemMoved(holder.getAdapterPosition(),i); // 체크 없앤 것 -> 맨 아래로
                        }
                    }
                }
                else{
                    holder.select_cb.setChecked(true);
                    for(int i=0;i<arrayList.size();i++){
                        if(!arrayList.get(i).isSelect_cb()){
                            notifyItemMoved(holder.getAdapterPosition(),0); // 체크 누른 것 -> 맨 위로
                        }
                    }
                }
            }
        });
    }

    public void setRecommedndRecipeList(ArrayList<RecommendRecipeData> list){
        this.arrayList = list;
        notifyDataSetChanged();
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

        protected TextView ingredientName_tv;
        protected TextView ingredientNumber_tv;
        protected TextView ingredientDate_tv;
        protected CheckBox select_cb;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ingredientName_tv = (TextView) itemView.findViewById(R.id.tv_ingredientName);
            this.ingredientNumber_tv = (TextView) itemView.findViewById(R.id.tv_ingredientNumber);
            this.ingredientDate_tv = (TextView) itemView.findViewById(R.id.tv_ingredientDate);
            this.select_cb = (CheckBox) itemView.findViewById(R.id.cb_select);


        }
    }
}