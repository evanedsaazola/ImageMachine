package io.prospace.submissions.imagemachine;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MachineDataModel.class}, exportSchema = false, version = 1)
public abstract class MachineDatabase extends RoomDatabase {

    public abstract MachineDao machineDao();

}
