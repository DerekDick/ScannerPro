package com.derekdick.scannerpro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v4.view.GravityCompat;
import android.support.design.widget.NavigationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;

    private List<Record> recordList = new ArrayList<Record>();

    private RecordAdapter adapter;
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
        //initRecords();
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new RecordAdapter(MainActivity.this, R.layout.record_item, recordList);
        listView.setAdapter(adapter);
    }

    /*private void initRecords() {
        for (int i = 0; i < 10; i++) {
            Record record_barcode = new Record("2016/12/20/01:32", "Barcode", "11111111", R.drawable.ic_barcode);
            recordList.add(record_barcode);
            Record record_qrcode = new Record("2016/12/20/01:34", "QR Code", "22222222", R.drawable.ic_qrcode);
            recordList.add(record_qrcode);
            Record record_ocr = new Record("2016/12/20/01:35", "OCR", "33333333", R.drawable.ic_ocr);
            recordList.add(record_ocr);
        }
    }*/

    private void initViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            case R.id.search:
                Toast.makeText(this, "You clicked Search.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.settings:
                Intent intent_settings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent_settings);
                break;

            default:
                break;
        }

        return true;
    }

    public void fabOnClick(View view) {
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
        SimpleDateFormat formatter=  new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
        Date currentDate = new Date(System.currentTimeMillis());
        return formatter.format(currentDate);
    }

    private void refreshListView(String result, int type) {
        /*if (result.equals("")) {
            Toast.makeText(this, "dereek", Toast.LENGTH_SHORT).show();/////////////////////////////
            return;
        }*/
        switch (type) {
            case 1:
                Record record_barcode = new Record(getCurrentTime(), "Barcode", result, R.drawable.ic_barcode);
                recordList.add(record_barcode);
                listView.setAdapter(adapter);
                break;

            case 2:
                Record record_qrcode = new Record(getCurrentTime(), "Barcode", result, R.drawable.ic_qrcode);
                recordList.add(record_qrcode);
                listView.setAdapter(adapter);
                break;

            case 3:

                break;

            default:
                break;
        }
    }
}
