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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;

import java.util.ArrayList;

import course.danon.ggxrdframedata.helper.DataBaseHelper;
import course.danon.ggxrdframedata.R;
import course.danon.ggxrdframedata.loader.FrameDataLoader;
import course.danon.ggxrdframedata.view.NestedListView;

import static course.danon.ggxrdframedata.helper.DataBaseParams.*;

/**
 * Fragment with full frame data table
 * @author Zobkov Dmitry (d@N0n)
 * @version 2.0
 */
public class FrameDataFullFragment extends Fragment implements LoaderManager.LoaderCallbacks<SimpleAdapter>{
    private final static String TABLE_NAME = "TableName";
    final String TABLE_LOG = "Fill_log";
    private ListView frameData;
    private ProgressBar pb;
//    private ArrayList<Integer> idHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(container == null) return null;
        else {
            View frameDataView = inflater.inflate(R.layout.fragment_frame_data_list_view, container, false);
            pb = (ProgressBar) frameDataView.findViewById(R.id.progressBar);
            frameData = (ListView) frameDataView.findViewById(R.id.frameDataList);
            Bundle bundle = new Bundle();
//            idHolder = new ArrayList<>();
//            Debug.startMethodTracing("FDOnActivityCreated");
            Log.d(TABLE_LOG, "FDOnCreateView");
            DataBaseHelper Base = new DataBaseHelper(getActivity());
            String tableName = getArguments().getString(TABLE_NAME);
            Cursor c = Base.getLandscapeTable(tableName);
            int rowCount = Base.getRowCount(tableName);
            int columnCount = c.getColumnCount()-2;
            String[][] frameTable = new String[columnCount][rowCount];
            int column = 0;
            int row = 0;
//            int id = 0;

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
//                bundle.putStringArray(Integer.toString(++id), frameDataRow);
//                getLoaderManager().initLoader(id, bundle, this);
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
    public void onDestroyView() {
        super.onDestroyView();
        if(frameData != null){
            getLoaderManager().destroyLoader(0);
        }
    }

    /**
     * This method puts name of character frame data table in fragment
     * @param TableName Name of character frame data table in database
     * @return fragment with bundle
     */
    public static FrameDataFullFragment newInstance(String TableName){
        FrameDataFullFragment fragment = new FrameDataFullFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TABLE_NAME, TableName);
//        bundle.putStringArray("CharInfo", charInfo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Loader<SimpleAdapter> onCreateLoader(int id, Bundle args) {
        return new FrameDataLoader(getActivity(), args, true, id);
    }

    @Override
    public void onLoadFinished(Loader<SimpleAdapter> loader, SimpleAdapter data) {
        pb.setVisibility(View.GONE);
        Log.d(TABLE_LOG, "pb is gone");
        frameData.setAdapter(data);
        Log.d(TABLE_LOG, "adapter is set");
        frameData.setVisibility(View.VISIBLE);
//        getLoaderManager().destroyLoader(0);
/*        int loaderId = loader.getId();
        int childCount = frameData.getChildCount();
        boolean isAlreadyAdded = false;
        if(childCount == 0){
            frameData.addView(data);
            idHolder.add(loaderId);
        }
        else for(int i=0; i < childCount; i++){
            if(loaderId < idHolder.get(i) && !isAlreadyAdded){
                frameData.addView(data, i);
                idHolder.add(i, loaderId);
                isAlreadyAdded = true;
            }
            else if(loaderId == idHolder.get(i) && !isAlreadyAdded){
                isAlreadyAdded = true;
            }
            else if(i == childCount-1 && !isAlreadyAdded){
                frameData.addView(data);
                idHolder.add(i, loaderId);
            }
        }
        getLoaderManager().destroyLoader(loaderId);*/
    }

    @Override
    public void onLoaderReset(Loader<SimpleAdapter> loader) {
        loader.reset();
    }
}