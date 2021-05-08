/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.infokristaly.backupclientv2.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import hu.infokristaly.backupclientv2.model.FileInfo;
import hu.infokristaly.backupclientv2.model.FolderInfo;
import hu.infokristaly.backupclientv2.model.ListItem;
import hu.infokristaly.backupclientv2.model.PackageInfo;
import hu.infokristaly.backupclientv2.swing.model.FileTableModel;
import hu.infokristaly.backupclientv2.utils.HttpClientRequestUtils;
import hu.infokristaly.backupclientv2.utils.PathUtils;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.net.ProtocolException;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author pzoli
 */
public class MainFrame extends javax.swing.JFrame {

    private ObjectMapper objectMapper = new ObjectMapper();
    private static final CollectionType FILEINFO_LIST_TYPE = TypeFactory.defaultInstance().constructCollectionType(List.class, FileInfo.class);
    private static final CollectionType FOLDERINFO_LIST_TYPE = TypeFactory.defaultInstance().constructCollectionType(List.class, FolderInfo.class);
    private FolderInfo currentBackupFolder;
    private String prevFolderName;
    private List<ListItem> currentBackupTableContent;

    public static String SERVER_URL = "http://localhost:8080";

    public static String RESTORE_PATH = "/home/pzoli/restore";

    private JFileChooser chooser;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        Properties prop = new Properties();
        String path = "./config.properties";
        try (FileInputStream file = new FileInputStream(path)) {
            prop.load(file);
            SERVER_URL = prop.getProperty("server.url");
            RESTORE_PATH = prop.getProperty("restore.path");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<ListItem> findRootFolders() throws Exception {
        Reader reader = HttpClientRequestUtils.sendGetRequestWithHttpClient(SERVER_URL + "/packageinfo/findAllPackage");
        List<ListItem> folders = objectMapper.readValue(reader, FOLDERINFO_LIST_TYPE);
        return folders;
    }

    private List<ListItem> findFoldersAndFilesInFolder(FolderInfo parentFolder) throws Exception {
        Reader reader = HttpClientRequestUtils.sendGetRequestWithHttpClient(SERVER_URL + "/folderinfo/findByParentFolderId/" + parentFolder.getId());
        List<ListItem> folders = objectMapper.readValue(reader, FOLDERINFO_LIST_TYPE);

        reader = HttpClientRequestUtils.sendGetRequestWithHttpClient(SERVER_URL + "/fileinfo/findByParentFolderId/" + parentFolder.getId());
        List<ListItem> files = objectMapper.readValue(reader, FILEINFO_LIST_TYPE);

        folders.addAll(files);
        return folders;
    }

    private int deleteFolder(FolderInfo folder) throws Exception {
        int responseCode = HttpClientRequestUtils.sendDeleteRequest(SERVER_URL + "/folderinfo/" + folder.getId());
        return responseCode;
    }

    private int deleteFile(FileInfo fileInfo) throws Exception {
        int responseCode = HttpClientRequestUtils.sendDeleteRequest(SERVER_URL + "/fileinfo/" + fileInfo.getId());
        return responseCode;
    }

    private void updateTableModel() throws Exception {
        FileTableModel model = new FileTableModel(currentBackupTableContent);
        itemTable.setModel(model);
    }

    private void restoreFile(FileInfo fileInfo, String restorePath) throws ProtocolException, IOException {
        String parentFolder = restorePath + File.separator + PathUtils.getFilePath((FileInfo) fileInfo);
        File parent = new File(parentFolder);
        parent.mkdirs();
        HttpClientRequestUtils.sendGetRequestWithDataInputStream(SERVER_URL + "/fileinfo/getFileById/" + fileInfo.getId(), parentFolder + File.separator, fileInfo.getFileName());
    }

    private void restoreFolder(FolderInfo folder, String restorePath) throws IOException, Exception {
        String parentPath = PathUtils.getParentPath(folder);
        File parentFolder = new File(restorePath + File.separator + parentPath);
        parentFolder.mkdirs();

        List<ListItem> content = findFoldersAndFilesInFolder(folder);
        for (ListItem item : content) {
            if (item instanceof FileInfo) {
                restoreFile((FileInfo) item, restorePath);
            } else if (item instanceof FolderInfo) {
                parentPath = PathUtils.getParentPath((FolderInfo) item);
                parentFolder = new File(restorePath + File.separator + parentPath);
                parentFolder.mkdirs();
                restoreFolder((FolderInfo) item, restorePath);
            }
        }
    }

    public void refreshTable() {
        try {
            if (currentBackupFolder == null) {
                currentBackupTableContent = findRootFolders();
            } else {
                currentBackupTableContent = findFoldersAndFilesInFolder(currentBackupFolder);
                currentBackupTableContent.add(0, new ListItem() {
                    @Override
                    public String getLabel() {
                        return "..";
                    }

                });
            }
            updateTableModel();
            updateSelection();
        } catch (Exception ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateSelection() {
        if (prevFolderName != null) {
            boolean NotFound = true;
            int idx = -1;
            for (int i = 0; i < currentBackupTableContent.size() && NotFound; i++) {
                if (currentBackupTableContent.get(i).getLabel().equals(prevFolderName)) {
                    idx = i;
                    NotFound = false;
                }
            }
            if (idx > -1) {
                itemTable.setRowSelectionInterval(idx, idx);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        itemTable = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        mFile = new javax.swing.JMenu();
        mExit = new javax.swing.JMenuItem();
        mEdit = new javax.swing.JMenu();
        mSearch = new javax.swing.JMenuItem();
        mBackup = new javax.swing.JMenuItem();
        mRestore = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        itemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "File", "Size", "Rights"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Long.class, java.lang.Short.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        itemTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        itemTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        itemTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                itemTableKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(itemTable);

        mFile.setText("File");

        mExit.setText("Exit");
        mExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mExitActionPerformed(evt);
            }
        });
        mFile.add(mExit);

        jMenuBar1.add(mFile);

        mEdit.setText("Edit");

        mSearch.setText("Search");
        mSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSearchActionPerformed(evt);
            }
        });
        mEdit.add(mSearch);

        mBackup.setText("Backup");
        mBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mBackupActionPerformed(evt);
            }
        });
        mEdit.add(mBackup);

        mRestore.setText("Restore");
        mRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mRestoreActionPerformed(evt);
            }
        });
        mEdit.add(mRestore);

        jMenuBar1.add(mEdit);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.pack();
        this.setLocationRelativeTo(null);

        try {
            currentBackupTableContent = findRootFolders();
            currentBackupFolder = null;
            updateTableModel();
        } catch (Exception ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    private void mBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mBackupActionPerformed
        BackupFrame backupFrame = new BackupFrame(this, true);
        backupFrame.pack();
        backupFrame.setLocationRelativeTo(null);
        backupFrame.setVisible(true);
    }//GEN-LAST:event_mBackupActionPerformed

    private void mExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_mExitActionPerformed

    private void mRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mRestoreActionPerformed
        int index = itemTable.getSelectedRow();
        if (index == -1) {
            return;
        }
        if (chooser == null) {
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File(RESTORE_PATH));
            chooser.setDialogTitle("Choose the restore root directory");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
        }

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File dir = chooser.getSelectedFile();
            RESTORE_PATH = dir.toString();

            ListItem selectedValue = currentBackupTableContent.get(index);
            try {
                if (selectedValue instanceof FolderInfo) {
                    restoreFolder((FolderInfo) selectedValue, RESTORE_PATH);
                    JOptionPane.showMessageDialog(null, "Folder restore done.");
                } else if (selectedValue instanceof FileInfo) {
                    restoreFile((FileInfo) selectedValue, RESTORE_PATH);
                    JOptionPane.showMessageDialog(null, "File restore done.");
                } else {

                }
            } catch (Exception ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_mRestoreActionPerformed

    private void mSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSearchActionPerformed
        SearchFrame searchFrame = new SearchFrame(this, true);
        searchFrame.pack();
        searchFrame.setLocationRelativeTo(null);
        searchFrame.setVisible(true);
    }//GEN-LAST:event_mSearchActionPerformed

    private void itemTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemTableKeyPressed
        int code = evt.getKeyCode();
        if (itemTable.getSelectedRow() == -1) {
            return;
        }
        int index = itemTable.getSelectedRow();
        ListItem selectedValue = currentBackupTableContent.get(index);
        switch (code) {
            case KeyEvent.VK_ENTER:
                prevFolderName = null;
                if (selectedValue instanceof PackageInfo) {
                    currentBackupFolder = (PackageInfo) selectedValue;
                    refreshTable();
                } else if (selectedValue instanceof FolderInfo) {
                    currentBackupFolder = (FolderInfo) selectedValue;
                    refreshTable();
                } else if (selectedValue instanceof FileInfo) {

                } else {
                    prevFolderName = currentBackupFolder.getFolderName();
                    currentBackupFolder = currentBackupFolder.getParentFolder();
                    refreshTable();
                }
                break;
            case KeyEvent.VK_DELETE:
            try {
                int input = JOptionPane.showConfirmDialog(null, "Do you want delete this item?", "Are you sure?", JOptionPane.YES_NO_OPTION);
                if (input == 0) {
                    prevFolderName = null;
                    if (selectedValue instanceof PackageInfo || selectedValue instanceof FolderInfo) {
                        deleteFolder((FolderInfo) selectedValue);
                        refreshTable();
                    } else if (selectedValue instanceof FileInfo) {
                        deleteFile((FileInfo) selectedValue);
                        refreshTable();
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            case KeyEvent.VK_BACK_SPACE:
                if (currentBackupFolder != null) {
                    prevFolderName = currentBackupFolder.getFolderName();
                    currentBackupFolder = currentBackupFolder.getParentFolder();
                }
                refreshTable();
                break;
            case KeyEvent.VK_HOME:
                itemTable.setRowSelectionInterval(0, 0);
                break;
            case KeyEvent.VK_END:
                itemTable.setRowSelectionInterval(itemTable.getRowCount() - 1, itemTable.getRowCount() - 1);
                break;
        }
    }//GEN-LAST:event_itemTableKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable itemTable;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem mBackup;
    private javax.swing.JMenu mEdit;
    private javax.swing.JMenuItem mExit;
    private javax.swing.JMenu mFile;
    private javax.swing.JMenuItem mRestore;
    private javax.swing.JMenuItem mSearch;
    // End of variables declaration//GEN-END:variables

}
