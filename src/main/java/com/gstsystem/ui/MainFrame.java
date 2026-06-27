package com.gstsystem.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.gstsystem.dao.InvoiceDao;
import com.gstsystem.model.Invoice;
import com.gstsystem.model.Product;
import com.gstsystem.service.GstService;

public class MainFrame extends JFrame {
    // UI Components
    private JTextField txtCustomer, txtProductName, txtPrice, txtQty, txtGstRate;
    private JCheckBox chkInterstate;
    private JTextArea txtOutput;
    
    // Backend Integrations
    private GstService gstService;
    private InvoiceDao invoiceDao;

    public MainFrame() {
        // Initialize Services
        gstService = new GstService();
        invoiceDao = new InvoiceDao();

        // Setup the Main Window
        setTitle("GST Compliance Automation System");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // --- Create the Input Panel (Top) ---
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("New Invoice Details"));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Customer Name:"));
        txtCustomer = new JTextField();
        inputPanel.add(txtCustomer);

        inputPanel.add(new JLabel("Product Name:"));
        txtProductName = new JTextField();
        inputPanel.add(txtProductName);

        inputPanel.add(new JLabel("Unit Price (INR):"));
        txtPrice = new JTextField();
        inputPanel.add(txtPrice);

        inputPanel.add(new JLabel("Quantity:"));
        txtQty = new JTextField("1");
        inputPanel.add(txtQty);

        inputPanel.add(new JLabel("GST Rate (%):"));
        txtGstRate = new JTextField("18.0");
        inputPanel.add(txtGstRate);

        inputPanel.add(new JLabel("Interstate Transaction?"));
        chkInterstate = new JCheckBox("Yes (Applies IGST only)");
        inputPanel.add(chkInterstate);

        JButton btnCalculate = new JButton("Calculate & Save Invoice");
        inputPanel.add(new JLabel()); // Empty spacer
        inputPanel.add(btnCalculate);

        // --- Create the Output Ledger (Bottom) ---
        txtOutput = new JTextArea();
        txtOutput.setEditable(false);
        txtOutput.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(txtOutput);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Generated System Ledger"));

        // Add Action Listener to Button
        btnCalculate.addActionListener((ActionEvent e) -> processInvoice());

        // Assemble the frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        setLocationRelativeTo(null); // Centers the window on your screen
    }

    private void processInvoice() {
        try {
            // 1. Gather User Input
            String customer = txtCustomer.getText();
            String prodName = txtProductName.getText();
            double price = Double.parseDouble(txtPrice.getText());
            int qty = Integer.parseInt(txtQty.getText());
            double gstRate = Double.parseDouble(txtGstRate.getText());
            boolean isInterstate = chkInterstate.isSelected();
            

            // 2. Create a temporary product object for the calculation
            Product product = new Product(1, prodName, "0000", price, gstRate);

            // 3. Run Business Logic (Your 40% error reduction engine)
            Invoice invoice = gstService.calculateInvoiceMetrics(customer, product, qty, isInterstate);

            // 4. Save to MySQL
            boolean saved = invoiceDao.saveInvoice(invoice);

            // --- NEW: Generate PDF ---
            // Calls the new utility class to generate and save the PDF to the desktop
            String pdfPath = com.gstsystem.util.PdfGenerator.generateInvoicePdf(invoice, product);

            // 5. Display the digital receipt
            StringBuilder sb = new StringBuilder();
            sb.append("=====================================\n");
            sb.append("         TAX INVOICE GENERATED       \n");
            sb.append("=====================================\n");
            sb.append("Customer: ").append(invoice.getCustomerName()).append("\n");
            sb.append("Product : ").append(product.getName()).append(" (Qty: ").append(qty).append(")\n");
            sb.append("-------------------------------------\n");
            sb.append(String.format("Taxable Base  : INR %.2f\n", invoice.getTaxableValue()));
            sb.append(String.format("CGST Applied  : INR %.2f\n", invoice.getCgst()));
            sb.append(String.format("SGST Applied  : INR %.2f\n", invoice.getSgst()));
            sb.append(String.format("IGST Applied  : INR %.2f\n", invoice.getIgst()));
            sb.append("-------------------------------------\n");
            sb.append(String.format("GROSS TOTAL   : INR %.2f\n", invoice.getTotalAmount()));
            sb.append("=====================================\n");
            
            if (saved) {
                sb.append("[SUCCESS] Record committed to MySQL Database.\n");
            } else {
                sb.append("[ERROR] Database transaction failed.\n");
            }

            // --- NEW: Output PDF Status ---
            if (pdfPath != null) {
                sb.append("[SUCCESS] PDF Slip saved to:\n").append(pdfPath).append("\n");
            }

            txtOutput.setText(sb.toString());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for Price, Quantity, and GST Rate.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}