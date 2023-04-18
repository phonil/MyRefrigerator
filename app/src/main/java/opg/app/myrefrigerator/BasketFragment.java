package opg.app.myrefrigerator;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class BasketFragment extends Fragment { // 임시 장바구니

    RecyclerView mRecyclerView;
    MyBasketRecyclerAdapter mRecyclerAdapter;
    ArrayList<BasketItem> mBasketItems = new ArrayList<BasketItem>();
    Context ct;
    Button btAdd, btDel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket,container,false);
        ct = container.getContext();

        btAdd = (Button) view.findViewById(R.id.basketRecyclerView);
        btDel = (Button) view.findViewById(R.id.basketRecyclerView);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.basketRecyclerView);

        /* initiate adapter */
        mRecyclerAdapter = new MyBasketRecyclerAdapter();

        /* initiate recyclerview */
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ct));

        /*for(int i=1;i<=20;i++){
            switch(i%8){
                case 0 :
                    mBasketItems.add(new BasketItem(R.drawable.test1,i+"번째 목록",i+"번째 상태메시지"));
                    break;
                case 1 :
                    mBasketItems.add(new BasketItem(R.drawable.test2,i+"번째 목록",i+"번째 상태메시지"));
                    break;
                case 2 :
                    mBasketItems.add(new BasketItem(R.drawable.test3,i+"번째 목록",i+"번째 상태메시지"));
                    break;
                case 3 :
                    mBasketItems.add(new BasketItem(R.drawable.test4,i+"번째 목록",i+"번째 상태메시지"));
                    break;
                case 4 :
                    mBasketItems.add(new BasketItem(R.drawable.test5,i+"번째 목록",i+"번째 상태메시지"));
                    break;
                case 5 :
                    mBasketItems.add(new BasketItem(R.drawable.test6,i+"번째 목록",i+"번째 상태메시지"));
                    break;
                case 6 :
                    mBasketItems.add(new BasketItem(R.drawable.test7,i+"번째 목록",i+"번째 상태메시지"));
                    break;
                case 7 :
                    mBasketItems.add(new BasketItem(R.drawable.test8,i+"번째 목록",i+"번째 상태메시지"));
                    break;
            }
        }
        mRecyclerAdapter.setBasketList(mBasketItems);*/

        // Inflate the layout for this fragment
        return view;
    }
}