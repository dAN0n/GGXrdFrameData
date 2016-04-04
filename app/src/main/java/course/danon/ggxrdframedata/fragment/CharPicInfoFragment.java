package course.danon.ggxrdframedata.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(container == null) return null;
        else {
            View CharPicInfoView = inflater.inflate(R.layout.fragment_char_pic_info, container, false);

            TextView infoList = (TextView) CharPicInfoView.findViewById(R.id.CharInfo);
            ImageView charIcon = (ImageView) CharPicInfoView.findViewById(R.id.CharPic);

            final String TABLE_LOG = "Fill_log";
            Log.d(TABLE_LOG, "CharPicOnCreateView");
//        Debug.startMethodTracing("CharPicOnActivityCreated");
            DataBaseHelper Base = new DataBaseHelper(getActivity());
            String[] charInfo = new String[7];
            String CharId = this.getArguments().getString(ID);
            Cursor c = Base.getCharInfo(CharId);
            while (c.moveToNext()) {
                charInfo[0] = getString(R.string.defense_mod) + " " + c.getString(c.getColumnIndexOrThrow(KEY_DEFENSE));
                charInfo[1] = getString(R.string.guts) + " " + c.getString(c.getColumnIndexOrThrow(KEY_GUTS));
                charInfo[2] = getString(R.string.stun) + " " + c.getString(c.getColumnIndexOrThrow(KEY_STUN));
                charInfo[3] = getString(R.string.jump) + " " + c.getString(c.getColumnIndexOrThrow(KEY_JUMP));
                charInfo[4] = getString(R.string.backdash_time) + " " + c.getString(c.getColumnIndexOrThrow(KEY_BD_TIME));
                charInfo[5] = getString(R.string.backdash_inv) + " " + c.getString(c.getColumnIndexOrThrow(KEY_BD_INV));
                charInfo[6] = getString(R.string.ik_activation) + " " + c.getString(c.getColumnIndexOrThrow(KEY_IK));
                CharPic = c.getString(c.getColumnIndexOrThrow("Icon"));
            }
            Base.close();

            String charInfoSet = "";
            for(int i = 0; i < 7; i++){
                charInfoSet += charInfo[i] + "\n";
            }
            infoList.setText(charInfoSet);

            charIcon.setImageResource(getResources().getIdentifier(CharPic, "drawable",
                getActivity().getPackageName()));
            Log.d(TABLE_LOG, "CharPicOnCreateView End");
//        Debug.stopMethodTracing();

            return CharPicInfoView;
        }
    }

    /**
     * This method puts id of character in fragment
     * @param CharId Id of character in database
     * @return fragment with bundle
     */
    public static CharPicInfoFragment newInstance(String CharId){
        CharPicInfoFragment fragment = new CharPicInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ID, CharId);
        fragment.setArguments(bundle);
        return fragment;
    }
}
