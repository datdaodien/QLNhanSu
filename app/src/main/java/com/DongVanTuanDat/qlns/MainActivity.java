package com.DongVanTuanDat.qlns;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.DongVanTuanDat.qlns.activity.ActivityBophan;
import com.DongVanTuanDat.qlns.activity.ActivityDangnhap;
import com.DongVanTuanDat.qlns.activity.ActivityNhanvienmain;
import com.DongVanTuanDat.qlns.activity.ActivityThongtinlienhe;

public class MainActivity extends AppCompatActivity {
    private Button btnBoPhan,btnNhanVien,btnDangXuat,btnTTLH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBoPhan = findViewById(R.id.btnBoPhan);
        btnNhanVien = findViewById(R.id.btnNhanVien);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        btnTTLH = findViewById(R.id.btnThongTinLienHe);

        btnBoPhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityBophan.class);
                startActivity(intent);

            }
        });
        //nut hiển thị nhân viên
        btnNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityNhanvienmain.class);
                startActivity(intent);
            }
        });
// nut thông tin liên hệ
        btnTTLH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityThongtinlienhe.class);
                startActivity(intent);
            }
        });
// nut dăng xuất
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogExit();
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
                Intent intent = new Intent(MainActivity.this, ActivityDangnhap.class);
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
//            Intent intent = new Intent(MainActivity.this,ActivityDangnhap.class);
//            startActivity(intent);
//            finish();
        }
    }

}