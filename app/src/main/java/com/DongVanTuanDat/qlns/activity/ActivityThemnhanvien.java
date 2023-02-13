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

public class ActivityThemnhanvien extends AppCompatActivity {

    private EditText edtMaNhanVienAdd,edtTenNhanVienAdd,edtNgaySinhNhanVienAdd,edtSDTNhanVienAdd,edtChucVuNhanVienAdd;
    private Button btnCameraAdd,btnLibraryAdd,btnAddNhanVien,btnThoatAddNV;
    private CircleImageView imgAnhNhanVienAdd;
    private int Resquet_code_camera=123;
    private int Resquet_code_library=456;
    private NhanvienDao nhanVienDao;
    private Spinner spnTenBoPhan;
    private BophanDao boPhanDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themnhanvien);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        anhxa();
        nhanVienDao = new NhanvienDao(this);
        boPhanDao = new BophanDao(this);
        Intent intent = getIntent();
        int maBoPhan = intent.getIntExtra("maBoPhan",0);
        ArrayList listtenNV = new ArrayList();
        if (maBoPhan==-1) {
            listtenNV = (ArrayList) boPhanDao.getAllTenBoPhan();
        } else {
            listtenNV.clear();
            listtenNV.add(boPhanDao.getTenBoPhan(maBoPhan));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                listtenNV);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spnTenBoPhan.setAdapter(adapter);


        btnCameraAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,Resquet_code_camera);
            }
        });

        btnLibraryAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,Resquet_code_library);

            }
        });
        btnAddNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maNhanVien =edtMaNhanVienAdd.getText().toString();
                String tenNV =edtTenNhanVienAdd.getText().toString();
                String ngaySinh =edtNgaySinhNhanVienAdd.getText().toString();
                String soDienThoai =edtSDTNhanVienAdd.getText().toString();
                String chucVu =edtChucVuNhanVienAdd.getText().toString();
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAnhNhanVienAdd.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream bytearray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,bytearray);
                byte[] hinh = bytearray.toByteArray();
                String tl = spnTenBoPhan.getSelectedItem().toString();
                int mabp = boPhanDao.getMaBoPhan(tl);
                Nhanvien nv = new Nhanvien(maNhanVien,tenNV,ngaySinh,soDienThoai,chucVu,hinh,mabp);
                nhanVienDao.ThemNhanVien(nv);
                finish();
            }
        });

        btnThoatAddNV.setOnClickListener(new View.OnClickListener() {
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
            imgAnhNhanVienAdd.setImageBitmap(bimap);
        }
        if (requestCode == Resquet_code_library && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAnhNhanVienAdd.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private void anhxa() {
        edtMaNhanVienAdd = findViewById(R.id.edtMaNhanVienAdd);
        edtTenNhanVienAdd = findViewById(R.id.edtTenNhanVienAdd);
        edtNgaySinhNhanVienAdd = findViewById(R.id.edtNgaySinhNhanVienAdd);
        edtSDTNhanVienAdd = findViewById(R.id.edtSDTNhanVienAdd);
        edtChucVuNhanVienAdd = findViewById(R.id.edtChucVuNhanVienAdd);

        btnCameraAdd = findViewById(R.id.btnCameraAdd);
        btnLibraryAdd = findViewById(R.id.btnLibraryAdd);
        btnAddNhanVien = findViewById(R.id.btnAddNhanVien);
        btnThoatAddNV = findViewById(R.id.btnThoatAddNV);

        imgAnhNhanVienAdd = findViewById(R.id.imgAnhNhanVienAdd);

        spnTenBoPhan = findViewById(R.id.spnTenBoPhan);
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
                Intent intent1 = new Intent(ActivityThemnhanvien.this, MainActivity.class);
                startActivity(intent1);
                break;
            // neuclick vào nút còn lại là nút back thì quay lại main
            case R.id.mnBophan:
                Intent intent2 = new Intent(ActivityThemnhanvien.this, ActivityBophan.class);
                startActivity(intent2);
                break;
            case R.id.mnNhanvien:
                Intent intent3 = new Intent(ActivityThemnhanvien.this, ActivityNhanvienmain.class);
                startActivity(intent3);
                break;
            case R.id.mnDangxuat:
                DialogExit();
                break;
            case R.id.mnThongtinlienhe:
                Intent intent5 = new Intent(ActivityThemnhanvien.this, ActivityThongtinlienhe.class);
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
                Intent intent = new Intent(ActivityThemnhanvien.this, ActivityDangnhap.class);
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