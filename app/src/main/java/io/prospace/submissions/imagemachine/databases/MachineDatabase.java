package io.prospace.submissions.imagemachine.databases;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import io.prospace.submissions.imagemachine.datamodel.MachineDataModel;

@Database(entities = {MachineDataModel.class}, exportSchema = false, version = 1)
public abstract class MachineDatabase extends RoomDatabase {

    public abstract MachineDao machineDao();

}
