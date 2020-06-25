package io.prospace.submissions.imagemachine.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import io.prospace.submissions.imagemachine.R;
import io.prospace.submissions.imagemachine.databases.MachineDatabase;
import io.prospace.submissions.imagemachine.datamodel.MachineDataModel;
import io.prospace.submissions.imagemachine.interfaces.MachineEditListener;

public class UpdateMachineDataActivity extends AppCompatActivity implements View.OnClickListener {

    private MachineDatabase machineDatabase;
    private MachineEditListener machineEditListener;

    private EditText et_update_id, et_update_name, et_update_type, et_update_qr_code;
    private TextView tv_update_date;
    private Button btn_update_data, btn_back;

    public static final String UPDATE_ID_EXTRA = "update_id";
    public static final String UPDATE_POSITION_EXTRA = "update_position";
    private Integer machineUpdatePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_machine_data);

        initializeViews();
        initializeDatabase();

        tv_update_date.setOnClickListener(this);
        btn_update_data.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        String machineUpdateId = getIntent().getStringExtra(UPDATE_ID_EXTRA);
        machineUpdatePosition = getIntent().getIntExtra(UPDATE_POSITION_EXTRA, 0);

        Log.d("DataCheck", "CheckId>>" + machineUpdateId);
        Log.d("DataCheck", "CheckPosition>>" + machineUpdatePosition);

        et_update_id.setText(machineUpdateId);
        et_update_id.setEnabled(false);
    }

    private void initializeDatabase() {
        machineDatabase = Room.databaseBuilder(this, MachineDatabase.class, "machinesdb")
                .allowMainThreadQueries()
                .build();
    }

    private void initializeViews() {
        et_update_id = findViewById(R.id.etUpdateDataId);
        et_update_name = findViewById(R.id.etUpdateDataName);
        et_update_type = findViewById(R.id.etUpdateDataType);
        et_update_qr_code = findViewById(R.id.etUpdateDataQrCodeNum);
        tv_update_date = findViewById(R.id.tvUpdateDate);
        btn_update_data = findViewById(R.id.btnUpdateData);
        btn_back = findViewById(R.id.btnBack);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;

            case R.id.tvUpdateDate:
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog dateUpdateDialog = new DatePickerDialog(UpdateMachineDataActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        tv_update_date.setText(getString(R.string.text_date_dialog, dayOfMonth, monthOfYear+1, year));
                    }
                }, year, month, day);

                dateUpdateDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dateUpdateDialog.show();
                break;

            case R.id.btnUpdateData:
                /*
                 Check all of data input views. If there is one input views empty, Toast will be shown.

                 If there isn;t any input view that is empty, the DB will update the data with the help of update DAO
                 and send it to another activity to be shown.
                 */
                if (et_update_id.getText().toString().isEmpty() || et_update_name.getText().toString().isEmpty() ||
                        et_update_type.getText().toString().isEmpty() || et_update_qr_code.getText().toString().isEmpty() ||
                        tv_update_date == null) {
                    Toast.makeText(this, "Please input your data correctly!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String updateTextId = et_update_id.getText().toString().trim();
                    String updateTextName = et_update_name.getText().toString().trim();
                    String updateTextType = et_update_type.getText().toString().trim();
                    String updateTextQrCode = et_update_qr_code.getText().toString().trim();
                    String updateTextDate = tv_update_date.getText().toString();

                    MachineDataModel machineModel = new MachineDataModel(updateTextId, updateTextName,
                            updateTextType, updateTextQrCode, updateTextDate);

                    machineDatabase.machineDao().updateMachineData(machineModel);
                    machineEditListener.onUpdate(machineUpdatePosition, machineModel);
                    Toast.makeText(this, "Your data have been updated!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateMachineDataActivity.this, MachineDataActivity.class));
                }
                break;
        }
    }
}