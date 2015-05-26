package course.danon.ggxrdframedata.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import course.danon.ggxrdframedata.R;

public class TableSimpleAdapter extends SimpleAdapter{

    private static final String TAG_COLOR = "COLOR";
    private int[] colors;
    private Context mContext;
    private int colorPos;

    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public TableSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);

        mContext = context;
//        mColor = Color;
        colors = new int[]{mContext.getResources().getColor(R.color.dark_row_color),
            mContext.getResources().getColor(R.color.light_row_color)};
    }

    @Override
    public void setViewText(TextView v, String text) {
        super.setViewText(v, text);
        v.setBackgroundColor(colors[colorPos]);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
//        for(int i = 0; i < view.)
        colorPos = position % 2;
        Log.d("Fill", Integer.toString(colorPos) + " " + Integer.toString(position));
//        view.setBackgroundColor(colors[colorPos]);
        return view;
    }
}
