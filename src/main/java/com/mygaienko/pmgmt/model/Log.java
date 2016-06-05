package com.mygaienko.pmgmt.model;

import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by enda1n on 04.06.2016.
 */
@Entity
public class Log {

    @Id
    @GeneratedValue
    private long id;

    @Column(name="log_date")
    private DateTime date;

    @Column(name="hours")
    private int hours;

    @OneToOne(/*orphanRemoval=false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.}*/)
    @JoinColumn(name="executor_fk")
    private Executor executor;

    @ManyToOne(/*cascade = {CascadeType.PERSIST, CascadeType.MERGE}*/)
    @JoinColumn(name="task_fk")
    private Task task;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return executor + ": " + hours + " hours on " + date.toString("yyyy-MMM-dd");
    }
}
