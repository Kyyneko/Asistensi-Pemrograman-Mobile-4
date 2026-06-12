package com.example.skillsaga.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SkillSagaDB.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_HISTORY = "tb_history";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CATEGORY = "kategori";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_SCORE = "skor";
    public static final String COLUMN_XP = "xp";
    public static final String COLUMN_DATE = "tanggal";
    public static final String TABLE_PROGRESS = "tb_progress";
    public static final String COLUMN_PROG_CATEGORY = "kategori_kuis";
    public static final String COLUMN_PROG_UNLOCKED = "level_terbuka";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableHistory = "CREATE TABLE " + TABLE_HISTORY + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_LEVEL + " TEXT, " +
                COLUMN_SCORE + " INTEGER, " +
                COLUMN_XP + " INTEGER, " +
                COLUMN_DATE + " TEXT)";
        db.execSQL(createTableHistory);

        String createTableProgress = "CREATE TABLE " + TABLE_PROGRESS + " (" +
                COLUMN_PROG_CATEGORY + " TEXT PRIMARY KEY, " +
                COLUMN_PROG_UNLOCKED + " INTEGER)";
        db.execSQL(createTableProgress);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRESS);
        onCreate(db);
    }

    public boolean insertHistory(String kategori, String level, int skor, int xp, String tanggal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY, kategori);
        values.put(COLUMN_LEVEL, level);
        values.put(COLUMN_SCORE, skor);
        values.put(COLUMN_XP, xp);
        values.put(COLUMN_DATE, tanggal);
        long result = db.insert(TABLE_HISTORY, null, values);
        db.close();
        return result != -1;
    }

    public Cursor getAllHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_HISTORY + " ORDER BY " + COLUMN_ID + " DESC", null);
    }

    public int getTotalXP() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_XP + ") as total FROM " + TABLE_HISTORY, null);
        int total = 0;
        if (cursor.moveToFirst() && !cursor.isNull(cursor.getColumnIndexOrThrow("total"))) {
            total = cursor.getInt(cursor.getColumnIndexOrThrow("total"));
        }
        cursor.close();
        return total;
    }

    public int getTotalQuizCompleted() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(" + COLUMN_ID + ") as count FROM " + TABLE_HISTORY, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(cursor.getColumnIndexOrThrow("count"));
        }
        cursor.close();
        return count;
    }

    public int getUnlockedLevel(String kategori) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_PROG_UNLOCKED + " FROM " + TABLE_PROGRESS +
                " WHERE " + COLUMN_PROG_CATEGORY + " = ?", new String[]{kategori});

        int unlockedLevel = 1;

        if (cursor.moveToFirst()) {
            unlockedLevel = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PROG_UNLOCKED));
        }
        cursor.close();
        return unlockedLevel;
    }

    public void unlockNextLevel(String kategori, int newlyUnlockedLevel) {
        SQLiteDatabase db = this.getWritableDatabase();

        int currentUnlocked = getUnlockedLevel(kategori);

        if (newlyUnlockedLevel > currentUnlocked) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_PROG_CATEGORY, kategori);
            values.put(COLUMN_PROG_UNLOCKED, newlyUnlockedLevel);

            db.replace(TABLE_PROGRESS, null, values);
        }
        db.close();
    }

    public String getKategoriFavorit() {
        String kategoriFavorit = "-";
        android.database.sqlite.SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_CATEGORY + ", COUNT(" + COLUMN_CATEGORY + ") as frekuensi " +
                "FROM " + TABLE_HISTORY + " " +
                "GROUP BY " + COLUMN_CATEGORY + " " +
                "ORDER BY frekuensi DESC " +
                "LIMIT 1";

        android.database.Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                kategoriFavorit = cursor.getString(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }

        return kategoriFavorit;
    }
    public void clearAllData() {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_HISTORY);
        db.execSQL("DELETE FROM " + TABLE_PROGRESS);
        db.execSQL("DELETE FROM sqlite_sequence WHERE name='" + TABLE_HISTORY + "'");

        db.close();
    }
}