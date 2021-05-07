/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.infokristaly.backupclientv2.app;

import hu.infokristaly.backupclientv2.utils.HttpClientRequestUtils;
import hu.infokristaly.backupclientv2.model.FileInfo;
import hu.infokristaly.backupclientv2.model.FolderInfo;
import hu.infokristaly.backupclientv2.model.PackageInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import hu.infokristaly.backupclientv2.swing.model.PackageInfoComboBoxModel;
import hu.infokristaly.backupclientv2.swing.renderer.PackageInfoComboBoxRenderer;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;

/**
 *
 * @author pzoli
 */
public class BackupFrame extends javax.swing.JDialog {

    JFileChooser chooser;
    private static final CollectionType PACKAGE_LIST_TYPE = TypeFactory.defaultInstance().constructCollectionType(List.class, PackageInfo.class);

    /**
     * Creates new form MainFrame
     */
    public BackupFrame() {

        initComponents();
    }

    private ListCellRenderer<? super PackageInfo> comboBoxRenderer = new PackageInfoComboBoxRenderer();

    private ObjectMapper objectMapper = new ObjectMapper();

    BackupFrame(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtRoot = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtExtesions = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnBackup = new javax.swing.JButton();
        btnChoose = new javax.swing.JButton();
        cbxPackage = new javax.swing.JComboBox<>();
        btnCreateLocation = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(443, 199));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setText("Package:");

        jLabel2.setText("Root folder:");

        jLabel3.setText("File extensions:");

        btnBackup.setText("Backup");
        btnBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackupActionPerformed(evt);
            }
        });

        btnChoose.setText("...");
        btnChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseActionPerformed(evt);
            }
        });

        btnCreateLocation.setText("...");
        btnCreateLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateLocationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxPackage, 0, 253, Short.MAX_VALUE)
                            .addComponent(txtRoot)
                            .addComponent(txtExtesions))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCreateLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBackup)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxPackage, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCreateLocation)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRoot, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChoose)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtExtesions, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(btnBackup)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void backupFiles(String pathString, Long rootFolderId) throws IOException {
        LinkedList<Long> folderIdStack = new LinkedList<Long>();
        folderIdStack.addLast(rootFolderId);
        Files.walkFileTree(Paths.get(pathString), new FileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Long forderId = createFolder(dir.getFileName().toString(), folderIdStack.getLast());
                if (forderId != null) {
                    folderIdStack.addLast(forderId);
                }
                System.out.println("preVisitDirectory[" + forderId + "]: " + dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                try {
                    System.out.println("visitFile[dirId:" + folderIdStack.getLast() + "]: " + file);
                    createFile(file, folderIdStack.getLast());
                    return FileVisitResult.CONTINUE;
                } catch (Exception ex) {
                    Logger.getLogger(BackupFrame.class.getName()).log(Level.SEVERE, null, ex);
                    return FileVisitResult.TERMINATE;
                }
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                System.out.println("visitFileFailed: " + file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Long lastDirId = folderIdStack.pollLast();
                System.out.println("postVisitDirectory [" + lastDirId + "]: " + dir);
                return FileVisitResult.CONTINUE;
            }

        });
    }

    public Long createFolder(String folderName, Long parentFolderId) {
        Long result = null;
        try {
            FolderInfo folderInfo = new FolderInfo(folderName, new FolderInfo(parentFolderId));
            String body = objectMapper.writeValueAsString(folderInfo);
            Reader response = HttpClientRequestUtils.sendPostRequestWithHttpClient(MainFrame.SERVER_URL + "/folderinfo/save", body);

            folderInfo = objectMapper.readValue(response, FolderInfo.class);
            result = folderInfo.getId();
        } catch (Exception ex) {
            Logger.getLogger(BackupFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public void createFile(Path file, Long folderId) throws IOException {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(file.getFileName().toString());
        fileInfo.setFolderInfo(new FolderInfo(folderId));
        fileInfo.setSize(Files.size(file));

        String body = objectMapper.writeValueAsString(fileInfo);
        HttpClientRequestUtils.sendFileWithMultipartData(MainFrame.SERVER_URL + "/fileinfo/fileupload", body, file.toFile());
    }

    public void refreshPackages() {
        try {
            PackageInfo tmpLocation = (PackageInfo) cbxPackage.getSelectedItem();
            Reader result = HttpClientRequestUtils.sendGetRequestWithHttpClient(MainFrame.SERVER_URL + "/packageinfo/findAllPackage");
            List<PackageInfo> packages = objectMapper.readValue(result, PACKAGE_LIST_TYPE);

            int idx = -1;
            if (tmpLocation != null) {
                boolean notFound = true;
                for (int i = 0; i < packages.size() && notFound; i++) {
                    if (packages.get(i).getFolderName().equals(tmpLocation.getFolderName())) {
                        idx = i;
                        notFound = false;
                    }
                }
            }
            PackageInfoComboBoxModel model = new PackageInfoComboBoxModel(packages);
            cbxPackage.setModel(model);
            cbxPackage.setRenderer(comboBoxRenderer);
            cbxPackage.setSelectedIndex(idx);
        } catch (Exception ex) {
            Logger.getLogger(BackupFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private FolderInfo findFolderInPackage(FolderInfo folderInfo) {
        try {
            String body = objectMapper.writeValueAsString(folderInfo);
            Reader result = HttpClientRequestUtils.sendPostRequestWithHttpClient(MainFrame.SERVER_URL + "/folderinfo/findByParentIdAndName", body);
            folderInfo = objectMapper.readValue(result, FolderInfo.class);
        } catch (Exception e) {
            folderInfo = null;
        }
        return folderInfo;
    }

    private void btnBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackupActionPerformed
        try {
            if (cbxPackage.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Choose location!");
                return;
            }
            if (txtRoot.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Set backup root folder!");
                return;
            }

            FolderInfo parentFolder = ((PackageInfo) cbxPackage.getSelectedItem());
            File folder = new File(txtRoot.getText());
            String folderName = folder.getName();
            FolderInfo checkFolder = new FolderInfo(folderName, parentFolder);
            checkFolder = findFolderInPackage(checkFolder);
            int input = 0;
            if (checkFolder != null) {
                input = JOptionPane.showConfirmDialog(null, "Folder exist in package. Do you want override it?", "Conflict", JOptionPane.YES_NO_OPTION);
            }
            if (input == 0) {
                backupFiles(txtRoot.getText(), parentFolder.getId());
                JOptionPane.showMessageDialog(null, "Backup done.");
                ((MainFrame) getParent()).refreshTable();
                dispose();
            }
        } catch (Exception ex) {
            Logger.getLogger(BackupFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBackupActionPerformed

    private void btnChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseActionPerformed
        if (chooser == null) {
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Choose the root directory");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
        }
        //    
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File dir = chooser.getSelectedFile();
            txtRoot.setText(dir.toString());
        }
    }//GEN-LAST:event_btnChooseActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        refreshPackages();
    }//GEN-LAST:event_formWindowOpened

    private void btnCreateLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateLocationActionPerformed
        NewPackageForm newPackageForm = new NewPackageForm(this, true);
        newPackageForm.setVisible(true);
    }//GEN-LAST:event_btnCreateLocationActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        ((MainFrame) getParent()).refreshTable();
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(BackupFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BackupFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BackupFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BackupFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BackupFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBackup;
    private javax.swing.JButton btnChoose;
    private javax.swing.JButton btnCreateLocation;
    private javax.swing.JComboBox<PackageInfo> cbxPackage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField txtExtesions;
    private javax.swing.JTextField txtRoot;
    // End of variables declaration//GEN-END:variables

}
