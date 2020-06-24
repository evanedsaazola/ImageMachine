package io.prospace.submissions.imagemachine;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MachineDataDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private MachineImageAdapter machineImageAdapter;
    private ArrayList<MachineImageDataModel> machineImageArrayList = new ArrayList<>();

    public static MachineImageDatabaseHelper machineImageDatabaseHelper;

    private List<MachineDataModel> machineDataArrayList = new ArrayList<>();
    private MachineDatabase machineDatabase;

    private RecyclerView rv_machine_image;
    private TextView tv_toolbar_title, tv_detail_id, tv_detail_name, tv_detail_type, tv_detail_qr_code, tv_detail_date;

    public static final String MACHINE_ID_EXTRA = "machine_id";
    public static final String MACHINE_QR_CODE_EXTRA = "machine_qr_code";
    private String machineId, machineQrCode;
    private boolean checked = false;

//    ArrayList<Integer> imageId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_data_detail);

        rv_machine_image = findViewById(R.id.rvMachineDetailImage);
        Button btn_detail_add_images = findViewById(R.id.btnMachineDetailAddImage);
        Button btn_detail_delete_data = findViewById(R.id.btnMachineDetailDeleteData);
        Button btn_back = findViewById(R.id.btnBack);
        tv_toolbar_title = findViewById(R.id.tvToolbarMachineDetailTitle);
        tv_detail_id = findViewById(R.id.tvMachineDetailId);
        tv_detail_name = findViewById(R.id.tvMachineDetailName);
        tv_detail_type = findViewById(R.id.tvMachineDetailType);
        tv_detail_qr_code = findViewById(R.id.tvMachineDetailQrCodeNum);
        tv_detail_date = findViewById(R.id.tvMachineDetailMaintenanceDate);
        FloatingActionButton fab_delete_images = findViewById(R.id.fabDeleteItems);

        btn_detail_add_images.setOnClickListener(this);
        btn_detail_delete_data.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        fab_delete_images.setOnClickListener(this);

        machineId = getIntent().getStringExtra(MACHINE_ID_EXTRA);
        machineQrCode = getIntent().getStringExtra(MACHINE_QR_CODE_EXTRA);
        initializeDatabase();
        if (machineId != null) {
            showDataFromId();
        }
        else if (machineQrCode != null) {
            showDataFromQrCode();
        }

        machineImageDatabaseHelper = new MachineImageDatabaseHelper
                (this, "IMAGESDB.sqlite", null, 1);
        machineImageDatabaseHelper.queryData
                ("CREATE TABLE IF NOT EXISTS IMAGES(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, image BLOB)");

        Cursor cursor = getData();
        machineImageArrayList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            Log.d("DataCheck", "Check>> " + name);
            Log.d("DataCheck", "Check>> " + Arrays.toString(image));
            machineImageArrayList.add(new MachineImageDataModel(id, name, image, checked));
        }
        machineImageAdapter = new MachineImageAdapter(machineImageArrayList, this, imageClickCallback);

        rv_machine_image.setHasFixedSize(true);
        rv_machine_image.setLayoutManager(new GridLayoutManager(this, 2));
        rv_machine_image.setAdapter(machineImageAdapter);
        machineImageAdapter.notifyDataSetChanged();

    }

    private void initializeDatabase() {
        machineDatabase = Room.databaseBuilder(this, MachineDatabase.class, "machinesdb")
                .allowMainThreadQueries()
                .build();
    }

    private void showDataFromId() {
        machineDataArrayList = machineDatabase.machineDao().getMachineDataById(machineId);

        for (int position = 0; position < machineDataArrayList.size(); position++) {
            tv_toolbar_title.setText(machineDataArrayList.get(position).getMachineName());
            tv_detail_id.setText(machineDataArrayList.get(position).getMachineId());
            tv_detail_name.setText(machineDataArrayList.get(position).getMachineName());
            tv_detail_type.setText("Type: " + machineDataArrayList.get(position).getMachineType());
            tv_detail_qr_code.setText("QR Code Number: " + machineDataArrayList.get(position).getMachineQrCodeNum());
            tv_detail_date.setText("Latest Maintenance Date: " + machineDataArrayList.get(position).getMachineDate());
        }
    }

    private void showDataFromQrCode() {
        machineDataArrayList = machineDatabase.machineDao().getMachinesDataByQrCode(machineQrCode);

        for (int position = 0; position < machineDataArrayList.size(); position++) {
            tv_toolbar_title.setText(machineDataArrayList.get(position).getMachineName());
            tv_detail_id.setText(machineDataArrayList.get(position).getMachineId());
            tv_detail_name.setText(machineDataArrayList.get(position).getMachineName());
            tv_detail_type.setText("Type: " + machineDataArrayList.get(position).getMachineType());
            tv_detail_qr_code.setText("QR Code Number: " + machineDataArrayList.get(position).getMachineQrCodeNum());
            tv_detail_date.setText("Latest Maintenance Date: " + machineDataArrayList.get(position).getMachineDate());
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnMachineDetailAddImage:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                   ActivityCompat.requestPermissions(this,
                           new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                   return;
                }

                Intent intentSelectImages = new Intent(Intent.ACTION_GET_CONTENT);
                intentSelectImages.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intentSelectImages.setType("image/+");
                startActivityForResult(intentSelectImages, 1);
                break;

            case R.id.btnMachineDetailDeleteData:
                machineDatabase.machineDao().deleteMachineDataById(tv_detail_id.getText().toString());
                startActivity(new Intent(MachineDataDetailActivity.this, MachineDataActivity.class));
                Toast.makeText(this, "Your data have been deleted!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnBack:
                finish();
                break;
//
//            case R.id.fabDeleteItems:
//                Cursor cursor = machineImageDatabaseHelper.getQuery("SELECT id FROM IMAGES");
//
//                while (cursor.moveToNext()) {
//                    imageId.add(cursor.getInt(idX));
//
//                }
//                deleteImages(imageId.get(idX));
//
//                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            ClipData clipData = Objects.requireNonNull(data).getClipData();
            if (clipData != null) {
                if (clipData.getItemCount() > 10) {
                    Toast.makeText(this, "You only can input 10 images!", Toast.LENGTH_LONG).show();
                    Intent test = new Intent();
                    setResult(Activity.RESULT_CANCELED, test);
                }
                else {
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Uri imageUri = clipData.getItemAt(i).getUri();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                            machineImageDatabaseHelper.insertMachineImages(
                                    tv_detail_name.getText().toString(),
                                    convertToBytes(bitmap));
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            else {
                Uri imageUri = data.getData();

                try {
                    assert imageUri != null;
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    machineImageDatabaseHelper.insertMachineImages(
                            tv_detail_name.getText().toString(),
                            convertToBytes(bitmap));
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Cursor cursor = getData();
            machineImageArrayList.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                byte[] image = cursor.getBlob(2);
                Log.d("DataCheck", "Check>> " + name);
                Log.d("DataCheck", "Check>> " + Arrays.toString(image));
                machineImageArrayList.add(new MachineImageDataModel(id, name, image, checked));
            }
            machineImageAdapter = new MachineImageAdapter(machineImageArrayList, this, imageClickCallback);
            rv_machine_image.setHasFixedSize(true);
            rv_machine_image.setLayoutManager(new GridLayoutManager(this, 2));
            rv_machine_image.setAdapter(machineImageAdapter);
            machineImageAdapter.notifyDataSetChanged();
        }
    }

    private static byte[] convertToBytes(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30 ,byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private Cursor getData() {
        SQLiteDatabase sqLiteDatabase = machineImageDatabaseHelper.getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM IMAGES WHERE name = ?", new String[]{tv_detail_name.getText().toString()});
    }

//    private void deleteImages(final int idImages) {
//        machineImageDatabaseHelper.deleteMachineImages(idImages);
//        updateImagesList();
//    }
//
//    private void updateImagesList() {
//        Cursor cursor = getData();
//        machineImageArrayList.clear();
//        while (cursor.moveToNext()) {
//            int id = cursor.getInt(0);
//            String name = cursor.getString(1);
//            byte[] image = cursor.getBlob(2);
//            Log.d("DataCheck", "CheckAfter>> " + name);
//            Log.d("DataCheck", "CheckAfter>> " + image);
//            machineImageArrayList.add(new MachineImageDataModel(id, name, image, checked));
//        }
//        machineImageAdapter = new MachineImageAdapter(machineImageArrayList, this, imageClickCallback);
//        rv_machine_image.setHasFixedSize(true);
//        rv_machine_image.setLayoutManager(new GridLayoutManager(this, 2));
//        rv_machine_image.setAdapter(machineImageAdapter);
//        machineImageAdapter.notifyDataSetChanged();
//    }

    private ImageClickCallback imageClickCallback = new ImageClickCallback() {
        @Override
        public void onClickImage(MachineImageDataModel machineImageDataModel) {
            Intent intentImage = new Intent(MachineDataDetailActivity.this, ViewImageActivity.class);
            intentImage.putExtra(ViewImageActivity.VIEW_IMAGE_EXTRA, machineImageDataModel.getImages());
            startActivity(intentImage);
        }
    };
}