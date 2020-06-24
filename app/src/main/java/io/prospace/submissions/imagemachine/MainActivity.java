package io.prospace.submissions.imagemachine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
}