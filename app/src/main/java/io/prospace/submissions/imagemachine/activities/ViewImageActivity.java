package io.prospace.submissions.imagemachine.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import io.prospace.submissions.imagemachine.R;

public class ViewImageActivity extends AppCompatActivity {

    public static final String VIEW_IMAGE_EXTRA = "view_image_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        ImageView iv_view_image = findViewById(R.id.ivViewImage);

        // Retrieve image byte from another activity and display the image.
        byte[] images = getIntent().getByteArrayExtra(VIEW_IMAGE_EXTRA);

        assert images != null;
        Bitmap bitmapImages = BitmapFactory.decodeByteArray(images, 0, images.length);

        Glide.with(ViewImageActivity.this)
                .asBitmap()
                .load(bitmapImages)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_placeholder)
                .into(iv_view_image);
    }
}