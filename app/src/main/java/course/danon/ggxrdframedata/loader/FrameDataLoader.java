package course.danon.ggxrdframedata.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import course.danon.ggxrdframedata.R;

public class FrameDataLoader extends AsyncTaskLoader<View> {

    private Context mContext;
    private final String mData[];
    private boolean mFull;
    private View view;

    public FrameDataLoader (Context context, Bundle args, boolean fullFrameData, View inflaterView, int id){
        super(context);
        mContext = context;
        mData = args.getStringArray(Integer.toString(id));
        mFull = fullFrameData;
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
            TextView Input = (TextView) view.findViewById(R.id.Input);
            TextView Guard = (TextView) view.findViewById(R.id.Guard);
            TextView Startup = (TextView) view.findViewById(R.id.Startup);
            TextView Adv = (TextView) view.findViewById(R.id.Adv);

            Input.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
            Guard.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
            Startup.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));
            Adv.setBackgroundColor(mContext.getResources().getColor(R.color.light_row_color));

            Input.setText(mData[i]);
            Guard.setText(mData[++i]);
            Startup.setText(mData[++i]);
            Adv.setText(mData[++i]);
        }
        return view;
    }

    @Override
    public void deliverResult(View data) {
        super.deliverResult(data);
    }
}
