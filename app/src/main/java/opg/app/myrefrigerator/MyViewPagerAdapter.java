package opg.app.myrefrigerator;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyViewPagerAdapter extends FragmentStateAdapter { // ViewPager2 Adapter

    public int mCount;

    public MyViewPagerAdapter(HomeFragment fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        // 개수 3개로 정했으므로 3개 화면 띄우기
        if(index==0) return new Fragment1();
        else if(index==1) return new Fragment2();
        else return new Fragment3();

    }

    @Override
    public int getItemCount() {
        return 2000;
    }

    public int getRealPosition(int position) { return position % mCount; }

}