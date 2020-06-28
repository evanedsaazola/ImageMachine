package io.prospace.submissions.imagemachine.datamodel;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "machineimages")
public class MachineImageDataModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int imagesId;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] images;

    @ColumnInfo(name = "images_name")
    private String imagesName;

    @ColumnInfo(name = "image_checker")
    private boolean isChecked;

    public MachineImageDataModel(byte[] images, String imagesName, boolean isChecked) {
        this.images = images;
        this.imagesName = imagesName;
        this.isChecked = isChecked;
    }

    public int getImagesId() {
        return imagesId;
    }

    public byte[] getImages() {
        return images;
    }

    public String getImagesName() {
        return imagesName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setImagesId(int imagesId) {
        this.imagesId = imagesId;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
