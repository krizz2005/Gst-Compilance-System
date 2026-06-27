package com.gstsystem.dao;

import com.gstsystem.model.Invoice;
import com.gstsystem.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InvoiceDao {

    public boolean saveInvoice(Invoice invoice) {
        String sql = "INSERT INTO invoices (customer_name, product_id, quantity, taxable_value, cgst, sgst, igst, total_amount, is_interstate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DatabaseConnection.getConnection();

        if (conn == null) return false;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, invoice.getCustomerName());
            stmt.setInt(2, invoice.getProduct().getId());
            stmt.setInt(3, invoice.getQuantity());
            stmt.setDouble(4, invoice.getTaxableValue());
            stmt.setDouble(5, invoice.getCgst());
            stmt.setDouble(6, invoice.getSgst());
            stmt.setDouble(7, invoice.getIgst());
            stmt.setDouble(8, invoice.getTotalAmount());
            stmt.setBoolean(9, invoice.isInterstate());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[DB ERROR] Failed to save invoice transaction: " + e.getMessage());
            return false;
        }
    }
}