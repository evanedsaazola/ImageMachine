package io.prospace.submissions.imagemachine.databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.prospace.submissions.imagemachine.datamodel.MachineDataModel;
import io.prospace.submissions.imagemachine.datamodel.MachineImageDataModel;

@Dao
public interface MachineDao {

    @Query("SELECT * FROM machines")
    List<MachineDataModel> getMachineDataList();

    @Query("SELECT * FROM machines WHERE machineId = :id")
    List<MachineDataModel> getMachineDataById(String id);

    @Query("SELECT * FROM machines WHERE machine_qr_code = :qrCode")
    List<MachineDataModel> getMachinesDataByQrCode(String qrCode);

    @Query("SELECT * FROM machineimages WHERE images_name = :id")
    List<MachineImageDataModel> getMachineImagesById(String id);

    @Insert
    void insertMachineData(MachineDataModel machineDataModel);

    @Insert
    void insertMachineImageData(MachineImageDataModel machineImageDataModel);

    @Update
    void updateMachineData(MachineDataModel machineDataModel);

    @Query("DELETE FROM machines WHERE machineId = :id")
    void deleteMachineDataById(String id);

    @Query("DELETE FROM machineimages WHERE imagesId = :id")
    void deleteMachineImageDataById(String id);

}
