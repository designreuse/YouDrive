YouDrive
========
POC for the YouDrive project (part of the Software Engineering class requirements).

0. Create database called YouDrive on localhost. If you decide to create a differently named database, update the JDBC_URL value in Constants.java in the .util package.
1. Create the tables in the order outlined in the schema.sql document
2. Create Admin user by going to http://localhost:8080/YouDrive/adduser.jsp
3. Go to /admin.jsp to start adding/editing/deleting items.


Folder structure:

1. com.youdrive.helpers -- Contains the database access object files which implement the interfaces e.g. UserDAO,LocationDAO,VehicleDAO,VehicleTypeDAO, etc
2. com.youdrive.interfaces -- Contains the interfaces e.g. IVehicleManager,IVehicleTypeManager,ILocationManager,IUserManager,etc
3. com.youdrive.models -- Contains the models e.g. Vehicle, VehicleType,Membership,Location,User,Location and Reservation
4. com.youdrive.servlets -- Contains the specific servlets e.g. LocationManagement,UserManagement,VehicleManagement,VehicleTypeManagement, etc
5. com.youdrive.tests -- Some junit tests (unfinished)
6. com.youdrive.util -- Contains constants such as the table names in Constants.java and ConnectionManager singleton class to create a single Connection object.

