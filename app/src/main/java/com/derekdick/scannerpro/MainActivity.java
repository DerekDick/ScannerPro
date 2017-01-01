package com.derekdick.scannerpro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v4.view.GravityCompat;
import android.support.design.widget.NavigationView;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    private List<Record> recordList = DataSupport.findAll(Record.class);

    private RecordAdapter recordAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the private views
        initViews();

        // For the Toolbar
        setSupportActionBar(toolbar);

        // For the DrawerLayout
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                //mDrawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.nav_call:
                        Toast.makeText(MainActivity.this, "MenuItem Call clicked.",
                                Toast.LENGTH_SHORT).show();

                        break;

                    case R.id.nav_friends:
                        Toast.makeText(MainActivity.this, "MenuItem Friends clicked.",
                                Toast.LENGTH_SHORT).show();

                        break;

                    case R.id.nav_location:
                        Toast.makeText(MainActivity.this, "MenuItem Location clicked.",
                                Toast.LENGTH_SHORT).show();

                        break;

                    case R.id.nav_mail:
                        Toast.makeText(MainActivity.this, "MenuItem Mail clicked.",
                                Toast.LENGTH_SHORT).show();

                        break;

                    default:
                        break;
                }

                return true;
            }
        });

        // For the records list view
        listView = (ListView) findViewById(R.id.list_view);
        recordAdapter = new RecordAdapter(MainActivity.this, R.layout.record_item, recordList);
        listView.setAdapter(recordAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("content", recordList.get(position).getContent());
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        // For the LitePal database
        Connector.getDatabase();
    }

    private void initViews() {
        /* Initialize the private views of this activity */
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);

                break;

            case R.id.search:
                Toast.makeText(this, "You clicked Search.", Toast.LENGTH_SHORT).show();

                break;

            case R.id.settings:
                Intent intent_settings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent_settings);

                break;

            case R.id.about:
                Intent intent_about = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent_about);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                break;

            default:
                break;
        }

        return true;
    }

    public void fabOnClick(View view) {
        /* On click of the floating action button */
        Intent intent = new Intent(MainActivity.this, ScanActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String returnedContent = bundle.getString("content_return");
                    int returnedType = bundle.getInt("type_return");
                    refreshListView(returnedContent, returnedType);
                }
                break;

            default:
                break;
        }
    }

    private String getCurrentTime() {
        /* Return the current time as a String */
        SimpleDateFormat formatter=  new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
        Date currentDate = new Date(System.currentTimeMillis());
        return formatter.format(currentDate);
    }

    private void refreshListView(String result, int type) {
        /* Add a new record into the recordList */
        switch (type) {
            case 1:
                Record record_barcode = new Record(getCurrentTime(), "Barcode", result,
                        R.drawable.ic_barcode);
                record_barcode.save();

                break;

            case 2:
                Record record_qrcode = new Record(getCurrentTime(), "Barcode", result,
                        R.drawable.ic_qrcode);
                record_qrcode.save();

                break;

            case 3:
                Record record_ocr = new Record(getCurrentTime(), "OCR", result,
                        R.drawable.ic_ocr);
                record_ocr.save();

                break;

            default:
                break;
        }

        updateListView();
    }

    public void updateListView() {
        recordList = DataSupport.findAll(Record.class);
        recordAdapter = new RecordAdapter(MainActivity.this, R.layout.record_item, recordList);
        listView.setAdapter(recordAdapter);
    }
}
