package course.danon.ggxrdframedata.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import course.danon.ggxrdframedata.adapter.TableSimpleAdapter;
import course.danon.ggxrdframedata.helper.DataBaseHelper;
import course.danon.ggxrdframedata.R;
import course.danon.ggxrdframedata.loader.FrameDataLoader;

import static course.danon.ggxrdframedata.helper.DataBaseParams.*;

/**
 * Fragment with lite frame data table
 * @author Zobkov Dmitry (d@N0n)
 * @version 2.0
 */
public class FrameDataFragment extends Fragment implements LoaderManager.LoaderCallbacks<TableSimpleAdapter>{
    private final static String TABLE_NAME = "TableName";
    private final static String CHARID = "CharId";
    final String TABLE_LOG = "Fill_log";
    private ListView frameData;
    private ProgressBar pb;
    private View charPic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(container == null) return null;
        else {
            View frameDataView = inflater.inflate(R.layout.fragment_frame_data_list_view, container, false);
            View test = inflater.inflate(R.layout.char_pic_container, null, false);
            frameData = (ListView) frameDataView.findViewById(R.id.frameDataList);
            pb = (ProgressBar) frameDataView.findViewById(R.id.progressBar);
            String charId = getArguments().getString(CHARID);
            charPic = test.findViewById(R.id.CharPicParentContainer);

            CharPicInfoFragment fragCharPic = CharPicInfoFragment.newInstance(charId);
            FragmentManager fm = getChildFragmentManager();
            if (savedInstanceState == null) fm.beginTransaction()
                    .replace(R.id.CharPicContainer, fragCharPic, "charPic").commit();
//            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT &
//                    fragCharPic.isDetached()) fm.beginTransaction().attach(fragCharPic).commit();

            Bundle bundle = new Bundle();
//            Debug.startMethodTracing("FDOnActivityCreated");
            Log.d(TABLE_LOG, "FDOnCreateView");
            DataBaseHelper Base = new DataBaseHelper(getActivity());
            String tableName = getArguments().getString(TABLE_NAME);
            Cursor c = Base.getPortraitTable(tableName);
            int rowCount = Base.getRowCount(tableName)+1;
            int columnCount = c.getColumnCount()-2;
            String[][] frameTable = new String[columnCount][rowCount];
            int column = 0;
            int row = 0;
            frameTable[column][row] = getResources().getString(R.string.input);
            frameTable[++column][row] = getResources().getString(R.string.guard);
            frameTable[++column][row] = getResources().getString(R.string.startup);
            frameTable[++column][row] = getResources().getString(R.string.advantage);
            column = 0;

            while (c.moveToNext()) {
                row++;
                frameTable[column][row] = c.getString(c.getColumnIndexOrThrow(KEY_INPUT));
                frameTable[++column][row] = c.getString(c.getColumnIndexOrThrow(KEY_GUARD));
                frameTable[++column][row] = c.getString(c.getColumnIndexOrThrow(KEY_STARTUP));
                frameTable[++column][row] = c.getString(c.getColumnIndexOrThrow(KEY_ADV));
                column = 0;
            }
            bundle.putSerializable(KEY_ID, frameTable);
            getLoaderManager().initLoader(0, bundle, this);
            Base.close();

            Log.d(TABLE_LOG, "FDOnCreateView End");
//        Debug.stopMethodTracing();
            return frameDataView;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(frameData != null) {
            getLoaderManager().destroyLoader(0);
        }
    }

    /**
     * This method puts name of character frame data table in fragment
     * @param TableName Name of character frame data table in database
     * @return fragment with bundle
     */
    public static FrameDataFragment newInstance(String TableName, String CharId){
        FrameDataFragment fragment = new FrameDataFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TABLE_NAME, TableName);
        bundle.putString(CHARID, CharId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Loader<TableSimpleAdapter> onCreateLoader(int id, Bundle args) {
        Log.d(TABLE_LOG, "load start");
        frameData.addHeaderView(charPic);
        return new FrameDataLoader(getActivity(), args, false);
    }

    @Override
    public void onLoadFinished(Loader<TableSimpleAdapter> loader, TableSimpleAdapter data) {
        Log.d(TABLE_LOG, "load finish");
        frameData.setAdapter(data);
        pb.setVisibility(View.GONE);
        Log.d(TABLE_LOG, "pb is gone");
        frameData.setFocusable(false);
        frameData.setVisibility(View.VISIBLE);
        Log.d(TABLE_LOG, "height set");
    }

    @Override
    public void onLoaderReset(Loader<TableSimpleAdapter> loader) {
        loader.reset();
    }
}