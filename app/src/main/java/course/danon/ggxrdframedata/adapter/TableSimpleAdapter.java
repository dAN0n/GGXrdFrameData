package course.danon.ggxrdframedata.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

import course.danon.ggxrdframedata.R;

public class TableSimpleAdapter extends SimpleAdapter{

    private int[] colors;

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
    public TableSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource,
                              String[] from, int[] to) {
        super(context, data, resource, from, to);

        colors = new int[]{context.getResources().getColor(R.color.light_row_color),
            context.getResources().getColor(R.color.dark_row_color)};
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        int colorPos = position % 2;
        view.setBackgroundColor(colors[colorPos]);
        return view;
    }
}
