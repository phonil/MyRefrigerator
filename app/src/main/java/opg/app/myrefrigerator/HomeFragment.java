package opg.app.myrefrigerator;

import static opg.app.myrefrigerator.Login.mainId;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

// 홈 화면이자 메인 화면
public class HomeFragment extends Fragment {

    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 3; // 페이지 개수 정하기 위함
    private Button baguniBt, leftBt, rightBt;

    //황호준-데이터변수
    SQLiteDatabase sqlDB;
    TextView user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    // 프래그먼트
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        //TextView
        user =(TextView) view.findViewById(R.id.userName_user);

        //ViewPager2
        mPager = (ViewPager2) view.findViewById(R.id.viewpager);

        //Adapter
        pagerAdapter = new MyViewPagerAdapter(this, num_page);
        mPager.setAdapter(pagerAdapter);

        sqlDB = Login.mySQLiteHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM JoinGroup WHERE ID = '" + mainId + "';", null);
        cursor.moveToFirst();
        user.setText("Hello, "+cursor.getString(2));
        cursor.close();
        sqlDB.close();

        baguniBt = (Button) view.findViewById(R.id.btBaguni);
        baguniBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 장바구니 화면으로
                Intent intent = new Intent(getActivity(),Baguni.class); //fragment라서 activity intent와는 다른 방식
                startActivity(intent);
            }
        });



        leftBt = (Button) view.findViewById(R.id.bt_left);
        leftBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 냉장실 화면 이동
                Intent intent = new Intent(getActivity(), RefrigeratorLeft.class);
                startActivity(intent);

            }
        });

        rightBt = (Button) view.findViewById(R.id.bt_right);
        rightBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 냉동실 화면 이동
                Intent intent = new Intent(getActivity(), RefrigeratorRight.class);
                startActivity(intent);

            }
        });

        //ViewPager Setting
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager.setCurrentItem(1000);
        mPager.setOffscreenPageLimit(3);

        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

        });

        final float pageMargin= getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);

        mPager.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float myOffset = position * -(2 * pageOffset + pageMargin);
                if (mPager.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(mPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.setTranslationX(-myOffset);
                    } else {
                        page.setTranslationX(myOffset);
                    }
                } else {
                    page.setTranslationY(myOffset);
                }
            }
        });

        return view;

    }
}