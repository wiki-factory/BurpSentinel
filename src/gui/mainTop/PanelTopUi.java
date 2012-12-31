package gui.mainTop;

import gui.MainUi;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import model.SentinelHttpMessage;
import util.UiUtil;

/**
 * Display all HttpMessage the user added to sentinel
 * 
 * @author Dobin
 */
public class PanelTopUi extends javax.swing.JPanel {
    private MainUi mainGuiFrame = null;
    private PanelTopTableModel tableTopModel;
    
    private int currentSelectedRow = -1;
    
    /**
     * Creates new form PanelTop
     */
    public PanelTopUi() {
        tableTopModel = new PanelTopTableModel(this);
        initComponents();
        tableAllMessages.setAutoCreateRowSorter(true);
        
        UiUtil.restoreTableDimensions(tableAllMessages, this);
        
        int width = 100;
        tableAllMessages.getColumnModel().getColumn(0).setMaxWidth(40);
        tableAllMessages.getColumnModel().getColumn(0).setMinWidth(40);
        
        tableAllMessages.getColumnModel().getColumn(1).setMaxWidth(60);
        tableAllMessages.getColumnModel().getColumn(1).setMinWidth(60);
        
        tableAllMessages.getColumnModel().getColumn(5).setMaxWidth(width);
        tableAllMessages.getColumnModel().getColumn(5).setMinWidth(width);
        
        tableAllMessages.getColumnModel().getColumn(6).setMaxWidth(width);
        tableAllMessages.getColumnModel().getColumn(6).setMinWidth(width);
        
        tableAllMessages.getColumnModel().getColumn(7).setMaxWidth(120);
        tableAllMessages.getColumnModel().getColumn(7).setMinWidth(120);
        
        tableAllMessages.getColumnModel().getColumn(8).setMaxWidth(120);
        tableAllMessages.getColumnModel().getColumn(8).setMinWidth(120);
        
        
        // Add selection listener
        ListSelectionModel lsm = tableAllMessages.getSelectionModel();
        lsm.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    //Ignore extra messages.
                    if (e.getValueIsAdjusting()) return;
 
                    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                    if (lsm.isSelectionEmpty()) {
                       //
                    } else {
                        // Get selected row and tell the main frame to show it 
                        // in the bottom frame
                        currentSelectedRow = lsm.getMinSelectionIndex();
                        mainGuiFrame.showMessage(currentSelectedRow);
                    }
                }});
    }

    public int getSelected() {
        return currentSelectedRow;
    }
    
    // This gets called from MainGui
    // If the user sends a new HttpMessage from Burp to Sentinel
    public void addMessage(SentinelHttpMessage httpMessage) {
        tableTopModel.addMessage(httpMessage);
        //this.updateUI();
    }

        
    // Used for swing
    private TableModel getMessageTableModel() {
        return tableTopModel;
    }
    
    
    // set MainGui (parent)
    // So we can interact with other UI parts (especially bot panel)
    public void setMainGui(MainUi aThis) {
        this.mainGuiFrame = aThis;
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableAllMessages = new javax.swing.JTable();

        tableAllMessages.setModel(getMessageTableModel());
        tableAllMessages.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        tableAllMessages.setRowHeight(20);
        tableAllMessages.setSelectionBackground(new java.awt.Color(255, 205, 129));
        tableAllMessages.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tableAllMessages.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tableAllMessages);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableAllMessages;
    // End of variables declaration//GEN-END:variables

    
    public void storeUiPrefs() {
        UiUtil.storeTableDimensions(tableAllMessages, this);
    }

    public void reset() {
        tableTopModel.reset();
    }

    public void setSelected(int index) {
        tableAllMessages.getSelectionModel().setSelectionInterval(index, index);
        this.currentSelectedRow = index;
    }
    
}
