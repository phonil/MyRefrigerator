package opg.app.myrefrigerator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import java.util.Random;

// Fragment 1,2,3 --> 랜덤 음식 추천 / 뷰페이저 클릭 시 랜덤으로 음식 사진 변경
public class Fragment3 extends Fragment { // ViewPager2 - 3
    ImageView imgBanner3;
    Random random = new Random();
    int index = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_3, container, false);
        imgBanner3 =  (ImageView) rootView.findViewById(R.id.imgBanner3);

        imgBanner3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = random.nextInt(14);

                switch (index){
                    case 0:
                        imgBanner3.setImageResource((R.drawable.jap_1));
                        break;
                    case 1:
                        imgBanner3.setImageResource(R.drawable.jap_2);
                        break;
                    case 2:
                        imgBanner3.setImageResource(R.drawable.jap_3);
                        break;
                    case 3:
                        imgBanner3.setImageResource(R.drawable.jap_4);
                        break;
                    case 4:
                        imgBanner3.setImageResource(R.drawable.jap_5);
                        break;
                    case 5:
                        imgBanner3.setImageResource(R.drawable.jap_6);
                        break;
                    case 6:
                        imgBanner3.setImageResource(R.drawable.jap_7);
                        break;
                    case 7:
                        imgBanner3.setImageResource(R.drawable.jap_8);
                        break;
                    case 8:
                        imgBanner3.setImageResource(R.drawable.jap_9);
                        break;
                    case 9:
                        imgBanner3.setImageResource(R.drawable.jap_10);
                        break;
                }
            }
        });

        return rootView;
    }
}