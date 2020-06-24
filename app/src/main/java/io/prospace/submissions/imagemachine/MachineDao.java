package io.prospace.submissions.imagemachine;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MachineDao {

    @Query("SELECT * FROM machines")
    List<MachineDataModel> getMachineDataList();

    @Query("SELECT * FROM machines WHERE machineId = :id")
    List<MachineDataModel> getMachineDataById(String id);

    @Query("SELECT * FROM machines WHERE machine_qr_code = :qrCode")
    List<MachineDataModel> getMachinesDataByQrCode(String qrCode);

    @Insert
    void insertMachineData(MachineDataModel machineDataModel);

    @Update
    void updateMachineData(MachineDataModel machineDataModel);

    @Query("DELETE FROM machines WHERE machineId = :id")
    void deleteMachineDataById(String id);

}
