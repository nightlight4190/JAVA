import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EMSDriver {
    public static void main(String[] args) {
        new EMSGUI();
    }
}

class EMSGUI extends JFrame {
    JButton register, search, delete, update, logout;

    EMSGUI() {
        setSize(600, 400);
        setTitle("Employee Management System");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        register = new JButton("Register");
        search = new JButton("Search");
        delete = new JButton("Delete");
        update = new JButton("Update");
        logout = new JButton("Logout");
        
        setLayout(null);
        register.setBounds(20, 50, 100, 50);
        search.setBounds(150, 50, 100, 50);
        delete.setBounds(280, 50, 100, 50);
        update.setBounds(400, 50, 100, 50);
        logout.setBounds(150, 150, 150, 50);
        
        add(register);
        add(search);
        add(delete);
        add(update);
        add(logout);

        register.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        new RegisterGui();
                    }
                });
    }
}

class RegisterGui extends JFrame {

    private JLabel idLabel, nameLabel, salaryLabel, cityLabel, genderLabel;
    private JTextField idField, nameField, salaryField;
    private JComboBox<String> cityComboBox;
    private JRadioButton maleRadio, femaleRadio, otherRadio;
    private ButtonGroup genderGroup;
    private JButton saveButton, deleteButton, updateButton;
    private JTable employeeTable;
    private DefaultTableModel tableModel;

    RegisterGui() {
        setSize(800, 600);
        setTitle("Employee Registration");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        // Left Panel for registration form
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);

        idLabel = new JLabel("Employee ID:");
        idLabel.setBounds(30, 30, 100, 30);
        idField = new JTextField();
        idField.setBounds(140, 30, 120, 30);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 70, 100, 30);
        nameField = new JTextField();
        nameField.setBounds(140, 70, 120, 30);

        salaryLabel = new JLabel("Salary:");
        salaryLabel.setBounds(30, 110, 100, 30);
        salaryField = new JTextField();
        salaryField.setBounds(140, 110, 120, 30);

        cityLabel = new JLabel("City:");
        cityLabel.setBounds(30, 150, 100, 30);
        String[] cities = {"Pokhara", "Kathmandu", "Butwal", "Chitwan", "Baglung"};
        cityComboBox = new JComboBox<>(cities);
        cityComboBox.setBounds(140, 150, 120, 30);

        genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(30, 190, 100, 30);
        maleRadio = new JRadioButton("Male");
        maleRadio.setBounds(140, 190, 70, 30);
        femaleRadio = new JRadioButton("Female");
        femaleRadio.setBounds(210, 190, 80, 30);
        otherRadio = new JRadioButton("Other");
        otherRadio.setBounds(290, 190, 70, 30);

        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderGroup.add(otherRadio);

        saveButton = new JButton("Save");
        saveButton.setBounds(30, 240, 100, 30);

        updateButton = new JButton("Update");
        updateButton.setBounds(140, 240, 100, 30);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(250, 240, 100, 30);

        leftPanel.add(idLabel);
        leftPanel.add(idField);
        leftPanel.add(nameLabel);
        leftPanel.add(nameField);
        leftPanel.add(salaryLabel);
        leftPanel.add(salaryField);
        leftPanel.add(cityLabel);
        leftPanel.add(cityComboBox);
        leftPanel.add(genderLabel);
        leftPanel.add(maleRadio);
        leftPanel.add(femaleRadio);
        leftPanel.add(otherRadio);
        leftPanel.add(saveButton);
        leftPanel.add(updateButton);
        leftPanel.add(deleteButton);

        // Right Panel for table
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Salary", "City", "Gender"}, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(employeeTable);
        rightPanel.add(tableScrollPane, BorderLayout.CENTER);

        add(leftPanel);
        add(rightPanel);

        // Action Listeners
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                String salary = salaryField.getText();
                String city = (String) cityComboBox.getSelectedItem();
                String gender = maleRadio.isSelected() ? "Male" : femaleRadio.isSelected() ? "Female" : "Other";

                if (!id.isEmpty() && !name.isEmpty() && !salary.isEmpty() && city != null) {
                    String[] row = { id, name, salary, city, gender };
                    tableModel.addRow(row);
                    saveToFile();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow >= 0) {
                    tableModel.removeRow(selectedRow);
                    saveToFile();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String id = idField.getText();
                    String name = nameField.getText();
                    String salary = salaryField.getText();
                    String city = (String) cityComboBox.getSelectedItem();
                    String gender = maleRadio.isSelected() ? "Male" : femaleRadio.isSelected() ? "Female" : "Other";

                    if (!id.isEmpty() && !name.isEmpty() && !salary.isEmpty() && city != null) {
                        tableModel.setValueAt(id, selectedRow, 0);
                        tableModel.setValueAt(name, selectedRow, 1);
                        tableModel.setValueAt(salary, selectedRow, 2);
                        tableModel.setValueAt(city, selectedRow, 3);
                        tableModel.setValueAt(gender, selectedRow, 4);
                        saveToFile();
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(null, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to update", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        salaryField.setText("");
        genderGroup.clearSelection();
        cityComboBox.setSelectedIndex(0);
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("employee.txt"))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String id = (String) tableModel.getValueAt(i, 0);
                String name = (String) tableModel.getValueAt(i, 1);
                String salary = (String) tableModel.getValueAt(i, 2);
                String city = (String) tableModel.getValueAt(i, 3);
                String gender = (String) tableModel.getValueAt(i, 4);
                writer.write("ID: " + id + ", Name: " + name + ", Salary: " + salary +
                        ", City: " + city + ", Gender: " + gender);
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
