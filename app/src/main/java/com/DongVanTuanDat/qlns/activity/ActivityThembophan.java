package com.DongVanTuanDat.qlns.activity;

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

public class ActivityThembophan extends AppCompatActivity {
    private EditText edtTenBoPhanThem,edtGhiChuThem;
    private Button btnThemBoPhan,btnThoatThemBoPhan;
    private BophanDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thembophan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        anhxa();
        dao = new BophanDao(this);
        btnThemBoPhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bophan bp = new Bophan(edtTenBoPhanThem.getText().toString(),edtGhiChuThem.getText().toString());
                dao.ThemBoPhan(bp);
                finish();
            }
        });
        btnThoatThemBoPhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    
    private void anhxa() {
        edtTenBoPhanThem = findViewById(R.id.edtTenBoPhanThem);
        edtGhiChuThem = findViewById(R.id.edtGhiChuThem);
        btnThemBoPhan = findViewById(R.id.btnThemBoPhan);
        btnThoatThemBoPhan = findViewById(R.id.btnThoatThemBoPhan);
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
                Intent intent1 = new Intent(ActivityThembophan.this, MainActivity.class);
                startActivity(intent1);
                break;
            // neuclick v??o n??t c??n l???i l?? n??t back th?? quay l???i main
            case R.id.mnBophan:
                Intent intent2 = new Intent(ActivityThembophan.this, ActivityBophan.class);
                startActivity(intent2);
                break;
            case R.id.mnNhanvien:
                Intent intent3 = new Intent(ActivityThembophan.this, ActivityNhanvienmain.class);
                startActivity(intent3);
                break;
            case R.id.mnDangxuat:
                DialogExit();
                break;
            case R.id.mnThongtinlienhe:
                Intent intent5 = new Intent(ActivityThembophan.this, ActivityThongtinlienhe.class);
                startActivity(intent5);
                break;
            default:
                Intent intent = new Intent(ActivityThembophan.this,ActivityBophan.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void DialogExit() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_exit);
        //nh???n ra ngoi ????? tho??t c???a s???
        dialog.setCanceledOnTouchOutside(true);
        Button btnYes = dialog.findViewById(R.id.buttunYes);
        Button btnNo = dialog.findViewById(R.id.buttunNo);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityThembophan.this, ActivityDangnhap.class);
                startActivity(intent);
                //thoat
                Intent intent1 =new Intent(Intent.ACTION_MAIN);
                intent1.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent1);
            }
        });
        // khi nh???n n??t no ??ong c???a s???
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        //hi???n th??? c???a s???
        dialog.show();
    }
    // n???u click back ??? ??i???n tho???i s??? tr??? l???i main activity
    @Override
    public void onBackPressed() {
        count++;
        if(count>=1){
            finish();
        }
    }
}