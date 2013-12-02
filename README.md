YouDrive
========
POC for the YouDrive project (part of the Software Engineering class requirements).

0. Create a mysql user called "team7" with password "overload"
1. Create database called team7 on localhost. Connection details are in Constants.java if you want to take a look.
1. Create the tables in the order outlined in the schema.sql document
2. Populate the tables using data in quick-data.sql
4. jane:test, james:test, trevor:test, rod:test are username:password pairs that are admins
5. test:test is a customer:password pair that is not an admin
5. Hosted here: http://uml.cs.uga.edu:8080/team7_youride (This is the official url)
6. http://cobweb.cs.uga.edu/~kochut/Teaching/x050/ProjectInfo-Programming.html
7. http://cobweb.cs.uga.edu/~kochut/Teaching/x050/TermProject.html



Folder structure:

1. *com.youdrive.helpers* -- Contains the database access object files which implement the interfaces e.g. UserDAO,LocationDAO,VehicleDAO,VehicleTypeDAO, etc
2. *com.youdrive.interfaces* -- Contains the interfaces e.g. IVehicleManager,IVehicleTypeManager,ILocationManager,IUserManager,etc
3. *com.youdrive.models* -- Contains the models e.g. Vehicle, VehicleType,Membership,Location,User,Location and Reservation
4. *com.youdrive.servlets* -- Contains the specific servlets e.g. LocationManagement,UserManagement,VehicleManagement,VehicleTypeManagement, etc
5. *com.youdrive.tests* -- Some junit tests (unfinished)
6. *com.youdrive.util* -- Contains constants such as the table names in Constants.java and ConnectionManager singleton class to create a single Connection object.

Project Status:

1. Adds/Edits on the admin backend are 90% done.
2. Deletions on the admin backend are completed except for Users and Vehicles
3. Need to decide how we are handling reservations and account terminations

Implementation Notes:

1. All adds to the database return the added item's id or 0 if the addition failed
2. All deletes and updates to the database return true if successful and false otherwise.
3. All counts return -1 if an exception occurred or n where n can be 0 which represents the count of whatever is being requested.



Users table
===========
emailAddress and usernames are unique in this table. Admins are only required to provide the following:

1. firstName
2. lastName
3. userName
4. password
5. emailAddress

Attribute membershipLevel is linked to the  Memberships table which shows what type of Membership the user has.


Vehicles table
==============
All attributes of this table are required except isAvailable which defaults to 0 or false. This table is linked to the Locations tables via assignedLocation.


Locations, VehicleTypes
========================
All attributes of these table are required (type,hourlyPrice,dailyPrice)

Comments table
===============
Linked to the Users table via the author field
