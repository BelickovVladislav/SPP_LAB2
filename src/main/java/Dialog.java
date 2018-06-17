import org.javalite.activejdbc.Base;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

public class Dialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField idTextField;
    private JTextField mobileProducerTextField;
    private JTextField mobileModelTextField;
    private JTextField previewTextField;
    private JButton openImage;
    private JLabel idLabel;
    private JLabel mobileProducerLabel;
    private JLabel mobileModelLabel;
    private JLabel previewLabel;
    private Phone phone;

    public Dialog() {
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        setLocationRelativeTo(null);
        setSize(300, 500);
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        idLabel.setVisible(false);
        idTextField.setVisible(false);
        setContentPane(contentPane);
        setModal(true);
        openImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(Dialog.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //This is where a real application would open the file.
                    previewTextField.setText(file.getPath());
                } else {
                    previewTextField.setText("");
                }
            }
        });
    }

    public Dialog(Phone phone) {
        this();
        this.phone = phone;
        idLabel.setVisible(true);
        idTextField.setVisible(true);
        mobileModelTextField.setText(phone.getString("mobile_model"));
        mobileProducerTextField.setText(phone.getString("mobile_producer"));
        previewTextField.setText(phone.getString("preview"));
        idTextField.setText(phone.getString("id"));

    }

    public static void main(String[] args) {
        Dialog dialog = new Dialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void onOK() {
        // add your code here
        if (mobileProducerTextField.getText().isEmpty() || mobileModelTextField.getText().isEmpty() || previewTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Все поля обязательны для заполнения!");
            return;
        }
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/phone_manager", "root", "");
        boolean isNew = false;
        if (phone == null) {
            phone = new Phone();
            isNew = true;
        }
        phone.set("mobile_producer", mobileProducerTextField.getText());
        phone.set("mobile_model", mobileModelTextField.getText());
        phone.set("preview", previewTextField.getText());
        if (isNew) {
            phone.saveIt();
        } else {
            phone.save();
        }

        Base.close();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
