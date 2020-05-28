package c346.rp.edu.sg.taskmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnAddTask;
    ListView lvTask;
    TaskAdapter ta;
    ArrayList<Task> tasks;
    int activityCode = 0;
    AlarmManager am;
    int reqCode = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddTask = findViewById(R.id.btnAddTask);
        DBHelper dbh = new DBHelper(this);

        lvTask = findViewById(R.id.lvTask);

        tasks = dbh.getTask();
        ta = new TaskAdapter(this, R.layout.row, tasks);
        lvTask.setAdapter(ta);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(i, activityCode);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(data != null) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                tasks = dbh.getTask();

                ta = new TaskAdapter(MainActivity.this, R.layout.row, tasks);
                lvTask.setAdapter(ta);

                int returnCode = data.getIntExtra("returnCode", 0);
                int remindTime = data.getIntExtra("remindTime", 0);
                String taskName = data.getStringExtra("taskName");
                String taskDescr = data.getStringExtra("taskDescr");
                if (returnCode == 1){
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.SECOND, remindTime);

                    Intent intent = new Intent(MainActivity.this,
                            TaskNotificationReceiver.class);

                    intent.putExtra("taskName", taskName);
                    intent.putExtra("taskDescr", taskDescr);

                    PendingIntent pendingIntent =
                            PendingIntent.getBroadcast(MainActivity.this,
                                    reqCode, intent,
                                    PendingIntent.FLAG_CANCEL_CURRENT);

                    am = (AlarmManager)
                            getSystemService(Activity.ALARM_SERVICE);

                    am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                            pendingIntent);
                }
            }

        }
    }
}
