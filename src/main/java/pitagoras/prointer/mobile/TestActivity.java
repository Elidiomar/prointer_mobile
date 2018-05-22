package pitagoras.prointer.mobile;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.Task;

public class TestActivity extends BaseDemoActivity {
    private static final String TAG = "TestActivity";

    @Override
    protected void onDriveClientReady() {
        /*pickTextFile()
                .addOnSuccessListener(this,
                        driveId -> editMetadata(driveId.asDriveFile()))
                .addOnFailureListener(this, e -> {
                    Log.e(TAG, "No file selected", e);
                    showMessage(getString(R.string.file_not_selected));
                    finish();
                });*/
    }
    private void editMetadata(DriveFile file) {

        Log.e(TAG, "Id: " + file.getDriveId().encodeToString());
        Toast.makeText(getBaseContext(),file.getDriveId().encodeToString(),Toast.LENGTH_SHORT).show();

        /*
        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                .setStarred(true)
                .setIndexableText("Description about the file")
                .setTitle("A new title")
                .build();
        Task<Metadata> updateMetadataTask =
                getDriveResourceClient().updateMetadata(file, changeSet);
        updateMetadataTask
                .addOnSuccessListener(this,
                        metadata -> {
                            showMessage(getString(R.string.metadata_updated));
                            finish();
                        })
                .addOnFailureListener(this, e -> {
                    Log.e(TAG, "Unable to update metadata", e);
                    showMessage(getString(R.string.update_failed));
                    finish();
                });*/
    }
}