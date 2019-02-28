package techgroup.com.workmanager;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Date;

public class NoteRecordingActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_start, btn_stop, btn_play;
    private NoteRecorder noteRecorder;
    private String[] permissions = {"android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE"};
    public static final int REQUEST_CODE = 200;
    private static final String TAG = "NoteRecordingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_recording);
        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);
        btn_play = findViewById(R.id.btn_play);
        noteRecorder = new NoteRecorder(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, REQUEST_CODE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                String filePath = noteRecorder.recordUserTask(new Date());
                Log.d(TAG, "recordUserTask: " + filePath);
                btn_start.setEnabled(false);
                btn_stop.setEnabled(true);
                break;

            case R.id.btn_stop:
                noteRecorder.stopRecordingUserTask();
                btn_stop.setEnabled(false);
                btn_play.setEnabled(true);
                break;


            case R.id.btn_play:
                noteRecorder.playUserRecording();
                btn_stop.setEnabled(true);
                break;

            default:
                return;

        }
    }

    private void initView() {
        btn_play.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_start.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                boolean permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean permissionToWriteAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (permissionToWriteAccepted && permissionToRecordAccepted) {
                    initView();
                } else {
                    noteRecorder.showToast(this, "Permissions Denied");
                    if (!permissionToRecordAccepted) NoteRecordingActivity.super.finish();
                    if (!permissionToWriteAccepted) NoteRecordingActivity.super.finish();
                }
        }
    }
}
