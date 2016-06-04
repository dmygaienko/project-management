package com.mygaienko.pmgmt.service.interfaces;

import com.mygaienko.pmgmt.model.Log;
import com.mygaienko.pmgmt.model.Task;

import java.util.List;

/**
 * Created by enda1n on 04.06.2016.
 */
public interface LogService {
    void persist(Log log);

    void delete(Log log);

    List<Log> getLogsByTask(Task task);
}
