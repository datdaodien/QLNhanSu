package com.DongVanTuanDat.qlns.activity;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.DongVanTuanDat.qlns.MainActivity;
import com.DongVanTuanDat.qlns.R;
import com.DongVanTuanDat.qlns.dao.BophanDao;
import com.DongVanTuanDat.qlns.model.Bophan;

public class ActivitySuabophan extends AppCompatActivity {
    private EditText edtTenBoPhanSua,edtGhiChuSua,edtMaBoPhanSua;
    private Button btnSuaBoPhan,btnThoatSuaBoPhan;
    private BophanDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suabophan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        anhxa();
        dao = new BophanDao(this);
        Intent intent= getIntent();
        final Bophan bp=(Bophan) intent.getSerializableExtra("DuLieu");

        edtMaBoPhanSua.setText(String.valueOf(bp.getMaBoPhan()));

        edtTenBoPhanSua.setText(bp.getTenBoPhan());
        edtGhiChuSua.setText(bp.getGhiChu());
        btnSuaBoPhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bophan bp = new Bophan(Integer.parseInt(edtMaBoPhanSua.getText().toString().trim()),edtTenBoPhanSua.getText().toString().trim(),edtGhiChuSua.getText().toString().trim());
                dao.SuaBoPhan(bp);
                finish();
//                Integer.parseInt(edtMaBoPhanSua.getText().toString()),
            }
        });
        btnThoatSuaBoPhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void anhxa() {
        edtTenBoPhanSua = findViewById(R.id.edtTenBoPhanSua);
        edtGhiChuSua = findViewById(R.id.edtGhiChuSua);
        edtMaBoPhanSua = findViewById(R.id.edtMaBoPhanSua);
        btnSuaBoPhan = findViewById(R.id.btnSuaBoPhan);
        btnThoatSuaBoPhan = findViewById(R.id.btnThoatSuaBoPhan);
    }

    int count=0;
    // them mot menu laf add vao toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnTrangchu:
                Intent intent1 = new Intent(ActivitySuabophan.this, MainActivity.class);
                startActivity(intent1);
                break;
            // neuclick vào nút còn lại là nút back thì quay lại main
            case R.id.mnBophan:
                Intent intent2 = new Intent(ActivitySuabophan.this, ActivityBophan.class);
                startActivity(intent2);
                break;
            case R.id.mnNhanvien:
                Intent intent3 = new Intent(ActivitySuabophan.this, ActivityNhanvienmain.class);
                startActivity(intent3);
                break;
            case R.id.mnDangxuat:
                DialogExit();
                break;
            case R.id.mnThongtinlienhe:
                Intent intent5 = new Intent(ActivitySuabophan.this, ActivityThongtinlienhe.class);
                startActivity(intent5);
                break;
            default:
                Intent intent = new Intent(ActivitySuabophan.this,ActivityBophan.class);
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
                Intent intent = new Intent(ActivitySuabophan.this, ActivityDangnhap.class);
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
            finish();
        }
    }
}