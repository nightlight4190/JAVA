
//using only register button

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.*;

public class EMSFrame extends JFrame {
    private JPanel leftPanel, rightPanel;
    private JLabel idLabel, nameLabel, salaryLabel;
    private JTextField idField, nameField, salaryField;
    private JButton saveButton, deleteButton, updateButton;
    private JTable employeeTable;
    private DefaultTableModel tableModel;

    public EMSFrame() {
        // Frame settings
        setTitle("Employee Management System");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        // Left Panel Components
        leftPanel = new JPanel();
        leftPanel.setLayout(null);

        idLabel = new JLabel("ID:");
        idLabel.setBounds(30, 30, 100, 30);
        idField = new JTextField();
        idField.setBounds(130, 30, 120, 30);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 70, 100, 30);
        nameField = new JTextField();
        nameField.setBounds(130, 70, 120, 30);

        salaryLabel = new JLabel("Salary:");
        salaryLabel.setBounds(30, 110, 100, 30);
        salaryField = new JTextField();
        salaryField.setBounds(130, 110, 120, 30);

        saveButton = new JButton("Save");
        saveButton.setBounds(30, 160, 100, 30);

        updateButton = new JButton("Update");
        updateButton.setBounds(150, 160, 100, 30);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(90, 200, 100, 30);

        // Add components to left panel
        leftPanel.add(idLabel);
        leftPanel.add(idField);
        leftPanel.add(nameLabel);
        leftPanel.add(nameField);
        leftPanel.add(salaryLabel);
        leftPanel.add(salaryField);
        leftPanel.add(saveButton);
        leftPanel.add(updateButton);
        leftPanel.add(deleteButton);

        // Right Panel (Table)
        rightPanel = new JPanel();
        String[] columnNames = {"ID", "Name", "Salary"};
        tableModel = new DefaultTableModel(null, columnNames);
        employeeTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(employeeTable);
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Add panels to the frame
        add(leftPanel);
        add(rightPanel);

        // Button actions
        saveButton.addActionListener(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String salary = salaryField.getText();

            if (!id.isEmpty() && !name.isEmpty() && !salary.isEmpty()) {
                String[] row = {id, name, salary};
                tableModel.addRow(row);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(null, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow >= 0) {
                tableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow >= 0) {
                String id = idField.getText();
                String name = nameField.getText();
                String salary = salaryField.getText();

                if (!id.isEmpty() && !name.isEmpty() && !salary.isEmpty()) {
                    tableModel.setValueAt(id, selectedRow, 0);
                    tableModel.setValueAt(name, selectedRow, 1);
                    tableModel.setValueAt(salary, selectedRow, 2);
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to update", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Mouse Adapter for row selection and double-click editing
        employeeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow >= 0) {
                    idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    salaryField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                }

                // Handle double-click to perform a custom action if needed
                if (e.getClickCount() == 2) {
                    // For example, double-click could trigger an update or open a detailed view
                    JOptionPane.showMessageDialog(null, "Double-clicked on row " + selectedRow, "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        salaryField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EMSFrame().setVisible(true));
    }
}
