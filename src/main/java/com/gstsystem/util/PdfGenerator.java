package com.gstsystem.util;

import com.gstsystem.model.Invoice;
import com.gstsystem.model.Product;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PdfGenerator {

    public static String generateInvoicePdf(Invoice invoice, Product product) {
        // Save the PDF to the user's home folder (Desktop/Documents)
        String userHome = System.getProperty("user.home");
        String fileName = "GST_Invoice_" + System.currentTimeMillis() + ".pdf";
        String filePath = Paths.get(userHome, "Desktop", fileName).toString();

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Set up Fonts
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font regularFont = new Font(Font.COURIER, 12, Font.NORMAL);

            // Build Document
            document.add(new Paragraph("TAX INVOICE - GST COMPLIANCE SYSTEM", titleFont));
            document.add(new Paragraph("Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
            document.add(new Paragraph("---------------------------------------------------------"));
            
            document.add(new Paragraph("Customer Name : " + invoice.getCustomerName(), regularFont));
            document.add(new Paragraph("Product Name  : " + product.getName() + " (Qty: " + invoice.getQuantity() + ")", regularFont));
            
            document.add(new Paragraph("---------------------------------------------------------"));
            document.add(new Paragraph(String.format("Taxable Base  : INR %.2f", invoice.getTaxableValue()), regularFont));
            document.add(new Paragraph(String.format("CGST Applied  : INR %.2f", invoice.getCgst()), regularFont));
            document.add(new Paragraph(String.format("SGST Applied  : INR %.2f", invoice.getSgst()), regularFont));
            document.add(new Paragraph(String.format("IGST Applied  : INR %.2f", invoice.getIgst()), regularFont));
            document.add(new Paragraph("---------------------------------------------------------"));
            document.add(new Paragraph(String.format("GROSS TOTAL   : INR %.2f", invoice.getTotalAmount()), titleFont));
            
            document.add(new Paragraph("\nThank you for your business."));

            document.close();
            return filePath;
            
        } catch (Exception e) {
            System.err.println("Error generating PDF: " + e.getMessage());
            return null;
        }
    }
}