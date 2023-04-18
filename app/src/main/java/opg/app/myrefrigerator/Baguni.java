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

// 장바구니 화면
public class Baguni extends AppCompatActivity {

    //황호준 - 데이터베이스 변수
    SQLiteDatabase sqlDB;
    static Baguni.mySQLiteHelper mySQLiteHelper;



    private ArrayList<BaguniData> arrayList;
    private BaguniAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private EditText et_baguni;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baguni);

        //황호준 - 데이터베이스 선언
        mySQLiteHelper = new Baguni.mySQLiteHelper(this);


        recyclerView = (RecyclerView) findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        et_baguni = (EditText) findViewById(R.id.et_baguni);

        arrayList = new ArrayList<>();

        mainAdapter = new BaguniAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        sqlDB = Baguni.mySQLiteHelper.getReadableDatabase();
        Cursor cursor =sqlDB.rawQuery("SELECT * FROM guni",null);
        if(cursor.moveToFirst()){
            do{
                BaguniData mainData = new BaguniData(cursor.getString(0));
                arrayList.add(mainData);
                mainAdapter.notifyDataSetChanged(); // 새로고침 / 추가하고 꼭 해줘야 함
            }while(cursor.moveToNext());}
        cursor.close();
        sqlDB.close();

        Button btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_baguni.getText ().length ()==0){
                    Toast.makeText (getApplicationContext(),"장보기 목록을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    //데이터 베이스 저장
                    sqlDB = Baguni.mySQLiteHelper.getWritableDatabase();
                    sqlDB.execSQL("INSERT INTO guni VALUES( '" + et_baguni.getText().toString() + "');");
                    sqlDB.close();


                    BaguniData mainData = new BaguniData(et_baguni.getText().toString()); //
                    arrayList.add(mainData);
                    mainAdapter.notifyDataSetChanged(); // 새로고침 / 추가하고 꼭 해줘야 함
                    et_baguni.setText("");
                }


            }
        });
    }
    //황호준 itemData 테이블 생성
    public static class mySQLiteHelper extends SQLiteOpenHelper {
        public mySQLiteHelper(Context context) {
            super(context, "guni", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE guni (itemName TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS guni");
            onCreate(db);
        }
    }
}