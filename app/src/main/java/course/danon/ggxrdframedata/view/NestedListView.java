package course.danon.ggxrdframedata.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class NestedListView extends ListView implements View.OnTouchListener, AbsListView.OnScrollListener
{
    private int listViewTouchAction;
    boolean expanded = false;
    private static final int MAXIMUM_LIST_ITEMS_VIEWABLE = 99;

    public NestedListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        listViewTouchAction = -1;
        setOnScrollListener(this);
        setOnTouchListener(this);
    }

    public boolean isExpanded()
    {
        return expanded;
    }

    public void setExpanded(boolean expanded)
    {
        this.expanded = expanded;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount)
    {
        if (getAdapter() != null && getAdapter().getCount() > MAXIMUM_LIST_ITEMS_VIEWABLE)
        {
            if (listViewTouchAction == MotionEvent.ACTION_MOVE)
            {
                scrollBy(0, -1);
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if(isExpanded()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            int newHeight = 0;
            final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            if (heightMode != MeasureSpec.EXACTLY) {
                ListAdapter listAdapter = getAdapter();
                if (listAdapter != null && !listAdapter.isEmpty()) {
                    int listPosition = 0;
                    for (listPosition = 0; listPosition < listAdapter.getCount()
                        && listPosition < MAXIMUM_LIST_ITEMS_VIEWABLE; listPosition++) {
                        View listItem = listAdapter.getView(listPosition, null, this);
                        //now it will not throw a NPE if listItem is a ViewGroup instance
                        if (listItem instanceof ViewGroup) {
                            listItem.setLayoutParams(new LayoutParams(
                                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                        }
                        listItem.measure(widthMeasureSpec, heightMeasureSpec);
                        newHeight += listItem.getMeasuredHeight();
                    }
                    newHeight += getDividerHeight() * listPosition;
                }
                if ((heightMode == View.MeasureSpec.AT_MOST) && (newHeight > heightSize)) {
                    if (newHeight > heightSize) {
                        newHeight = heightSize;
                    }
                }
            } else {
                newHeight = getMeasuredHeight();
            }
            setMeasuredDimension(getMeasuredWidth(), newHeight);
        }
        else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (getAdapter() != null && getAdapter().getCount() > MAXIMUM_LIST_ITEMS_VIEWABLE)
        {
            if (listViewTouchAction == MotionEvent.ACTION_MOVE)
            {
                scrollBy(0, 1);
            }
        }
        return false;
    }
}