package io.prospace.submissions.imagemachine.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import io.prospace.submissions.imagemachine.interfaces.ImageClickCallback;
import io.prospace.submissions.imagemachine.datamodel.MachineImageDataModel;
import io.prospace.submissions.imagemachine.R;

public class MachineImageAdapter extends RecyclerView.Adapter<MachineImageAdapter.MachineDetailViewHolder> {

    private ArrayList<MachineImageDataModel> machineDataDetailModels;
    private Context context;
    private ImageClickCallback imageClickCallback;

    private static final int IMAGE_LIMIT = 10;

    public MachineImageAdapter(ArrayList<MachineImageDataModel> machineDataDetailModels, Context context,
                               ImageClickCallback imageClickCallback) {
        this.machineDataDetailModels = machineDataDetailModels;
        this.context = context;
        this.imageClickCallback = imageClickCallback;
    }

    @NonNull
    @Override
    public MachineDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_machine_image, parent, false);
        return new MachineDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MachineDetailViewHolder holder, int position) {

        final MachineImageDataModel dataModel = machineDataDetailModels.get(position);

        final byte[] machineImages = dataModel.getImages();
        Bitmap bitmapImages = BitmapFactory.decodeByteArray(machineImages, 0, machineImages.length);

        Glide.with(context)
                .asBitmap()
                .load(bitmapImages)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_placeholder)
                .into(holder.img_machine_detail_picture);

        holder.bind(dataModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageClickCallback.onClickImage(holder.machineImageDataModel);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dataModel.setChecked(!dataModel.isChecked());

                if (dataModel.isChecked()) {
                    holder.img_machine_detail_picture.setColorFilter(ContextCompat.getColor
                            (context, R.color.colorBlackOverlay), PorterDuff.Mode.SRC_OVER);
                }
                else {
                    dataModel.setChecked(dataModel.isChecked());
                    holder.img_machine_detail_picture.clearColorFilter();
                }

//                imageClickCallback.onLongClickImage(holder.machineImageDataModel);

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        /*
        Limit the size of DataModel into 10. If it above 10, then only item below 10 that will be
        displayed
         */
        return Math.min(machineDataDetailModels.size(), IMAGE_LIMIT);
    }

    public static class MachineDetailViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_machine_detail_picture;
        private MachineImageDataModel machineImageDataModel;

        public MachineDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            img_machine_detail_picture = itemView.findViewById(R.id.imgMachineDetailPicture);
        }

        void  bind(MachineImageDataModel machineImageDataModel) {
            this.machineImageDataModel = machineImageDataModel;
        }
    }

//    public ArrayList<MachineImageDataModel> getImageSelected() {
//        ArrayList<MachineImageDataModel> imageSelected = new ArrayList<>();
//        for (int i = 0; i < machineDataDetailModels.size(); i++) {
//            if (machineDataDetailModels.get(i).isChecked()) {
//                imageSelected.add(machineDataDetailModels.get(i));
//            }
//        }
//        return imageSelected;
//    }
}
