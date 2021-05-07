/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.infokristaly.backupclientv2.swing.renderer;

import hu.infokristaly.backupclientv2.model.PackageInfo;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

/**
 *
 * @author pzoli
 */
public class PackageInfoComboBoxRenderer extends BasicComboBoxRenderer {

    public PackageInfoComboBoxRenderer() {
        super();
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object locationInfo, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, locationInfo, index, isSelected, cellHasFocus);
        if (isSelected) {
            setBackground(Color.gray);
            setForeground(Color.WHITE);
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        if (locationInfo != null) {
            setText(((PackageInfo) locationInfo).getFolderName());
        }

        setFont(list.getFont());
        setOpaque(true);

        return this;
    }

}
