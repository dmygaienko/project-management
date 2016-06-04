package com.mygaienko.pmgmt.dao.interfaces;


import com.mygaienko.pmgmt.model.Log;
import com.mygaienko.pmgmt.model.Task;

import java.util.List;

public interface LogDao extends Dao<Log>{

	List<Log> getByTask(Task task);
}
