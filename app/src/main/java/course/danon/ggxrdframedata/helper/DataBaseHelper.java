package course.danon.ggxrdframedata.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataBaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "ggxrdfd.db";
    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public int getRowCount(String TableName){
        SQLiteDatabase db = getReadableDatabase();
        String Selection = "Max(_id)";
        Cursor c = db.query(TableName, new String[] {Selection}, null, null, null, null, null);
        c.moveToFirst();
        return c.getInt(0);
    }

    public Cursor getColumn(String TableName, String Selection){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TableName, new String[]{Selection}, null, null, null, null, null);
    }

    public Cursor getPortraitTable(String TableName){
        SQLiteDatabase db = getReadableDatabase();
        String Selection[] = new String[]{"_id", "Input", "Guard", "Startup", "`Frame Adv.` as Adv", "Type"};
        return db.query(TableName, Selection, null, null, null, null, null);
    }

    public Cursor getLandscapeTable(String TableName){
        SQLiteDatabase db = getReadableDatabase();
        String Selection[] = new String[]{"_id", "Input", "Damage", "Tension", "RISC", "Prorate", "`Attack Lv.` as Attack",
            "Guard", "Cancel", "RC", "Startup", "Active", "Recovery", "`Frame Adv.` as Adv", "`Inv.` as Inv", "Type"};
        return db.query(TableName, Selection, null, null, null, null, null);
    }

/*    public Cursor getRow(String TableName, String id){
        SQLiteDatabase db = getReadableDatabase();
        String Selection[] = new String[]{"Input", "Damage", "Tension", "RISC", "Prorate", "`Attack Lv.` as Attack",
            "Guard", "Cancel", "RC", "Startup", "Active", "Recovery", "`Frame Adv.` as Adv", "`Inv.` as Inv", "Type"};
        String Where = "_id = ?";
        Cursor c = db.query(TableName, Selection, Where, new String[]{id}, null, null, null);
        return c;
    }*/

    public Cursor getCharInfo(String id){
        SQLiteDatabase db = getReadableDatabase();
        String TableName = "CharSelectIcons";
        String[] Selection = new String[]{"Char", "FDTableName", "Icon", "`Defense Modifier`", "`Guts Rating`",
            "`Stun Resistance`", "`Jump Startup`", "`Backdash Time`", "`Backdash Invincibility`", "`IK Activation`"};
        String Where = "_id = ?";
        return db.query(TableName, Selection, Where, new String[]{id}, null, null, null);
    }

    public void close(){
        SQLiteDatabase db = getReadableDatabase();
        db.close();
    }
}