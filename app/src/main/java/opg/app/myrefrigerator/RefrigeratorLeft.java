package opg.app.myrefrigerator;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// 냉장실 화면
public class RefrigeratorLeft extends AppCompatActivity {
    Button add_ingredient_bt;
    EditText ingredient_name_et, ingredient_number_et, ingredient_date_et, where_et;
    //황호준 - 데이터베이스 변수
    SQLiteDatabase sqlDB;
    static RefrigeratorLeft.mySQLiteHelper mySQLiteHelper;



    private ArrayList<RefrigeratorLeftData> arrayList;
    private RefrigeratorLeftAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refrigerator_left);
        //황호준 - 데이터베이스 선언
        mySQLiteHelper = new mySQLiteHelper(this);

        recyclerView = (RecyclerView) findViewById(R.id.rv_refrigerator_left);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        mainAdapter = new RefrigeratorLeftAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        ingredient_name_et = (EditText) findViewById(R.id.et_name);
        ingredient_number_et = (EditText) findViewById(R.id.et_number);
        ingredient_date_et = (EditText) findViewById(R.id.et_date);
        where_et = (EditText) findViewById(R.id.et_where);

        sqlDB = RefrigeratorLeft.mySQLiteHelper.getReadableDatabase();
        Cursor cursor =sqlDB.rawQuery("SELECT * FROM ItemDataLeft",null);
        if(cursor.moveToFirst()){
            do{
                RefrigeratorLeftData mainData = new RefrigeratorLeftData(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                arrayList.add(mainData);
                mainAdapter.notifyDataSetChanged(); // 새로고침 / 추가하고 꼭 해줘야 함
            }while(cursor.moveToNext());}
        cursor.close();
        sqlDB.close();


        add_ingredient_bt = (Button) findViewById(R.id.bt_add_ingredient);
        // 재료 추가. 입력한 정보대로 반영됨
        add_ingredient_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ingredient_name_et.getText ().length ()==0){
                    Toast.makeText (getApplicationContext(),"재료명을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    //황호준 - 데이터 베이스 저장
                    sqlDB = RefrigeratorLeft.mySQLiteHelper.getWritableDatabase();
                    sqlDB.execSQL("INSERT INTO ItemDataLeft VALUES( '" + ingredient_name_et.getText().toString() + "' , '" + ingredient_number_et.getText().toString() + "', '" + ingredient_date_et.getText().toString() + "', '" + where_et.getText().toString() + "');");
                    sqlDB.close();

                    RefrigeratorLeftData mainData = new RefrigeratorLeftData(ingredient_name_et.getText().toString(),ingredient_number_et.getText().toString(),ingredient_date_et.getText().toString(),where_et.getText().toString()); //
                    arrayList.add(mainData);
                    mainAdapter.notifyDataSetChanged(); // 새로고침 / 추가하고 꼭 해줘야 함
                    ingredient_name_et.setText("");
                    ingredient_number_et.setText("");
                    ingredient_date_et.setText("");
                    where_et.setText("");

                }


            }
        });

    }
    //황호준 - itemData 테이블 생성
    public static class mySQLiteHelper extends SQLiteOpenHelper {
        public mySQLiteHelper(Context context) {
            super(context, "ItemData", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE ItemDataLeft (itemName TEXT,itemNo TEXT,itemdate TEXT,itemMemo TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS ItemDataLeft");
            onCreate(db);
        }
    }
}