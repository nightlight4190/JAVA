import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

class GUI {
    public static void main(String[] args) {
        EMIGUI gui = new EMIGUI();
    }
}

class EMIGUI extends JFrame {
    JButton register, search, delete, update, logout;

    EMIGUI() {
        setSize(800, 600);
        setTitle("Employee Management System");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        register = new JButton("Register");
        search = new JButton("Search");
        delete = new JButton("Delete");
        update = new JButton("Update");
        logout = new JButton("Logout");

        register.setBounds(20, 50, 150, 30);
        search.setBounds(190, 50, 150, 30);
        delete.setBounds(360, 50, 150, 30);
        update.setBounds(530, 50, 150, 30);
        logout.setBounds(330, 160, 140, 30);

        add(register);
        add(search);
        add(delete);
        add(update);
        add(logout);

        register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RegisterForm(EMIGUI.this);
            }
        });
    }
}

class RegisterForm extends JFrame {
    JTable empTable;
    JTextField idField, nameField, salaryField;
    JButton submit, remove, update, load;
    JRadioButton maleButton, femaleButton;
    ButtonGroup genderGroup;
    JScrollPane pane;
    JPanel leftPanel;
    JPanel rightPanel;
    JComboBox<String> cityField;
    DefaultTableModel model;

    RegisterForm(JFrame parent) {
        super();
        setSize(1000, 600);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(1, 2));

        String[] head = {"ID", "Name", "Salary", "City", "Gender"};
        model = new DefaultTableModel(null, head);
        empTable = new JTable(model);

        leftPanel = new JPanel();
        rightPanel = new JPanel();

        EmptyBorder padding = new EmptyBorder(20, 20, 20, 20);
        leftPanel.setBorder(padding);
        rightPanel.setBorder(padding);

        leftPanel.setLayout(new GridLayout(12, 1, 5, 10));

        JLabel idLabel = new JLabel("ID:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel salaryLabel = new JLabel("Salary:");
        JLabel cityLabel = new JLabel("City:");
        JLabel genderLabel = new JLabel("Gender:");

        idField = new JTextField(15);
        nameField = new JTextField(15);
        salaryField = new JTextField(15);

        String[] cities = {"Pokhara", "Kathmandu", "Dharan", "Jhapa", "Lalitpur"};
        cityField = new JComboBox<>(cities);

        JPanel radioPanel = new JPanel();
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");

        genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        radioPanel.add(maleButton);
        radioPanel.add(femaleButton);

        submit = new JButton("Submit");
        remove = new JButton("Remove");
        update = new JButton("Update");
        load = new JButton("Load Data");  // New load button

        leftPanel.add(idLabel);
        leftPanel.add(idField);
        leftPanel.add(nameLabel);
        leftPanel.add(nameField);
        leftPanel.add(salaryLabel);
        leftPanel.add(salaryField);
        leftPanel.add(cityLabel);
        leftPanel.add(cityField);
        leftPanel.add(genderLabel);
        leftPanel.add(radioPanel);
        leftPanel.add(submit);
        leftPanel.add(remove);
        leftPanel.add(update);
        leftPanel.add(load);  // Add the load button to the panel

        pane = new JScrollPane(empTable);
        pane.setBorder(BorderFactory.createEmptyBorder());
        rightPanel.add(pane);

        add(leftPanel);
        add(rightPanel);

        // Action listener for the Load button
        load.addActionListener(e -> loadDataFromDatabase());

        submit.addActionListener(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String salary = salaryField.getText();
            String city = cityField.getSelectedItem().toString();
            String gender = maleButton.isSelected() ? "Male" : "Female";

            if (!maleButton.isSelected() && !femaleButton.isSelected()) {
                new ErrorDialogueBox("Please select gender");
                return;
            }

            String query = "INSERT INTO hr.employees (id, name, salary, city, gender) VALUES ('" + id + "', '" + name + "', '" + salary + "', '" + city + "', '" + gender + "')";

            try {
                Connection conn = Database.connect();
                Database.insert(conn, query);
                loadDataFromDatabase(); // Reload data after insertion
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Clear input fields after submission
            idField.setText("");
            nameField.setText("");
            salaryField.setText("");
            cityField.setSelectedIndex(0);
            genderGroup.clearSelection();
        });

        remove.addActionListener(e -> {
            int selectedRow = empTable.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) model.getValueAt(selectedRow, 0);
                String query = "DELETE FROM hr.employees WHERE id = '" + id + "'";

                try {
                    Connection conn = Database.connect();
                    Database.delete(conn, query);
                    model.removeRow(selectedRow); // Remove row from table
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                new ErrorDialogueBox("Please select a row to remove");
            }
        });

        update.addActionListener(e -> {
            int selectedRow = empTable.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) model.getValueAt(selectedRow, 0);
                String name = nameField.getText();
                String salary = salaryField.getText();
                String city = cityField.getSelectedItem().toString();
                String gender = maleButton.isSelected() ? "Male" : "Female";

                if (!maleButton.isSelected() && !femaleButton.isSelected()) {
                    new ErrorDialogueBox("Please select gender");
                    return;
                }

                String query = "UPDATE hr.employees SET name = '" + name + "', salary = '" + salary + "', city = '" + city + "', gender = '" + gender + "' WHERE id = '" + id + "'";

                try {
                    Connection conn = Database.connect();
                    Database.update(conn, query);
                    loadDataFromDatabase(); // Reload data after update
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                new ErrorDialogueBox("Please select a row to update");
            }
        });

        setVisible(true);
    }

    private void loadDataFromDatabase() {
        try {
            Connection conn = Database.connect();
            String query = "SELECT * FROM hr.employees";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            model.setRowCount(0);  // Clear existing data

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String salary = rs.getString("salary");
                String city = rs.getString("city");
                String gender = rs.getString("gender");

                model.addRow(new String[]{id, name, salary, city, gender});
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ErrorDialogueBox {
    JDialog errorDialog = new JDialog();

    ErrorDialogueBox(String errText) {
        errorDialog.setLayout(new GridLayout(2, 1));
        errorDialog.setTitle("Error");
        JLabel errorLabel = new JLabel(errText);
        JButton okButton = new JButton("OK");
        okButton.setBounds(20, 50, 100, 30);
        errorDialog.add(errorLabel);
        errorDialog.add(okButton);
        errorDialog.setSize(300, 100);
        okButton.addActionListener(e -> errorDialog.dispose());
        errorDialog.setVisible(true);
    }
}