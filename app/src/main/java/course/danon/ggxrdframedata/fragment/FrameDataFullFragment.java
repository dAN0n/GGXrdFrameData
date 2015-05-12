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

import java.util.ArrayList;

import course.danon.ggxrdframedata.helper.DataBaseHelper;
import course.danon.ggxrdframedata.R;
import course.danon.ggxrdframedata.loader.FrameDataLoader;

import static course.danon.ggxrdframedata.helper.DataBaseParams.*;

/**
 * Fragment with full frame data table
 * @author Zobkov Dmitry (d@N0n)
 * @version 2.0
 */
public class FrameDataFullFragment extends Fragment implements LoaderManager.LoaderCallbacks<View>{
    private final static String TABLE_NAME = "TableName";
    final String TABLE_LOG = "Fill_log";
    private TableLayout frameData;
    private ArrayList<Integer> idHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(container == null) return null;
        else {
            View frameDataView = inflater.inflate(R.layout.fragment_frame_data_table_layout, container, false);
            frameData = (TableLayout) frameDataView.findViewById(R.id.framedatatable);
            Bundle bundle = new Bundle();
            idHolder = new ArrayList<>();
//            Debug.startMethodTracing("FDOnActivityCreated");
            Log.d(TABLE_LOG, "FDOnCreateView");
            DataBaseHelper Base = new DataBaseHelper(getActivity());
            String tableName = getArguments().getString(TABLE_NAME);
            Cursor c = Base.getLandscapeTable(tableName);
            int columnCount = c.getColumnCount()-2;
            int i = 0;
            int id = 0;

            while (c.moveToNext()) {
                String[] frameDataRow = new String[columnCount];
                frameDataRow[i] = c.getString(c.getColumnIndexOrThrow(KEY_INPUT));
                frameDataRow[++i] = c.getString(c.getColumnIndexOrThrow(KEY_DAMAGE));
                frameDataRow[++i] = c.getString(c.getColumnIndexOrThrow(KEY_TENSION));
                frameDataRow[++i] = c.getString(c.getColumnIndexOrThrow(KEY_RISC));
                frameDataRow[++i] = c.getString(c.getColumnIndexOrThrow(KEY_PRORATE));
                frameDataRow[++i] = c.getString(c.getColumnIndexOrThrow(KEY_ATTACK));
                frameDataRow[++i] = c.getString(c.getColumnIndexOrThrow(KEY_GUARD));
                frameDataRow[++i] = c.getString(c.getColumnIndexOrThrow(KEY_CANCEL));
                frameDataRow[++i] = c.getString(c.getColumnIndexOrThrow(KEY_RC));
                frameDataRow[++i] = c.getString(c.getColumnIndexOrThrow(KEY_STARTUP));
                frameDataRow[++i] = c.getString(c.getColumnIndexOrThrow(KEY_ACTIVE));
                frameDataRow[++i] = c.getString(c.getColumnIndexOrThrow(KEY_RECOVERY));
                frameDataRow[++i] = c.getString(c.getColumnIndexOrThrow(KEY_ADV));
                frameDataRow[++i] = c.getString(c.getColumnIndexOrThrow(KEY_INV));
                i=0;
                bundle.putStringArray(Integer.toString(++id), frameDataRow);
                getLoaderManager().initLoader(id, bundle, this);
            }
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
            frameData.removeAllViews();
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
    public Loader<View> onCreateLoader(int id, Bundle args) {
        LayoutInflater Inflater = getActivity().getLayoutInflater();
        View inflaterView = Inflater.inflate(R.layout.table_row, frameData, false);
        return new FrameDataLoader(getActivity(), args, true, inflaterView, id);
    }

    @Override
    public void onLoadFinished(Loader<View> loader, View data) {
        int loaderId = loader.getId();
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
    }

    @Override
    public void onLoaderReset(Loader<View> loader) {

    }
}