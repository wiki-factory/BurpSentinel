/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.mainBot;

import gui.botLeft.*;
import model.SentinelHttpMessage;
import util.UiUtil;

/**
 *
 * @author unreal
 */
public class PanelBotUi extends javax.swing.JPanel {

    private PanelBotLinkManager linkManager;
    
    /**
     * Creates new form PanelBotUi
     */
    public PanelBotUi() {
        linkManager = new PanelBotLinkManager();
        initComponents();
    }
    
    public PanelBotUi(SentinelHttpMessage iHttpRequestResponse) {
        this();
                
        UiUtil.restoreSplitLocation(jSplitPane1, this);
        panelLeftUi.setMessage(iHttpRequestResponse);
        
        panelLeftUi.setPanelParent(this);
        panelRightUi.setPanelParent(this);
    }
    
    // Called when attacking from leftpanel
    public void addAttackMessage(SentinelHttpMessage httpMessage) {
        panelRightUi.addHttpMessage(httpMessage);
        //this.updateUI();
    }
    
    public PanelBotLinkManager getLinkManager() {
        return linkManager;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        panelRightTop = new javax.swing.JPanel();
        panelRightUi = new gui.botRight.PanelRightUi();
        jPanel1 = new javax.swing.JPanel();
        panelLeftTop = new javax.swing.JPanel();
        panelLeftUi = new gui.botLeft.PanelLeftUi();

        jPanel2.setLayout(new java.awt.BorderLayout());

        panelRightTop.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelRightTop.setLayout(new java.awt.BorderLayout());
        panelRightTop.add(panelRightUi, java.awt.BorderLayout.CENTER);

        jPanel2.add(panelRightTop, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel2);

        jPanel1.setLayout(new java.awt.BorderLayout());

        panelLeftTop.setLayout(new java.awt.BorderLayout());
        panelLeftTop.add(panelLeftUi, java.awt.BorderLayout.CENTER);

        jPanel1.add(panelLeftTop, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1223, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel panelLeftTop;
    private gui.botLeft.PanelLeftUi panelLeftUi;
    private javax.swing.JPanel panelRightTop;
    private gui.botRight.PanelRightUi panelRightUi;
    // End of variables declaration//GEN-END:variables

    public void storeUiPrefs() {
        UiUtil.storeSplitLocation(jSplitPane1, this);
        
        panelLeftUi.storeUiPrefs();
        panelRightUi.storeUiPrefs();
    }
}
