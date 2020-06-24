package io.prospace.submissions.imagemachine.datamodel;


import java.io.Serializable;

public class MachineImageDataModel implements Serializable {

    private byte[] images;
    private boolean isChecked;


    public MachineImageDataModel(int imagesId, String imagesName, byte[] images, boolean isChecked) {
        this.images = images;
        this.isChecked = isChecked;
    }

    public byte[] getImages() {
        return images;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
