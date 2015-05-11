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
import android.widget.TextView;

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
    private TableLayout frameData;

/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(container == null) return null;
        else {
            View frameDataView = inflater.inflate(R.layout.fragment_frame_data_table_layout, container, false);
            TableLayout frameData = (TableLayout) frameDataView.findViewById(R.id.framedatatable);

//            Debug.startMethodTracing("FDFullOnActivityCreated");
            Log.d(TABLE_LOG, "FDFullOnCreateView");
            DataBaseHelper Base = new DataBaseHelper(getActivity());
            String tableName = getArguments().getString(TABLE_NAME);
            Cursor c = Base.getLandscapeTable(tableName);
            boolean evenRow = true;
            while (c.moveToNext()) {
                LayoutInflater Inflater = getActivity().getLayoutInflater();
                View inflaterView = Inflater.inflate(R.layout.table_row, frameData, false);
                TextView Input = (TextView) inflaterView.findViewById(R.id.Input);
                TextView Damage = (TextView) inflaterView.findViewById(R.id.Damage);
                TextView Tension = (TextView) inflaterView.findViewById(R.id.Tension);
                TextView Risc = (TextView) inflaterView.findViewById(R.id.RISC);
                TextView Prorate = (TextView) inflaterView.findViewById(R.id.Prorate);
                TextView Attack = (TextView) inflaterView.findViewById(R.id.Attack);
                TextView Guard = (TextView) inflaterView.findViewById(R.id.Guard);
                TextView Cancel = (TextView) inflaterView.findViewById(R.id.Cancel);
                TextView Rc = (TextView) inflaterView.findViewById(R.id.RC);
                TextView Startup = (TextView) inflaterView.findViewById(R.id.Startup);
                TextView Active = (TextView) inflaterView.findViewById(R.id.Active);
                TextView Recovery = (TextView) inflaterView.findViewById(R.id.Recovery);
                TextView Adv = (TextView) inflaterView.findViewById(R.id.Adv);
                TextView Inv = (TextView) inflaterView.findViewById(R.id.Inv);

                if (evenRow) {
                    Input.setBackgroundColor(getResources().getColor(R.color.dark_row_color));
                    Damage.setBackgroundColor(getResources().getColor(R.color.dark_row_color));
                    Tension.setBackgroundColor(getResources().getColor(R.color.dark_row_color));
                    Risc.setBackgroundColor(getResources().getColor(R.color.dark_row_color));
                    Prorate.setBackgroundColor(getResources().getColor(R.color.dark_row_color));
                    Attack.setBackgroundColor(getResources().getColor(R.color.dark_row_color));
                    Guard.setBackgroundColor(getResources().getColor(R.color.dark_row_color));
                    Cancel.setBackgroundColor(getResources().getColor(R.color.dark_row_color));
                    Rc.setBackgroundColor(getResources().getColor(R.color.dark_row_color));
                    Startup.setBackgroundColor(getResources().getColor(R.color.dark_row_color));
                    Active.setBackgroundColor(getResources().getColor(R.color.dark_row_color));
                    Recovery.setBackgroundColor(getResources().getColor(R.color.dark_row_color));
                    Adv.setBackgroundColor(getResources().getColor(R.color.dark_row_color));
                    Inv.setBackgroundColor(getResources().getColor(R.color.dark_row_color));
                } else {
                    Input.setBackgroundColor(getResources().getColor(R.color.light_row_color));
                    Damage.setBackgroundColor(getResources().getColor(R.color.light_row_color));
                    Tension.setBackgroundColor(getResources().getColor(R.color.light_row_color));
                    Risc.setBackgroundColor(getResources().getColor(R.color.light_row_color));
                    Prorate.setBackgroundColor(getResources().getColor(R.color.light_row_color));
                    Attack.setBackgroundColor(getResources().getColor(R.color.light_row_color));
                    Guard.setBackgroundColor(getResources().getColor(R.color.light_row_color));
                    Cancel.setBackgroundColor(getResources().getColor(R.color.light_row_color));
                    Rc.setBackgroundColor(getResources().getColor(R.color.light_row_color));
                    Startup.setBackgroundColor(getResources().getColor(R.color.light_row_color));
                    Active.setBackgroundColor(getResources().getColor(R.color.light_row_color));
                    Recovery.setBackgroundColor(getResources().getColor(R.color.light_row_color));
                    Adv.setBackgroundColor(getResources().getColor(R.color.light_row_color));
                    Inv.setBackgroundColor(getResources().getColor(R.color.light_row_color));
                }
                Input.setText(c.getString(c.getColumnIndexOrThrow(KEY_INPUT)));
                Damage.setText(c.getString(c.getColumnIndexOrThrow(KEY_DAMAGE)));
                Tension.setText(c.getString(c.getColumnIndexOrThrow(KEY_TENSION)));
                Risc.setText(c.getString(c.getColumnIndexOrThrow(KEY_RISC)));
                Prorate.setText(c.getString(c.getColumnIndexOrThrow(KEY_PRORATE)));
                Attack.setText(c.getString(c.getColumnIndexOrThrow(KEY_ATTACK)));
                Guard.setText(c.getString(c.getColumnIndexOrThrow(KEY_GUARD)));
                Cancel.setText(c.getString(c.getColumnIndexOrThrow(KEY_CANCEL)));
                Rc.setText(c.getString(c.getColumnIndexOrThrow(KEY_RC)));
                Startup.setText(c.getString(c.getColumnIndexOrThrow(KEY_STARTUP)));
                Active.setText(c.getString(c.getColumnIndexOrThrow(KEY_ACTIVE)));
                Recovery.setText(c.getString(c.getColumnIndexOrThrow(KEY_RECOVERY)));
                Adv.setText(c.getString(c.getColumnIndexOrThrow(KEY_ADV)));
                Inv.setText(c.getString(c.getColumnIndexOrThrow(KEY_INV)));

                frameData.addView(inflaterView);
                evenRow = !evenRow;
            }
            Base.close();
            
            Log.d(TABLE_LOG, "FDFullOnCreateView End");
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
            Bundle bundle = new Bundle();
//            Debug.startMethodTracing("FDOnActivityCreated");
            final String TABLE_LOG = "Fill_log";
            Log.d(TABLE_LOG, "FDOnCreateView");
            DataBaseHelper Base = new DataBaseHelper(getActivity());
            String tableName = getArguments().getString("TableName");
            int MaxRow = Base.getRowCount(tableName);
            Cursor c = Base.getLandscapeTable(tableName);
            int columncount = c.getColumnCount()-2;
            int res = (columncount*MaxRow)+columncount;
            int i = 0;
            int id = 0;

            while (c.moveToNext()) {
                String[] FillNew = new String[columncount];
                FillNew[i] = c.getString(c.getColumnIndexOrThrow("Input"));
                FillNew[++i] = c.getString(c.getColumnIndexOrThrow("Damage"));
                FillNew[++i] = c.getString(c.getColumnIndexOrThrow("Tension"));
                FillNew[++i] = c.getString(c.getColumnIndexOrThrow("RISC"));
                FillNew[++i] = c.getString(c.getColumnIndexOrThrow("Prorate"));
                FillNew[++i] = c.getString(c.getColumnIndexOrThrow("Attack"));
                FillNew[++i] = c.getString(c.getColumnIndexOrThrow("Guard"));
                FillNew[++i] = c.getString(c.getColumnIndexOrThrow("Cancel"));
                FillNew[++i] = c.getString(c.getColumnIndexOrThrow("RC"));
                FillNew[++i] = c.getString(c.getColumnIndexOrThrow("Startup"));
                FillNew[++i] = c.getString(c.getColumnIndexOrThrow("Active"));
                FillNew[++i] = c.getString(c.getColumnIndexOrThrow("Recovery"));
                FillNew[++i] = c.getString(c.getColumnIndexOrThrow("Adv"));
                FillNew[++i] = c.getString(c.getColumnIndexOrThrow("Inv"));
                i=0;
//            bundle.clear();
                bundle.putStringArray(Integer.toString(++id), FillNew);
                getLoaderManager().initLoader(id, bundle, this);
            }
            Base.close();

            Log.d(TABLE_LOG, "FDOnCreateView End");
//        Debug.stopMethodTracing();
            return frameDataView;
        }
    }

/*    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }*/


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
        data.setId(loaderId);
        if(childCount == 0) frameData.addView(data);
        else for(int i=0; i < childCount; i++){
            if(loaderId < frameData.getChildAt(i).getId() && !isAlreadyAdded){
                frameData.addView(data, i);
                isAlreadyAdded = true;
            }
            else if(i == childCount-1 && !isAlreadyAdded) frameData.addView(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<View> loader) {

    }
}
