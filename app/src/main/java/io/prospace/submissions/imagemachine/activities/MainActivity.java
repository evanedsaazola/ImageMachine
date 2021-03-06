package io.prospace.submissions.imagemachine.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.prospace.submissions.imagemachine.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_machine_data = findViewById(R.id.btnMainMachineData);
        Button btn_code_reader = findViewById(R.id.btnMainCodeReader);

        btn_machine_data.setOnClickListener(this);
        btn_code_reader.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnMainMachineData:
                Intent intentMachineData = new Intent(MainActivity.this, MachineDataActivity.class);
                startActivity(intentMachineData);
                break;

            case R.id.btnMainCodeReader:
                Intent intentCodeReader = new Intent(MainActivity.this, CodeReaderActivity.class);
                startActivity(intentCodeReader);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}