package hu.infokristaly.backupclientv2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import hu.infokristaly.backupclientv2.model.FolderInfo;

@JsonIgnoreProperties(value = {"label"})
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class PackageInfo extends FolderInfo {

    private String comment;

    public PackageInfo() {
        super();
    }
    
    public PackageInfo(String folderName, String comment) {
        this.setFolderName(folderName);
        this.setComment(comment);
    }

    public PackageInfo(Long id) {
        super();
        this.setId(id);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
