package pitagoras.prointer.mobile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HistoryActivity extends BaseDemoActivity {

    private static final String TAG = "DownloadProgress";
    private ExecutorService mExecutorService;
    private ProgressBar mProgressBar;
    private TextView mDownloadInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mExecutorService = Executors.newSingleThreadExecutor();
    }

    @Override
    protected void onDriveClientReady() {
        retrieveContents(DriveId.decodeFromString(Util.DRIVE_ID).asDriveFile());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExecutorService.shutdown();
    }

    private void retrieveContents(DriveFile file) {

        final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
        Task<Metadata> getMetadataTask = getDriveResourceClient().getMetadata(file);
        getMetadataTask
                .addOnSuccessListener(this,
                        new OnSuccessListener<Metadata>() {
                            @Override
                            public void onSuccess(Metadata metadata) {
                                ((TextView) findViewById(R.id.txt_title_meta)).setText(metadata.getTitle());
                                ((TextView) findViewById(R.id.txt_date_create_meta)).setText(dateFormat.format(metadata.getCreatedDate()));
                                ((TextView) findViewById(R.id.txt_date_edit_meta)).setText(dateFormat.format(metadata.getModifiedDate()));
                                ((TextView) findViewById(R.id.txt_size_meta)).setText((metadata.getFileSize() + ""));
                                ((TextView) findViewById(R.id.txt_type_meta)).setText((metadata.getFileExtension()));
                            }
                        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Unable to retrieve metadata", e);
                        showMessage(getString(R.string.read_failed));
                    }
                });
    }
}
