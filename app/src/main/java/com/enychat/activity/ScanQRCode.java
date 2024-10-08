package com.enychat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enychat.R;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.androidhive.barcode.BarcodeReader;

public class ScanQRCode extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener,View.OnClickListener {
    BarcodeReader barcodeReader;
   /* @BindView(R.id.toolbar_new_broadcast)Toolbar toolbar;*/
    @BindView(R.id.iv_btn_back)ImageView ivBtnBack;
    @BindView(R.id.tv_title)TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr_code);
        ButterKnife.bind(this);
       /* setSupportActionBar(toolbar);*/
        tvTitle.setText(R.string.scan_qr_code);
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
        ivBtnBack.setOnClickListener(this);
    }
    @Override
    public void onScanned(Barcode barcode) {
        barcodeReader.playBeep();
        Intent intent = new Intent(ScanQRCode.this, EnyChatAppWeb.class);
        intent.putExtra("code", barcode.displayValue);
        startActivity(intent);
    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onCameraPermissionDenied() {
        finish();
    }

    @Override
    public void onScanError(String s) {
        Toast.makeText(getApplicationContext(), "Error occurred while scanning " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if(view==ivBtnBack){
            finish();
        }
    }
}