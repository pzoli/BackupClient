/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.infokristaly.backupclientv2.swing.model;

import hu.infokristaly.backupclientv2.model.FileInfo;
import hu.infokristaly.backupclientv2.model.ListItem;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author pzoli
 */
public class FileTableModel extends AbstractTableModel {

    private final List<ListItem> items;
    String[] columns = new String[]{
        "Name", "Size", "Rights"
    };

    final Class[] columnClass = new Class[]{
        String.class, Long.class, String.class
    };

    public FileTableModel(List<ListItem> items) {
        this.items = items;
    }

    @Override
    public String getColumnName(int col) {
      return columns[col];
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ListItem row = items.get(rowIndex);
        if (0 == columnIndex) {
            return row.getLabel();
        } else if (1 == columnIndex) {
            if (row instanceof FileInfo) {
                return ((FileInfo) row).getSize();
            }
        } else if (2 == columnIndex) {
            return null;
        }
        return null;
    }
}
