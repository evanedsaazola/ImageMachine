package io.prospace.submissions.imagemachine.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.Objects;

import io.prospace.submissions.imagemachine.R;

public class CodeReaderActivity extends AppCompatActivity {

    private SurfaceView view_camera;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;

    final int requestCameraPermissionId = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_reader);

        view_camera = findViewById(R.id.viewCodeReaderCamera);

        // Initialize barcode detector and set to only scan QR Code formats.
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        // Initialize camera source and set the size of camera preview.
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        /*
        Providing access to surface view and check if the user has given the permission for the app
        to access the camera.
         */
        view_camera.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if(ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CodeReaderActivity.this,
                            new String[]{Manifest.permission.CAMERA}, requestCameraPermissionId);
                    return;
                }
                try {
                    cameraSource.start(view_camera.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            /*
            Check if there is QR code detected. If it does, then it will send the value stored inside
            QR code to another activity.
             */
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCode = detections.getDetectedItems();
                if (qrCode.size() != 0) {
                    barcodeDetector.release();
                    Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                    Objects.requireNonNull(vibrator).vibrate(500);
                    String qrCodeValueText = qrCode.valueAt(0).displayValue;
                    Log.d("DataCheck", "Check QR Code Result >> " + qrCodeValueText);

                    Intent intent = new Intent(CodeReaderActivity.this, MachineDataDetailActivity.class);
                    intent.putExtra(MachineDataDetailActivity.MACHINE_QR_CODE_EXTRA, qrCodeValueText);
                    startActivity(intent);
                }
            }
        });

    }

    /*
    Check if the user has given the permission for the app to access the camera.
    */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == requestCameraPermissionId) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(CodeReaderActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try {
                    cameraSource.start(view_camera.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}