/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.infokristaly.backupclientv2.utils;

import hu.infokristaly.backupclientv2.model.FileInfo;
import hu.infokristaly.backupclientv2.model.FolderInfo;
import java.io.File;

/**
 *
 * @author pzoli
 */
public class PathUtils {

    public static String getParentPath(FolderInfo folderInfo) {
        StringBuilder sb = new StringBuilder();
        do {
            sb.insert(0, File.separator + folderInfo.getFolderName());
            folderInfo = folderInfo.getParentFolder();
        } while (folderInfo != null);
        return sb.toString();

    }

    public static String getFilePath(FileInfo fileInfo) {
        StringBuilder sb = new StringBuilder();
        FolderInfo folderInfo = fileInfo.getFolderInfo();
        String result = getParentPath(folderInfo);
        return result;
    }
    
}
