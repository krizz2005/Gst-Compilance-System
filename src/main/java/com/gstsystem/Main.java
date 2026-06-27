package com.gstsystem;

import java.sql.Connection;

import javax.swing.SwingUtilities;

import com.gstsystem.dao.InvoiceDao;
import com.formdev.flatlaf.FlatLightLaf;
import com.gstsystem.model.Invoice;
import com.gstsystem.model.Product;
import com.gstsystem.service.GstService;
import com.gstsystem.ui.MainFrame;
import com.gstsystem.util.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        System.out.println("====== Starting GST Compliance Automated System ======");

        try {
            // Replaces the old Windows 98 look with a sleek, modern flat design
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Failed to initialize modern UI.");
        }
        
        // 1. Verify Database Availability
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.err.println("[CRITICAL] Database target unreachable. Shutting down execution.");
            return;
        }
        
        SwingUtilities.invokeLater(() -> {
            MainFrame dashboard = new MainFrame();
            dashboard.setVisible(true); // This opens the window!
        });

        // 2. Initialize Core Services and DAO layers
        GstService gstService = new GstService();
        InvoiceDao invoiceDao = new InvoiceDao();

        // 3. Setup a mock product representation (e.g., Electronic Device under 18% slab)
        Product macbook = new Product(1, "Premium Corporate Laptop", "84713010", 120000.00, 18.0);

        System.out.println("\n[PROCESS] Executing tax distribution algorithms for item: " + macbook.getName());
        
        // 4. Run calculations for an intra-state transaction
        Invoice processingInvoice = gstService.calculateInvoiceMetrics(
            "Nanda Trading Corp", 
            macbook, 
            2, 
            false
        );

        // 5. Output calculations to the console to confirm accuracy
        System.out.println("----------------------------------------------");
        System.out.println("Customer Name  : " + processingInvoice.getCustomerName());
        System.out.println("Taxable Base   : INR " + processingInvoice.getTaxableValue());
        System.out.println("Computed CGST  : INR " + processingInvoice.getCgst());
        System.out.println("Computed SGST  : INR " + processingInvoice.getSgst());
        System.out.println("Computed IGST  : INR " + processingInvoice.getIgst());
        System.out.println("Gross Total    : INR " + processingInvoice.getTotalAmount());
        System.out.println("----------------------------------------------");

        // 6. Save the final calculated record to the database
        System.out.println("[PROCESS] Committing verified transactional data models to MySQL datastore...");
        boolean persistenceCheck = invoiceDao.saveInvoice(processingInvoice);

        if (persistenceCheck) {
            System.out.println("[SUCCESS] GST Compliance Ledger updated securely. System idling smoothly.\n");
        } else {
            System.err.println("[FAILURE] Transaction aborted by persistence engine layer.");
        }
    }
}