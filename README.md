

#Project Planner 


##Introduction
In this document the project description will be discussed, the project requirements will be looked at, the functionality and finally APIs used will be discussed. The screen will be looked at and what the user can do on each screen. A data dictionary was created to show all the data items and their type, description and where they were acquired. 
##Description
This application would be used for anyone who wishes to plan a future project. In the application the user can add a project. In this project a Project name, start date, estimated finish date, No of steps, members and location is added. The user can view all of the projects on a map with markers showing all the projects locations, these markers will also show that project name and address. Also google play services is needed for this application.
##Requirements
This project has a number of requirements these project are:
..*A simple add, edit delete and search functionality. 
..*Multiple screens and fragments.
..*Different widgets such as TextViews, EditTexts and Buttons.
..*An advanced navigation component.
..*Some way for the projects data to be store Eg. SQLiteDatabase
..*Input Validation so the application wont crash when the user enters something they aren’t meant to.
..*At least one Third Party API.
##Functionality
In this application users have the ability to sign-in (using FireBase)  then add a project with that project save a location add drop a marker on a map. The user then has the ability to edit, delete and search for that project. They can also look at all the projects markers on a map and seeing where these projects are located. If the developer has enough time they will develop a notification a day before the project is due.
##APIs Used
Google Places API which enables the user to use placepicker and pick a location for the project after selecting a place the longitude, latitude and address of this location will be saved.
Another API that is used is Google Maps this API has the ability to display a map on the screen. This application uses the API to display the markers chosen by the user by the Google places API.
The last API this application uses is the Google Firebase API to authenticate the user and to store the data as a JSON type.
##Firebase
###Authentication  
In the application users can login in using their Google account, their email or their Facebook account. The dataflow diagram below shows how the authentication for firebase works.

  ![alt text](https://github.com/MichWhite/ProjectPlanner/diagram.png)

##UML Diagram 

![alt text](https://github.com/MichWhite/ProjectPlanner/uml Diagram.png)




##Application Screens
The first screen the users will see is the is the splash screen this screen will display an image after the image disappears a login prompt is displayed which asks for the users Google account details or email and password. After the user has successfully log-in they will see their list of projects which are saved using Firebase(unless the developer does not get in frebase working in time then SQLiteDatabase will be used.).


  
![alt text](https://github.com/MichWhite/ProjectPlanner/splash-screen.png)

![alt text](https://github.com/MichWhite/ProjectPlanner/login.png)


	
The user will then be shown a list of projects that they have previously saved. They will also be a navigation drawer that the use can navigate to different activities and fragments such as Add, Search, Map and Sign Out.

 ![alt text](https://github.com/MichWhite/ProjectPlanner/drawer.png)

The user can Then Add a Project this will display the add Project screen allowing the user to enter the project Name, Project Start Date, estimated finish date, number of steps, Project Members and Project Location. All this data will be store so the use can edit and search for it later. 
 ![alt text](https://github.com/MichWhite/ProjectPlanner/add.png)

The user can press the Add Location Button to pick a location for this project. This screen is displayed using the Google Places API. On this screen the user can search for a location, get their current location or drop the marker anywhere on the map. They can then select that place and the location, latitude and longitude will be added to the Database and project.
 
![alt text](https://github.com/MichWhite/ProjectPlanner/map.png)


After Adding the project the user can see their project added to the lists of projects and they can edit the project by clicking on the details or delete it by clicking on the delete icon which they will be then prompted with a message asking to confirm to delete their project.

![alt text](https://github.com/MichWhite/ProjectPlanner/list.png)



 



If the user clicks on “Project Map” from the navigation drawer they can see all their projects location in one map, they can also see the name and address of the Project. This can be seen in the image below
![alt text](https://github.com/MichWhite/ProjectPlanner/add-to-map.png)

 
Just from looking at the screens that are displayed it can been seen that the developer has meet all of the requirement discussed earlier.



##Project Evaluation
The Application meets all off the requirements and is deemed a success although some of the features were not implemented because there wasn’t enough time. Features such as the notification alarm that would alert users a day before the projects estimated finish date.

##References
[https://firebase.google.com/docs/]
[https://developers.google.com/maps/documentation/android-api/]
[https://developer.android.com/training/implementing-navigation/nav-drawer.html]
[https://developers.google.com/places/documentation/]

