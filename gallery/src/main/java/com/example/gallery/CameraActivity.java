package com.example.gallery;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.service.voice.VoiceInteractionSession;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {
    private File file = null;
    private String currentPhotoPath;
    private String imageFileName;
    private final int REQUEST_TAKE_PHOTO = 1;
    private MediaScannerConnection mediaScannerConnection = null;
    private MediaScannerConnection.MediaScannerConnectionClient mClient = null;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        try{ capture(this);}
        catch (IOException e) { finish();}
    }

    final class CustomMediaScannerConnectionClient implements MediaScannerConnection.MediaScannerConnectionClient{
        private String mFilename;
        private String mMimetype;
        private MediaScannerConnection mediaScanner;

        public CustomMediaScannerConnectionClient(Context content, File file, String mimetype){
            this.mFilename = currentPhotoPath;
            mediaScanner = new MediaScannerConnection(content, this);
            mediaScanner.connect();
        }

        @Override
        public void onMediaScannerConnected() {
            mediaScanner.scanFile(mFilename, mMimetype);
        }

        @Override
        public void onScanCompleted(String s, Uri uri) {
            mediaScanner.disconnect();
        }
    }

    private void capture(Context content) throws IOException {
        File capturedImage = new File(getExternalCacheDir(), "Captured_Photo.bmp");
        currentPhotoPath = capturedImage.getAbsolutePath();
        if(capturedImage.exists()){
            capturedImage.delete();
        }
        capturedImage.createNewFile();
        uri = FileProvider.getUriForFile(this, "com.example.app.provider", capturedImage);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            file = createImageFile();
        } catch (IOException e){
            Toast.makeText(content, "오류가 있습니다", Toast.LENGTH_SHORT).show();
            return ;
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        System.out.println("1");

        //noinspection deprecation
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    private File createImageFile() throws IOException {
        File sdcard = Environment.getExternalStorageDirectory();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "JPEG_" + timeStamp + ".jpg";

        File image = new File(sdcard, imageFileName);

        currentPhotoPath = image.getAbsolutePath();

        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUEST_TAKE_PHOTO:
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    Matrix matrix = new Matrix();

                    matrix.postRotate(90);

                    Bitmap rotatedBitmap;
                    rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                    saveFile(rotatedBitmap);

                    finish();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

        }
    }

    private void saveFile(Bitmap bitmap){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, imageFileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");

        values.put(MediaStore.Images.Media.IS_PENDING, 1);

        ContentResolver contentResolver = getContentResolver();
        Uri item = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        try{
            System.out.println("================================================");
            System.out.println("저장 시작");
            ParcelFileDescriptor pdf = contentResolver.openFileDescriptor(item, "w", null);

            if(pdf == null){
                Log.d("Capture", "null");
            } else {
                FileOutputStream fos = new FileOutputStream(pdf.getFileDescriptor());

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                values.clear();
                values.put(MediaStore.Images.Media.IS_PENDING, 0);
                contentResolver.update(item, values, null, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}