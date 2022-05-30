# Ride Share
The project is meant to simulate the management of a bike sharing system, that allows users to travel more efficiently and economicaly through a city<br/>
Through a bike sharing system, a user can take a bike from a near station, and leave it at another station close to his destination.<br/>
This naturally leads to the problem of having to many bikes in a station, and less in others, so they must be redistributed.
<br/>
This project includes two applications, a Rest API for the backend and JavaFX client application.<br/>
The REST API provides endpoints for obtaining information about the stations throughout the city, the bikes, the routes between stations and the cars employed to redistribute bikes among stations.<br/>
There are also endpoints meant specificaly for solving the bike sharing rebalancing problem, one for generating random distribuitions of stations and bikes, and a endpoint that can provide a path that a truck must take to fulfill the stations requests.<br/>
The JavaFX client app provides a simulation of such a system in action, with users taking bikes, and relocating them to other stations, and how a car can redistribute the bikes among stations such that each of the has their needs fulfilled.<br/>
The database system used for storing the info for a problem configuration is Postgres, the API comunicates with the database through jpa, the back-end of the application is made in Spring Boot.<br/> For generating a problem instace we use a pl/pgsql procedure.<br/>
A timekeeper daemon thread messures the amount of time the user spent in the application.


