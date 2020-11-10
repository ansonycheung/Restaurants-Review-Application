# Create the schema if necessary.
CREATE SCHEMA IF NOT EXISTS ReviewApplication;
USE ReviewApplication;

DROP TABLE IF EXISTS Reservations;
DROP TABLE IF EXISTS Recommendations;
DROP TABLE IF EXISTS Reviews;
DROP TABLE IF EXISTS FoodCartRestaurant;
DROP TABLE IF EXISTS TakeOutRestaurant;
DROP TABLE IF EXISTS SitDownRestaurant;
DROP TABLE IF EXISTS Restaurants;
DROP TABLE IF EXISTS Companies;
DROP TABLE IF EXISTS CreditCards;
DROP TABLE IF EXISTS Users;

CREATE TABLE Users
(
    UserName  VARCHAR(255),
    Password  VARCHAR(255) NOT NULL,
    FirstName VARCHAR(255) NOT NULL,
    LastName  VARCHAR(255) NOT NULL,
    Email     VARCHAR(255) NOT NULL,
    Phone     VARCHAR(255),
    CONSTRAINT pk_Users_UserName PRIMARY KEY (UserName)
);

CREATE TABLE CreditCards
(
    CardNumber BIGINT,
    Expiration DATE         NOT NULL,
    UserName   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_CreditCards_CardNumber PRIMARY KEY (CardNumber),
    CONSTRAINT fk_CreditCards_Username FOREIGN KEY (UserName)
        REFERENCES Users (UserName)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Companies
(
    CompanyName VARCHAR(255),
    About       VARCHAR(1024),
    CONSTRAINT pk_Companies_CompanyName PRIMARY KEY (CompanyName)
);

CREATE TABLE Restaurants
(
    RestaurantId INT AUTO_INCREMENT,
    Name         VARCHAR(255)  NOT NULL,
    Description  VARCHAR(1024) NOT NULL,
    Menu         VARCHAR(1024) NOT NULL,
    Hours        VARCHAR(1024) NOT NULL,
    Active       BOOLEAN       NOT NULL,
    CuisineType  ENUM ('AFRICAN', 'AMERICAN', 'ASIAN', 'EUROPEAN', 'HISPANIC'),
    Street1      VARCHAR(255)  NOT NULL,
    Street2      VARCHAR(255),
    City         VARCHAR(255)  NOT NULL,
    State        VARCHAR(255)  NOT NULL,
    Zip          INT           NOT NULL,
    CompanyName  VARCHAR(255),
    CONSTRAINT pk_Restaurants_RestaurantId PRIMARY KEY (RestaurantId),
    CONSTRAINT fk_Restaurants_CompanyName FOREIGN KEY (CompanyName)
        REFERENCES Companies (CompanyName)
        ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE SitDownRestaurant
(
    RestaurantId INT,
    Capacity     INT NOT NULL,
    CONSTRAINT pk_SitDownRestaurant_RestaurantId PRIMARY KEY (RestaurantId),
    CONSTRAINT fk_SitDownRestaurant_RestaurantId FOREIGN KEY (RestaurantId)
        REFERENCES Restaurants (RestaurantId)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE TakeOutRestaurant
(
    RestaurantId INT,
    MaxWaitTime  INT NOT NULL,
    CONSTRAINT pk_TakeOutRestaurant_RestaurantId PRIMARY KEY (RestaurantId),
    CONSTRAINT fk_TakeOutRestaurant_RestaurantId FOREIGN KEY (RestaurantId)
        REFERENCES Restaurants (RestaurantId)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE FoodCartRestaurant
(
    RestaurantId INT,
    Licensed     BOOLEAN NOT NULL,
    CONSTRAINT pk_FoodCartRestaurant_RestaurantId PRIMARY KEY (RestaurantId),
    CONSTRAINT fk_FoodCartRestaurant_RestaurantId FOREIGN KEY (RestaurantId)
        REFERENCES Restaurants (RestaurantId)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Reviews
(
    ReviewId     INT AUTO_INCREMENT,
    Created      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    Content      VARCHAR(1024)                       NOT NULL,
    Rating       DECIMAL(2, 1)                       NOT NULL,
    UserName     VARCHAR(255),
    RestaurantId INT,
    CONSTRAINT pk_Reviews_ReviewId PRIMARY KEY (ReviewId),
    CONSTRAINT uq_Reviews_Review UNIQUE (UserName, RestaurantId),
    CONSTRAINT fk_Reviews_UserName FOREIGN KEY (UserName)
        REFERENCES Users (UserName)
        ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_Reviews_RestaurantId FOREIGN KEY (RestaurantId)
        REFERENCES Restaurants (RestaurantId)
        ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE Recommendations
(
    RecommendationId INT AUTO_INCREMENT,
    UserName         VARCHAR(255),
    RestaurantId     INT,
    CONSTRAINT pk_Recommendations_RecommendationId PRIMARY KEY (RecommendationId),
    CONSTRAINT fk_Recommendations_UserName FOREIGN KEY (UserName)
        REFERENCES Users (UserName)
        ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_Recommendations_RestaurantId FOREIGN KEY (RestaurantId)
        REFERENCES Restaurants (RestaurantId)
        ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE Reservations
(
    ReservationId INT AUTO_INCREMENT,
    Start         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    End           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Size          INT NOT NULL,
    UserName      VARCHAR(255),
    RestaurantId  INT,
    CONSTRAINT pk_Reservations_ReservationId PRIMARY KEY (ReservationId),
    CONSTRAINT fk_Reservations_UserName FOREIGN KEY (UserName)
        REFERENCES Users (UserName)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_Reservations_RestaurantId FOREIGN KEY (RestaurantId)
        REFERENCES SitDownRestaurant (RestaurantId)
        ON UPDATE CASCADE ON DELETE CASCADE
);
