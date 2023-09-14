# Pharmanuman
Abstract:
Pharmanuman is a web application designed to streamline the supply chain in the pharmaceutical industry, empowering local pharmacies and stockists to efficiently manage their inventory. This application facilitates collaboration between stockists and local pharmacies, enabling direct ordering of medicines. By eliminating traditional processes like phone calls and paper-based order notes, the application significantly improves efficiency and accuracy. The demand forecasting feature has been removed from Pharmanuman, focusing solely on inventory management and order placement. The web app remains accessible from any device with an internet connection and maintains a seamless and secure user experience, revolutionizing the ordering process in the pharmaceutical industry.

## Table Of Content
    1. Features
    2. Technologies Used
    3. Tools Used
    4. Installation
    5. Screenshots

    
    
## Features

    1. User(Pharmacy, Stokcist, Pharmaceutical Company) can register, login and logout.
    2. Each user can manage their inventory.
    3. Pharmacy can order medicine from stockist.
    4. Stockist receive order and give reponse to received order.

## Technologies Used

1. Spring Boot
2. Thymeleaf
3. MySQL
4. Spring Data JPA
5. Spring Security
6. HTML, CSS, JavaScript
7. Bootstrap

## Tools Used
1. STS IDE
2. Mysql WorkBench
3. Git

## Installation
1. Clone this repository to specific folder
https://github.com/Prabin-sc1/Pharmanuman 

2. Open STS IDE
a. At the top left corner, click one file and choose Open Project file from system.
ss1
b. Import the folder where you clone the project
ss2

c. Click on finish...

3. Now it's time to setup database
A. open application.properties


# server.port= 9494
# spring.datasource.url= jdbc:mysql://localhost:3306/pharmanuman
# spring.datasource.username= root
# spring.datasource.password= root
# spring.datasource.driver-class-name= com.mysql.cj.jdbc.Driver
# spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
# spring.jpa.hibernate.ddl-auto= update

Prerequisite :
a. You have to create database namely loginregister
b. Put your database user and password
c. You should be connected with internet

ss3

4. Now run the app as spring boot app

5. Open your favourite browser and copy the below url and paste it in your browser 
  http://localhost:9494/

##  Screenshots

  

