package io.prospace.submissions.imagemachine.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.prospace.submissions.imagemachine.interfaces.MachineClickCallback;
import io.prospace.submissions.imagemachine.datamodel.MachineDataModel;
import io.prospace.submissions.imagemachine.interfaces.MachineEditListener;
import io.prospace.submissions.imagemachine.R;
import io.prospace.submissions.imagemachine.activities.UpdateMachineDataActivity;

public class MachineDataAdapter extends RecyclerView.Adapter<MachineDataAdapter.MachineViewHolder> implements MachineEditListener {

    private List<MachineDataModel> machineDataModels;
    private Context context;

    private MachineClickCallback machineClickCallback;

    public MachineDataAdapter(List<MachineDataModel> machineDataModels, Context context,
                              MachineClickCallback machineClickCallback) {
        this.machineDataModels = machineDataModels;
        this.context = context;
        this.machineClickCallback = machineClickCallback;
    }


    @NonNull
    @Override
    public MachineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_machine_data, parent, false);
        return new MachineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MachineViewHolder holder, final int position) {

        final MachineDataModel dataModel = machineDataModels.get(position);

        holder.text_machine_name.setText(dataModel.getMachineName());
        holder.text_machine_type.setText(dataModel.getMachineType());
        holder.text_machine_maintenance_date.setText(dataModel.getMachineDate());

        holder.bind(dataModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                machineClickCallback.onClick(holder.machineDataModel);
            }
        });

        // Update item data and send the item ID data and item position to another activity.
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intentLongClick = new Intent(context, UpdateMachineDataActivity.class);
                intentLongClick.putExtra(UpdateMachineDataActivity.UPDATE_ID_EXTRA, dataModel.getMachineId());
                intentLongClick.putExtra(UpdateMachineDataActivity.UPDATE_POSITION_EXTRA, position);
                context.startActivity(intentLongClick);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return machineDataModels.size();
    }

    @Override
    public void onUpdate(int position, MachineDataModel machineDataModel) {
        machineDataModels.get(position).setMachineId(machineDataModel.getMachineId());
        machineDataModels.get(position).setMachineName(machineDataModel.getMachineName());
        machineDataModels.get(position).setMachineType(machineDataModel.getMachineType());
        machineDataModels.get(position).setMachineQrCodeNum(machineDataModel.getMachineQrCodeNum());
        machineDataModels.get(position).setMachineDate(machineDataModel.getMachineDate());

        notifyDataSetChanged();
    }

    public static class MachineViewHolder extends RecyclerView.ViewHolder {

        private TextView text_machine_name, text_machine_type, text_machine_maintenance_date;
        private MachineDataModel machineDataModel;

        public MachineViewHolder(@NonNull final View itemView) {
            super(itemView);

            text_machine_name = itemView.findViewById(R.id.textMachineName);
            text_machine_type = itemView.findViewById(R.id.textMachineType);
            text_machine_maintenance_date = itemView.findViewById(R.id.textMachineMaintenanceDate);
        }

        void bind(MachineDataModel machineDataModel) {
            this.machineDataModel = machineDataModel;
        }
    }
}
