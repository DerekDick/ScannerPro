package com.derekdick.scannerpro;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.derekdick.scannerpro.zxing.android.CaptureActivity;

public class ScanActivity extends AppCompatActivity {
    private TextView resultTv;
    private String barcodeResult;
    private String qrcodeResult;
    private String ocrResult;
    int type;
    private static final String DECODED_CONTENT_KEY = "codedContent";

    private static final int REQUEST_CODE_SCAN = 0x0000;
    //private static final int REQUEST_CODE_OCR = 0x0001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        // Initialize the private views
        initView();

        // For the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_scan);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView() {
        resultTv = (TextView) findViewById(R.id.text_result);
    }

    public void scanBarcode(View view) {
        /* On click of the button scan barcode */
        type = 1;
        Intent intent = new Intent(ScanActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    public void scanQRCode(View view) {
        /* On click of the button scan QR Code */
        type = 2;
        Intent intent = new Intent(ScanActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    public void scanOCR(View view) {
        /* On click of the button scan OCR */
        type = 3;
        Intent intent = new Intent(ScanActivity.this, OCRActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_scan, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();

                switch (type) {
                    case 1:
                        bundle.putString("content_return", barcodeResult);

                        break;

                    case 2:
                        bundle.putString("content_return", qrcodeResult);

                        break;

                    case 3:
                        bundle.putString("content_return", ocrResult);

                        break;

                    default:
                        break;
                }

                //bundle.putString("content_return", barcodeResult);
                bundle.putInt("type_return", type);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;

            default:
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = "";

                switch (type) {
                    case 1:
                        content = data.getStringExtra(DECODED_CONTENT_KEY);
                        barcodeResult = content;
                        qrcodeResult = "";
                        ocrResult = "";

                        break;

                    case 2:
                        content = data.getStringExtra(DECODED_CONTENT_KEY);
                        barcodeResult = "";
                        qrcodeResult = content;
                        ocrResult = "";

                        break;

                    case 3:
                        content = data.getStringExtra("content_return");
                        barcodeResult = "";
                        qrcodeResult = "";
                        ocrResult = content;

                        break;

                    default:
                        break;
                }

                resultTv.setText("解码结果： \n" + content);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();

        switch (type) {
            case 1:
                bundle.putString("content_return", barcodeResult);

                break;

            case 2:
                bundle.putString("content_return", qrcodeResult);

                break;

            case 3:
                bundle.putString("content_return", ocrResult);

                break;

            default:
                break;
        }
        bundle.putInt("type_return", type);
        intent.putExtras(bundle);
        switch (type) {
            case 1:
                if (barcodeResult == "") {
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }

                break;

            case 2:
                if (qrcodeResult == "") {
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }

                break;

            case 3:
                if (ocrResult == "") {
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }

                break;

            default:
                break;
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
