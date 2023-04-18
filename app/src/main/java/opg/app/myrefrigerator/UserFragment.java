package opg.app.myrefrigerator;

import static opg.app.myrefrigerator.Login.mainId;
import static opg.app.myrefrigerator.Login.mainPass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

 // 유저 정보 화면
public class UserFragment extends Fragment {
    ImageView LogoutimgV, change_pw_iv, info_user_iv;//iv_change_pw, iv_info_user;
    Context ct;

    // 황호준 - 데이터 변수
    SQLiteDatabase sqlDB;
    static String Aname;
    static String Bbirth;

    LinearLayout layout1_pw, layout2_pw, layout3_pw;
    TextView current_check_pw_tv, change_pw_tv_, check_pw_tv_,username,userbirth;
    EditText current_check_pw_et, change_pw_et, check_pw_et;
    Button check_pw_bt, change_pw_bt;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        layout1_pw = (LinearLayout) view.findViewById(R.id.pw_layout1);
        layout2_pw = (LinearLayout) view.findViewById(R.id.pw_layout2);
        layout3_pw = (LinearLayout) view.findViewById(R.id.pw_layout3);

        current_check_pw_tv = (TextView) view.findViewById(R.id.tv_current_check_pw);
        change_pw_tv_ = (TextView) view.findViewById(R.id.tv_change_pw);
        check_pw_tv_ = (TextView) view.findViewById(R.id.tv_check_pw);
        username =(TextView) view.findViewById(R.id.user_name);
        userbirth=(TextView) view.findViewById(R.id.user_birth);

        current_check_pw_et = (EditText) view.findViewById(R.id.et_current_check_pw);
        change_pw_et = (EditText) view.findViewById(R.id.et_change_pw);
        check_pw_et = (EditText) view.findViewById(R.id.et_check_pw);

        sqlDB = Login.mySQLiteHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM JoinGroup WHERE ID = '" + mainId + "';", null);
        cursor.moveToFirst();
        username.setText(cursor.getString(2));
        userbirth.setText(cursor.getString(3));
        Aname=cursor.getString(2);
        Bbirth=cursor.getString(3);
        cursor.close();
        sqlDB.close();

        check_pw_bt = (Button) view.findViewById(R.id.bt_check_pw);
        check_pw_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = Login.mySQLiteHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM JoinGroup WHERE ID = '" + mainId + "' AND Pass = '" + current_check_pw_et.getText().toString() + "';", null);
                if(cursor.getCount() !=1){
                    Toast.makeText(ct, "잘못된 정보입니다.",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ct, "비밀번호가 일치합니다",Toast.LENGTH_SHORT).show();
                    layout2_pw.setVisibility(View.VISIBLE);
                    layout3_pw.setVisibility(View.VISIBLE);
                    change_pw_bt.setVisibility(View.VISIBLE);
                }

                cursor.close();
                sqlDB.close();

            }
        });

        change_pw_bt = (Button) view.findViewById(R.id.bt_change_pw);
        change_pw_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t1 =change_pw_et.getText().toString();
                String t2 =check_pw_et.getText().toString();
                if(!t1.equals(t2)){
                    Toast.makeText(ct, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }else if(t1.isEmpty()||t2.isEmpty()){
                    Toast.makeText(ct, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    showChangePasswordDialog(ct);
                }

            }
        });

        ct = container.getContext();

        change_pw_iv = (ImageView) view.findViewById(R.id.iv_change_pw);
        change_pw_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layout1_pw.getVisibility() == View.VISIBLE) {// 보일 때
                    layout1_pw.setVisibility(View.INVISIBLE);
                    check_pw_bt.setVisibility(View.INVISIBLE);
                }else{ // 안보일 때
                    layout1_pw.setVisibility(View.VISIBLE);
                    check_pw_bt.setVisibility(View.VISIBLE);
                }

            }
        });

        info_user_iv = (ImageView) view.findViewById(R.id.iv_info_user);
        info_user_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUserInfoDialog(ct);
            }
        });

        LogoutimgV = (ImageView) view.findViewById(R.id.logoutIV);
        LogoutimgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog(ct);
            }
        });
        return view;
    }

    // 유저 정보 다이얼로그그
   void showUserInfoDialog(Context ct){
        AlertDialog.Builder builder = new AlertDialog.Builder(ct);
        builder.setTitle("User Information");
        builder.setMessage("이름 : "+Aname+"\n\n생일 : "+Bbirth );
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    // 로그아웃 다이얼로그
    void showLogoutDialog(Context ct){
        AlertDialog.Builder builder = new AlertDialog.Builder(ct);
        builder.setTitle("Logout");
        builder.setMessage("정말로 로그아웃 하시겠습니까?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ct, Login.class);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
    // 비밀번호 변경 다이얼로그
    void showChangePasswordDialog(Context ct){
        AlertDialog.Builder builder = new AlertDialog.Builder(ct);
        builder.setTitle("비밀번호 변경");
        builder.setMessage("정말로 비밀번호를 변경 하시겠습니까?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sqlDB = Login.mySQLiteHelper.getReadableDatabase() ;
                sqlDB.execSQL("UPDATE JoinGroup SET PASS='"+change_pw_et.getText().toString()+"' WHERE ID= '" + mainId +"' ;");
                sqlDB.close();
                Toast.makeText(ct, "비밀번호가 변경되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ct, "비밀번호가 변경 취소.",Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
        layout1_pw.setVisibility(View.INVISIBLE);
        layout2_pw.setVisibility(View.INVISIBLE);
        layout3_pw.setVisibility(View.INVISIBLE);
        check_pw_bt.setVisibility(View.INVISIBLE);
        change_pw_bt.setVisibility(View.INVISIBLE);
    }
}