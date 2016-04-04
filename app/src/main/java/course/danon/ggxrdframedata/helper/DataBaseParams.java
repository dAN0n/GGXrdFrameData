package course.danon.ggxrdframedata.helper;

/**
 * Class with constant parameters for working with DataBaseHelper class
 * @author Zobkov Dmitry (d@N0n)
 * @version 1.0
 */
public final class DataBaseParams {
    public static final String KEY_MAX_ID = "Max(_id)",
        KEY_ID = "_id",
        KEY_INPUT = "Input",
        KEY_DAMAGE = "Damage",
        KEY_TENSION = "Tension",
        KEY_RISC = "RISC",
        KEY_PRORATE = "Prorate",
        KEY_ATTACK_LV = "`Attack Lv.` as Attack",
        KEY_GUARD = "Guard",
        KEY_CANCEL = "Cancel",
        KEY_RC = "RC",
        KEY_STARTUP = "Startup",
        KEY_ACTIVE = "Active",
        KEY_RECOVERY = "Recovery",
        KEY_FRAME_ADV = "`Frame Adv.` as Adv",
        KEY_INVUL = "`Inv.` as Inv",
        KEY_TYPE = "Type",
        KEY_WHERE_ID_QUESTION = "_id = ?",
        KEY_CHAR_SELECT = "CharSelectIcons",
        KEY_CHAR = "Char",
        KEY_TABLENAME = "FDTableName",
        KEY_ICON = "Icon",
        KEY_DEFENSE_MODIF = "`Defense Modifier`",
        KEY_GUTS_RATING = "`Guts Rating`",
        KEY_STUN_RESIST = "`Stun Resistance`",
        KEY_JUMP_STARTUP = "`Jump Startup`",
        KEY_BACKDASH_TIME = "`Backdash Time`",
        KEY_BACKDASH_INV = "`Backdash Invincibility`",
        KEY_IK_ACTIVATION = "`IK Activation`";

    public static final String KEY_ATTACK = "Attack",
        KEY_ADV = "Adv",
        KEY_INV = "Inv",
        KEY_DEFENSE = "Defense Modifier",
        KEY_GUTS = "Guts Rating",
        KEY_STUN = "Stun Resistance",
        KEY_JUMP = "Jump Startup",
        KEY_BD_TIME = "Backdash Time",
        KEY_BD_INV = "Backdash Invincibility",
        KEY_IK = "IK Activation",
        TABLE_LOG = "Fill_log",
        CHAR_LIST = "CharList",
        CHAR_ID = "CharId";

    /**
     * Preventing from creating objects of class
     */
    private DataBaseParams(){
        throw new AssertionError();
    }
}
