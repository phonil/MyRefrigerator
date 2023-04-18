package opg.app.myrefrigerator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

// 달력 다이어리 화면
public class CalendarFragment extends Fragment {

    mySQLiteHelper mySQLiteHelper;
    SQLiteDatabase sqlDB;
    CalendarView calendarView;
    TextView textBig, food_txt, cal_txt, qty_txt, total_txt;
    Button check_btn, check_btn02, add_btn, clear_DB;
    String url, new_url, next_page, DATE, Food;
    Double user_num, cal;
    Integer Year, DayOfMonth, Month;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        textBig = (TextView) view.findViewById(R.id.bigText);
        food_txt = (TextView) view.findViewById(R.id.Food_Name_Text);
        cal_txt = (TextView) view.findViewById(R.id.cal_text);
        qty_txt = (TextView) view.findViewById(R.id.eat_qty_Text);
        total_txt = (TextView) view.findViewById(R.id.eat_cal_text);
        check_btn = (Button) view.findViewById(R.id.CheckButton);
        check_btn02 = (Button) view.findViewById(R.id.CheckButton02);
        add_btn = (Button) view.findViewById(R.id.add_db_Button);
        clear_DB = (Button) view.findViewById(R.id.Clear_DB);

        mySQLiteHelper = new mySQLiteHelper(getActivity());

        // 달력. CalendarView 사용, 날짜 클릭 시 이벤트 처리
        calendarView = (CalendarView) view.findViewById(R.id.calendarView_frag);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // todo

                Year = year;
                DayOfMonth = dayOfMonth;
                Month = month;
                DATE = Year + "년" + Month + "월" + DayOfMonth + "일";

                textBig.setText(year + "년 " + (month + 1) + "월 " + dayOfMonth + "일 ");

                sqlDB = mySQLiteHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT FoodName, KCal FROM CalendarDB WHERE DATE = '" + DATE + "';", null);

                String FN = "";
                Double Total_KCal = 0.0;

                while (cursor.moveToNext()) {
                    FN += "* " + cursor.getString(0) + "\r\n칼로리 : " + cursor.getString(1) + "kcal \r\n \r\n";
                    Total_KCal += cursor.getDouble(1);
                }
                textBig.setText(FN + "총합 칼로리 \r\n" + Total_KCal + "kcal");
                if(FN == "") textBig.setText("0.0kcal");
                cursor.close();
                sqlDB.close();
            }
        });

        // 칼로리 체크 - Jsoup
        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next_page = "";
                url = "https://www.fatsecret.kr/%EC%B9%BC%EB%A1%9C%EB%A6%AC-%EC%98%81%EC%96%91%EC%86%8C/search?q=" + food_txt.getText().toString();
                JsoupAsyncTask JsoupAsyncTask = new JsoupAsyncTask();
                JsoupAsyncTask.execute();
            }
        });

        check_btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_num = Double.parseDouble(qty_txt.getText().toString());
                cal = cal * user_num;
                total_txt.setText(cal + "kcal");
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {

                Food = food_txt.getText().toString();

                sqlDB = mySQLiteHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO CalendarDB VALUES('" + DATE + "','" + Food + "'," + cal + ");");

                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT FoodName, KCal FROM CalendarDB WHERE DATE = '" + DATE + "';", null);

                String FN = "";
                Double Total_KCal = 0.0;

                while (cursor.moveToNext()) {
                    FN += "* " + cursor.getString(0) + "\r\n칼로리 : " + cursor.getString(1) + "kcal \r\n \r\n";
                    Total_KCal += cursor.getDouble(1);
                }
                textBig.setText(FN + "총합 칼로리 \r\n" + Total_KCal + "kcal");
                if(FN == "") textBig.setText("0.0kcal");

                cursor.close();
                sqlDB.close();

                food_txt.setText("");///////
                qty_txt.setText("");///////
            }
        });

        clear_DB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = mySQLiteHelper.getWritableDatabase();
                sqlDB.execSQL("DELETE FROM CalendarDB WHERE DATE = '" + DATE + "';");
                textBig.setText("0.0kcal");
                sqlDB.close();
            }
        });

        return view;
    }

    public class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(url).get();
                Elements el = document.select(".borderBottom");
                next_page = el.select(".prominent").attr("href");
                new_url = "https://www.fatsecret.kr" + next_page;
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Document document = Jsoup.connect(new_url).get();
                Elements e = document.select(".fact");
                cal = Double.valueOf(e.select(".factValue").first().text());
                next_page = cal + "kcal / 단위 : ";

                Elements ele = document.select(".nutrition_facts.international");
                next_page = next_page + ele.select(".serving_size.black.serving_size_value").text();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            cal_txt.setText(next_page);
        }
    }

    public class mySQLiteHelper extends SQLiteOpenHelper {
        private Context mContext;

        public mySQLiteHelper(Context context) {
            super(context, "groupDB", null, 1);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE CalendarDB (DATE CHAR(20), FoodName CHAR(20), KCal DOUBLE);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS CalendarDB");
            onCreate(db);

        }
    }
}