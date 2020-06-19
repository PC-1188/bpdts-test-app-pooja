# BDD RestAssured API Test Framework with Cucumber-jvm reporting
BDD framework for automation using Cucumber and Junit

# The framework has following features
1. RestAssured - Endpoints model
2. Maven based framework
3. Report Generation (cucumber jvm reports)
4. CucumberOptions with detailed explanation of using "tags", "glue"
6. Dependency defined in pom.xml along with report plugins

# To Get Started

## Pre-requisites
1. Java installed in the system
2. Maven installed in the system
3. IDE(Eclipse) used with cucumber and maven plugins

## Description
1. Feature files mentioning the scenarios i.e. all the endpoints in herokuapp.com (path: src/tests/resources/APITests/GetUsers.feature)
	```Scenarios:
	a. Get instructions to perform tests
	b. Get all users details
	c. Get user details by id ( Adhoc Data)
	d. Get user details by city ( Adhoc Data)
	e. Get user details by respective id ( for all the users )
	f. Get users details by city ( for all cities )
	```
2. Step definition: glue code linked to steps defined in scenario outline in feature file (path: src/test/java/stepDefinitions/APITest_GetUsers.java)

3. Endpoint Model: Each step definition is calling methods used in Endpoints Class to perform operations (path: src/test/java/apiEngine/Endpoints.java) 
	```Endpoints.java:
	a. getInstructions
	b. getUsers
	c. getUserDetailsById
	d. getUserDetailsByCity
	```
4. Junit Test Runner: Test Runner based on the JUnit Test Runner for running API tests.(path: src/test/java/runners/TestRunner.java)
	```Runner class incorporates path with CucumberOptions for below:
	a. feature files
	b. glue i.e. StepDefinitions 
	c. tags - to run specific scenarios or all scenarios in feature file
	d. plugin - path for cucumber.json to create cucumber jvm reports
	```

## Run Scripts

1. Pull this repo, keep the folder structure intact
2. Navigate to the folder 'bpdts-test'
3. In Test Runner class, setup tags as '@APITests' to run whole test suite
3. Run command 'mvn clean verify' to run tests
4. If wish to run specific tags ( specific scenarios), need to change tags name in TestRunner class as per tags mentioned in feature file and re-run command 'mvn clean verify'


## HTML Reports 
Cucumber-jvm-reports will be created in Target folder as cucumber-report-html (path: target/cucumber-report-html/cucumber-html-reports/feature-overview.html



# Test observations and implementations
1. /city/{city}/users endpoint:
Returns 200 status code with correct information in the body when valid test data is passed
Returns 200 with empty Json response when invalid data is passed. 
There is no information mentioned in swagger for scenario when {city} request is blank but the endpoint returns 404 Not Found - added and tested

2. /user/{id} endpoint:
Returns 200 if id exists
Returns 404 if id not exists. 
Returns 404 if nothing is passed for {id} -  added and tested

3. Response data of all users from /users endpoint is saved in JsonArray for comparision/assertions later.

4. Above users data from saved json array is asserted with endpoint /user/{id} for scenario where the test is run for all the user ids.
While comparision found there is a data mismatch for latitude and longitude fields between values present in /users  and /users/{id} enpoints for same user id which is a bug

5. The city and user id fields from endpoint /users/{id} is stored in a different array for comparision later

6. For all the cities, '/city/{city}/users' endpoint is run and response is verified from array mentioned in observation 5 i.e. users ids for requested city

7. We have used soft assertions. At the end of tests run, if assertions failure is present it log the details in test reports.


# Other possible solutions: 
These API can also be automated and tested via Postman-Newman Integration API framework which could be run via CLI 
Due to time constraint was not able to perform the Proof of concept, but please find below details of implementation.
### Prerequisite:
1. install node js 
2. install dependency: newman, dotenv , newman-reporter-htmlextra

### Implementation Steps:
1. Create a new collection in postman tool and define environment (base url and endpoints name defined in environment variables)
2. Write mentioned get endpoints in postman tool via swagger and write pre-requisite and tests in postman using Javascript
3. Export the collection json and environment json.
4. Create node js file APITests.js and write newman.run js scripts mentioning path of postman_collection.json and environment.json. Also write reporting dependency as 'newman-reporter-htmlextra'
	```
	var newman = require('newman'); // require newman in your project

	// call newman.run to pass `options` object and wait for callback
	newman.run({
		collection: require('./sample-collection.json'),
		reporters: ['cli','htmlextra'],
		reporter: {
        htmlextra: {
            export: './<html file path>'
			}
		}
	}, function (err) {
		if (err) { throw err; }
		console.log('collection run complete!');
	});
	```
5. In cli run the command ' node APITests.js ' - This will run the whole collection suite of all get requests endpoint and post Newman Html reports in mentioned report folder path
