CREATE DATABASE IF NOT EXISTS transport_office;
USE transport_office;

CREATE TABLE IF NOT EXISTS bus_pass (
    pass_id VARCHAR(30) PRIMARY KEY,
    reg_no VARCHAR(30) NOT NULL,
    name VARCHAR(100) NOT NULL,
    route_no INT NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    UNIQUE KEY uq_bus_pass_reg (reg_no)
);
