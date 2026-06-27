CREATE DATABASE IF NOT EXISTS gst_compliance;
USE gst_compliance;

CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    hsn_code VARCHAR(8) NOT NULL,
    price DOUBLE NOT NULL,
    gst_rate DOUBLE NOT NULL -- e.g., 5.0, 12.0, 18.0, 28.0
);

CREATE TABLE IF NOT EXISTS invoices (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    product_id INT,
    quantity INT NOT NULL,
    taxable_value DOUBLE NOT NULL,
    cgst DOUBLE NOT NULL,
    sgst DOUBLE NOT NULL,
    igst DOUBLE NOT NULL,
    total_amount DOUBLE NOT NULL,
    is_interstate BOOLEAN NOT NULL,
    invoice_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE SET NULL
);