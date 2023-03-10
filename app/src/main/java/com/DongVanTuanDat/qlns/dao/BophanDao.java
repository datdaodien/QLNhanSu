package com.DongVanTuanDat.qlns.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.DongVanTuanDat.qlns.database.Database;
import com.DongVanTuanDat.qlns.model.Bophan;

import java.util.ArrayList;
import java.util.List;

public class BophanDao {
    private Database database;

        public BophanDao(Context context) {
            database = new Database(context);
        }
        public List<Bophan> DanhSachBoPhan() {
            List<Bophan> BPLIST = new ArrayList<Bophan>();
            String sql = "SELECT * FROM '"+database.TABLE_NAME_BOPHAN+"'";
            SQLiteDatabase db = database.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int maBoPhan = cursor.getInt(0);
                String tenBoPhan = cursor.getString(1);
                String ghiChu = cursor.getString(2);
                Bophan bp = new Bophan(maBoPhan, tenBoPhan, ghiChu);
                BPLIST.add(bp);
                cursor.moveToNext();
            }
            return BPLIST;
        }

        public void ThemBoPhan(Bophan boPhan) {
            SQLiteDatabase db = database.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("tenBoPhan", boPhan.getTenBoPhan());
            values.put("ghiChu", boPhan.getGhiChu());
            db.insert("'"+ database.TABLE_NAME_BOPHAN +"'", null, values);

        }

        public void SuaBoPhan(Bophan boPhan) {
            SQLiteDatabase db = database.getWritableDatabase();
            String sql = "UPDATE '"+database.TABLE_NAME_BOPHAN+"' SET tenBoPhan='" + boPhan.getTenBoPhan() + "', ghiChu='" + boPhan.getGhiChu() + "' WHERE maBoPhan= '" + boPhan.getMaBoPhan() + "' ";
            db.execSQL(sql);
            db.close();

        }

        public int XoaBoPHan(int id) {
            SQLiteDatabase db = database.getWritableDatabase();
            int kq = db.delete("'"+database.TABLE_NAME_BOPHAN+"'", "maBoPhan=?", new String[]{String.valueOf(id)});
            db.close();
            return kq;
        }

        public List<String> getAllTenBoPhan() {
            List<String> HHList = new ArrayList<>();
            String sql = "SELECT tenBoPhan FROM '"+database.TABLE_NAME_BOPHAN+"'";
            SQLiteDatabase db = database.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            if(cursor!=null && cursor.getCount() > 0)
            {
                if (cursor.moveToFirst())
                {
                    do {
                        HHList.add(cursor.getString(0));
                    } while (cursor.moveToNext());
                }
            }
            return HHList;
        }
    public String getTenBoPhan(int maBoPhan) {
        String sql = "SELECT tenBoPhan FROM '"+database.TABLE_NAME_BOPHAN+"' where maBoPhan = "+maBoPhan;
        String tenBoPhan = "";
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        if(cursor!=null && cursor.getCount() > 0)
        {
            if (cursor.moveToFirst())
            {
                    tenBoPhan = cursor.getString(0);
            }
        }
        return tenBoPhan;
    }
    public int getMaBoPhan(String tenBoPhan) {
        String sql = "SELECT maBoPhan FROM '"+database.TABLE_NAME_BOPHAN+"' where tenBoPhan = '"+tenBoPhan+"'";
        int mabophan = 0;
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        if(cursor!=null && cursor.getCount() > 0)
        {
            if (cursor.moveToFirst())
            {
                mabophan = cursor.getInt(0);
            }
        }
        return mabophan;
    }




}

