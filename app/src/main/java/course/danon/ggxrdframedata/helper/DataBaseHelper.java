package course.danon.ggxrdframedata.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataBaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "ggxrdfd.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void open(){
        db = getReadableDatabase();
    }
    public int getRowCount(String TableName){
        open();
        String Selection = "Max(_id)";
        Cursor c = db.query(TableName, new String[] {Selection}, null, null, null, null, null);
        c.moveToFirst();
        return c.getInt(0);
    }

    public Cursor getColumn(String TableName, String Selection){
        open();
        return db.query(TableName, new String[]{Selection}, null, null, null, null, null);
    }

    public Cursor getPortraitTable(String TableName){
        open();
        String Selection[] = new String[]{"_id", "Input", "Guard", "Startup", "`Frame Adv.` as Adv", "Type"};
        return db.query(TableName, Selection, null, null, null, null, null);
    }

    public Cursor getLandscapeTable(String TableName){
        open();
        String Selection[] = new String[]{"_id", "Input", "Damage", "Tension", "RISC", "Prorate", "`Attack Lv.` as Attack",
            "Guard", "Cancel", "RC", "Startup", "Active", "Recovery", "`Frame Adv.` as Adv", "`Inv.` as Inv", "Type"};
        return db.query(TableName, Selection, null, null, null, null, null);
    }

/*    public Cursor getRow(String TableName, String id){
        open();
        String Selection[] = new String[]{"Input", "Damage", "Tension", "RISC", "Prorate", "`Attack Lv.` as Attack",
            "Guard", "Cancel", "RC", "Startup", "Active", "Recovery", "`Frame Adv.` as Adv", "`Inv.` as Inv", "Type"};
        String Where = "_id = ?";
        Cursor c = db.query(TableName, Selection, Where, new String[]{id}, null, null, null);
        return c;
    }*/

    public Cursor getCharInfo(String id){
        open();
        String TableName = "CharSelectIcons";
        String[] Selection = new String[]{"Char", "FDTableName", "Icon", "`Defense Modifier`", "`Guts Rating`",
            "`Stun Resistance`", "`Jump Startup`", "`Backdash Time`", "`Backdash Invincibility`", "`IK Activation`"};
        String Where = "_id = ?";
        return db.query(TableName, Selection, Where, new String[]{id}, null, null, null);
    }

    public void close(){
        db.close();
    }
}