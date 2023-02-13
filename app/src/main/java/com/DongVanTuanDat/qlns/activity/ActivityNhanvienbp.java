package com.DongVanTuanDat.qlns.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.DongVanTuanDat.qlns.MainActivity;
import com.DongVanTuanDat.qlns.R;
import com.DongVanTuanDat.qlns.adapter.NhanvienAdapter;
import com.DongVanTuanDat.qlns.dao.BophanDao;
import com.DongVanTuanDat.qlns.dao.NhanvienDao;
import com.DongVanTuanDat.qlns.model.Nhanvien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ActivityNhanvienbp extends AppCompatActivity {
    private FloatingActionButton btnThemNhanVienBP;
    private SearchView svNhanVienBP;
    private ListView lvNhanVienBP;
    private TextView tvTieuDeNhanVienBP,tvTongSoBP;
    private NhanvienDao dao;
    private List<Nhanvien> nvList;
    private NhanvienAdapter nhanVienAdapter;
    private String tenBoPhan;
    private BophanDao bpdao;
    private int mabp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhanvienbp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        anhxa();
        bpdao = new BophanDao(this);
        nvList = new ArrayList<>();
        Intent intent = getIntent();
        mabp = intent.getIntExtra("MaBoPhan", 0);
        tenBoPhan = bpdao.getTenBoPhan(mabp);
        tvTieuDeNhanVienBP.setText("Danh sách nhân viên bộ phận " + tenBoPhan);
        dao = new NhanvienDao(this);
        nvList = dao.DanhSachNhanVien(mabp);
        tvTongSoBP.setText("Tổng số : "+nvList.size() + " nhân viên");
        nhanVienAdapter = new NhanvienAdapter(this, nvList);
        lvNhanVienBP.setAdapter(nhanVienAdapter);
        registerForContextMenu(lvNhanVienBP);
        btnThemNhanVienBP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent thisintent = new Intent(ActivityNhanvienbp.this, ActivityThemnhanvien.class);
                thisintent.putExtra("maBoPhan", mabp);
                startActivity(thisintent);
            }
        });

        lvNhanVienBP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Nhanvien nv = nvList.get(i);
                Intent inte = new Intent(ActivityNhanvienbp.this, ActivityThongtinnv.class);
                inte.putExtra("maNhanVien", nv.getMaNhanVien());
                startActivity(inte);
            }
        });

        lvNhanVienBP.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Dialog dialog = new Dialog(ActivityNhanvienbp.this);
                dialog.setContentView(R.layout.dialog_xoanhanvien);
                dialog.setCanceledOnTouchOutside(false);
                Button btnYes = dialog.findViewById(R.id.btnYes);
                Button btnNo = dialog.findViewById(R.id.btnNo);
                dialog.show();
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dao.XoaNhanVien(nvList.get(i).getMaNhanVien());
                        nhanVienAdapter.notifyDataSetChanged();
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
                nhanVienAdapter.notifyDataSetChanged();
                onResume();
                return true;
            }
        });
        svNhanVienBP.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;

                if (text != null) {
                    nhanVienAdapter.filter(text);

                }

                return false;
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        nvList.clear();
        nvList.addAll(dao.DanhSachNhanVien(mabp));
        nhanVienAdapter.notifyDataSetChanged();
    }
    private void anhxa() {
        btnThemNhanVienBP = findViewById(R.id.btnThemNhanVienBP);
        svNhanVienBP = findViewById(R.id.svNhanVienBP);
        lvNhanVienBP = findViewById(R.id.lvNhanVienBP);
        tvTieuDeNhanVienBP = findViewById(R.id.tvTieuDeNhanVienBP);
        tvTongSoBP = findViewById(R.id.tvTongSoBP);
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
                Intent intent1 = new Intent(ActivityNhanvienbp.this, MainActivity.class);
                startActivity(intent1);
                break;
            // neuclick vào nút còn lại là nút back thì quay lại main
            case R.id.mnBophan:
                Intent intent2 = new Intent(ActivityNhanvienbp.this, ActivityBophan.class);
                startActivity(intent2);
                break;
            case R.id.mnNhanvien:
                Intent intent3 = new Intent(ActivityNhanvienbp.this, ActivityNhanvienmain.class);
                startActivity(intent3);
                break;
            case R.id.mnDangxuat:
                DialogExit();
                break;
            case R.id.mnThongtinlienhe:
                Intent intent5 = new Intent(ActivityNhanvienbp.this, ActivityThongtinlienhe.class);
                startActivity(intent5);
                break;
            default:
                Intent intent = new Intent(ActivityNhanvienbp.this,ActivityBophan.class);
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
                Intent intent = new Intent(ActivityNhanvienbp.this, ActivityDangnhap.class);
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
            Intent intent = new Intent(ActivityNhanvienbp.this,ActivityBophan.class);
            startActivity(intent);
            finish();
        }
    }
}