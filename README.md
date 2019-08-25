# Fire-Department-Event
This software is used to add and manage time-based operations for an event.
The event allows children to simulate one day in a fire department.

The application is written in Java and is based on [JavaFX](https://openjfx.io).

## Features
- add, remove and edit operations
- sound alert if an operation starts
- intuitive and responsive UI
- local data storage

## Getting started
1. Clone or download the repository.  
`git clone https://github.com/Paul2708/fire-department-event.git`

2. Build the jar.  
  `cd fire-department-event`  
  `mvn clean package`
  
3. Run the jar by double-click or `java -jar fire-department-event.jar` in `target/`.

## Screenshots
### Empty list of operations
![image1](https://github.com/paul2708/fire-department-event/raw/master/.github/images/image1.png "Empty list")

### Sample list of operations
![image2](https://github.com/paul2708/fire-department-event/raw/master/.github/images/image2.png "Full list")
## Development
Trigger warning: The code is not as clean as it should be.

As the application is needed soon, there was not enough time to create a clean design.
However I still tried to apply the MVC design pattern.
As JavaFX features its own MVC pattern, I tried to work with it.

### Issues and contributing
As the project is going to be used only one time, the development will end after this date.
Nevertheless, if you still find bugs, just open an issue.
The same applies to contributing.
Feel free to play around with it.
