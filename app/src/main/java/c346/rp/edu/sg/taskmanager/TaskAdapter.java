package c346.rp.edu.sg.taskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TaskAdapter extends ArrayAdapter<Task> {
    Context context;
    ArrayList<Task> tasks;
    TextView tvId, tvName, tvDescription;
    int resource;

    public TaskAdapter(Context context, int resource, ArrayList<Task> tasks){
        super(context, resource, tasks);
        this.context = context;
        this.resource = resource;
        this.tasks = tasks;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(resource, parent, false);

        tvId = rowView.findViewById(R.id.tvId);
        tvName = rowView.findViewById(R.id.tvName);
        tvDescription = rowView.findViewById(R.id.tvDescription);

        Task task = tasks.get(position);
        tvId.setText(String.valueOf(task.getId()));
        tvName.setText(task.getName());
        tvDescription.setText(task.getDescription());

        return rowView;
    }
}
