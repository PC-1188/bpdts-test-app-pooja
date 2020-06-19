package apiEngine;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Endpoints {

	private static final String BASE_URL = "http://bpdts-test-app-v2.herokuapp.com";
	
	public static Response getInstructions(String endpoint) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
 
        request.header("Content-Type", "application/json");
        
        //end point is instructions
        Response response = request.get("/" + endpoint);
        return response;
    }
	public static Response getUsers(String endpoint) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
 
        request.header("Content-Type", "application/json");
        
        //end point is users
        Response response = request.get("/" + endpoint);
        return response;
    }
	
	public static Response getUserDetailsById(String endpoint, String id) {
		 
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
 
        request.header("Content-Type", "application/json");
        //end point name is user
        Response response = request.get("/" + endpoint + "/" + id);
        return response;
	}
	
	public static Response getUserDetailsByCity(String endpoint,String city) {
		 
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
 
        request.header("Content-Type", "application/json");
        //end point name is city 
        Response response = request.get("/" + endpoint + "/" + city + "/users");
        return response;
	}
}
