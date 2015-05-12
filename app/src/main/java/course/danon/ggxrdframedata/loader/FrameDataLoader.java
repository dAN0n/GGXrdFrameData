package course.danon.ggxrdframedata.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import course.danon.ggxrdframedata.R;

public class FrameDataLoader extends AsyncTaskLoader<View> {

    private Context mContext;
    private final String mData[];
    private boolean mFull;
    private View view;
    private int mId;

    public FrameDataLoader (Context context, Bundle args, boolean fullFrameData, View inflaterView, int id){
        super(context);
        mContext = context;
        mData = args.getStringArray(Integer.toString(id));
        mFull = fullFrameData;
        mId = id;
        view = inflaterView;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public View loadInBackground() {
        int i = 0;
        if(!mFull){
            liteFrameDataRow();
        }
        else{
            fullFrameDataRow();
        }
        return view;
    }

    @Override
    public void deliverResult(View data) {
        super.deliverResult(data);
    }

    private void liteFrameDataRow(){
        int i = 0;

        TextView Input = (TextView) view.findViewById(R.id.Input);
        TextView Guard = (TextView) view.findViewById(R.id.Guard);
        TextView Startup = (TextView) view.findViewById(R.id.Startup);
        TextView Adv = (TextView) view.findViewById(R.id.Adv);

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
        Adv.setText(mData[++i]);
    }

    private void fullFrameDataRow(){
        int i = 0;

        TextView Input = (TextView) view.findViewById(R.id.Input);
        TextView Damage = (TextView) view.findViewById(R.id.Damage);
        TextView Tension = (TextView) view.findViewById(R.id.Tension);
        TextView Risc = (TextView) view.findViewById(R.id.RISC);
        TextView Prorate = (TextView) view.findViewById(R.id.Prorate);
        TextView Attack = (TextView) view.findViewById(R.id.Attack);
        TextView Guard = (TextView) view.findViewById(R.id.Guard);
        TextView Cancel = (TextView) view.findViewById(R.id.Cancel);
        TextView Rc = (TextView) view.findViewById(R.id.RC);
        TextView Startup = (TextView) view.findViewById(R.id.Startup);
        TextView Active = (TextView) view.findViewById(R.id.Active);
        TextView Recovery = (TextView) view.findViewById(R.id.Recovery);
        TextView Adv = (TextView) view.findViewById(R.id.Adv);
        TextView Inv = (TextView) view.findViewById(R.id.Inv);

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
        Inv.setText(mData[++i]);
    }
}
