package io.prospace.submissions.imagemachine;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Comparator;

@Entity(tableName = "machines")
public class MachineDataModel {

    @NonNull
    @PrimaryKey
    public String machineId;

    @ColumnInfo(name = "machine_name")
    public String machineName;

    @ColumnInfo(name = "machine_type")
    public String machineType;

    @ColumnInfo(name = "machine_qr_code")
    public String machineQrCodeNum;

    @ColumnInfo(name = "machine_date")
    public String machineDate;

    public MachineDataModel(@NonNull String machineId, String machineName, String machineType,
                            String machineQrCodeNum, String machineDate) {
        this.machineId = machineId;
        this.machineName = machineName;
        this.machineType = machineType;
        this.machineQrCodeNum = machineQrCodeNum;
        this.machineDate = machineDate;
    }

    @NonNull
    public String getMachineId() {
        return machineId;
    }

    public String getMachineName() {
        return machineName;
    }

    public String getMachineType() {
        return machineType;
    }

    public String getMachineQrCodeNum() {
        return machineQrCodeNum;
    }

    public String getMachineDate() {
        return machineDate;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public void setMachineQrCodeNum(String machineQrCodeNum) {
        this.machineQrCodeNum = machineQrCodeNum;
    }

    public void setMachineDate(String machineDate) {
        this.machineDate = machineDate;
    }

    public static final Comparator<MachineDataModel> sortByName = new Comparator<MachineDataModel>() {
        @Override
        public int compare(MachineDataModel dm1, MachineDataModel dm2) {
            return dm1.getMachineName().compareTo(dm2.getMachineName());
        }
    };

    public static final Comparator<MachineDataModel> sortByType = new Comparator<MachineDataModel>() {
        @Override
        public int compare(MachineDataModel dm1, MachineDataModel dm2) {
            return dm1.getMachineType().compareTo(dm2.getMachineType());
        }
    };
}
