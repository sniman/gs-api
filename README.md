# README #
Paynet Java Assessment -  Assigment 2

### Requirement ###
 
 * identify any publicly available service exposing REST API that returns data in JSON format
 * Issue one or more suitable GET requests to the relevant API endpoints to retrieve a small subset of the full dataset retrievable from that service.
 * Create a schema for a database table to hold this small data subset.
 * Design a REST API that is exposed by the app to that is capable of processing HTTP POST and GET methods. The GET simply returns the entire contents of the database table,while the POST provides information for appending a new record into the table.
 * Create a simple application client that can issue these GET and POST requests to test your REST API. Data returned from the GET request should be viewable by the user. Data for the POST requests can be obtained interactively from the user via the command line, from a HTML/JSP page or from a hardcoded JSON/text file.

### Situation ###

* Load country data from external api (https://restcountries.eu/rest/v2)
* create api to access the data save in database , CRUD functionality


### Technology Stack Used ###

* Springboot , hibernate,JPA (backend)
* Front-End (AngularJs,freemaker)
* Swagger
* db mysql


### How to run###

* create app db as per script provided (_appdb.sql)
* mvn clean install -DskipTest //to build
* mvn spring-boot:run  //run
* swagger -> http://localhost:8080/swagger-ui.html
* demo page -> http://localhost:8080/


Note: 
Ensure Authorization Header are set first
Ensure User/Password is already in db
Ensure API_SIGNSTUREKE in table BANK are same in jwt.secret in application.yml
MOD-Header to spoog Authorization header, value token without "<content>"

### JWT ###
* first have to insert user & password to allow token generation ( Since the user n password is retrive from db [enhancement])
* to do a request, first generate token @(http://localhost:8080/authenticate) refer Generate JWT token section.
* each request should have request header as below:
              * Header Name :Authorization
              * Header Content: Bearer <jwt token generate>
              * example :
              Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZWNoZ2Vla25leHQiLCJleHAiOjE2NDQ5MDgwNTQsImlhdCI6MTY0NDg5MDA1NH0.ti8ebn84HKl1DRwBo7NoBsKZ0#####Nx-o20XP-   Be5zGwMTLmANboqUPDWtWUenPfhaUCcwnMul1npw69gSXMbpxlEw

### Generate JWT access token ###
    Post Request (Post Man)
    Body :
    {
     "username":"norirman",
    "password":"password"
    }
    
    Response:
    {
    "token":  	"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZWNoZ2Vla25leHQiLCJleHAiOjE2NDQ4NTQzNDAsImlhdCI6MTY0NDgzNjM0MH0.4UNDaoHr_zlDY	Mcyrr-9aq3e#####-D9yuu5Vc84RhN5ElN4BVmxiKtLfYvJ0rAHcPC9Y9X72PRhzdCHPJ6ZTtj3Ocw"
    }
    
    
    OR
    curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{ "username": "norirman","password": "password" }' 'http://localhost:8080/authenticate'
    





