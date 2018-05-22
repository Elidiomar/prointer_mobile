package pitagoras.prointer.mobile;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SaveProgressActivity extends BaseDemoActivity {

    private static final String TAG = "SaveProgress";
    private ExecutorService mExecutorService;
    private ProgressBar mProgressBar;
    private TextView mDownloadInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        mExecutorService = Executors.newSingleThreadExecutor();

        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setMax(100);

        mDownloadInfo = findViewById(R.id.download_progress);
        mDownloadInfo.setText("0%");

        ((ImageView) findViewById(R.id.img_progress)).setImageResource(R.drawable.ic_file_upload_black);
    }

    @Override
    protected void onDriveClientReady() {
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        appendContents(DriveId.decodeFromString(Util.DRIVE_ID).asDriveFile());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExecutorService.shutdown();
    }

    private void appendContents(DriveFile file) {

        final File fileUpload = Util.getFile(getBaseContext());

        Task<DriveContents> openTask = getDriveResourceClient().openFile(file, DriveFile.MODE_READ_WRITE);

        openTask.continueWithTask(new Continuation<DriveContents, Task<Void>>() {
            @Override
            public Task<Void> then(@NonNull Task<DriveContents> task) throws Exception {
                DriveContents driveContents = task.getResult();
                ParcelFileDescriptor pfd = driveContents.getParcelFileDescriptor();
                long bytesToSkip = pfd.getStatSize();
                try (InputStream in = new FileInputStream(pfd.getFileDescriptor())) {

                    while (bytesToSkip > 0) {
                        long skipped = in.skip(bytesToSkip);
                        bytesToSkip -= skipped;
                    }
                }
                try (OutputStream out = new FileOutputStream(pfd.getFileDescriptor())) {
                    out.write(read(fileUpload));
                }

                MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                        .setStarred(true)
                        .setLastViewedByMeDate(new Date())
                        .build();
                Task<Void> commitTask = getDriveResourceClient().commitContents(driveContents, changeSet);

                return commitTask;
            }
        })
                .addOnSuccessListener(this,
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                showMessage(getString(R.string.content_updated));
                                fileUpload.delete();
                                finish();
                            }
                        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Unable to update contents", e);
                        showMessage(getString(R.string.content_update_failed));
                        finish();
                    }
                });
    }

    public byte[] read(File file){

            int size = (int) file.length();
            byte bytes[] = new byte[size];
            byte tmpBuff[] = new byte[size];

            try {
                FileInputStream fis = new FileInputStream(file);
                int read = fis.read(bytes, 0, size);
                if (read < size) {
                    int remain = size - read;
                    while (remain > 0) {
                        read = fis.read(tmpBuff, 0, remain);
                        System.arraycopy(tmpBuff, 0, bytes, size - remain, read);
                        remain -= read;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        return bytes;
    }
}
