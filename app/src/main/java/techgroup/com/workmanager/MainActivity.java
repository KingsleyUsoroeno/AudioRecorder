package techgroup.com.workmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // So here am Simply saying that our PeriodicWorkRequest should execute
        // After a Time Interval of 15 Minutes
        final PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                Notification.class,15,TimeUnit.MINUTES
        ).build();

        // An Example of creating a WorkManager Constraints
        Constraints workConstraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .setRequiresBatteryNotLow(true)
                .setRequiresBatteryNotLow(false)
                .build();
        // So our WorkRequest that is entangled to these Constraints will only be
        // executed only if these Conditions are met

        // Create a WorkManagerSequence
//        WorkManager.getInstance()
//                .beginWith(simpleRequest)
//                .then(simpleRequest)
//                .enqueue();

        // Sending Data to our WorkRequest Object
        Data dataSource = new Data.Builder()
                .putString("title","Zac Efron")
                .putString("message","We are your Friends")
                .build();
        // Create a Data to get Data back from our WorkManager
        Data refreshTime = new Data.Builder()
                .putString("refreshTime","Current time is " + System.currentTimeMillis())
                .build();


        // Create a oneTimeWorkRequest
        final OneTimeWorkRequest simpleRequest = new OneTimeWorkRequest.Builder(Notification.class)
                .setInputData(dataSource)
                .build();


        Button btn_startRequest = findViewById(R.id.simpleWorkButton);
        btn_startRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               WorkManager.getInstance().enqueue(simpleRequest);
            }
        });
    }
}
