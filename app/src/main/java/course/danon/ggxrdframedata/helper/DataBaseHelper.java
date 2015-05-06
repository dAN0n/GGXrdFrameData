package course.danon.ggxrdframedata.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import static course.danon.ggxrdframedata.helper.DataBaseParams.*;

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
        Cursor c = db.query(TableName, new String[] {KEY_MAX_ID}, null, null, null, null, null);
        c.moveToFirst();
        return c.getInt(0);
    }

    public Cursor getColumn(String TableName, String Selection){
        open();
        return db.query(TableName, new String[]{Selection}, null, null, null, null, null);
    }

    public Cursor getPortraitTable(String TableName){
        open();
        String Selection[] = new String[]{
            KEY_ID,
            KEY_INPUT,
            KEY_GUARD,
            KEY_STARTUP,
            KEY_FRAME_ADV,
            KEY_TYPE};
        return db.query(TableName, Selection, null, null, null, null, null);
    }

    public Cursor getLandscapeTable(String TableName){
        open();
        String Selection[] = new String[]{
            KEY_ID,
            KEY_INPUT,
            KEY_DAMAGE,
            KEY_TENSION,
            KEY_RISC,
            KEY_PRORATE,
            KEY_ATTACK_LV,
            KEY_GUARD,
            KEY_CANCEL,
            KEY_RC,
            KEY_STARTUP,
            KEY_ACTIVE,
            KEY_RECOVERY,
            KEY_FRAME_ADV,
            KEY_INVUL,
            KEY_TYPE};
        return db.query(TableName, Selection, null, null, null, null, null);
    }

/*    public Cursor getRow(String TableName, String id){
        open();
        String Selection[] = new String[]{
                    KEY_ID,
                    KEY_INPUT,
                    KEY_DAMAGE,
                    KEY_TENSION,
                    KEY_RISC,
                    KEY_PRORATE,
                    KEY_ATTACK_LV,
                    KEY_GUARD,
                    KEY_CANCEL,
                    KEY_RC,
                    KEY_STARTUP,
                    KEY_ACTIVE,
                    KEY_RECOVERY,
                    KEY_FRAME_ADV,
                    KEY_INVUL,
                    KEY_TYPE};
        return db.query(TableName, Selection, KEY_WHERE_ID_QUESTION, new String[]{id}, null, null, null);
    }*/

    public Cursor getCharInfo(String id){
        open();
        String[] Selection = new String[]{
            KEY_CHAR,
            KEY_TABLENAME,
            KEY_ICON,
            KEY_DEFENSE_MODIF,
            KEY_GUTS_RATING,
            KEY_STUN_RESIST,
            KEY_JUMP_STARTUP,
            KEY_BACKDASH_TIME,
            KEY_BACKDASH_INV,
            KEY_IK_ACTIV};
        return db.query(KEY_CHAR_SELECT, Selection, KEY_WHERE_ID_QUESTION, new String[]{id}, null, null, null);
    }

    public void close(){
        db.close();
    }
}