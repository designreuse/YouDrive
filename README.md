YouDrive
========
Database Schema:
Create table Locations(
id int not null primary key auto_increment, 
name varchar(255) unique not null, 
address varchar(255) not null, 
capacity int not null
);

Create table VehicleTypes(
id int not null primary key auto_increment,
type varchar(255) unique not null,
hourlyPrice decimal(10,2) not null,
dailyPrice decimal(10,2) not null
);

create table Memberships(
id int not null primary key auto_increment,
name varchar(255) unique not null,
price decimal(10,2) not null,
duration int not null
);

create table Users(
id int not null primary key auto_increment,
username varchar(255) unique not null,
password varchar(255) not null,
firstName varchar(255) not null,
lastName varchar(255) not null,
state varchar(255),
license varchar(255),
email varchar(255) unique not null,
address text,
ccType varchar(255),
ccNumber int,
ccSecurityCode int,
ccExpirationDate varchar(6),
isAdmin boolean,
memberExpiration date,
membershipLevel int,
FOREIGN KEY (membershipLevel) references Memberships(id)
);


Create table Vehicles(
id int not null primary key auto_increment,
make varchar(255) not null,
model varchar(255) not null,
year int not null,
tag varchar(255) unique not null,
mileage int not null,
lastServiced date not null,
isAvailable boolean default 0,
vehicleType int not null,
assignedLocation int not null,
FOREIGN KEY (vehicleType) references VehicleTypes(id),
FOREIGN KEY (assignedLocation) references Locations(id)
);

create table Reservations(
id int not null primary key auto_increment,
customerID int not null,
locationID int not null,
vehicleID int not null,
timePickup datetime not null,
reservationStart datetime not null,
reservationEnd datetime  not null,
FOREIGN KEY (customerID) references Users(id),
FOREIGN KEY (locationID) references Locations(id),
FOREIGN KEY (vehicleID) references Vehicles(id)
);

create table Comments(
id int not null primary key auto_increment,
createdOn date not null,
comment text not null,
author int not null,
FOREIGN KEY (author) references Users(id)
);

Run the application and navigate to adduser.jsp and create a user (make sure you check isAdmin) so that you'll be able to access admin functions.
