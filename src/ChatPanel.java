/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author John
 */

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatPanel extends javax.swing.JPanel implements Runnable{

    /**
     * Creates new form ChatPanel
     */
    
        Socket socket = null;
    String sender;
    String receiver;
    BufferedReader bf = null;
    DataOutputStream os = null;
    
    public ChatPanel(Socket s, String sender, String receiver) throws IOException {
        initComponents();
        socket = s;
	    
	    //Nhận tên người gửi và người nhận
	    this.sender = sender;
	    this.receiver = receiver;
	    
	    //Tạo các bộ đệm để gửi và nhận tin nhắn
	    bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    os = new DataOutputStream(socket.getOutputStream());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        chat_area = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        history_area = new javax.swing.JTextArea();

        jButton1.setText("Send");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jLabel1.setText("HISTORY");

        chat_area.setColumns(20);
        chat_area.setRows(5);
        jScrollPane2.setViewportView(chat_area);

        history_area.setColumns(20);
        history_area.setRows(5);
        jScrollPane3.setViewportView(history_area);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        if (chat_area.getText().isEmpty()) return;
            try {
                os.writeBytes(sender + ": " + chat_area.getText() + '\n');
                os.flush();
                history_area.append(sender + ": " + chat_area.getText() + "\n");
		        chat_area.setText("");
            } catch (IOException ex) {
                Logger.getLogger(ChatPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_jButton1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea chat_area;
    private javax.swing.JTextArea history_area;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        while (true) {
	    try {
		if (socket != null) {
		    String msg = "";
		    while ((msg = bf.readLine()) != null) {
			//Nếu có tin nhắn đến thì ghi vào lịch sử
			history_area.append(msg + '\n');
		    }
		}
	    } catch (IOException e) {
		// Do not change this because it spawn try-catch many time while running thread!
	    }
	}
    }
}
