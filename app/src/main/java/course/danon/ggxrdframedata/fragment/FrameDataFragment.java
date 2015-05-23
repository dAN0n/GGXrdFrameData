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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;

import java.util.ArrayList;

import course.danon.ggxrdframedata.helper.DataBaseHelper;
import course.danon.ggxrdframedata.R;
import course.danon.ggxrdframedata.loader.FrameDataLoader;
import course.danon.ggxrdframedata.view.NestedListView;

import static course.danon.ggxrdframedata.helper.DataBaseParams.*;

/**
 * Fragment with lite frame data table
 * @author Zobkov Dmitry (d@N0n)
 * @version 2.0
 */
public class FrameDataFragment extends Fragment implements LoaderManager.LoaderCallbacks<SimpleAdapter>{
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
            frameData = (ListView) frameDataView.findViewById(R.id.frameDataList);
            pb = (ProgressBar) frameDataView.findViewById(R.id.progressBar);
            Bundle bundle = new Bundle();
//            idHolder = new ArrayList<>();
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
//            int id = 0;
//            bundle.putStringArray(Integer.toString(id), headerRow);
//            getLoaderManager().initLoader(id, bundle, this);

            while (c.moveToNext()) {
                row++;
//                String[] frameDataRow = new String[columnCount];
                frameTable[column][row] = c.getString(c.getColumnIndexOrThrow(KEY_INPUT));
                frameTable[++column][row] = c.getString(c.getColumnIndexOrThrow(KEY_GUARD));
                frameTable[++column][row] = c.getString(c.getColumnIndexOrThrow(KEY_STARTUP));
                frameTable[++column][row] = c.getString(c.getColumnIndexOrThrow(KEY_ADV));
                column = 0;
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
    public static FrameDataFragment newInstance(String TableName){
        FrameDataFragment fragment = new FrameDataFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TABLE_NAME, TableName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Loader<SimpleAdapter> onCreateLoader(int id, Bundle args) {
        Log.d(TABLE_LOG, "load start");
        return new FrameDataLoader(getActivity(), args, false, id);
    }

    @Override
    public void onLoadFinished(Loader<SimpleAdapter> loader, SimpleAdapter data) {
        Log.d(TABLE_LOG, "load finish");
        frameData.setAdapter(data);
        Log.d(TABLE_LOG, "adapter is set");
        frameData.postDelayed(new Runnable() {
            public void run() {
                setListViewHeightBasedOnChildren(frameData);
                pb.setVisibility(View.GONE);
                frameData.setVisibility(View.VISIBLE);
            }
        }, 1);
//        pb.setVisibility(View.GONE);
//        frameData.setVisibility(View.VISIBLE);
        Log.d(TABLE_LOG, "height set");
        Log.d(TABLE_LOG, "pb is gone");
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

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if(listItem != null){
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}