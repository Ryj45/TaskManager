package c346.rp.edu.sg.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    EditText etName, etDescr, etTime;
    Button btnAdd, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etName = findViewById(R.id.etName);
        etDescr = findViewById(R.id.etDescr);
        etTime = findViewById(R.id.etTime);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String description = etDescr.getText().toString();
                int remindTime = Integer.parseInt(etTime.getText().toString());

                DBHelper dbh = new DBHelper(AddTaskActivity.this);
                long insert_id = dbh.insert(name, description, remindTime);
                if(insert_id != 1){
                    Toast.makeText(AddTaskActivity.this, "Inserted", Toast.LENGTH_LONG).show();
                }

                dbh.close();

                Intent i = new Intent();
                i.putExtra("returnCode", 1);
                i.putExtra("remindTime", Integer.valueOf(etTime.getText().toString()));
                i.putExtra("taskName", name);
                i.putExtra("taskDescr", description);
                Log.d("return Code", "return code is 1");
                setResult(RESULT_OK, i);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("returnCode", 0);
                setResult(RESULT_OK, i);
                finish();
            }
        });

    }
}
