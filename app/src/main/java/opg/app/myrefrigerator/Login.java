package opg.app.myrefrigerator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    Button btLogin, btResister, btSignUp, btSingIn;
    LinearLayout loLinear, reLinear;
    TextView tvFindAccount;

    // 황호준 - edittext 변수
    EditText EditID,EditPassword,EditJoinID,EditJoinPw,EditJoinPwCk,EditName,EditBirth;
    // 황호준 - 데이터 변수
    SQLiteDatabase sqlDB;
    static mySQLiteHelper mySQLiteHelper;
    public static String mainId;
    public static String mainPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //데이터 베이스
        mySQLiteHelper = new mySQLiteHelper(this);
        //황호준 eitetext 지정
        EditID = (EditText) findViewById(R.id.idET1);
        EditPassword = (EditText) findViewById(R.id.pwEt1);

        EditJoinID = (EditText) findViewById(R.id.idET2);
        EditJoinPw = (EditText) findViewById(R.id.pwEt2);
        EditJoinPwCk = (EditText) findViewById(R.id.pwCheckEt1);
        EditName = (EditText) findViewById(R.id.nameEt1);
        EditBirth = (EditText) findViewById(R.id.birthEt1);

        loLinear = (LinearLayout) findViewById(R.id.loginLinear);
        reLinear = (LinearLayout) findViewById(R.id.resisterLinear);

        // ID/PW 찾기 화면으로
        tvFindAccount = (TextView) findViewById(R.id.forgotTv1);
        tvFindAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // FindAccount 클래스로 전환
                Intent intent = new Intent(getApplicationContext(), FindAccount.class);
                startActivity(intent);
            }
        });

        // 로그인! 메인 화면으로
        btSingIn = (Button) findViewById(R.id.singInbt1);
        btSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 황호준 로그인! 데이터베이스 + 기능
                String t1 = EditID.getText().toString();
                String t2 = EditPassword.getText().toString();

                if(t1.isEmpty() || t2.isEmpty()){
                    Toast.makeText(Login.this, "ID와 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else{
                    sqlDB = mySQLiteHelper.getReadableDatabase();
                    Cursor cursor;
                    cursor = sqlDB.rawQuery("SELECT * FROM JoinGroup WHERE ID = '" + EditID.getText().toString() + "';", null);
                    if(cursor.getCount() !=1){
                        Toast.makeText(Login.this, "ID를 확인해주세요", Toast.LENGTH_SHORT).show();
                    }else{
                        cursor = sqlDB.rawQuery("SELECT * FROM JoinGroup WHERE ID = '" + EditID.getText().toString() + "'AND Pass = '" + EditPassword.getText().toString() + "';", null);
                        if(cursor.getCount() !=1){
                            Toast.makeText(Login.this, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                        }else{
                            mainId= EditID.getText().toString();
                            mainPass= EditPassword.getText().toString();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                    sqlDB.close();
                    cursor.close();
                }
            }
        });


        // 로그인 화면으로
        btLogin = (Button) findViewById(R.id.loginbt2);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loLinear.setVisibility(View.VISIBLE);
                reLinear.setVisibility(View.INVISIBLE);
            }
        });

        // 등록 화면으로
        btResister = (Button) findViewById(R.id.resisterbt1);
        btResister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loLinear.setVisibility(View.INVISIBLE);
                reLinear.setVisibility(View.VISIBLE);
            }
        });

        // 등록 화면에서 회원가입 버튼 눌렀을 때
        btSignUp = (Button) findViewById(R.id.singUpbt1);
        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 황호준 회원가입 데이터베이스 + 기능
                String t1 =EditJoinID.getText().toString();
                String t2 =EditJoinPw.getText().toString();
                String t3 =EditJoinPwCk.getText().toString();
                String t4 =EditName.getText().toString();
                String t5 =EditBirth.getText().toString();
                //아이디중복 try catch 문 ?? , ?생년월일 전화번호 숫자?
                if(t1.isEmpty() || t2.isEmpty()|| t3.isEmpty()|| t4.isEmpty()|| t5.isEmpty()){
                    Toast.makeText(Login.this, "가입 정보를 기입해주세요.", Toast.LENGTH_SHORT).show();
                }else if(!t2.equals(t3)){
                    Toast.makeText(Login.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }else {
                    try{
                        sqlDB = mySQLiteHelper.getWritableDatabase();
                        sqlDB.execSQL("INSERT INTO JoinGroup VALUES( '" + EditJoinID.getText().toString() + "' , '" + EditJoinPw.getText().toString() + "', '" + EditName.getText().toString() + "', '" + EditBirth.getText().toString() + "');");
                        sqlDB.close();
                        showSignUpDialog();
                    }catch (Exception e) {
                        Toast.makeText(Login.this, "ID 중복입니다..", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }
    // 회원가입 성공 다이얼로그
    void showSignUpDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sign Up");
        builder.setMessage("회원가입 성공, 로그인 화면으로 이동합니다.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                loLinear.setVisibility(View.VISIBLE);
                reLinear.setVisibility(View.INVISIBLE);
            }
        });
        builder.show();
    }
    public static class mySQLiteHelper extends SQLiteOpenHelper {
        public mySQLiteHelper(Context context) {
            super(context, "groupDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE JoinGroup (ID TEXT PRIMARY KEY, Pass TEXT,Name TEXT,Birth TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS JoinGroup");
            onCreate(db);
        }
    }

}