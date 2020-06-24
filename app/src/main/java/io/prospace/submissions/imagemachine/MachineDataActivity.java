package io.prospace.submissions.imagemachine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MachineDataActivity extends AppCompatActivity implements View.OnClickListener {

    private MachineDataAdapter machineAdapter;
    private List<MachineDataModel> machineDataArrayList = new ArrayList<>();

    private RecyclerView rv_machine_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_data);

        rv_machine_list = findViewById(R.id.rvMachineDataList);
        Toolbar toolbar_machine_data = findViewById(R.id.toolbarMachineData);
        Button btn_add_machine_data = findViewById(R.id.btnMachineDataAddData);
        Button btn_back = findViewById(R.id.btnBack);

        setSupportActionBar(toolbar_machine_data);
        btn_add_machine_data.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        initializeDatabase();
        showData();
    }

    private void initializeDatabase() {
        MachineDatabase machineDatabase = Room.databaseBuilder(this, MachineDatabase.class, "machinesdb")
                .allowMainThreadQueries()
                .build();
        machineDataArrayList = machineDatabase.machineDao().getMachineDataList();
    }

    private void showData() {
        machineAdapter = new MachineDataAdapter(machineDataArrayList, this, machineClickCallback);
        Collections.sort(machineDataArrayList, MachineDataModel.sortByName);
        rv_machine_list.setHasFixedSize(true);
        rv_machine_list.setLayoutManager(new LinearLayoutManager(this));
        rv_machine_list.setAdapter(machineAdapter);
    }

    private MachineClickCallback machineClickCallback = new MachineClickCallback() {
        @Override
        public void onClick(MachineDataModel machineDataModel) {
            Intent intentClick = new Intent(MachineDataActivity.this, MachineDataDetailActivity.class);
            intentClick.putExtra(MachineDataDetailActivity.MACHINE_ID_EXTRA, machineDataModel.getMachineId());
            startActivity(intentClick);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.sort_item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_sort_by_name:
                Toast.makeText(this, "List sort by name", Toast.LENGTH_SHORT).show();
                Collections.sort(machineDataArrayList, MachineDataModel.sortByName);
                machineAdapter.notifyDataSetChanged();
                break;

            case R.id.action_sort_by_type:
                Toast.makeText(this, "List sort by type", Toast.LENGTH_SHORT).show();
                Collections.sort(machineDataArrayList, MachineDataModel.sortByType);
                machineAdapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnMachineDataAddData) {
            Intent intent = new Intent(MachineDataActivity.this, AddMachineDataActivity.class);
            startActivity(intent);
        }
        else if (view.getId() == R.id.btnBack) {
            finish();
        }
    }
}