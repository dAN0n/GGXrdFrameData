package course.danon.ggxrdframedata.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import course.danon.ggxrdframedata.R;
import course.danon.ggxrdframedata.adapter.TableSimpleAdapter;

import static course.danon.ggxrdframedata.helper.DataBaseParams.*;

public class FrameDataLoader extends AsyncTaskLoader<TableSimpleAdapter> {

    private Context mContext;
    private String mData[][];
    private boolean mFull;
    private List<HashMap<String, String>> aList;
    private TableSimpleAdapter mAdapter;

    public FrameDataLoader (Context context, Bundle args, boolean fullFrameData){
        super(context);
        mContext = context;
        mData = (String[][]) args.getSerializable(KEY_ID);
        mFull = fullFrameData;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        if(aList != null) {
            aList.clear();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public TableSimpleAdapter loadInBackground() {
        if(!mFull){
            liteFrameDataRow();
        }
        else{
            fullFrameDataRow();
        }
        return mAdapter;
    }

    @Override
    public void deliverResult(TableSimpleAdapter data) {
        super.deliverResult(data);
    }

    private void liteFrameDataRow(){
        aList = new ArrayList<>();
        for(int i=0; i< mData[0].length; i++){
            int j = 0;
            HashMap<String, String> hm = new HashMap<>();
            hm.put(KEY_INPUT, mData[j][i]);
            hm.put(KEY_GUARD, mData[++j][i]);
            hm.put(KEY_STARTUP, mData[++j][i]);
            hm.put(KEY_ADV, mData[++j][i]);
            aList.add(hm);
        }

        String[] from = { KEY_INPUT, KEY_GUARD, KEY_STARTUP, KEY_ADV };
        int[] to = { R.id.Input, R.id.Guard, R.id.Startup, R.id.Adv };
        mAdapter = new TableSimpleAdapter(mContext, aList, R.layout.list_row, from, to);
    }

    private void fullFrameDataRow(){
        aList = new ArrayList<>();
        for(int i=0; i< mData[0].length; i++){
            int j = 0;
            HashMap<String, String> hm = new HashMap<>();
            hm.put(KEY_INPUT, mData[j][i]);
            hm.put(KEY_DAMAGE, mData[++j][i]);
            hm.put(KEY_TENSION, mData[++j][i]);
            hm.put(KEY_RISC, mData[++j][i]);
            hm.put(KEY_PRORATE, mData[++j][i]);
            hm.put(KEY_ATTACK, mData[++j][i]);
            hm.put(KEY_GUARD, mData[++j][i]);
            hm.put(KEY_CANCEL, mData[++j][i]);
            hm.put(KEY_RC, mData[++j][i]);
            hm.put(KEY_STARTUP, mData[++j][i]);
            hm.put(KEY_ACTIVE, mData[++j][i]);
            hm.put(KEY_RECOVERY, mData[++j][i]);
            hm.put(KEY_ADV, mData[++j][i]);
            hm.put(KEY_INV, mData[++j][i]);
            aList.add(hm);
        }

        String[] from = { KEY_INPUT,KEY_DAMAGE, KEY_TENSION, KEY_RISC, KEY_PRORATE, KEY_ATTACK,
            KEY_GUARD, KEY_CANCEL, KEY_RC, KEY_STARTUP, KEY_ACTIVE, KEY_RECOVERY, KEY_ADV, KEY_INV };
        int[] to = { R.id.Input, R.id.Damage, R.id.Tension, R.id.RISC, R.id.Prorate, R.id.Attack,
            R.id.Guard, R.id.Cancel, R.id.RC, R.id.Startup, R.id.Active, R.id.Recovery, R.id.Adv, R.id.Inv };
        mAdapter = new TableSimpleAdapter(mContext, aList, R.layout.list_row, from, to);
    }
}
