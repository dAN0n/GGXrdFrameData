package course.danon.ggxrdframedata.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import static course.danon.ggxrdframedata.helper.DataBaseParams.*;

/**
 * Class for working with database
 * @author Zobkov Dmitry (d@N0n)
 * @version 1.0
 */
public class DataBaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "ggxrdfd.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    /**
     * Constructor of class
     * @param context Context from activity
     */
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Open the database to read
     */
    private void open(){
        db = getReadableDatabase();
    }

    /**
     * This method returns number of rows in target table from database
     * @param TableName Name of target table in database
     * @return Number of rows in target table
     */
    public int getRowCount(String TableName){
        open();
        Cursor c = db.query(TableName, new String[] {KEY_MAX_ID}, null, null, null, null, null);
        c.moveToFirst();
        return c.getInt(0);
    }

    /**
     * This method returns target column from table in database
     * @param TableName Name of target table in database
     * @param Selection Name of target column to getting
     * @return Cursor with target column
     */
    public Cursor getColumn(String TableName, String Selection) {
        open();
        return db.query(TableName, new String[]{Selection}, null, null, null, null, null);
    }

    /**
     * This method returns cursor with 6 columns for showing lite frame data
     * @param TableName Name of character frame data table in database
     * @return Cursor with 6 columns
     */
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

    /**
     * This method returns cursor with full table for showing full frame data
     * @param TableName Name of character frame data table in database
     * @return Cursor with full table
     */
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

    /**
     * This method returns target row from table in database
     * @param TableName Name of character frame data table in database
     * @param id Number of target row
     * @return Cursor with target row
     */
    public Cursor getRow(String TableName, String id){
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
    }

    /**
     * This method returns row with additional info about character from CharSelectIcons table
     * @param id Number of row from CharSelectIcons table
     * @return Cursor with target row from CharSelectIcons table
     */
    public Cursor getCharInfo(String id){
        open();
        String[] Selection = new String[]{
            KEY_CHAR,
            KEY_TABLENAME,
            KEY_ICON,
            KEY_WEIGHT,
            KEY_DEFENSE_MODIF,
            KEY_GUTS_RATING,
            KEY_STUN_RESIST,
            KEY_JUMP_STARTUP,
            KEY_BACKDASH_TIME,
            KEY_BACKDASH_INV,
            KEY_IK_ACTIVATION,
            KEY_FACEUP,
            KEY_FACEDOWN};
        return db.query(KEY_CHAR_SELECT, Selection, KEY_WHERE_ID_QUESTION, new String[]{id},
            null, null, null);
    }

    /**
     * Close the database
     */
    public void close(){
        db.close();
    }
}