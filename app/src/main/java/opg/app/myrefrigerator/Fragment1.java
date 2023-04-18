package opg.app.myrefrigerator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Random;
// Fragment 1,2,3 --> 랜덤 음식 추천 / 뷰페이저 클릭 시 랜덤으로 음식 사진 변경
public class Fragment1 extends Fragment { // ViewPager2 - 1

    ImageView imgBanner1;
    Random random = new Random();
    int index = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_1, container, false);

        imgBanner1 =  (ImageView) rootView.findViewById(R.id.imgBanner1);

        imgBanner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = random.nextInt(14);

                switch (index){
                    case 0:
                        imgBanner1.setImageResource((R.drawable.kor_1));
                        break;
                    case 1:
                        imgBanner1.setImageResource(R.drawable.kor_2);
                        break;
                    case 2:
                        imgBanner1.setImageResource(R.drawable.kor_3);
                        break;
                    case 3:
                        imgBanner1.setImageResource(R.drawable.kor_4);
                        break;
                    case 4:
                        imgBanner1.setImageResource(R.drawable.kor_5);
                        break;
                    case 5:
                        imgBanner1.setImageResource(R.drawable.kor_6);
                        break;
                    case 6:
                        imgBanner1.setImageResource(R.drawable.kor_7);
                        break;
                    case 7:
                        imgBanner1.setImageResource(R.drawable.kor_8);
                        break;
                    case 8:
                        imgBanner1.setImageResource(R.drawable.kor_9);
                        break;
                    case 9:
                        imgBanner1.setImageResource(R.drawable.kor_10);
                        break;
                }
            }
        });

        return rootView;
    }
}