package ua.in.levor.arkanoid.DB;


import com.badlogic.gdx.sql.Database;
import com.badlogic.gdx.sql.DatabaseCursor;
import com.badlogic.gdx.sql.DatabaseFactory;
import com.badlogic.gdx.sql.SQLiteGdxException;

public class DBHelper {
    private static DBHelper instance;
    private static final String DATABASE_NAME = "arkanoid.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table if not exists "
            + CurrencyTable.TABLE_NAME
            + "(_id integer primary key autoincrement, "
            + CurrencyTable.Cols.GOLD + " integer default 300, "
            + CurrencyTable.Cols.GEMS + " integer default 10);";

    // Database update sql statement
    private static final String DATABASE_UPDATE = null;

    public static DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
        }
        return instance;
    }

    private Database database;

    private DBHelper() {
        database = DatabaseFactory.getNewDatabase(DATABASE_NAME,
                DATABASE_VERSION, DATABASE_CREATE, DATABASE_UPDATE);
        database.setupDatabase();

        try {
            database.openOrCreateDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        initDB();
    }

    private void initDB() {
        try {
            DatabaseCursor cursor = database.rawQuery("SELECT * FROM " + CurrencyTable.TABLE_NAME);
            if (cursor.getCount() < 1) {
                database.beginTransaction();
                database.execSQL("INSERT INTO " + CurrencyTable.TABLE_NAME + "(" + CurrencyTable.Cols.GOLD + ") VALUES (" + 300 + ")");
                database.execSQL("INSERT INTO " + CurrencyTable.TABLE_NAME + "(" + CurrencyTable.Cols.GEMS + ") VALUES (" + 10 + ")");
                database.setTransactionSuccessful();
                database.endTransaction();

                System.out.println(getGoldFromDB());
            }
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    public long getGoldFromDB() {
        try {
            DatabaseCursor cursor = database.rawQuery("SELECT * FROM " + CurrencyTable.TABLE_NAME);
            while (cursor.next()) {
                return cursor.getLong(CurrencyTable.Cols.GOLD);
            }
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getGemsFromDB() {
        try {
            DatabaseCursor cursor = database.rawQuery("SELECT * FROM " + CurrencyTable.TABLE_NAME);
            if (cursor.next()) {
                return cursor.getInt(CurrencyTable.Cols.GEMS);
            }
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void updateGold(long value) {
        try {
            database.beginTransaction();
            database.execSQL("UPDATE " + CurrencyTable.TABLE_NAME + " SET " + CurrencyTable.Cols.GOLD + " = " + value);
            database.setTransactionSuccessful();
            database.endTransaction();
            System.out.println("Written");

        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }

    public void updateGems(long value) {
        try {
            database.beginTransaction();
            database.execSQL("UPDATE " + CurrencyTable.TABLE_NAME + " SET " + CurrencyTable.Cols.GEMS + " = " + value);
            database.setTransactionSuccessful();
            database.endTransaction();

        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
    }





    //DB layout
    public class CurrencyTable {
        public static final String TABLE_NAME = "currency_table";
        public class Cols {
            public static final String GOLD = "gold";
            public static final String GEMS = "gems";
        }
    }
}