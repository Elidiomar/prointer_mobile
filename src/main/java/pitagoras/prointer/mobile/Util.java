package pitagoras.prointer.mobile;

import android.content.Context;

import java.io.File;

public class Util {
    public static final String DRIVE_ID ="DriveId:CAESITFUa3BZNjZNN1ctQ2h3dWlvdU9iYy1aeTJ5dHBUUTlHRBiGyQEg3sKxsulQKAA=";

    public static File getFile(Context context) {
        return new File(context.getExternalFilesDir("download")+"/Test_Prointer.docx");
    }
}
