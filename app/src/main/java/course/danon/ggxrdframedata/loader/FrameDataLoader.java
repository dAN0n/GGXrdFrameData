package course.danon.ggxrdframedata.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import course.danon.ggxrdframedata.R;
import static course.danon.ggxrdframedata.helper.DataBaseParams.*;

public class FrameDataLoader extends AsyncTaskLoader<SimpleAdapter> {

    private Context mContext;
    private String mData[][];
    private boolean mFull;
    private List<HashMap<String, String>> aList;
    private SimpleAdapter mAdapter;

    public FrameDataLoader (Context context, Bundle args, boolean fullFrameData, int id){
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
    public SimpleAdapter loadInBackground() {
        LayoutInflater Inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View inflaterView = Inflater.inflate(R.layout.table_row, null, false);
        if(!mFull){
            liteFrameDataRow(inflaterView);
        }
        else{
            fullFrameDataRow(inflaterView);
        }
        return mAdapter;
    }

    @Override
    public void deliverResult(SimpleAdapter data) {
        super.deliverResult(data);
    }

    private void liteFrameDataRow(View inflaterView){
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
        mAdapter = new SimpleAdapter(mContext, aList, R.layout.list_row, from, to);
/*        int i = 0;

        TextView Input = (TextView) inflaterView.findViewById(R.id.Input);
        TextView Guard = (TextView) inflaterView.findViewById(R.id.Guard);
        TextView Startup = (TextView) inflaterView.findViewById(R.id.Startup);
        TextView Adv = (TextView) inflaterView.findViewById(R.id.Adv);

        if (mId % 2 != 0) {
            Input.setBackgroundColor(mContext.getResources().getColor(R.color.dark_row_color));
            Guard.setBackgroundColor(mContext.getResources().getColor(R.color.dark_row_color));
            Startup.setBackgroundColor(mContext.getResources().getColor(R.color.dark_row_color));
            Adv.setBackgroundColor(mContext.getResources().getColor(R.color.dark_row_color));
        } else {
            Input.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
            Guard.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
            Startup.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
            Adv.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
        }

        Input.setText(mData[i]);
        Guard.setText(mData[++i]);
        Startup.setText(mData[++i]);
        Adv.setText(mData[++i]);*/
    }

    private void fullFrameDataRow(View inflaterView){
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
        mAdapter = new SimpleAdapter(mContext, aList, R.layout.list_row, from, to);
/*        int i = 0;

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

        if (mId % 2 != 0) {
            Input.setBackgroundColor(mContext.getResources().getColor(R.color.dark_row_color));
            Damage.setBackgroundColor(mContext.getResources().getColor(R.color.dark_row_color));
            Tension.setBackgroundColor(mContext.getResources().getColor(R.color.dark_row_color));
            Risc.setBackgroundColor(mContext.getResources().getColor(R.color.dark_row_color));
            Prorate.setBackgroundColor(mContext.getResources().getColor(R.color.dark_row_color));
            Attack.setBackgroundColor(mContext.getResources().getColor(R.color.dark_row_color));
            Guard.setBackgroundColor(mContext.getResources().getColor(R.color.dark_row_color));
            Cancel.setBackgroundColor(mContext.getResources().getColor(R.color.dark_row_color));
            Rc.setBackgroundColor(mContext.getResources().getColor(R.color.dark_row_color));
            Startup.setBackgroundColor(mContext.getResources().getColor(R.color.dark_row_color));
            Active.setBackgroundColor(mContext.getResources().getColor(R.color.dark_row_color));
            Recovery.setBackgroundColor(mContext.getResources().getColor(R.color.dark_row_color));
            Adv.setBackgroundColor(mContext.getResources().getColor(R.color.dark_row_color));
            Inv.setBackgroundColor(mContext.getResources().getColor(R.color.dark_row_color));
        } else {
            Input.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
            Damage.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
            Tension.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
            Risc.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
            Prorate.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
            Attack.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
            Guard.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
            Cancel.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
            Rc.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
            Startup.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
            Active.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
            Recovery.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
            Adv.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
            Inv.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
        }
        Input.setText(mData[i]);
        Damage.setText(mData[++i]);
        Tension.setText(mData[++i]);
        Risc.setText(mData[++i]);
        Prorate.setText(mData[++i]);
        Attack.setText(mData[++i]);
        Guard.setText(mData[++i]);
        Cancel.setText(mData[++i]);
        Rc.setText(mData[++i]);
        Startup.setText(mData[++i]);
        Active.setText(mData[++i]);
        Recovery.setText(mData[++i]);
        Adv.setText(mData[++i]);
        Inv.setText(mData[++i]);*/
    }
}
