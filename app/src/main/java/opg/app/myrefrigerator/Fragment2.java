package opg.app.myrefrigerator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import java.util.Random;

// Fragment 1,2,3 --> 랜덤 음식 추천 / 뷰페이저 클릭 시 랜덤으로 음식 사진 변경
public class Fragment2 extends Fragment { // ViewPager2 - 2
    ImageView imgBanner2;
    Random random = new Random();
    int index = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_2, container, false);

        imgBanner2 =  (ImageView) rootView.findViewById(R.id.imgBanner2);

        imgBanner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = random.nextInt(14);

                switch (index){
                    case 0:
                        imgBanner2.setImageResource((R.drawable.chi_1));
                        break;
                    case 1:
                        imgBanner2.setImageResource(R.drawable.chi_2);
                        break;
                    case 2:
                        imgBanner2.setImageResource(R.drawable.chi_3);
                        break;
                    case 3:
                        imgBanner2.setImageResource(R.drawable.chi_4);
                        break;
                    case 4:
                        imgBanner2.setImageResource(R.drawable.chi_5);
                        break;
                    case 5:
                        imgBanner2.setImageResource(R.drawable.chi_6);
                        break;
                    case 6:
                        imgBanner2.setImageResource(R.drawable.chi_7);
                        break;
                    case 7:
                        imgBanner2.setImageResource(R.drawable.chi_8);
                        break;
                    case 8:
                        imgBanner2.setImageResource(R.drawable.chi_9);
                        break;
                    case 9:
                        imgBanner2.setImageResource(R.drawable.chi_10);
                        break;
                }
            }
        });

        return rootView;
    }
}