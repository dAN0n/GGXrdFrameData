package course.danon.ggxrdframedata.fragment;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import course.danon.ggxrdframedata.helper.DataBaseHelper;
import course.danon.ggxrdframedata.R;
import course.danon.ggxrdframedata.loader.FrameDataLoader;

import static course.danon.ggxrdframedata.helper.DataBaseParams.*;

/**
 * Fragment with lite frame data table
 * @author Zobkov Dmitry (d@N0n)
 * @version 2.0
 */
public class FrameDataFragment extends Fragment implements LoaderManager.LoaderCallbacks<View>{
    private final static String TABLE_NAME = "TableName";
    private TableLayout frameData;

/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(container == null) return null;
        else {
            View frameDataView = inflater.inflate(R.layout.fragment_frame_data_table_layout, container, false);
            TableLayout frameData = (TableLayout) frameDataView.findViewById(R.id.framedatatable);

//            Debug.startMethodTracing("FDOnActivityCreated");
            Log.d(TABLE_LOG, "FDOnCreateView");
            DataBaseHelper Base = new DataBaseHelper(getActivity());
            String tableName = getArguments().getString(TABLE_NAME);
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
                Input.setText(c.getString(c.getColumnIndexOrThrow(KEY_INPUT)));
                Guard.setText(c.getString(c.getColumnIndexOrThrow(KEY_GUARD)));
                Startup.setText(c.getString(c.getColumnIndexOrThrow(KEY_STARTUP)));
                Adv.setText(c.getString(c.getColumnIndexOrThrow(KEY_ADV)));

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
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(container == null) return null;
        else {
            View frameDataView = inflater.inflate(R.layout.fragment_frame_data_table_layout, container, false);
            frameData = (TableLayout) frameDataView.findViewById(R.id.framedatatable);

            return frameDataView;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = new Bundle();
//            Debug.startMethodTracing("FDOnActivityCreated");
        final String TABLE_LOG = "Fill_log";
        Log.d(TABLE_LOG, "FDOnCreateView");
        DataBaseHelper Base = new DataBaseHelper(getActivity());
        String tableName = getArguments().getString("TableName");
        int MaxRow = Base.getRowCount(tableName);
        Cursor c = Base.getPortraitTable(tableName);
        int columncount = c.getColumnCount()-2;
        int res = (columncount*MaxRow)+columncount;
        String[] Fill = new String[columncount];
        int i = 0;
        Fill[i] = getResources().getString(R.string.input);
        Fill[++i] = getResources().getString(R.string.guard);
        Fill[++i] = getResources().getString(R.string.startup);
        Fill[++i] = getResources().getString(R.string.advantage);
        i=0;
        int id = 0;
        bundle.putStringArray(Integer.toString(id), Fill);
        getLoaderManager().initLoader(0, bundle, this);

        while (c.moveToNext()) {
            String[] FillNew = new String[columncount];
            FillNew[i] = c.getString(c.getColumnIndexOrThrow("Input"));
            FillNew[++i] = c.getString(c.getColumnIndexOrThrow("Guard"));
            FillNew[++i] = c.getString(c.getColumnIndexOrThrow("Startup"));
            FillNew[++i] = c.getString(c.getColumnIndexOrThrow("Adv"));
            i=0;
//            bundle.clear();
            bundle.putStringArray(Integer.toString(++id), FillNew);
            getLoaderManager().initLoader(id, bundle, this);
        }
        Base.close();

        Log.d(TABLE_LOG, "FDOnCreateView End");
//        Debug.stopMethodTracing();
    }

    /**
     * This method puts name of character frame data table in fragment
     * @param TableName Name of character frame data table in database
     * @return fragment with bundle
     */
    public static FrameDataFragment newInstance(String TableName){
        FrameDataFragment fragment = new FrameDataFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TABLE_NAME, TableName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Loader<View> onCreateLoader(int id, Bundle args) {
        LayoutInflater Inflater = getActivity().getLayoutInflater();
        View inflaterView = Inflater.inflate(R.layout.table_row, frameData, false);
        return new FrameDataLoader(getActivity(), args, false, inflaterView, id);
    }

    @Override
    public void onLoadFinished(Loader<View> loader, View data) {
        frameData.addView(data);
    }

    @Override
    public void onLoaderReset(Loader<View> loader) {

    }
}
