#Author: pooja.chawla88@gmail.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
@tag
Feature: Get users list 
Description: I want to run a get request to fetch all the user details or user details by id or users by city  
  
  @instructions @APITests
  Scenario Outline: Get instructions to perform tests
    Given a get request is made to fetch instructions with endpoint "<endpoint>"
    When the response code is "<responsecode>"
    Then the instructions how to perform the tests is displayed
    
    Examples: 
      | endpoint | responsecode  |
      |     instructions | 200 |
  
  @users @APITests
  Scenario Outline: Get all users details
    Given a get request is made to fetch all users with endpoint "<endpoint>"
    When the response code is "<responsecode>"
    Then the users list is displayed
    
    Examples: 
      | endpoint | responsecode  |
      |     users | 200 |
      
  @userById @APITests
  Scenario Outline: Get user details by id
    Given a get request is made to fetch user details with endpoint "<endpoint>" and by userId "<userid>"
    When  the response code is "<responsecode>"
    Then the users details is "<status>"
    Examples: 
       | endpoint | userid  | responsecode | status  |
       | user | 1 |     200 | success |
       | user | 1001 |     404 | notfound |
       | user |  abc|     404 | notfound |
       | user | -123 |     404 | notfound |
       | user | $£% |     404 | notfound |
       | user |  |     404 | notfound |
    
   @userByCity @APITests
	  Scenario Outline: Get user details by city
	    Given a get request is made to fetch user details with endpoint "<endpoint>" and by city "<city>" 
	    When the response code is "<responsecode>"
	    Then the users details is "<status>"
	    
	    Examples: 
	       |  endpoint | city  | responsecode | status  |
	       | city | Kax |     200 | success |
	       | city | Kaxxx |     200 | success |
	       | city | 123 |     200 | success |
	       | city | -123 |     200 | success |
	       | city | $£% |     200 | success |
	       | city |    |     404 | notfound |
	       
	       
	       
	       
		@users @APITests
		Scenario Outline: Get all user details by respective id
		Given a get request is made to fetch user details with endpoint "<endpoint>" for each userId with "<responsecode>"
		Then the user details fetched with endpoint "<endpoint>" should match with details of allUsers endpoint
		Examples: 
	       |  endpoint |  responsecode | 
	       | user |      200 | 
	       
	  @users @APITests
		Scenario Outline: Get users details by city for all cities
		Given a get request is made to fetch user details with endpoint "<endpoint>" for each city with "<responsecode>"
		Then all the user details fetched with endpoint "<endpoint>" should match with city details of user endpoint
		Examples: 
	       |  endpoint |  responsecode | 
	       | city |      200 | 
		
