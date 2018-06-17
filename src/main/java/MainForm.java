import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JFrame {
    private JTable dataTable;
    private JPanel mainPanel;
    private JButton editButton;
    private JButton addButton;
    private LazyList<Phone> phones;

    public MainForm() {
        super("Mobile Phone");
        this.setSize(500, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        mainPanel.setDoubleBuffered(true);
        updateTable();
        dataTable.setRowHeight(100);
        editButton.setEnabled(false);
        dataTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    editButton.setEnabled(true);
                    System.out.println(dataTable.getSelectedRow());
                }
            }
        });
        this.setContentPane(mainPanel);
        this.setVisible(true);
        this.pack();
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Dialog dialog = new Dialog();
                dialog.show();
                updateTable();
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Phone phone = phones.get(dataTable.getSelectedRow());
                Dialog dialog = new Dialog(phone);
                dialog.show();
                updateTable();
                editButton.setEnabled(false);
            }
        });
    }

    private void updateTable() {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/phone_manager", "root", "");
        String[] header = {"id", "Mobile Producer", "Mobile Model", "Preview"};
        phones = Phone.findAll();
        Object[][] data = new Object[phones.size()][];
        for (int i = 0; i < phones.size(); i++) {
            Phone phone = phones.get(i);
            data[i] = new Object[4];
            data[i][0] = phone.get("id");
            data[i][1] = phone.get("mobile_producer");
            data[i][2] = phone.get("mobile_model");
            data[i][3] = phone.get("preview");
        }

        dataTable.setModel(new DefaultTableModel(
                data,
                header
        ) {
            Class[] types = new Class[]{
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        dataTable.getColumnModel().getColumn(3).setCellRenderer(new ImageRenderer());

        Base.close();
    }
}
