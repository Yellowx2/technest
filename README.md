# Technest exercise
## Tools
This exercise is going to be coded using:
- Java 11
- Spring Boot 2.3.1
- JUnit 5
- IntelliJ IDEA Community
- Postman
## Development
1. Base project created through Spring Initializr Web.
Changed properties file to YML type. Added in-memory H2 database configuration.
2. Created basic MVC project structure with its classes: Model, Controller, Repository and Service.
Model has the constraints required plus an Id to easily select it through a REST call.
Service has an interface and its respective implementation with a simple method for now.
3. Created basic endpoints for obtaining, creating, updating and deleting accounts.
Parameter validation logic for creating and updating accounts.
4. Transfer service and endpoint created with simple validations of currency and balance.
To make it simpler, the amount to transfer will be the same as the account of origin.
I decided to create a simple Map that contains the exchange rate of Euro and American Dollar in a separate class.
## Testing
### Service Layer
1. Basic test configuration created with Mockito. Both gets tested.
2. Created tests for updateAccount, deleteAccount and createAccount. These tests have a lot of code without assertions
due to returning void values, and for the sake of covering more code.
3. Created test for transfer layer, with execution of the method with different parameters for covering more code.
### Controller Layer

### Model Layer
I'm not going to test the Model layer. It is too monotonous and very time-consuming.
## Notes
- Directly committing to master branch in order to avoid losing time.