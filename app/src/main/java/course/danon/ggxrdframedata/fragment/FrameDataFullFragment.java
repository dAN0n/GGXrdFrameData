package course.danon.ggxrdframedata.fragment;

import android.support.v4.app.Fragment;
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
 * Fragment with full frame data table
 * @author Zobkov Dmitry (d@N0n)
 * @version 2.0
 */
public class FrameDataFullFragment extends Fragment implements LoaderManager.LoaderCallbacks<TableSimpleAdapter>{
    private final static String TABLE_NAME = "TableName";
    final String TABLE_LOG = "Fill_log";
    private ListView frameData;
    private ProgressBar pb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(container == null) return null;
        else {
            View frameDataView = inflater.inflate(R.layout.fragment_frame_data_list_view, container, false);

            pb = (ProgressBar) frameDataView.findViewById(R.id.progressBar);
            frameData = (ListView) frameDataView.findViewById(R.id.frameDataList);
            Log.d(TABLE_LOG, "FDOnCreateView");

            getInfoFromDatabase();

            Log.d(TABLE_LOG, "FDOnCreateView End");
            return frameDataView;
        }
    }

    /**
     * This method collect information of character from database
     */
    private void getInfoFromDatabase() {
        String tableName = getArguments().getString(TABLE_NAME);

        DataBaseHelper Base = new DataBaseHelper(getActivity());
        Cursor c = Base.getLandscapeTable(tableName);
        int rowCount = Base.getRowCount(tableName);
        int columnCount = c.getColumnCount()-2;
        String[][] frameTable = new String[columnCount][rowCount];

        int column = 0;
        int row = 0;

        while (c.moveToNext()) {
            frameTable[column][row] = c.getString(c.getColumnIndexOrThrow(KEY_INPUT));
            frameTable[++column][row] = c.getString(c.getColumnIndexOrThrow(KEY_DAMAGE));
            frameTable[++column][row] = c.getString(c.getColumnIndexOrThrow(KEY_TENSION));
            frameTable[++column][row] = c.getString(c.getColumnIndexOrThrow(KEY_RISC));
            frameTable[++column][row] = c.getString(c.getColumnIndexOrThrow(KEY_PRORATE));
            frameTable[++column][row] = c.getString(c.getColumnIndexOrThrow(KEY_ATTACK));
            frameTable[++column][row] = c.getString(c.getColumnIndexOrThrow(KEY_GUARD));
            frameTable[++column][row] = c.getString(c.getColumnIndexOrThrow(KEY_CANCEL));
            frameTable[++column][row] = c.getString(c.getColumnIndexOrThrow(KEY_RC));
            frameTable[++column][row] = c.getString(c.getColumnIndexOrThrow(KEY_STARTUP));
            frameTable[++column][row] = c.getString(c.getColumnIndexOrThrow(KEY_ACTIVE));
            frameTable[++column][row] = c.getString(c.getColumnIndexOrThrow(KEY_RECOVERY));
            frameTable[++column][row] = c.getString(c.getColumnIndexOrThrow(KEY_ADV));
            frameTable[++column][row] = c.getString(c.getColumnIndexOrThrow(KEY_INV));
            column = 0;
            row++;
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_ID, frameTable);

        getLoaderManager().initLoader(0, bundle, this);
        Base.close();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(frameData != null){
            getLoaderManager().destroyLoader(0);
        }
    }

    /**
     * This method puts name of character frame data table in fragment
     * @param TableName Name of character frame data table in database
     * @return fragment Fragment with bundle
     */
    public static FrameDataFullFragment newInstance(String TableName){
        FrameDataFullFragment fragment = new FrameDataFullFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TABLE_NAME, TableName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Loader<TableSimpleAdapter> onCreateLoader(int id, Bundle args) {
        return new FrameDataLoader(getActivity(), args, true);
    }

    @Override
    public void onLoadFinished(Loader<TableSimpleAdapter> loader, TableSimpleAdapter data) {
        frameData.setAdapter(data);
        Log.d(TABLE_LOG, "adapter is set");
        pb.setVisibility(View.GONE);
        Log.d(TABLE_LOG, "pb is gone");
        frameData.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<TableSimpleAdapter> loader) {
        loader.reset();
    }
}