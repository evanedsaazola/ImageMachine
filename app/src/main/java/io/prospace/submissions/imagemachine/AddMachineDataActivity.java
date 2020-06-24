package io.prospace.submissions.imagemachine;

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
import java.util.Random;

public class AddMachineDataActivity extends AppCompatActivity implements View.OnClickListener {

    private MachineDatabase machineDatabase;

    private EditText et_add_id, et_add_name, et_add_type, et_add_qr_code;
    private TextView tv_add_date;
    private Button btn_add_data, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_machine_data);

        initializeViews();
        initializeDatabase();

        tv_add_date.setOnClickListener(this);
        btn_add_data.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        et_add_id.setText(generateMachineId(8));
        et_add_id.setEnabled(false);
    }

    private void initializeViews() {
        et_add_id = findViewById(R.id.etAddDataId);
        et_add_name = findViewById(R.id.etAddDataName);
        et_add_type = findViewById(R.id.etAddDataType);
        et_add_qr_code = findViewById(R.id.etAddDataQrCodeNum);
        tv_add_date = findViewById(R.id.tvAddDate);
        btn_add_data = findViewById(R.id.btnAddData);
        btn_back = findViewById(R.id.btnBack);
    }

    public static String generateMachineId(int idLength) {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder idResult = new StringBuilder();

        while (idLength > 0) {
            Random random = new Random();
            idResult.append(characters.charAt(random.nextInt(characters.length())));
            idLength--;
        }
        return idResult.toString();
    }

    private void initializeDatabase() {
        machineDatabase = Room.databaseBuilder(this, MachineDatabase.class, "machinesdb")
                .allowMainThreadQueries()
                .build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;

            case R.id.tvAddDate:
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog dateDialog = new DatePickerDialog(AddMachineDataActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        tv_add_date.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                    }
                }, year, month, day);

                dateDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dateDialog.show();
                break;

            case R.id.btnAddData:
                if (et_add_id.getText().toString().isEmpty() || et_add_name.getText().toString().isEmpty() ||
                        et_add_type.getText().toString().isEmpty() || et_add_qr_code.getText().toString().isEmpty() ||
                        tv_add_date == null) {
                    Toast.makeText(this, "Please input your data correctly!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String textId = et_add_id.getText().toString().trim();
                    String textName = et_add_name.getText().toString().trim();
                    String textType = et_add_type.getText().toString().trim();
                    String textQrCode = et_add_qr_code.getText().toString().trim();
                    String textDate = tv_add_date.getText().toString();

                    MachineDataModel machineModel = new MachineDataModel(textId, textName, textType, textQrCode, textDate);

                    machineDatabase.machineDao().insertMachineData(machineModel);
                    Toast.makeText(this, "Your data have been inputted!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddMachineDataActivity.this, MachineDataActivity.class));
                    Log.d("TAG", "" + machineModel);
                }
                break;
        }
    }
}