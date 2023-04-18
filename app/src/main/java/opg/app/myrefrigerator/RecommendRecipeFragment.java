package opg.app.myrefrigerator;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class RecommendRecipeFragment extends Fragment {

    CardView card1, card2, card3;
    EditText Food_name;
    Button btn;
    TextView Recipe_View, Ingredient_View, Title_View, Recipe_View2, Ingredient_View2, Title_View2,
            Recipe_View3, Ingredient_View3, Title_View3;
    String url, new_url1, new_url2, new_url3, step, Ingredient, Ingredient2, Ingredient3,
            Title, Title2, Title3, Recipe1 = "", Recipe2 = "", Recipe3 = "";
    int step_count = 1;
    Queue<String> urls = new LinkedList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recommend_recipe,container,false);

        card1 = (CardView) view.findViewById(R.id.card1);
        card2 = (CardView) view.findViewById(R.id.card2);
        card3 = (CardView) view.findViewById(R.id.card3);
        Food_name = (EditText) view.findViewById(R.id.Food_Name);
        btn = (Button) view.findViewById(R.id.button01);
        Recipe_View = (TextView) view.findViewById(R.id.Recipe_textView);
        Recipe_View2 = (TextView) view.findViewById(R.id.Recipe_textView2);
        Recipe_View3 = (TextView) view.findViewById(R.id.Recipe_textView3);
        Ingredient_View = (TextView) view.findViewById(R.id.Ingredient_textView);
        Ingredient_View2 = (TextView) view.findViewById(R.id.Ingredient_textView2);
        Ingredient_View3 = (TextView) view.findViewById(R.id.Ingredient_textView3);
        Title_View = (TextView) view.findViewById(R.id.Title_textView);
        Title_View2 = (TextView) view.findViewById(R.id.Title_textView2);
        Title_View3 = (TextView) view.findViewById(R.id.Title_textView3);
        Recipe_View.setMovementMethod(new ScrollingMovementMethod());
        Recipe_View2.setMovementMethod(new ScrollingMovementMethod());
        Recipe_View3.setMovementMethod(new ScrollingMovementMethod());

        // 레시피 검색 버튼 클릭 시 / JSoup 사용
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recipe1 = "";
                Recipe2 = "";
                Recipe3 = "";
                step_count = 1;
                url = "https://www.10000recipe.com/recipe/list.html?q=" + Food_name.getText().toString() + "&query=&cat1=&cat2=&cat3=&cat4=&fct=&order=accuracy&lastcate=order&dsearch=&copyshot=&scrap=&degree=&portion=&time=&niresource=";
                JsoupAsyncTask JsoupAsyncTask = new JsoupAsyncTask();
                JsoupAsyncTask.execute();
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable()  {
                    public void run() {
                        // 시간 지난 후 실행할 것들
                        card1.setVisibility(View.VISIBLE);
                        card2.setVisibility(View.VISIBLE);
                        card3.setVisibility(View.VISIBLE);
                    }
                }, 7000); //  레시피 등장 시간과 맞추기 위함

            }
        });
        return view;
    }

    public class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        public void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        public Void doInBackground(Void... params){
            try{
                Document document = Jsoup.connect(url).get();
                for(int i=0; i<=2; i++){
                    String query = ".common_sp_list_li:eq(" + i + ")";
                    Elements ur = document.select(query);
                    String new_url = ur.select(".common_sp_thumb a").attr("href");
                    urls.add(new_url);
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }

            try{
                new_url1 = urls.poll();
                Document document2 = Jsoup.connect("https://www.10000recipe.com" + new_url1).get();

                Elements title = document2.select("#contents_area");
                Title = title.select(".view2_summary.st3 h3").text();

                Elements ingredient = document2.select(".ready_ingre3");
                String Ingredient_a = ingredient.select(".case1 a").text();
                Ingredient = Ingredient_a.replace("구매","");
                Ingredient = Ingredient.replace("  ",", ");

                if(Ingredient.equals("")) {
                    Ingredient_a = document2.select(".ready_ingre3 a").text();
                    Ingredient = Ingredient_a.replace("구매", "");
                    Ingredient = Ingredient.replace("  ", ", ");
                }
                Elements how = document2.select(".view_step");
                while(step_count < 20){
                    String id = "#stepdescr" + step_count;
                    step = how.select(id).text();
                    if(step == "") break;
                    Recipe1 = Recipe1 + step_count + ") " + step + "\r\n \r\n";
                    step_count++;
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }

            try{
                step_count = 1;
                new_url2 = urls.poll();
                Document document3 = Jsoup.connect("https://www.10000recipe.com" + new_url2).get();

                Elements title = document3.select("#contents_area");
                Title2 = title.select(".view2_summary.st3 h3").text();

                Elements ingredient3 = document3.select(".ready_ingre3");
                String Ingredient_b = ingredient3.select(".case1 a").text();
                Ingredient2 = Ingredient_b.replace("구매","");
                Ingredient2 = Ingredient2.replace("  ",", ");

                if(Ingredient2.equals("")){
                    Ingredient_b = document3.select(".ready_ingre3 a").text();
                    Ingredient2 = Ingredient_b.replace("구매","");
                    Ingredient2 = Ingredient2.replace("  ",", ");
                }

                Elements how = document3.select(".view_step");
                while(step_count < 20){
                    String id = "#stepdescr" + step_count;
                    step = how.select(id).text();
                    if(step == "") break;
                    Recipe2 = Recipe2 + step_count + ") " + step + "\r\n \r\n";
                    step_count++;
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }

            try{
                step_count = 1;
                new_url3 = urls.poll();
                Document document4 = Jsoup.connect("https://www.10000recipe.com" + new_url3).get();

                Elements title = document4.select("#contents_area");
                Title3 = title.select(".view2_summary.st3 h3").text();

                Elements ingredient4 = document4.select(".ready_ingre3");
                String Ingredient_c = ingredient4.select(".case1 a").text();
                Ingredient3 = Ingredient_c.replace("구매","");
                Ingredient3 = Ingredient3.replace("  ",", ");

                if(Ingredient3.equals("")){
                    Ingredient_c = document4.select(".ready_ingre3 a").text();
                    Ingredient3 = Ingredient_c.replace("구매","");
                    Ingredient3 = Ingredient3.replace("  ",", ");
                }

                Elements how = document4.select(".view_step");
                while(step_count < 20){
                    String id = "#stepdescr" + step_count;
                    step = how.select(id).text();
                    if(step == "") break;
                    Recipe3 = Recipe3 + step_count + ") " + step + "\r\n \r\n";
                    step_count++;
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            Ingredient_View.setText(Ingredient);
            Title_View.setText(Title);
            Recipe_View.setText(Recipe1);
            Ingredient_View2.setText(Ingredient2);
            Title_View2.setText(Title2);
            Recipe_View2.setText(Recipe2);
            Ingredient_View3.setText(Ingredient3);
            Title_View3.setText(Title3);
            Recipe_View3.setText(Recipe3);
        }

    }
}