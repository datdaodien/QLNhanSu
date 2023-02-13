package com.DongVanTuanDat.qlns.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.DongVanTuanDat.qlns.MainActivity;
import com.DongVanTuanDat.qlns.R;
import com.DongVanTuanDat.qlns.dao.BophanDao;
import com.DongVanTuanDat.qlns.dao.NhanvienDao;
import com.DongVanTuanDat.qlns.model.Nhanvien;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityThongtinnv extends AppCompatActivity  {
    private EditText edtMaNhanVienInfo,edtTenNhanVienInfo,edtNgaySinhInfo,edtDienThoaiInfo,edtChucVuInfo;
    private Button btnSuaInFo,btnCameraSua,btnLibrarySua,btnSuaNhanVienInfo,btnThoatNhanVienInfo;
    private CircleImageView imgAnhInfo;
    private int Resquet_code_camera=321;
    private int Resquet_code_library=654;
    private NhanvienDao nhanVienDao;
    private BophanDao bpDao;
    private Spinner spnTenBoPhanInfo;
    private ArrayList listtenbophan;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinnv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        anhxa();
        nhanVienDao = new NhanvienDao(this);
        bpDao = new BophanDao(this);
        Intent intee= getIntent();
        String manv = intee.getStringExtra("maNhanVien");
        Nhanvien nhanvien = nhanVienDao.getNhanVien(manv);


        edtMaNhanVienInfo.setText(nhanvien.getMaNhanVien());
        edtTenNhanVienInfo.setText(nhanvien.getTenNhanVien());
        edtNgaySinhInfo.setText(nhanvien.getNgaySinh());
        edtDienThoaiInfo.setText(nhanvien.getSdt());
        edtChucVuInfo.setText(nhanvien.getChucVu());
        Bitmap bitmap = BitmapFactory.decodeByteArray(nhanvien.getAnh(), 0, nhanvien.getAnh().length);
        imgAnhInfo.setImageBitmap(bitmap);


        listtenbophan = new ArrayList();
        String ten = bpDao.getTenBoPhan(nhanvien.getMaBoPhan());
        listtenbophan.add(ten);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                listtenbophan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spnTenBoPhanInfo.setAdapter(adapter);

        btnSuaNhanVienInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String manhanvien =edtMaNhanVienInfo.getText().toString();
                String tennhanvien =edtTenNhanVienInfo.getText().toString();
                String ngaysinh =edtNgaySinhInfo.getText().toString();
                String sdt =edtDienThoaiInfo.getText().toString();
                String chucvu =edtChucVuInfo.getText().toString();
                String bophan = spnTenBoPhanInfo.getSelectedItem().toString();
                int mabophan = bpDao.getMaBoPhan(bophan);
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAnhInfo.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream bytearray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,bytearray);
                byte[] hinh = bytearray.toByteArray();

                Nhanvien nv = new Nhanvien(manhanvien,tennhanvien,ngaysinh,sdt,chucvu,hinh,mabophan);
                nhanVienDao.SuaNhanVien(nv);
                finish();
            }
        });


        btnSuaInFo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtTenNhanVienInfo.setEnabled(true);
                edtNgaySinhInfo.setEnabled(true);
                edtDienThoaiInfo.setEnabled(true);
                edtChucVuInfo.setEnabled(true);

                btnSuaNhanVienInfo.setVisibility(View.VISIBLE);
                btnLibrarySua.setVisibility(View.VISIBLE);
                btnCameraSua.setVisibility(View.VISIBLE);

                listtenbophan.clear();
                listtenbophan.addAll(bpDao.getAllTenBoPhan()) ;
                adapter.notifyDataSetChanged();
            }
        });




        btnCameraSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,Resquet_code_camera);
            }
        });

        btnLibrarySua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,Resquet_code_library);

            }
        });

        btnThoatNhanVienInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Resquet_code_camera && resultCode == RESULT_OK && data != null) {
            Bitmap bimap = (Bitmap) data.getExtras().get("data");
            imgAnhInfo.setImageBitmap(bimap);
        }
        if (requestCode == Resquet_code_library && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAnhInfo.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
    
    private void anhxa() {
        edtMaNhanVienInfo = findViewById(R.id.edtMaNhanVienInfo);
        edtTenNhanVienInfo = findViewById(R.id.edtTenNhanVienInfo);
        edtNgaySinhInfo = findViewById(R.id.edtNgaySinhInfo);
        edtDienThoaiInfo = findViewById(R.id.edtDienThoaiInfo);
        edtChucVuInfo = findViewById(R.id.edtChucVuInfo);

        imgAnhInfo = findViewById(R.id.imgAnhInfo);

        btnCameraSua = findViewById(R.id.btnCameraSua);
        btnLibrarySua = findViewById(R.id.btnLibrarySua);
        btnSuaInFo = findViewById(R.id.btnSuaInFo);
        btnSuaNhanVienInfo = findViewById(R.id.btnSuaNhanVienInfo);
        btnThoatNhanVienInfo = findViewById(R.id.btnThoatNhanVienInfo);

        spnTenBoPhanInfo = findViewById(R.id.spnTenBoPhanInfo);
    }
    int count=0;
    // them mot menu laf add vao toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.mnTrangchu:
                Intent intent1 = new Intent(ActivityThongtinnv.this, MainActivity.class);
                startActivity(intent1);
                break;
            // neuclick vào nút còn lại là nút back thì quay lại main
            case R.id.mnBophan:
                Intent intent2 = new Intent(ActivityThongtinnv.this, ActivityBophan.class);
                startActivity(intent2);
                break;
            case R.id.mnNhanvien:
                Intent intent3 = new Intent(ActivityThongtinnv.this, ActivityNhanvienmain.class);
                startActivity(intent3);
                break;
            case R.id.mnDangxuat:
                 DialogExit();
//                Intent intent4 = new Intent(ActivityThongtinnv.this, ActivityDangnhap.class);
//                startActivity(intent4);
                break;
            case R.id.mnThongtinlienhe:
                Intent intent5 = new Intent(ActivityThongtinnv.this, ActivityThongtinlienhe.class);
                startActivity(intent5);
                break;
            default:
                finish();
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
                Intent intent = new Intent(ActivityThongtinnv.this, ActivityDangnhap.class);
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