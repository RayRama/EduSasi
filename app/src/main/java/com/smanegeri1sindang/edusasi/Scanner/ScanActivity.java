package com.smanegeri1sindang.edusasi.Scanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.muddzdev.styleabletoast.StyleableToast;
import com.smanegeri1sindang.edusasi.MainActivity.MainActivity;
import com.smanegeri1sindang.edusasi.R;
import com.smanegeri1sindang.edusasi.Scanner.History.Entity.History;
import com.smanegeri1sindang.edusasi.Scanner.History.HistoryActivity;
import com.smanegeri1sindang.edusasi.Scanner.History.SQLite.ORM.HistoryORM;
import com.smanegeri1sindang.edusasi.Scanner.Result.ScanResult;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final String FLASH_STATE = "FLASH_STATE";
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    private boolean mFlash;

    private long backPressedTime;
    private Toast backToast;
    HistoryORM h = new HistoryORM();

    ImageButton historyImg, flash, imgPicker, scan, help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        if (savedInstanceState != null) {
            mFlash = savedInstanceState.getBoolean(FLASH_STATE, false);
        } else {
            mFlash = false;
        }

        setContentView(R.layout.activity_scan);
        ViewGroup scanner_frame = (ViewGroup)findViewById(R.id.scanner_frame);
        mScannerView = new ZXingScannerView(this);
        scanner_frame.addView(mScannerView);

        setupToolbar();
        centerBtn();

        historyImg = (ImageButton) findViewById(R.id.historyBtn);
        historyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScanActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        scan = (ImageButton) findViewById(R.id.scan);
    }

    public void scan_alert(View v) {
        StyleableToast.makeText(this, "You're in here", R.style.alert_scan).show();
    }
    public void centerBtn() {
        flash = (ImageButton) findViewById(R.id.flash);
        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFlash = !mFlash;
                mScannerView.setFlash(mFlash);
                if (mFlash) {
                    flash.setImageResource(R.drawable.ic_flash_on);
                } else {
                    flash.setImageResource(R.drawable.ic_flash_off);
                }
            }
        });
        imgPicker = (ImageButton) findViewById(R.id.img_picker);
        imgPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 111);
            }
        });
    }
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
            finish();
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView Title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            mScannerView.setResultHandler(this);
            mScannerView.setFlash(mFlash);
            mScannerView.startCamera();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FLASH_STATE, mFlash);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            //the case is because you might be handling multiple request codes here
            case 111:
                if(data == null || data.getData()==null) {
                    Log.e("TAG", "The uri is null, probably the user cancelled the image selection process using the back button.");
                    return;
                }
                Uri uri = data.getData();
                try
                {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    if (bitmap == null)
                    {
                        Log.e("TAG", "uri is not a bitmap," + uri.toString());
                        return;
                    }
                    int width = bitmap.getWidth(), height = bitmap.getHeight();
                    int[] pixels = new int[width * height];
                    bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
                    bitmap.recycle();
                    bitmap = null;
                    RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
                    BinaryBitmap bBitmap = new BinaryBitmap(new HybridBinarizer(source));
                    MultiFormatReader reader = new MultiFormatReader();
                    try
                    {
                        Result result = reader.decode(bBitmap);
                        String scanResult = result.getText().toString();
                        /*Intent intent = new Intent(ScanActivity.this, web_scan_result.class);
                        intent.putExtra("img", rr);
                        startActivity(intent);*/

                        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy, HH:mm:ss");
                        String mydate = dateFormat.format(Calendar.getInstance().getTime());
                        History history = new History();
                        history.setContext(result.getText());
                        history.setDate(mydate);
                        h.add(getApplicationContext(), history);

                        Intent intent = new Intent(ScanActivity.this, ScanResult.class);
                        intent.putExtra("content", scanResult);
                        intent.putExtra("date", mydate);
                        startActivity(intent);
                    }
                    catch (NotFoundException e)
                    {
                        Log.e("TAG", "decode exception", e);
                        StyleableToast.makeText(this, "Opsss... Invalid image !!!", R.style.alert_scan).show();
                    }
                }
                catch (FileNotFoundException e)
                {
                    Log.e("TAG", "can not open file" + uri.toString(), e);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            Intent intent = new Intent(ScanActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }   else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    @Override
    public void handleResult(Result result) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy, HH:mm:ss");
        String mydate = dateFormat.format(Calendar.getInstance().getTime());
        History history = new History();
        history.setContext(result.getText());
        history.setDate(mydate);
        h.add(getApplicationContext(), history);

        final String scanResult = result.getText();
        Intent intent = new Intent(ScanActivity.this, ScanResult.class);
        intent.putExtra("content", scanResult);
        intent.putExtra("date", mydate);
        startActivity(intent);

    }
}

//Backup
/*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNeutralButton("Buka", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                *//*Intent intent = new Intent(ScanActivity.this, web_scan_result.class);
                intent.putExtra("web", scanResult);
                startActivity(intent);*//*
            }
        });

        builder.setMessage(scanResult);
        AlertDialog alert = builder.create();
        alert.show();
        alert.setCanceledOnTouchOutside(false);
        if (alert==alert){
            mScannerView.stopCamera();
            mScannerView.startCamera();
            mScannerView.resumeCameraPreview(this);
            Toast.makeText(this, "Berhasil !!!", Toast.LENGTH_SHORT).show();
        }*/
