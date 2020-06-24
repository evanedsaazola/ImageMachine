package io.prospace.submissions.imagemachine.interfaces;

import io.prospace.submissions.imagemachine.datamodel.MachineDataModel;

public interface MachineEditListener {

    void onUpdate(int position, MachineDataModel machineDataModel);

}
