package com.mygaienko.pmgmt.model;

import javax.persistence.*;

/**
 * Created by enda1n on 21.02.2016.
 */
@Entity
public class AttachedFile {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = false, nullable = false, length = 100000)
    private byte[] data;

    @Column
    private String name;

    @ManyToOne
    private Task task;

    public AttachedFile() {}

    public AttachedFile(byte[] data, String name, Task task) {
        this.data = data;
        this.name = name;
        this.task = task;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
