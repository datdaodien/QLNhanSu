package com.DongVanTuanDat.qlns.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.DongVanTuanDat.qlns.MainActivity;
import com.DongVanTuanDat.qlns.R;

public class ActivityThongtinlienhe extends AppCompatActivity {
    int count=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinlienhe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
    // them mot menu add vao toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnTrangchu:
                Intent intent1 = new Intent(ActivityThongtinlienhe.this, MainActivity.class);
                startActivity(intent1);
                break;
            // neuclick vào nút còn lại là nút back thì quay lại main
            case R.id.mnBophan:
                Intent intent2 = new Intent(ActivityThongtinlienhe.this, ActivityBophan.class);
                startActivity(intent2);
                break;
            case R.id.mnNhanvien:
                Intent intent3 = new Intent(ActivityThongtinlienhe.this, ActivityNhanvienmain.class);
                startActivity(intent3);
                break;
            case R.id.mnDangxuat:
                DialogExit();
                break;
            case R.id.mnThongtinlienhe:
                Intent intent5 = new Intent(ActivityThongtinlienhe.this, ActivityThongtinlienhe.class);
                startActivity(intent5);
                break;
            default:
                Intent intent = new Intent(ActivityThongtinlienhe.this,MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
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
                Intent intent = new Intent(ActivityThongtinlienhe.this, ActivityDangnhap.class);
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
    // nếu click back ở điện thoại sẽ trở lại main activity
    @Override
    public void onBackPressed() {
        count++;
        if(count>=1){
            Intent intent = new Intent(ActivityThongtinlienhe.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
