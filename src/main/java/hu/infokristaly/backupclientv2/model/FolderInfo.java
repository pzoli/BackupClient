package hu.infokristaly.backupclientv2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(value = {"label"})
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
	  @JsonSubTypes.Type(value=PackageInfo.class, name = "PackageInfo"),
	})
public class FolderInfo implements ListItem {

    private Long id;
    private String folderName;
    private FolderInfo parentFolder;

    public FolderInfo() {
    }

    public FolderInfo(String folderName, FolderInfo parentFolder) {
        this.folderName = folderName;
        this.parentFolder = parentFolder;
    }

    public FolderInfo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public FolderInfo getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(FolderInfo parentFolder) {
        this.parentFolder = parentFolder;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((folderName == null) ? 0 : folderName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((parentFolder == null) ? 0 : parentFolder.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        FolderInfo other = (FolderInfo) obj;
        if (folderName == null) {
            if (other.folderName != null) {
                return false;
            }
        } else if (!folderName.equals(other.folderName)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (parentFolder == null) {
            if (other.parentFolder != null) {
                return false;
            }
        } else if (!parentFolder.equals(other.parentFolder)) {
            return false;
        }
        return true;
    }

    @Override
    public String getLabel() {
        return folderName;
    }

}
