package course.danon.ggxrdframedata.fragment;


import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import course.danon.ggxrdframedata.helper.DataBaseHelper;
import course.danon.ggxrdframedata.R;

public class FrameDataFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(container == null) return null;
        else {
            View frameDataView = inflater.inflate(R.layout.fragment_frame_data_table_layout, container, false);
            TableLayout frameData = (TableLayout) frameDataView.findViewById(R.id.framedatatable);

//            Debug.startMethodTracing("FDOnActivityCreated");
            final String TABLE_LOG = "Fill_log";
            Log.d(TABLE_LOG, "FDOnCreateView");
            DataBaseHelper Base = new DataBaseHelper(getActivity());
            String tableName = getArguments().getString("TableName");
            Cursor c = Base.getPortraitTable(tableName);
            boolean evenRow = true;
            while (c.moveToNext()) {
                LayoutInflater Inflater = getActivity().getLayoutInflater();
                View inflaterView = Inflater.inflate(R.layout.table_row, frameData, false);
                TextView Input = (TextView) inflaterView.findViewById(R.id.Input);
                TextView Guard = (TextView) inflaterView.findViewById(R.id.Guard);
                TextView Startup = (TextView) inflaterView.findViewById(R.id.Startup);
                TextView Adv = (TextView) inflaterView.findViewById(R.id.Adv);

                if (evenRow) {
                    Input.setBackgroundColor(getResources().getColor(R.color.dark_row_color));
                    Guard.setBackgroundColor(getResources().getColor(R.color.dark_row_color));
                    Startup.setBackgroundColor(getResources().getColor(R.color.dark_row_color));
                    Adv.setBackgroundColor(getResources().getColor(R.color.dark_row_color));
                } else {
                    Input.setBackgroundColor(getResources().getColor(R.color.light_row_color));
                    Guard.setBackgroundColor(getResources().getColor(R.color.light_row_color));
                    Startup.setBackgroundColor(getResources().getColor(R.color.light_row_color));
                    Adv.setBackgroundColor(getResources().getColor(R.color.light_row_color));
                }
                Input.setText(c.getString(c.getColumnIndexOrThrow("Input")));
                Guard.setText(c.getString(c.getColumnIndexOrThrow("Guard")));
                Startup.setText(c.getString(c.getColumnIndexOrThrow("Startup")));
                Adv.setText(c.getString(c.getColumnIndexOrThrow("Adv")));

                frameData.addView(inflaterView);
                evenRow = !evenRow;
            }
            Base.close();

            LayoutInflater Inflater = getActivity().getLayoutInflater();
            View inflaterView = Inflater.inflate(R.layout.table_row, frameData, false);
            TextView Input = (TextView) inflaterView.findViewById(R.id.Input);
            TextView Guard = (TextView) inflaterView.findViewById(R.id.Guard);
            TextView Startup = (TextView) inflaterView.findViewById(R.id.Startup);
            TextView Adv = (TextView) inflaterView.findViewById(R.id.Adv);

            Input.setBackgroundColor(getResources().getColor(R.color.light_row_color));
            Guard.setBackgroundColor(getResources().getColor(R.color.light_row_color));
            Startup.setBackgroundColor(getResources().getColor(R.color.light_row_color));
            Adv.setBackgroundColor(getResources().getColor(R.color.light_row_color));

            frameData.addView(inflaterView, 0);
            Log.d(TABLE_LOG, "FDOnCreateView End");
//        Debug.stopMethodTracing();

            return frameDataView;
        }
    }

    public static FrameDataFragment newInstance(String TableName){
        FrameDataFragment fragment = new FrameDataFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TableName", TableName);
        fragment.setArguments(bundle);
        return fragment;
    }

}
