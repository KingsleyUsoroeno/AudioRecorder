package techgroup.com.workmanager;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NoteRecorder {
    private MediaRecorder recorder;
    private static final String TAG = "NoteRecorder";
    private String filePath;
    private Context context;

    public NoteRecorder(Context context) {
        // Every time these Class is instantiated a New MediaRecorder will be created
        this.context = context;
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
    }

    public String recordUserTask(Date recordedDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:a");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(recordedDate);
            Date calendarTime = calendar.getTime();
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + sdf.format(calendarTime) + " note_rec.3gp";
            recorder.setOutputFile(filePath);
            recorder.prepare();
            recorder.start();
            showToast(context, "Recording Has Started");
        } catch (IOException e) {
            Log.d(TAG, "recordUserTask Exception : " + e.getMessage());
        }
        return filePath;
    }

    public void stopRecordingUserTask() {
        recorder.stop();
        recorder.release();
        recorder = null;
        showToast(context, "Recording Stopped");
    }

    public void playUserRecording() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
            showToast(context, "Playing");
        } catch (IOException e) {
            Log.d(TAG, "playUserRecording: Exception " + e.getMessage());
        }
    }

    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
