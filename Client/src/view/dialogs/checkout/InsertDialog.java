package view.dialogs.checkout;

/*============================================================================
Name        : CheckoutScreen.java
Author      : Tyrien Gilpin
Version     : 1
Description : GUI Screen class for cashing checkout 
============================================================================
*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import client.Client;
import models.Customer;
import utils.PhoneNumberValidator;
import utils.StringValidator;
import view.components.DatePicker;
import utils.EmailValidator;
import view.components.RoundedBorder;

public class InsertDialog extends JDialog implements ActionListener {

    private JLabel idLabel, firstNameLabel, lastNameLabel, dobLabel;
    private JLabel addressLabel, telephoneLabel, emailLabel;
    private JLabel membershipDateLabel, membershipExpiryDateLabel;
    private JTextField idField, firstNameField, lastNameField;
    private JTextField addressField, telephoneField, emailField;
    private DatePicker dobPicker, membershipDatePicker, membershipExpiryDatePicker;
    private JButton cancelButton, confirmButton;

    public InsertDialog(String idNum) {
        initializeComponents(idNum);
        addPanelsToWindow();
        registerListeners();
        setWindowProperties();
    }

    private void initializeComponents(String idNum) {
        Dimension labelSize = new Dimension(140, 20);
        Dimension fieldSize = new Dimension(250, 35);
        Font labelFont = new Font("arial", Font.BOLD, 14);
        Font fieldFont = new Font("arial", Font.PLAIN, 14);

        //Label properties
        idLabel = new JLabel("ID");
        idLabel.setFont(labelFont);
        idLabel.setPreferredSize(labelSize);

        firstNameLabel = new JLabel("First Name");
        firstNameLabel.setFont(labelFont);
        firstNameLabel.setPreferredSize(labelSize);

        lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setFont(labelFont);
        lastNameLabel.setPreferredSize(labelSize);

        dobLabel = new JLabel("Date of Birth");
        dobLabel.setFont(labelFont);
        dobLabel.setPreferredSize(labelSize);

        emailLabel = new JLabel("Email");
        emailLabel.setFont(labelFont);
        emailLabel.setPreferredSize(labelSize);

        addressLabel = new JLabel("Address");
        addressLabel.setFont(labelFont);
        addressLabel.setPreferredSize(labelSize);

        telephoneLabel = new JLabel("Phone #");
        telephoneLabel.setFont(labelFont);
        telephoneLabel.setPreferredSize(labelSize);

        membershipDateLabel = new JLabel("Membership Date");
        membershipDateLabel.setFont(labelFont);
        membershipDateLabel.setPreferredSize(labelSize);

        membershipExpiryDateLabel = new JLabel("Expiry Date");
        membershipExpiryDateLabel.setFont(labelFont);
        membershipExpiryDateLabel.setPreferredSize(labelSize);

        //Field properties
        idField = new JTextField(idNum);
        idField.setBorder(new RoundedBorder(8));
        idField.setPreferredSize(fieldSize);
        idField.setFont(fieldFont);

        firstNameField = new JTextField();
        firstNameField.setBorder(new RoundedBorder(8));
        firstNameField.setPreferredSize(fieldSize);
        firstNameField.setFont(fieldFont);

        lastNameField = new JTextField();
        lastNameField.setBorder(new RoundedBorder(8));
        lastNameField.setPreferredSize(fieldSize);
        lastNameField.setFont(fieldFont);

        emailField = new JTextField();
        emailField.setBorder(new RoundedBorder(8));
        emailField.setPreferredSize(fieldSize);
        emailField.setFont(fieldFont);

        addressField = new JTextField();
        addressField.setBorder(new RoundedBorder(8));
        addressField.setPreferredSize(fieldSize);
        addressField.setFont(fieldFont);

        telephoneField = new JTextField();
        telephoneField.setBorder(new RoundedBorder(8));
        telephoneField.setPreferredSize(fieldSize);
        telephoneField.setFont(fieldFont);

        //DatePicker properties
        dobPicker = new DatePicker();
        membershipDatePicker = new DatePicker();
        membershipExpiryDatePicker = new DatePicker();

        //Button properties
        confirmButton = new JButton("ADD CUSTOMER");
        confirmButton.setPreferredSize(new Dimension(200, 30));
        confirmButton.setForeground(Color.BLUE);
        confirmButton.setFont(new Font("arial", Font.BOLD, 14));
        confirmButton.setFocusPainted(false);

        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.setFocusPainted(false);
    }

    private void addPanelsToWindow() {
        add(idLabel);
        add(idField);
        add(firstNameLabel);
        add(firstNameField);
        add(lastNameLabel);
        add(lastNameField);
        add(dobLabel);
        add(dobPicker);
        add(emailLabel);
        add(emailField);
        add(addressLabel);
        add(addressField);
        add(telephoneLabel);
        add(telephoneField);
        add(membershipDateLabel);
        add(membershipDatePicker);
        add(membershipExpiryDateLabel);
        add(membershipExpiryDatePicker);
        add(confirmButton);
        add(cancelButton);
    }

    private void setWindowProperties() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        setTitle("Add New Customer");
        setSize(450, 440);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        setVisible(true);
    }

    private void registerListeners() {
        confirmButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }

    private boolean validateFields() {
        if (idField.getText().isEmpty() || firstNameField.getText().isEmpty()
                || lastNameField.getText().isEmpty() || addressField.getText().isEmpty()
                || telephoneField.getText().isEmpty() || emailField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "One or more fields empty",
                    "Warning", JOptionPane.WARNING_MESSAGE
            );
            return false;
        } else return StringValidator.isValid(firstNameField.getText(), this)
                && StringValidator.isValid(lastNameField.getText(), this)
                && PhoneNumberValidator.isValid(telephoneField.getText(), this)
                && EmailValidator.isValid(emailField.getText(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(confirmButton)) {
            if (validateFields()) {
                Client client = new Client();
                Customer customer = new Customer(
                        idField.getText(),
                        firstNameField.getText(),
                        lastNameField.getText(),
                        dobPicker.getSelectedDate(),
                        addressField.getText(),
                        telephoneField.getText(),
                        emailField.getText(),
                        membershipDatePicker.getSelectedDate(),
                        membershipExpiryDatePicker.getSelectedDate()
                );
                client.sendAction("Add Customer");
                client.sendCustomer(customer);
                client.receiveResponse();
                client.closeConnections();
                dispose();
            }

            if (e.getSource().equals(cancelButton)) {
                dispose();
            }
        }
    }
}
