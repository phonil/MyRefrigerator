package opg.app.myrefrigerator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyBasketRecyclerAdapter extends RecyclerView.Adapter<MyBasketRecyclerAdapter.ViewHolder> {

    private ArrayList<BasketItem> mBasketList;

    @NonNull
    @Override
    public MyBasketRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_basket, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
       // return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBasketRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(mBasketList.get(position));
    }

    public void setBasketList(ArrayList<BasketItem> list){
        this.mBasketList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mBasketList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
        }

        void onBind(BasketItem item){
            name.setText(item.getName());
        }
    }
}