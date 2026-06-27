package com.gstsystem.service;
import com.gstsystem.model.Invoice;
import com.gstsystem.model.Product;

public class GstService {

    public Invoice calculateInvoiceMetrics(String customer, Product product, int qty, boolean isInterstate) {
        Invoice invoice = new Invoice();
        invoice.setCustomerName(customer);
        invoice.setProduct(product);
        invoice.setQuantity(qty);
        invoice.setInterstate(isInterstate);

        // Core Math calculations
        double taxableValue = product.getPrice() * qty;
        double totalGst = taxableValue * (product.getGstRate() / 100.0);

        invoice.setTaxableValue(roundToTwoDecimals(taxableValue));

        if (isInterstate) {
            // IGST applies for interstate transactions
            invoice.setIgst(roundToTwoDecimals(totalGst));
            invoice.setCgst(0.0);
            invoice.setSgst(0.0);
        } else {
            // CGST and SGST split evenly for intra-state transactions
            double halfGst = totalGst / 2.0;
            invoice.setCgst(roundToTwoDecimals(halfGst));
            invoice.setSgst(roundToTwoDecimals(halfGst));
            invoice.setIgst(0.0);
        }

        double totalAmount = taxableValue + totalGst;
        invoice.setTotalAmount(roundToTwoDecimals(totalAmount));

        return invoice;
    }

    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}