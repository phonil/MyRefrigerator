package opg.app.myrefrigerator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// 냉동실 화면
public class RefrigeratorRight extends AppCompatActivity {
    Button add_ingredient_bt;
    EditText ingredient_name_et, ingredient_number_et, ingredient_date_et, where_et;

    //황호준 - 데이터베이스 변수
    SQLiteDatabase sqlDB;
    static RefrigeratorRight.mySQLiteHelper mySQLiteHelper;

    private ArrayList<RefrigeratorRightData> arrayList;
    private RefrigeratorRightAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refrigerator_right);

        //황호준 - 데이터베이스 선언
        mySQLiteHelper = new mySQLiteHelper(this);

        recyclerView = (RecyclerView) findViewById(R.id.rv_refrigerator_right);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        mainAdapter = new RefrigeratorRightAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        ingredient_name_et = (EditText) findViewById(R.id.et_name);
        ingredient_number_et = (EditText) findViewById(R.id.et_number);
        ingredient_date_et = (EditText) findViewById(R.id.et_date);
        where_et = (EditText) findViewById(R.id.et_where);

        sqlDB = RefrigeratorRight.mySQLiteHelper.getReadableDatabase();
        Cursor cursor =sqlDB.rawQuery("SELECT * FROM ItemDataRight",null);
        if(cursor.moveToFirst()){
            do{
                RefrigeratorRightData mainData = new RefrigeratorRightData(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                arrayList.add(mainData);
                mainAdapter.notifyDataSetChanged(); // 새로고침
            }while(cursor.moveToNext());}
        cursor.close();
        sqlDB.close();



        add_ingredient_bt = (Button) findViewById(R.id.bt_add_ingredient);
        // 재료 추가 (정보 입력 후 버튼 클릭)
        add_ingredient_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ingredient_name_et.getText ().length ()==0){
                    Toast.makeText (getApplicationContext(),"재료명을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    //데이터 베이스 저장
                    sqlDB = RefrigeratorRight.mySQLiteHelper.getWritableDatabase();
                    sqlDB.execSQL("INSERT INTO ItemDataRight VALUES( '" + ingredient_name_et.getText().toString() + "' , '" + ingredient_number_et.getText().toString() + "', '" + ingredient_date_et.getText().toString() + "', '" + where_et.getText().toString() + "');");
                    sqlDB.close();

                    RefrigeratorRightData mainData = new RefrigeratorRightData(ingredient_name_et.getText().toString(),ingredient_number_et.getText().toString(),ingredient_date_et.getText().toString(),where_et.getText().toString()); //
                    arrayList.add(mainData);
                    mainAdapter.notifyDataSetChanged(); // 새로고침
                    ingredient_name_et.setText("");
                    ingredient_number_et.setText("");
                    ingredient_date_et.setText("");
                    where_et.setText("");


                }


            }
        });

    }

    //황호준 itemData 테이블 생성
    public static class mySQLiteHelper extends SQLiteOpenHelper {
        public mySQLiteHelper(Context context) {
            super(context, "ItemData2", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE ItemDataRight (itemName TEXT,itemNo TEXT,itemdate TEXT,itemMemo TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS ItemDataRight");
            onCreate(db);
        }
    }
}