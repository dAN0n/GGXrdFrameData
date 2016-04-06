package course.danon.ggxrdframedata.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
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
 * @version 3.0
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

            frameData = (ListView) frameDataView.findViewById(R.id.frameDataList);
            pb = (ProgressBar) frameDataView.findViewById(R.id.progressBar);
            Log.d(TABLE_LOG, "FDOnCreateView");

            initCharPicInfoFragment(inflater);
            getInfoFromDatabase();

            Log.d(TABLE_LOG, "FDOnCreateView End");
            return frameDataView;
        }
    }

    /**
     * Create CharPicInfoFragment and put it to FrameLayout container
     * @param inflater Inflater from parent fragment
     */
    private void initCharPicInfoFragment(LayoutInflater inflater) {
        String charId = getArguments().getString(CHARID);

        View test = inflater.inflate(R.layout.char_pic_container, null, false);
        charPic = test.findViewById(R.id.CharPicParentContainer);

        CharPicInfoFragment fragCharPic = CharPicInfoFragment.newInstance(charId);
        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction().replace(R.id.CharPicContainer, fragCharPic, "charPic").commit();
    }

    /**
     * This method collect information of character from database
     */
    private void getInfoFromDatabase() {
        String tableName = getArguments().getString(TABLE_NAME);

        DataBaseHelper Base = new DataBaseHelper(getActivity());
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

        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_ID, frameTable);

        getLoaderManager().initLoader(0, bundle, this);
        Base.close();
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
     * @return fragment Fragment with bundle
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