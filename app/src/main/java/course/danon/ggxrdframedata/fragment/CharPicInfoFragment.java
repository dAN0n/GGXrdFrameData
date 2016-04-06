package course.danon.ggxrdframedata.fragment;

import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import course.danon.ggxrdframedata.helper.DataBaseHelper;
import course.danon.ggxrdframedata.R;

import static course.danon.ggxrdframedata.helper.DataBaseParams.*;

/**
 * Fragment with picture of character and additional information
 * @author Zobkov Dmitry (d@N0n)
 * @version 2.0
 */
public class CharPicInfoFragment extends Fragment {

    private final static String ID = "ID";
    private String CharPic;
    private String[] charInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(container == null) return null;
        else {
            View CharPicInfoView = inflater.inflate(R.layout.fragment_char_pic_info, container, false);

            final String TABLE_LOG = "Fill_log";
            Log.d(TABLE_LOG, "CharPicOnCreateView");

            String CharId = this.getArguments().getString(ID);
            getInfoFromDatabase(CharId);
            setInfoToView(CharPicInfoView);

            Log.d(TABLE_LOG, "CharPicOnCreateView End");

            return CharPicInfoView;
        }
    }

    /**
     * This method collect information of character from database
     * @param charId Id of character in database
     */
    private void getInfoFromDatabase(String charId) {
        DataBaseHelper Base = new DataBaseHelper(getActivity());
        charInfo = new String[10];
        Cursor c = Base.getCharInfo(charId);
        while (c.moveToNext()) {
            String weight = c.getString(c.getColumnIndexOrThrow(KEY_WEIGHT));
            switch (weight) {
                case "L":
                    weight = getString(R.string.light);
                    break;
                case "M":
                    weight = getString(R.string.medium);
                    break;
                case "H":
                    weight = getString(R.string.heavy);
                    break;
                default:
                    weight = getString(R.string.super_heavy);
                    break;
            }
            charInfo[0] = getString(R.string.weight) + " " + weight;
            charInfo[1] = getString(R.string.defense_mod) + " " + c.getString(c.getColumnIndexOrThrow(KEY_DEFENSE));
            charInfo[2] = getString(R.string.guts) + " " + c.getString(c.getColumnIndexOrThrow(KEY_GUTS));
            charInfo[3] = getString(R.string.stun) + " " + c.getString(c.getColumnIndexOrThrow(KEY_STUN));
            charInfo[4] = getString(R.string.jump) + " " + c.getString(c.getColumnIndexOrThrow(KEY_JUMP));
            charInfo[5] = getString(R.string.backdash_time) + " " + c.getString(c.getColumnIndexOrThrow(KEY_BD_TIME));
            charInfo[6] = getString(R.string.backdash_inv) + " " + c.getString(c.getColumnIndexOrThrow(KEY_BD_INV));
            charInfo[7] = getString(R.string.ik_activation) + " " + c.getString(c.getColumnIndexOrThrow(KEY_IK));
            charInfo[8] = getString(R.string.faceup) + " " + c.getString(c.getColumnIndexOrThrow(KEY_FACEUP)) + "F";
            charInfo[9] = getString(R.string.facedown) + " " + c.getString(c.getColumnIndexOrThrow(KEY_FACEDOWN)) + "F";
            CharPic = c.getString(c.getColumnIndexOrThrow("Icon"));
        }
        Base.close();
    }

    /**
     * Set information about character to views
     * @param charPicInfoView Base layout with LinearLayout and ImageView
     */
    private void setInfoToView(View charPicInfoView) {
        LinearLayout infoList = (LinearLayout) charPicInfoView.findViewById(R.id.CharInfoLinear);
        ImageView charIcon = (ImageView) charPicInfoView.findViewById(R.id.CharPic);

        for (String aCharInfo : charInfo) {
            TextView text = new TextView(getActivity().getBaseContext());
            text.setText(aCharInfo);
            text.setTextColor(getResources().getColor(R.color.black));
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            text.setGravity(Gravity.CENTER);
            infoList.addView(text);
        }

        charIcon.setImageResource(getResources().getIdentifier(CharPic, "drawable",
                getActivity().getPackageName()));
    }

    /**
     * This method puts id of character in fragment
     * @param CharId Id of character in database
     * @return fragment Fragment with bundle
     */
    public static CharPicInfoFragment newInstance(String CharId){
        CharPicInfoFragment fragment = new CharPicInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ID, CharId);
        fragment.setArguments(bundle);
        return fragment;
    }
}
