package course.danon.ggxrdframedata.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import course.danon.ggxrdframedata.helper.DataBaseHelper;
import course.danon.ggxrdframedata.R;


public class CharPicInfoFragment extends Fragment {

    private String CharPic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(container == null) return null;
        else {
            View CharPicInfoView = inflater.inflate(R.layout.fragment_char_pic_info, container, false);

            ListView infoList = (ListView) CharPicInfoView.findViewById(R.id.CharInfo);
            ImageView charIcon = (ImageView) CharPicInfoView.findViewById(R.id.CharPic);

            final String TABLE_LOG = "Fill_log";
            Log.d(TABLE_LOG, "CharPicOnCreateView");
//        Debug.startMethodTracing("CharPicOnActivityCreated");
            DataBaseHelper Base = new DataBaseHelper(getActivity());
            String[] charInfo = new String[7];
            String CharId = this.getArguments().getString("ID");
            Cursor c = Base.getCharInfo(CharId);
            while (c.moveToNext()) {
                charInfo[0] = getString(R.string.defense_mod) + " " + c.getString(c.getColumnIndexOrThrow("Defense Modifier"));
                charInfo[1] = getString(R.string.guts) + " " + c.getString(c.getColumnIndexOrThrow("Guts Rating"));
                charInfo[2] = getString(R.string.stun) + " " + c.getString(c.getColumnIndexOrThrow("Stun Resistance"));
                charInfo[3] = getString(R.string.jump) + " " + c.getString(c.getColumnIndexOrThrow("Jump Startup"));
                charInfo[4] = getString(R.string.backdash_time) + " " + c.getString(c.getColumnIndexOrThrow("Backdash Time"));
                charInfo[5] = getString(R.string.backdash_inv) + " " + c.getString(c.getColumnIndexOrThrow("Backdash Invincibility"));
                charInfo[6] = getString(R.string.ik_activation) + " " + c.getString(c.getColumnIndexOrThrow("IK Activation"));
                CharPic = c.getString(c.getColumnIndexOrThrow("Icon"));
            }
            Base.close();

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.char_info_list_view, charInfo);
            infoList.setAdapter(adapter);

            charIcon.setImageResource(getResources().getIdentifier(CharPic, "drawable", getActivity().getPackageName()));
            Log.d(TABLE_LOG, "CharPicOnCreateView End");
//        Debug.stopMethodTracing();

            return CharPicInfoView;
        }
    }

    public static CharPicInfoFragment newInstance(String CharId){
        CharPicInfoFragment fragment = new CharPicInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ID", CharId);
        fragment.setArguments(bundle);
        return fragment;
    }
}
