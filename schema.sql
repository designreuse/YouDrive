Create table Locations(
id int not null primary key auto_increment, 
name varchar(255) unique not null, 
address varchar(255) not null, 
capacity int not null
) Engine=InnoDB;

Create table VehicleTypes(
id int not null primary key auto_increment,
type varchar(255) unique not null,
hourlyPrice decimal(10,2) not null,
dailyPrice decimal(10,2) not null
) Engine=InnoDB;

create table Memberships(
id int not null primary key auto_increment,
name varchar(255) unique not null,
price decimal(10,2) not null,
duration int not null
) Engine=InnoDB;

create table Users(
id int not null primary key auto_increment,
username varchar(255) unique not null,
password varchar(255) not null,
firstName varchar(255) not null,
lastName varchar(255) not null,
state varchar(3),
license varchar(255),
email varchar(255) unique not null,
address text,
ccType varchar(255),
ccNumber varchar(17),
ccSecurityCode varchar(10),
ccExpirationDate varchar(7),
isAdmin boolean,
memberExpiration datetime,
membershipLevel int,
registrationDate datetime not null,
CONSTRAINT fk_users_memberships FOREIGN KEY (membershipLevel) references Memberships(id)
) Engine=InnoDB;

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
CONSTRAINT fk_vehicles_vehicletypes FOREIGN KEY (vehicleType) references VehicleTypes(id),
CONSTRAINT fk_vehicles_locations FOREIGN KEY (assignedLocation) references Locations(id)
) Engine=InnoDB;

create table Reservations(
id int not null primary key auto_increment,
customerID int not null,
locationID int not null,
vehicleID int not null,
reservationStart datetime not null,
reservationEnd datetime  not null,
CONSTRAINT fk_reservations_users FOREIGN KEY (customerID) references Users(id),
CONSTRAINT fk_reservations_locations FOREIGN KEY (locationID) references Locations(id),
CONSTRAINT fk_reservations_vehicles FOREIGN KEY (vehicleID) references Vehicles(id)
) Engine=InnoDB;

create table ReservationStatus(
id int not null primary key auto_increment,
reservationID int not null,
dateAdded datetime not null,
reservationStatus ENUM ('Created', 'Cancelled', 'Returned')  not null,
CONSTRAINT fk_reservationstatus_reservations FOREIGN KEY (reservationID) references Reservations(id)
) Engine=InnoDB;

create table Comments(
id int not null primary key auto_increment,
createdOn datetime not null,
comment text not null,
author int not null,
vehicleID int not null,
CONSTRAINT fk_comments_users FOREIGN KEY (author) references Users(id),
CONSTRAINT fk_comments_vehicles FOREIGN KEY (vehicleID) references Vehicles(id)
) Engine=InnoDB;
