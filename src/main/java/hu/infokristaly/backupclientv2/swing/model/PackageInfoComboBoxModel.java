/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.infokristaly.backupclientv2.swing.model;

import hu.infokristaly.backupclientv2.model.PackageInfo;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author pzoli
 */
public class PackageInfoComboBoxModel extends AbstractListModel implements ComboBoxModel  {
    
    private final List<PackageInfo> packages;
    private PackageInfo selectedItem;
    
    public PackageInfoComboBoxModel(List<PackageInfo> locations) {
        this.packages = locations;
    }

    @Override
    public int getSize() {
        return packages.size();
    }

    @Override
    public PackageInfo getElementAt(int i) {
        return packages.get(i);
    }

    @Override
    public void setSelectedItem(Object o) {
        this.selectedItem = (PackageInfo)o;
    }

    @Override
    public Object getSelectedItem() {
        return this.selectedItem;
    }
    
}
