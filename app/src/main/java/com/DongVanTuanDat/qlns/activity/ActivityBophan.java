package com.DongVanTuanDat.qlns.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.DongVanTuanDat.qlns.MainActivity;
import com.DongVanTuanDat.qlns.R;
import com.DongVanTuanDat.qlns.adapter.BophanAdapter;
import com.DongVanTuanDat.qlns.dao.BophanDao;
import com.DongVanTuanDat.qlns.dao.NhanvienDao;
import com.DongVanTuanDat.qlns.model.Bophan;

import java.util.ArrayList;
import java.util.List;

public class ActivityBophan extends AppCompatActivity {
    private BophanDao bpDao;
    private ListView lvBoPhan;
    private Button btnThemBoPhan;
    private List<Bophan> boPhanList;
    private BophanAdapter boPhanAdapter;
    private NhanvienDao nvd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bophan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        anhxa();
        boPhanList = new ArrayList<Bophan>();
        bpDao = new BophanDao(this);
        nvd = new NhanvienDao(this);
        boPhanList= bpDao.DanhSachBoPhan();
        boPhanAdapter=new BophanAdapter(this,boPhanList);
        lvBoPhan.setAdapter(boPhanAdapter);
        registerForContextMenu(lvBoPhan);

        btnThemBoPhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityBophan.this, ActivityThembophan.class);
                startActivity(intent);
            }
        });
        lvBoPhan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ActivityBophan.this, ActivityNhanvienbp.class);
                intent.putExtra("MaBoPhan",boPhanList.get(i).getMaBoPhan());
                startActivity(intent);
            }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu_lop,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position=info.position;
        Bophan boPhan=boPhanList.get(position);
        switch (item.getItemId()){
            case R.id.mnSuaBoPhan:{

                Intent intent=new Intent(ActivityBophan.this, ActivitySuabophan.class);
                intent.putExtra("DuLieu",boPhan);
                startActivity(intent);
                onResume();
                break;
            }
            case R.id.mnXoaBoPhan:{
                Dialog dialog = new Dialog(ActivityBophan.this);
                dialog.setContentView(R.layout.dialog_xoabophan);
                dialog.setCanceledOnTouchOutside(false);
                Button btnYes = dialog.findViewById(R.id.btnYes);
                Button btnNo = dialog.findViewById(R.id.btnNo);
                dialog.show();
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nvd.XoaBoPhanNhanVien(boPhan.getMaBoPhan());
                        bpDao.XoaBoPHan(boPhan.getMaBoPhan());
                        boPhanAdapter.notifyDataSetChanged();
                        onResume();
                        dialog.cancel();
                    }
                });
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                boPhanAdapter.notifyDataSetChanged();
                onResume();
                break;
            }
        }
        return super.onContextItemSelected(item);
    }

    private void anhxa() {
        btnThemBoPhan = findViewById(R.id.btnThemBoPhan);
        lvBoPhan = findViewById(R.id.lvBoPhan);
    }
    @Override
    protected void onResume() {
        super.onResume();
        boPhanList.clear();
        boPhanList.addAll(bpDao.DanhSachBoPhan());
        boPhanAdapter.notifyDataSetChanged();
    }


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
                Intent intent1 = new Intent(ActivityBophan.this, MainActivity.class);
                startActivity(intent1);
                break;
            // neuclick vào nút còn lại là nút back thì quay lại main
            case R.id.mnBophan:
                Intent intent2 = new Intent(ActivityBophan.this, ActivityBophan.class);
                startActivity(intent2);
                break;
            case R.id.mnNhanvien:
                Intent intent3 = new Intent(ActivityBophan.this, ActivityNhanvienmain.class);
                startActivity(intent3);
                break;
            case R.id.mnDangxuat:
               DialogExit();
                break;
            case R.id.mnThongtinlienhe:
                Intent intent5 = new Intent(ActivityBophan.this, ActivityThongtinlienhe.class);
                startActivity(intent5);
                break;
            default:
                Intent intent = new Intent(ActivityBophan.this,MainActivity.class);
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
                Intent intent = new Intent(ActivityBophan.this, ActivityDangnhap.class);
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
    int count=0;
    @Override
    public void onBackPressed() {
        count++;
        if(count>=1){
            Intent intent = new Intent(ActivityBophan.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}