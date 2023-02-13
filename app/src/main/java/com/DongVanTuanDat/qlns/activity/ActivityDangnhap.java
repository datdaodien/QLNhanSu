package com.DongVanTuanDat.qlns.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.DongVanTuanDat.qlns.MainActivity;
import com.DongVanTuanDat.qlns.R;

public class ActivityDangnhap extends AppCompatActivity {
    private EditText edtPass,edtUser;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        edtPass = findViewById(R.id.edtPass);
        edtUser = findViewById(R.id.edtUser);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_user = edtUser.getText().toString().trim();
                String str_pass = edtPass.getText().toString().trim();


                if(TextUtils.isEmpty(str_user)){
                    Toast.makeText(getApplicationContext(),"Bạn chưa nhập Tài khoản",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(str_pass)){
                    Toast.makeText(getApplicationContext(),"Bạn chưa nhập mật khẩu",Toast.LENGTH_SHORT).show();
                } else if ((str_user.equals("admin")) && (str_pass.equals("admin"+""))||
                           (str_user.equals("dat")) && (str_pass.equals("31122001"+""))||
                           (str_user.equals("a")) && (str_pass.equals("a"+""))
                ) {
                    Intent inte = new Intent(ActivityDangnhap.this, MainActivity.class);
                    startActivity(inte);
                } else {
                    Toast.makeText(ActivityDangnhap.this, "Tên hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();

                }
            }
        });
        }
    // hàm xửa lý đăng xuất
    private void DialogExit() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_exit);
        //nhắn ra ngoi để thoát cửa sổ
        dialog.setCanceledOnTouchOutside(true);
        Button btnYes = dialog.findViewById(R.id.buttunYes);
        Button btnNo = dialog.findViewById(R.id.buttunNo);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityDangnhap.this, ActivityDangnhap.class);
                startActivity(intent);
                //thoat
                Intent intent1 =new Intent(Intent.ACTION_MAIN);
                intent1.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent1);
            }
        });
        // khi nhắn nút no đong cửa sổ
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        //hiển thị cửa sổ
        dialog.show();
    }
    int count=0;
    @Override
    public void onBackPressed() {
        count++;
        if(count>=1){
            DialogExit();
        }
     }
    }