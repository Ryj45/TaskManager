package c346.rp.edu.sg.taskmanager;

import java.io.Serializable;

public class Task implements Serializable {
    private int id;
    private String name;
    private String description;
    private int remindTime;

    public Task(int id, String name, String description, int remindTime){
        this.id = id;
        this.name = name;
        this.description = description;
        this.remindTime = remindTime;
    }

    public int getId(){return id;}
    public String getName(){return name;}
    public String getDescription(){return description;}
    public int getRemindTime(){return remindTime;}

}
