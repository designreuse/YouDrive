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
isAdmin boolean not null default 0,
memberExpiration datetime,
membershipLevel int,
registrationDate datetime not null,
isActive boolean not null default 1,
INDEX membership_ind(membershipLevel),
CONSTRAINT fk_users_memberships 
FOREIGN KEY (membershipLevel) REFERENCES Memberships(id)
ON UPDATE CASCADE
) Engine=InnoDB;

Create table Vehicles(
id int not null primary key auto_increment,
make varchar(255) not null,
model varchar(255) not null,
year int not null,
tag varchar(255) unique not null,
mileage int not null,
lastServiced date not null,
vehicleType int not null,
assignedLocation int not null,
INDEX vehicletype_ind(vehicleType),
INDEX location_ind(assignedLocation),
CONSTRAINT fk_vehicles_vehicletypes FOREIGN KEY (vehicleType) references VehicleTypes(id)
ON UPDATE CASCADE ON DELETE CASCADE, 
CONSTRAINT fk_vehicles_locations FOREIGN KEY (assignedLocation) references Locations(id)
ON UPDATE CASCADE ON DELETE CASCADE
) Engine=InnoDB;

create table Reservations(
id int not null primary key auto_increment,
customerID int not null,
locationID int not null,
vehicleID int not null,
reservationStart datetime not null,
reservationEnd datetime  not null,
INDEX user_ind(customerID),
INDEX location_ind(locationID),
INDEX vehicle_ind(vehicleID),
CONSTRAINT fk_reservations_users FOREIGN KEY (customerID) references Users(id)
ON UPDATE CASCADE ON DELETE CASCADE, 
CONSTRAINT fk_reservations_locations FOREIGN KEY (locationID) references Locations(id)
ON UPDATE CASCADE ON DELETE CASCADE, 
CONSTRAINT fk_reservations_vehicles FOREIGN KEY (vehicleID) references Vehicles(id)
ON UPDATE CASCADE ON DELETE CASCADE
) Engine=InnoDB;

create table ReservationStatus(
id int not null primary key auto_increment,
reservationID int not null,
dateAdded datetime not null,
reservationStatus ENUM ('Created', 'Cancelled', 'Returned')  not null,
INDEX reservations_ind(reservationID),
CONSTRAINT fk_reservationstatus_reservations FOREIGN KEY (reservationID) references Reservations(id)
ON UPDATE CASCADE ON DELETE CASCADE
) Engine=InnoDB;

create table Comments(
id int not null primary key auto_increment,
createdOn datetime not null,
comment text not null,
author int not null,
vehicleID int not null,
INDEX users_ind(author),
INDEX vehicle_ind(vehicleID),
CONSTRAINT fk_comments_users FOREIGN KEY (author) references Users(id)
ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_comments_vehicles FOREIGN KEY (vehicleID) references Vehicles(id)
ON UPDATE CASCADE ON DELETE CASCADE
) Engine=InnoDB;
