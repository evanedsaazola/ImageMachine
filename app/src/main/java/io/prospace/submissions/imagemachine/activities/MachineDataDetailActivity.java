package io.prospace.submissions.imagemachine.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.prospace.submissions.imagemachine.R;
import io.prospace.submissions.imagemachine.adapter.MachineImageAdapter;
import io.prospace.submissions.imagemachine.databases.MachineDatabase;
import io.prospace.submissions.imagemachine.datamodel.MachineDataModel;
import io.prospace.submissions.imagemachine.datamodel.MachineImageDataModel;
import io.prospace.submissions.imagemachine.interfaces.ImageClickCallback;

public class MachineDataDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private List<MachineDataModel> machineDataArrayList = new ArrayList<>();
    private MachineDatabase machineDatabase;

    private RecyclerView rv_machine_image;
    private TextView tv_toolbar_title, tv_detail_id, tv_detail_name, tv_detail_type, tv_detail_qr_code, tv_detail_date;

    public static final String MACHINE_ID_EXTRA = "machine_id";
    public static final String MACHINE_QR_CODE_EXTRA = "machine_qr_code";
    private String machineId, machineQrCode;
//    private boolean checked = false;

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

        btn_detail_add_images.setOnClickListener(this);
        btn_detail_delete_data.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        /*
        Retrieve the parameter that needed to show the data, is it from ID or from QR Code and
        show the data based on the retrieved parameter.
         */
        machineId = getIntent().getStringExtra(MACHINE_ID_EXTRA);
        machineQrCode = getIntent().getStringExtra(MACHINE_QR_CODE_EXTRA);
        initializeDatabase();

        if (machineId != null) {
            showDataFromId();
        }
        else if (machineQrCode != null) {
            showDataFromQrCode();
        }
        showImagesFromId();
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
            tv_detail_type.setText(getString(R.string.text_type, machineDataArrayList.get(position).getMachineType()));
            tv_detail_qr_code.setText(getString(R.string.text_qr_code_num, machineDataArrayList.get(position).getMachineQrCodeNum()));
            tv_detail_date.setText(getString(R.string.text_date, machineDataArrayList.get(position).getMachineDate()));
        }
    }

    private void showDataFromQrCode() {
        machineDataArrayList = machineDatabase.machineDao().getMachinesDataByQrCode(machineQrCode);

        for (int position = 0; position < machineDataArrayList.size(); position++) {
            tv_toolbar_title.setText(machineDataArrayList.get(position).getMachineName());
            tv_detail_id.setText(machineDataArrayList.get(position).getMachineId());
            tv_detail_name.setText(machineDataArrayList.get(position).getMachineName());
            tv_detail_type.setText(getString(R.string.text_type, machineDataArrayList.get(position).getMachineType()));
            tv_detail_qr_code.setText(getString(R.string.text_qr_code_num, machineDataArrayList.get(position).getMachineQrCodeNum()));
            tv_detail_date.setText(getString(R.string.text_date, machineDataArrayList.get(position).getMachineDate()));
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            // Check the permission from the user to access phone storage and direct user
            // to storage to select multiple images.
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

            /*
            Display a dialog to confirm the user, is he/she want to delete the data.
             */
            case R.id.btnMachineDetailDeleteData:
                final Dialog dialogDeleteWarning = new Dialog(MachineDataDetailActivity.this);

                if (!isFinishing()) {
                    dialogDeleteWarning.setContentView(R.layout.dialog_delete_data_warning);

                    Button btnCancelDelete = dialogDeleteWarning.findViewById(R.id.btnDialogDeleteCancel);
                    Button btnConfirmDelete = dialogDeleteWarning.findViewById(R.id.btnDialogDeleteConfirm);

                    btnCancelDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogDeleteWarning.dismiss();
                        }
                    });

                    btnConfirmDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            machineDatabase.machineDao().deleteMachineDataById(tv_detail_id.getText().toString());
                            Toast.makeText(MachineDataDetailActivity.this, "Your data have been deleted!", Toast.LENGTH_SHORT).show();
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(MachineDataDetailActivity.this, MachineDataActivity.class));
                                }
                            }, 1500);
                        }
                    });

                    dialogDeleteWarning.setCancelable(false);
                    dialogDeleteWarning.setCanceledOnTouchOutside(false);
                    Objects.requireNonNull(dialogDeleteWarning.getWindow())
                            .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogDeleteWarning.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    dialogDeleteWarning.show();
                }
                else if (isFinishing()) {
                    dialogDeleteWarning.dismiss();
                }
                break;

            case R.id.btnBack:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            /*
            Check if the inputted images is smaller than 10. If it does, then it will cancel the image retrieve.
             */
            ClipData clipData = Objects.requireNonNull(data).getClipData();
            if (clipData != null) {
                if (clipData.getItemCount() > 10) {
                    Toast.makeText(this, "You only can input 10 images!", Toast.LENGTH_LONG).show();
                    Intent test = new Intent();
                    setResult(Activity.RESULT_CANCELED, test);
                }
                // Convert image from gallery and decode it to bitmap and convert it again into
                // byte and also input it to the insert DAO.
                else {
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Uri imageUri = clipData.getItemAt(i).getUri();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                            MachineImageDataModel machineImages = new MachineImageDataModel
                                    (convertToBytes(bitmap), tv_detail_id.getText().toString(), false);
                            machineDatabase.machineDao().insertMachineImageData(machineImages);
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

                    MachineImageDataModel machineImages = new MachineImageDataModel
                            (convertToBytes(bitmap), tv_detail_id.getText().toString(), false);
                    machineDatabase.machineDao().insertMachineImageData(machineImages);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            showImagesFromId();
        }
    }

    // get image data from database by machine ID and display it into RecyclerView.
    private void showImagesFromId() {
        List<MachineImageDataModel> machineImageArrayList = machineDatabase.machineDao().getMachineImagesById(machineId);

        MachineImageAdapter machineImageAdapter = new MachineImageAdapter(machineImageArrayList, this, imageClickCallback);
        rv_machine_image.setHasFixedSize(true);
        rv_machine_image.setLayoutManager(new GridLayoutManager(this, 2));
        rv_machine_image.setAdapter(machineImageAdapter);
        machineImageAdapter.notifyDataSetChanged();
    }

    private static byte[] convertToBytes(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30 ,byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    // Get image byte and send it to another activity to be viewed fullscreen
    private ImageClickCallback imageClickCallback = new ImageClickCallback() {
        @Override
        public void onClickImage(MachineImageDataModel machineImageDataModel) {
            Intent intentImage = new Intent(MachineDataDetailActivity.this, ViewImageActivity.class);
            intentImage.putExtra(ViewImageActivity.VIEW_IMAGE_EXTRA, machineImageDataModel.getImages());
            startActivity(intentImage);
        }
    };
}