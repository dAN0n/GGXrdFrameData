package course.danon.ggxrdframedata.activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import course.danon.ggxrdframedata.fragment.CharPicInfoFragment;
import course.danon.ggxrdframedata.helper.DataBaseHelper;
import course.danon.ggxrdframedata.fragment.FrameDataFragment;
import course.danon.ggxrdframedata.fragment.FrameDataFullFragment;
import course.danon.ggxrdframedata.R;
import static course.danon.ggxrdframedata.helper.DataBaseParams.*;

//TODO Потестировать typeFace monospace
//TODO Подумать над цветами таблицы
//TODO Прикрутить-таки Loader
//TODO Разобраться с profiler и hierarchyviewer для отчёта

public class CharacterFrameDataActivity extends ActionBarActivity {
    final String DRAWER_IMAGE = "DrawerImage";
    final String DRAWER_TEXT = "DrawerText";

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private DataBaseHelper Base;
    private TextView Name;
    private String CharName;
    private String CharId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Debug.startMethodTracing("CharOnCreate");
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        String[] charList = intent.getStringArrayExtra(CHAR_LIST);
        if(savedInstanceState == null) {
            CharId = intent.getStringExtra(CHAR_ID);
        }
        else CharId = savedInstanceState.getString(CHAR_ID);

        setContentView(R.layout.character_frame_data);

        Log.d(TABLE_LOG, "Id: " + CharId);
        Log.d(TABLE_LOG, "ContentView Set");
        Base = new DataBaseHelper(this);

        Log.d(TABLE_LOG, "DrawerListFillng");
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
//        mActivityTitle = getTitle().toString();

        Cursor c = Base.getColumn(KEY_CHAR_SELECT, KEY_ICON);
        String[] charIcons = new String[charList.length];
        int i = 0;
        while(c.moveToNext()){
            charIcons[i] = c.getString(c.getColumnIndexOrThrow(KEY_ICON))+"_icon";
            i++;
        }

        List<HashMap<String,String>> aList = new ArrayList<>();
        for(i=0; i< charList.length; i++){
            HashMap<String, String> hm = new HashMap<>();
            hm.put(DRAWER_IMAGE, Integer.toString(getResources().getIdentifier(charIcons[i], "drawable", getPackageName())));
            hm.put(DRAWER_TEXT, charList[i]);
            aList.add(hm);
        }

        String[] from = { DRAWER_IMAGE, DRAWER_TEXT };
        int[] to = { R.id.DrawerImage,R.id.DrawerText };
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.drawer_list_item, from, to);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        setupDrawer();
        Log.d(TABLE_LOG, "DrawerListFilling End");

        CharName = null;
        String CharTableName = null;
        Log.d(TABLE_LOG, "CharInfo Get Start");
        c = Base.getCharInfo(CharId);
        Log.d(TABLE_LOG, "CharInfo Filling");
        while (c.moveToNext()) {
            CharName = c.getString(c.getColumnIndexOrThrow(KEY_CHAR));
            CharTableName = c.getString(c.getColumnIndexOrThrow(KEY_TABLENAME));
        }

        Name = (TextView)findViewById(R.id.CharNameLabel);
        Name.setText(CharName);
        setTitle(CharName);
        Base.close();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            CharPicInfoFragment fragCharPic = CharPicInfoFragment.newInstance(CharId);
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().replace(R.id.CharPicContainer, fragCharPic).commit();
            FrameDataFragment fragFD = FrameDataFragment.newInstance(CharTableName);
            fm.beginTransaction().replace(R.id.FDContainer, fragFD).commit();

            Log.d(TABLE_LOG, "CharName Set");
        }

        else {
            FrameDataFullFragment fragFDFull = FrameDataFullFragment.newInstance(CharTableName);
            FragmentTransaction fm = getFragmentManager().beginTransaction();
            fm.replace(R.id.FDFullContainer, fragFDFull).commit();

            Log.d(TABLE_LOG, "CharName Set Land");
        }
//        Debug.stopMethodTracing();
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
            R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(getResources().getString(R.string.char_select));
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(CharName);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        String Id = Integer.toString(position+1);
        FragmentManager fragmentManager = getFragmentManager();
        Cursor c = Base.getCharInfo(Id);
        String CharTableName = null;
        while (c.moveToNext()) {
            CharName = c.getString(c.getColumnIndexOrThrow(KEY_CHAR));
            CharTableName = c.getString(c.getColumnIndexOrThrow(KEY_TABLENAME));
        }
        Base.close();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            CharPicInfoFragment fragment = CharPicInfoFragment.newInstance(Id);
            fragmentManager.beginTransaction().replace(R.id.CharPicContainer, fragment).commit();

            FrameDataFragment fragment2 = FrameDataFragment.newInstance(CharTableName);
            fragmentManager.beginTransaction().replace(R.id.FDContainer, fragment2).commit();
        }
        else {
            FrameDataFullFragment fragment3 = FrameDataFullFragment.newInstance(CharTableName);
            fragmentManager.beginTransaction().replace(R.id.FDFullContainer, fragment3).commit();
        }

        mDrawerList.setItemChecked(position, true);
        CharId = Id;
        Name.setText(CharName);
        setTitle(CharName);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CHAR_ID, CharId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_character_frame_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final TextView message = new TextView(this);
            message.setText(R.string.help_message);
            message.setMovementMethod(LinkMovementMethod.getInstance());
            message.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            builder.setTitle(R.string.help_title)
                .setView(message)
                .setCancelable(false)
                .setNegativeButton(R.string.close_button,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        }

        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

}
