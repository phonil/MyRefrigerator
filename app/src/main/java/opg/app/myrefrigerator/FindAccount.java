package opg.app.myrefrigerator;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class FindAccount extends AppCompatActivity {
    Button btFindId, btResetPw, btNewPwOkBt;
    LinearLayout findIdL, findPwL;
    String newPassword;
    TextView tvNewPwTitle, tvNewPw, tvNewPwCheck;
    EditText etNewPw, etNewPwCheck;
    // 황호준 - 아이디 비번 정보 EditText 변수
    EditText FindIDName,FindIDBirth,FindPwID,FindPwName,FindPwBirth;
    // 황호준 - 데이터베이스 변수
    SQLiteDatabase SqlDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_account);

        findIdL = (LinearLayout) findViewById(R.id.findIdLinear1);
        findPwL = (LinearLayout) findViewById(R.id.findPwLinear1);

        tvNewPwTitle = (TextView) findViewById(R.id.newPwTitleTv1);
        tvNewPw = (TextView) findViewById(R.id.newPwTv1);
        tvNewPwCheck = (TextView) findViewById(R.id.newPwCheckTv1);
        //황호준 - 아이디,비번찾기 editText 지정
        FindIDName =(EditText) findViewById(R.id.nameET2);
        FindIDBirth = (EditText) findViewById(R.id.birthEt2);
        FindPwID =(EditText) findViewById(R.id.idET3);
        FindPwName = (EditText) findViewById(R.id.nameEt3);
        FindPwBirth =(EditText) findViewById(R.id.birthEt3);

        etNewPw = (EditText) findViewById(R.id.newPwEt1);
        etNewPwCheck = (EditText) findViewById(R.id.newPwCheckEt1);

        btNewPwOkBt = (Button) findViewById(R.id.newPwOkBt1);
        btNewPwOkBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 황호준 - 비밀번호 찾기 데이터베이스 연결 , 변경
                String t1 =etNewPw.getText().toString();
                String t2 =etNewPwCheck.getText().toString();
                String t3 = FindPwID.getText().toString();
                if(!t1.equals(t2)){
                    Toast.makeText(FindAccount.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }else if(t1.isEmpty()||t2.isEmpty()){
                    Toast.makeText(FindAccount.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    SqlDB = Login.mySQLiteHelper.getReadableDatabase() ;
                    SqlDB.execSQL("UPDATE JoinGroup SET PASS='"+etNewPw.getText().toString()+"' WHERE ID= '" + FindPwID.getText().toString() +"' ;");
                    showNewPwDialog();
                }
            }
        });

        btFindId = (Button) findViewById(R.id.findIdBt1);
        btFindId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 황호준 - id 찾기 버튼 데이터베이스 연결, 아이디 불러오기
                SqlDB = Login.mySQLiteHelper.getReadableDatabase();
                Cursor cursor;
                cursor = SqlDB.rawQuery("SELECT ID FROM JoinGroup WHERE NAME = '" + FindIDName.getText().toString() + "' AND Birth = '" + FindIDBirth.getText().toString() + "';", null);
                if(cursor.getCount() !=1){
                    Toast.makeText(FindAccount.this, "잘못된 정보입니다.", Toast.LENGTH_SHORT).show();
                }else{
                    cursor.moveToFirst();
                    String A = cursor.getString(0);
                    showFindIdDialog(A);
                }
                cursor.close();
                SqlDB.close();
            }
        });

        btResetPw = (Button) findViewById(R.id.resetBt1);
        btResetPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Reset 버튼 정보 확인 위한 데이터베이스 연결
                SqlDB = Login.mySQLiteHelper.getReadableDatabase();
                Cursor cursor;
                cursor = SqlDB.rawQuery("SELECT * FROM JoinGroup WHERE ID = '" + FindPwID.getText().toString() + "' AND NAME = '" + FindPwName.getText().toString() + "' AND Birth = '"+ FindPwBirth.getText().toString() +"';", null);
                if(cursor.getCount() !=1){
                    Toast.makeText(FindAccount.this, "잘못된 정보입니다.", Toast.LENGTH_SHORT).show();
                }else{
                    cursor.moveToFirst();
                    String A = cursor.getString(0);
                    tvNewPwTitle.setVisibility(View.VISIBLE);
                    tvNewPw.setVisibility(View.VISIBLE);
                    etNewPw.setVisibility(View.VISIBLE);
                    tvNewPwCheck.setVisibility(View.VISIBLE);
                    etNewPwCheck.setVisibility(View.VISIBLE);
                    btNewPwOkBt.setVisibility(View.VISIBLE);
                }
                cursor.close();
                SqlDB.close();
            }
        });

    }
    // 아이디 찾기 다이얼로그
    void showFindIdDialog(String A){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Find ID");
        builder.setMessage("ID는 "+A+" 입니다.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                findIdL.setVisibility(View.INVISIBLE);
                findPwL.setVisibility(View.VISIBLE);
            }
        });
        builder.show();
    }

    void showNewPwDialog(){
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("New Password");
        builder2.setMessage("비밀번호가 재설정 되었습니다.");
        builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
        builder2.show();
    }


}