package pitagoras.prointer.mobile;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.OpenFileCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadProgressActivity extends BaseDemoActivity {

    private static final String TAG = "DownloadProgress";
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

        ((ImageView) findViewById(R.id.img_progress)).setImageResource(R.drawable.ic_get_app_black);
    }

    @Override
    protected void onDriveClientReady() {
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        retrieveContents(DriveId.decodeFromString(Util.DRIVE_ID).asDriveFile());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExecutorService.shutdown();
    }

    private void retrieveContents(DriveFile file) {

        OpenFileCallback openCallback = new OpenFileCallback() {
            @Override
            public void onProgress(long bytesDownloaded, long bytesExpected) {

                int progress = (int) (bytesDownloaded * 100 / bytesExpected);
                Log.d(TAG, String.format("Loading progress: %d percent", progress));
                mProgressBar.setProgress(progress);
                mDownloadInfo.setText((progress + " %"));
            }

            @Override
            public void onContents(@NonNull DriveContents driveContents) {
                mProgressBar.setProgress(100);
                mDownloadInfo.setText("Concluido!");


                    try {
                        File file = Util.getFile(getBaseContext());

                        OutputStream outStream = new FileOutputStream(file);
                        outStream.write(getBytes(driveContents.getInputStream()));

                    getDriveResourceClient().discardContents(driveContents);

                        openFile(file);

                    } catch (IOException e) {
                        onError(e);
                    }

                }

            @Override
            public void onError(@NonNull Exception e) {
                Log.e(TAG, "Unable to read contents", e);
                showMessage(getString(R.string.read_failed));
                finish();

            }
        };

        getDriveResourceClient().openFile(file, DriveFile.MODE_READ_ONLY, openCallback);

    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public void openFile (File file){

        mDownloadInfo.setText("Abrindo arquivo...");

        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), "application/msword");
        Intent intent = Intent.createChooser(target, getString(R.string.message_open_file));
        try {
            startActivity(intent);
            finish();
        } catch (ActivityNotFoundException e) {
            showMessage(getString(R.string.install_word));
        }

        mProgressBar.setVisibility(ProgressBar.GONE);
    }
}
