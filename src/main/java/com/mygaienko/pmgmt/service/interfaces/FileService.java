package com.mygaienko.pmgmt.service.interfaces;

import com.mygaienko.pmgmt.model.AttachedFile;

/**
 * Created by enda1n on 04.06.2016.
 */
public interface FileService {
    void attachFile(AttachedFile file);

    void deleteFile(AttachedFile file);
}
