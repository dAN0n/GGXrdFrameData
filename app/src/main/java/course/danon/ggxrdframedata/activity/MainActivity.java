package course.danon.ggxrdframedata.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import course.danon.ggxrdframedata.helper.DataBaseHelper;
import course.danon.ggxrdframedata.R;
import static course.danon.ggxrdframedata.helper.DataBaseParams.*;

/**
 * Main Menu activity with list of characters for selecting
 * @author Zobkov Dmitry (d@N0n)
 * @version 1.5
 */
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);

        final Intent intent = new Intent(this, CharacterFrameDataActivity.class);
        DataBaseHelper Base = new DataBaseHelper(this);
        int CharCount = Base.getRowCount(KEY_CHAR_SELECT);
        String msg = String.valueOf(CharCount);
        Log.d(TABLE_LOG, "Char count: " + msg);

        Cursor c = Base.getColumn(KEY_CHAR_SELECT, KEY_CHAR);
        int i = 0;
        final String[] Char = new String[CharCount];
        while(c.moveToNext()){
            Char[i] = c.getString(c.getColumnIndexOrThrow(KEY_CHAR));
            i++;
        }
        Base.close();

        ListView ListView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.main_activity_list_view_item, Char);
        ListView.setAdapter(adapter);

        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                String CharId = String.valueOf(id + 1);
                Log.d(TABLE_LOG, "Selected CharId: " + CharId);
                intent.putExtra(CHAR_ID, CharId);
                intent.putExtra(CHAR_LIST, Char);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final TextView message = new TextView(this);
            message.setText(R.string.about_message);
            message.setMovementMethod(LinkMovementMethod.getInstance());
            message.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            builder.setTitle(R.string.about_title)
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
        return super.onOptionsItemSelected(item);
    }
}
