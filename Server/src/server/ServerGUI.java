/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import static server.Server.DIS;
import static server.Server.cetak;

/**
 *
 * @author danbo
 */
public class ServerGUI extends javax.swing.JFrame implements Runnable {

    Socket fromClient;
    ServerSocket serverSocket;
    
    BufferedReader serverReader;
    BufferedWriter serverWriter;
    DefaultListModel dlm = new DefaultListModel();
    
    JFrame frameEmoticon = new JFrame();
    private final Map<String, ImageIcon> imageMap;

    /**
     * Creates new form ServerGUI
     */
    public ServerGUI() {
        initComponents();
        this.setTitle("SERVER");
        
        //Hide View
        jLip.setVisible(false);
        jLbatas.setVisible(false);
        jLport.setVisible(false);
        jLstatus.setVisible(false);
        
        String[] nameList = {"Grinning", "Beaming", "Tears of Joy", "ROFL", "Grinning Face With Big Eyes"};
        //String[] nameList = new String[10];
        imageMap = createImageMap(nameList);
        JList list = new JList(nameList);
        list.setCellRenderer(new ListRenderer());

        //Taken from https://stackoverflow.com/questions/4344682/double-click-event-on-jlist-element
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() >= 2) {

                    // Double-click / more detected
                    int index = list.locationToIndex(evt.getPoint());

                    try {
                        
                        serverWriter.write(jTusername.getText() + " : " + nameList[index]);
                        serverWriter.newLine();
                        serverWriter.flush();
                    } catch (Exception e) {
                        Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, e);
                    }

                    dlm.addElement("Me : " + nameList[index]);
                    jListchat.setModel(dlm);

                }
            }
        });

        JScrollPane scroll = new JScrollPane(list);
        scroll.setPreferredSize(new Dimension(180, 500));

        frameEmoticon.add(scroll);
        frameEmoticon.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameEmoticon.pack();
        frameEmoticon.setLocationRelativeTo(null);
        frameEmoticon.setVisible(false);
        
    }
    
    
    //Taken from https://stackoverflow.com/questions/22266506/how-to-add-image-in-jlist?rq=1
    public class ListRenderer extends DefaultListCellRenderer {

        Font font = new Font("helvitica", Font.BOLD, 0);

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            label.setIcon(imageMap.get((String) value));
            //label.setHorizontalTextPosition(JLabel.BOTTOM);
            label.setVerticalTextPosition(JLabel.BOTTOM);
            label.setFont(font);
            return label;
        }
    }

    //Taken from https://stackoverflow.com/questions/22266506/how-to-add-image-in-jlist?rq=1
    private Map<String, ImageIcon> createImageMap(String[] list) {
        Map<String, ImageIcon> map = new HashMap<>();
        try {

            //Taken from https://emojipedia.org/apple/
            map.put("Grinning", new ImageIcon(new URL("https://emojipedia-us.s3.amazonaws.com/thumbs/144/apple/118/grinning-face_1f600.png")));
            map.put("Beaming", new ImageIcon(new URL("https://emojipedia-us.s3.amazonaws.com/thumbs/144/apple/118/grinning-face-with-smiling-eyes_1f601.png")));
            map.put("Tears of Joy", new ImageIcon(new URL("https://emojipedia-us.s3.amazonaws.com/thumbs/144/apple/118/face-with-tears-of-joy_1f602.png")));
            map.put("ROFL", new ImageIcon(new URL("https://emojipedia-us.s3.amazonaws.com/thumbs/144/apple/118/rolling-on-the-floor-laughing_1f923.png")));
            map.put("Grinning Face With Big Eyes", new ImageIcon(new URL("https://emojipedia-us.s3.amazonaws.com/thumbs/144/apple/118/smiling-face-with-open-mouth_1f603.png")));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    private void openServerConnection() {

        try {
            try {
                try {
                    
                    //Socket
                    serverSocket = new ServerSocket(Integer.parseInt(jTport.getText()));
                    this.setTitle("Menunggu Client ... ");

                } catch (Exception e) {
                    System.out.println("Could not listen");
                    System.exit(-1);
                }
                
                fromClient = serverSocket.accept();
                onConnect();

            } catch (Exception e) {
                jLstatus.setText("Accept Failed");
                System.exit(-1);
            }

            serverReader = new BufferedReader(new InputStreamReader(fromClient.getInputStream()));
            serverWriter = new BufferedWriter(new OutputStreamWriter(fromClient.getOutputStream()));
                   

        } catch (Exception e) {
            jLstatus.setText("Connection Failed");
            System.exit(-1);
        }

    }

    private void closeServerConnection() {

        try {
            //cetak.close();
            //DIS.close();
            
            serverReader.close();
            serverWriter.close();
            fromClient.close();
            serverSocket.close();
            
            jLstatus.setText("Server Off");
            
        } catch (Exception e) {
            Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, e);
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

        jPanel1 = new javax.swing.JPanel();
        jTport = new javax.swing.JTextField();
        jBactivate = new javax.swing.JButton();
        jTusername = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListchat = new javax.swing.JList<>();
        jBemoticon = new javax.swing.JButton();
        jTmessages = new javax.swing.JTextField();
        jLip = new javax.swing.JLabel();
        jLport = new javax.swing.JLabel();
        jLbatas = new javax.swing.JLabel();
        jLstatus = new javax.swing.JLabel();
        jBsend = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(335, 563));

        jPanel1.setPreferredSize(new java.awt.Dimension(335, 563));

        jBactivate.setText("ON");
        jBactivate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBactivateActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jListchat);

        jBemoticon.setText("SHOW EMOTICON");
        jBemoticon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBemoticonActionPerformed(evt);
            }
        });

        jLip.setText("127.0.0.1");

        jLport.setText("8080");

        jLbatas.setText(":");

        jLstatus.setText("Server Off");

        jBsend.setText("SEND");
        jBsend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBsendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBemoticon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTusername)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTport, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBactivate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLip)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLbatas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLport)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLstatus)
                        .addGap(8, 8, 8))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTmessages, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBsend, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTport)
                    .addComponent(jBactivate, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTusername, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLip)
                    .addComponent(jLport)
                    .addComponent(jLbatas)
                    .addComponent(jLstatus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBemoticon, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTmessages, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBsend, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBactivateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBactivateActionPerformed
        // TODO add your handling code here:

        boolean isServerActivate = false;
        
        if (jBactivate.getText().equals("ON")) {
            
            openServerConnection();
            jBactivate.setText("OFF");
            isServerActivate = true;
            
            Thread thread = new Thread(this);
            thread.start();
            
        }

        if (!isServerActivate) {
            
            closeServerConnection();
            jBactivate.setText("ON");
            isServerActivate = false;
            
            System.out.println("Status = " + "Server Dimatikan");
        }

    }//GEN-LAST:event_jBactivateActionPerformed

    private void jBsendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBsendActionPerformed
       
        editPositionText(jListchat, SwingConstants.RIGHT);
        
        try {
            serverWriter.write(jTusername.getText() + " : " + jTmessages.getText());
            serverWriter.newLine();
            serverWriter.flush();
        } catch (Exception e) {
            Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, e);
        }

        dlm.addElement("Me : " + jTmessages.getText());
        jListchat.setModel(dlm);

        jTmessages.setText("");
        
    }//GEN-LAST:event_jBsendActionPerformed

    private void jBemoticonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBemoticonActionPerformed
        // TODO add your handling code here:
        
        boolean emoFrameIsActive = false;
        if (jBemoticon.getText().equals("SHOW EMOTICON")) {

            frameEmoticon.setVisible(true);
            jBemoticon.setText("HIDE EMOTICON");
            emoFrameIsActive = true;
        }

        if (!emoFrameIsActive) {
            frameEmoticon.setVisible(false);
            jBemoticon.setText("SHOW EMOTICON");
        }
        
    }//GEN-LAST:event_jBemoticonActionPerformed

    //Taken from http://forums.whirlpool.net.au/archive/1686652
    public static void editPositionText(JList jList, int constant) {

        jList.setCellRenderer(new DefaultListCellRenderer() {
            public int getHorizontalAlignment() {
                return constant;
            }
        });
    }
    
    public void onConnect() {
    
        //Show View
        jLip.setVisible(true);
        jLbatas.setVisible(true);
        jLport.setVisible(true);
        jLstatus.setVisible(true);        
        
        //Mutator
        System.out.println("Status = " + "Server Dihidupkan");
        jTusername.setText("SERVER");
        jLstatus.setText("Server On");
        jLip.setText(fromClient.getInetAddress().toString());
        jLport.setText(jTport.getText().toString());
        this.setTitle("CONNECTED " + fromClient.getInetAddress());
        
        jTport.disable();
        
    }
    
    
    public void onDisonnect() {
    
        System.out.println("Status = " + "Server Dimatikan");
        jTusername.setText("");
        this.setTitle("SERVER");
        
    }
    
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
            java.util.logging.Logger.getLogger(ServerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerGUI().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBactivate;
    private javax.swing.JButton jBemoticon;
    private javax.swing.JButton jBsend;
    private javax.swing.JLabel jLbatas;
    private javax.swing.JLabel jLip;
    private javax.swing.JList<String> jListchat;
    private javax.swing.JLabel jLport;
    private javax.swing.JLabel jLstatus;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTmessages;
    private javax.swing.JTextField jTport;
    private javax.swing.JTextField jTusername;
    // End of variables declaration//GEN-END:variables
  
    
    public void run() {
       
        while (true) {
            try {
                dlm.addElement(serverReader.readLine());
                jListchat.setModel(dlm);

            } catch (IOException ex) {
                Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
