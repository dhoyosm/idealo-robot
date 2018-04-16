# Idealo Robot

Welcome to Idealo Robot coding puzzle.

## What is on this folder? ##

* *robot-[x.x].jar*: executable jar of the project. Version x.x
* *pom.xml*: Maven pom file with the information about about the project and dependencies.
* *src* folder: contains all the source code for the solution.
* *README.md* (this): Instructions on how to build and execute the project.

## How to run the project? ##

* To run the project, double-click on the executable jar file. It would star one instance of the project one the defaul port (8080).
* The project can also be started by executing it from the command line:
```
	java -jar robot-<version>.jar
```

## How to build the code? ##

* Prerequisites:
	* Java SDK 1.8 +
	* Maven 3.3 +

* In the command console go to the the root folder where the pom.xml is located
* Execute the following command:
```
	mvn clean install package
```
* This command will generate an executable file on target/ folder

## Command endpoints ##

Once the program is running, the robot commands can be executed by calling the following rest endpoints:

* **REPORT** will announce the **X**,**Y** and **F** of the robot.

  |URL|Method|URL Params|Data Params|Success Response|Error Response|
  |---|---|---|---|---|---|
  |*/robot*|GET| | |**CODE**: 200<br/>**CONTENT**:`{ "x": 4, "y": 4, "f": "NORTH" }`|**CODE**: 404<br/>**CONTENT**:`{"error": "Not Found", "message": "ROBOT MISSING"}`|
  
  **Sample Call**: `curl --request GET --url http://localhost:8080/robot`

* **PLACE** will put the toy robot on the table in position X,Y and facing NORTH, SOUTH, EAST or WEST.

  |URL|Method|URL Params|Data Params|Success Response|Error Response|
  |---|---|---|---|---|---|
  |*/robot*|POST| |`{"x":4, "y":4, "f":"NORTH"}`|**CODE**: 200<br/>**CONTENT**:|**CODE**: 400<br/>**CONTENT**:`{"error": "Bad Request", "message": "Validation failed for object='robot'. Error count:"}`|
  
  **Constraints**
  
  |||
  |:---:|---|
  |**x**  |Integer [0-4]|
  |**y**  |Integer [0-4]|
  |**f**  |Only **NORTH**, **EAST**, **SOUTH**, and **WEST** (case sensitive) are allowed|
  
  **Sample Call**:
  ```
  curl --request POST \
  --url http://localhost:8080/robot \
  --header 'Content-Type: application/json' \
  --data '{
	"x":4,
	"y":2,
	"f":"WEST"
  }'
  ```
  
* **LEFT** will rotate the robot 90 degrees to the left without changing the position of the robot.

  |URL|Method|URL Params|Data Params|Success Response|Error Response|
  |---|---|---|---|---|---|
  |*/robot/left*|PUT| | |**CODE**: 200<br/>**CONTENT**:|**CODE**: 404<br/>**CONTENT**:`{"error": "Not Found", "message": "ROBOT MISSING"}`|
  
  **Sample Call**: `curl --request PUT --url http://localhost:8080/robot/left`
  
* **RIGHT** will rotate the robot 90 degrees to the right without changing the position of the robot.

  |URL|Method|URL Params|Data Params|Success Response|Error Response|
  |---|---|---|---|---|---|
  |*/robot/right*|PUT| | |**CODE**: 200<br/>**CONTENT**:|**CODE**: 404<br/>**CONTENT**:`{"error": "Not Found", "message": "ROBOT MISSING"}`|
  
  **Sample Call**: `curl --request PUT --url http://localhost:8080/robot/right`
  
* **MOVE** will move the toy robot one unit forward in the direction it is currently facing.

  |URL|Method|URL Params|Data Params|Success Response|Error Response|
  |---|---|---|---|---|---|
  |*/robot/move*|PUT| | |**CODE**: 200<br/>**CONTENT**:|**CODE**: 403<br/>**CONTENT**:`{"error": "Forbidden", "message": "FORBIDDEN MOVE. IT WILL CAUSE ROBOT TO GO MISSING"}`<br/><br/>**CODE**: 404<br/>**CONTENT**:`{"error": "Not Found", "message": "ROBOT MISSING"}`|
  
  **Sample Call**: `curl --request PUT --url http://localhost:8080/robot/move`
