CREATE DATABASE bookstore;

USE bookstore;

CREATE TABLE Book(
	book_id 				INT AUTO_INCREMENT,
	title 					VARCHAR(110) NOT NULL,
    main_genre 				VARCHAR(50) NOT NULL,
    place_publication   	VARCHAR(110) NOT NULL,
	year_publication		INTEGER NOT NULL,
    price					DECIMAL(10,2) NOT NULL,
    amount_books 			INT NOT NULL,
    
    CONSTRAINT non_negative_price CHECK (price >= 0.0),
    CONSTRAINT non_negative_year CHECK (year_publication > 0), 
    CONSTRAINT non_negative_amount_books CHECK (amount_books > 0),
    
    PRIMARY KEY (book_id)
);

CREATE TABLE Book_Author(
	book_id					INT NOT NULL,
    author_id				INT NOT NULL,
    
    PRIMARY KEY (book_id, author_id)
);

CREATE TABLE Address(
	address_id 			INT AUTO_INCREMENT,
	thoroughfare		VARCHAR(300) NOT NULL,
    neighborhood		VARCHAR(150) NOT NULL,
    complement			VARCHAR(60),
    house_number	    INTEGER NOT NULL,
    zip_code 			VARCHAR(20) NOT NULL,
    
    CONSTRAINT non_negative_house_number CHECK (house_number > 0),
    
    PRIMARY KEY (address_id)
);

CREATE TABLE Author(
	author_id 			INT AUTO_INCREMENT,
    author_name			VARCHAR(110) NOT NULL,
    age 				INTEGER NOT NULL,
    email 				VARCHAR(100) NOT NULL,
    address_id			INT NOT NULL,
    
    CONSTRAINT author_invalid_age CHECK (age > 0),
    
    PRIMARY KEY (author_id),
    FOREIGN KEY (address_id) REFERENCES Address(address_id)
);

CREATE TABLE Client_t(
	client_t_id 		INT AUTO_INCREMENT,
    client_t_name 		VARCHAR(110) NOT NULL,
    age 				INTEGER NOT NULL,
    email				VARCHAR(100) NOT NULL,
    isOnePiece 			BOOLEAN DEFAULT FALSE,
    team				VARCHAR(50),
    client_t_password 	VARCHAR(50) NOT NULL,
    address_id			INT NOT NULL,
    
    CONSTRAINT client_t_invalid_age CHECK (age > 0),
    
    PRIMARY KEY (client_t_id),
    FOREIGN KEY (address_id) REFERENCES Address(address_id)
);

CREATE TABLE Seller(
	seller_id 			INT AUTO_INCREMENT,
	seller_name 		VARCHAR(110) NOT NULL,
    age					INTEGER NOT NULL,
    email 				VARCHAR(100) NOT NULL,
    seller_password		VARCHAR(50) NOT NULL,
    address_id 			INT NOT NULL,
    
	CONSTRAINT seller_age CHECK (age > 0),
    
    PRIMARY KEY (seller_id),
    FOREIGN KEY (address_id) REFERENCES Address(address_id)
);

CREATE TABLE Manager(
	manager_id 			INT AUTO_INCREMENT,
	manager_name 		VARCHAR(110) NOT NULL,
    age					INTEGER NOT NULL,
    email 				VARCHAR(100) NOT NULL,
    manager_password	VARCHAR(50) NOT NULL,
    address_id 			INT NOT NULL,
    
	CONSTRAINT manager_age CHECK (age > 0),
    
    PRIMARY KEY (manager_id),
    FOREIGN KEY (address_id) REFERENCES Address(address_id)
);

CREATE TABLE Order_t(
	order_t_id			INT AUTO_INCREMENT,
	total_amount		DECIMAL(20, 2) NOT NULL,
    client_t_id			INT NOT NULL,
    
    CONSTRAINT non_negative_total_amount_order CHECK (total_amount >= 0.0),
    
    PRIMARY KEY (order_t_id),
    FOREIGN KEY(client_t_id) REFERENCES Client_t(client_t_id)
);

CREATE TABLE Order_Book(
	order_t_id 			INT NOT NULL,
    book_id 			INT NOT NULL,
    
    PRIMARY KEY (order_t_id, book_id)
);

CREATE TABLE Payment(
	payment_id			INT AUTO_INCREMENT,
    total_amount 		DECIMAL(20, 2) NOT NULL,
    payment_status		INT NOT NULL,
    payment_type 		INT NOT NULL,
    payment_time		TIMESTAMP NOT NULL,
    client_t_id 		INT NOT NULL,
    
    CONSTRAINT non_negative_total_amount_payment CHECK (total_amount >= 0.0),
    
    PRIMARY KEY (payment_id),
    FOREIGN KEY (client_t_id) REFERENCES Client_t(client_t_id)
);

CREATE TABLE Payment_Seller(
	payment_id 			INT NOT NULL,
    seller_id 			INT NOT NULL,
    
    PRIMARY KEY (payment_id, seller_id)
);

CREATE TABLE Order_Payment(
	order_t_id			INT NOT NULL,
    payment_id			INT NOT NULL,
    
    PRIMARY KEY (order_t_id, payment_id)
);