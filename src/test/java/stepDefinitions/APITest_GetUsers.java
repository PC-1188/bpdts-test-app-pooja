package stepDefinitions;

//import junit.framework.Assert;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.JUnitSoftAssertions;
import org.assertj.core.api.SoftAssertions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import static org.assertj.core.api.Assertions.*;
import apiEngine.Endpoints;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class APITest_GetUsers {
	private SoftAssertions 	softAssertions = new SoftAssertions();
	public static String arrayIDCityGroup[][] = new String [2][1000];
	public static String responseStatus;
	private static Response response;
	private static ResponseBody body;
	private static 	String status;
	private static int totalUsers;
    private static JSONArray usersByID;
    
    @Given("a get request is made to fetch instructions with endpoint {string}")
	public void a_get_request_is_made_to_fetch_instructions_with_endpoint(String endpointName) {
		response = Endpoints.getInstructions(endpointName); 
		      
	}
    
    @Then("the instructions how to perform the tests is displayed")
	public void the_instructions_how_to_perform_the_tests_is_displayed() throws ParseException, IOException {
		body = response.getBody();
		System.out.println("Instruction Content: " + body.asString());
		
				
	}
    
	@Given("a get request is made to fetch all users with endpoint {string}")
	public void a_get_request_is_made_to_fetch_all_users_with_endpoint(String endpointName) {
		response = Endpoints.getUsers(endpointName); 
		List<String> jsonResponse = response.jsonPath().getList("$");
		totalUsers = jsonResponse.size();
        System.out.println("\nTotal users: " + jsonResponse.size());
      
	}

	//response code method is re-usable
	@When("the response code is {string}")
	public void the_response_code_is(String statusCode) {

		int int_statusCode = response.getStatusCode();
		String str_statusCode = new Integer(int_statusCode).toString();
		System.out.println("\nResponse code: " + str_statusCode);
		softAssertions.assertThat(str_statusCode).as("Response code").isEqualTo(statusCode);
		softAssertions.assertAll();

	}

	@Then("the users list is displayed")
	public void the_users_list_is_displayed() throws ParseException, IOException {
		body = response.getBody();
		System.out.println("Users list: " + body.asString());
		
		//saving all user details in Json Array
    	JSONParser parser = new JSONParser();
    	usersByID = (JSONArray) parser.parse(body.asString());  
		
	}
	
	
	@Given("a get request is made to fetch user details with endpoint {string} and by userId {string}")
	public void a_get_request_is_made_to_fetch_user_details_with_endpoint_and_by_userId(String endpointName,String id) {
		response = Endpoints.getUserDetailsById(endpointName,id); 
		


	}

	

	//user details method is re-usable
	@Then("the users details is {string}")
	public void the_users_details_is(String status) {
		body = response.getBody();
		responseStatus = "undefined";

		System.out.println("Response Body: " + body.asString());
		String strResponseBody = body.asString();
		if((strResponseBody.contains("first_name") && strResponseBody.contains("last_name") && strResponseBody.contains("email") && strResponseBody.contains("ip_address") && strResponseBody.contains("latitude") && strResponseBody.contains("longitude")) || (strResponseBody.contains("first_name") && strResponseBody.contains("last_name") && strResponseBody.contains("email") && strResponseBody.contains("ip_address") && strResponseBody.contains("latitude") && strResponseBody.contains("longitude") && strResponseBody.contains("city")) || (strResponseBody.contains("[]")))
		{
			responseStatus = "success";
		}
		else if ((strResponseBody.contains("doesn't exist")) || (strResponseBody.contains("requested URL was not found")) )
		{
			responseStatus = "notfound";

		}
		
  		softAssertions.assertThat(responseStatus).as("Response body").isEqualTo(status);
  		softAssertions.assertAll();

		
	}
	
	@Given("a get request is made to fetch user details with endpoint {string} and by city {string}")
	public void a_get_request_is_made_to_fetch_user_details_with_endpoint_and_by_city(String endpointName,String city) {
		response = Endpoints.getUserDetailsByCity(endpointName,city); 

	}
	
	@Given("a get request is made to fetch user details with endpoint {string} for each userId with {string}")
	public void a_get_request_is_made_to_fetch_user_details_with_endpoint_for_each_userId_with(String endpointName, String statusCode) throws FileNotFoundException, IOException, ParseException {
				
		String arrayIDCity[][] = new String [2][totalUsers];
		arrayIDCityGroup = arrayIDCity;

		for(int id = 1;id<=totalUsers;id++)
		{
			String str_id = new Integer(id).toString();

			response = Endpoints.getUserDetailsById(endpointName,str_id); 
			body = response.getBody();
			int int_statusCode = response.getStatusCode();
			String str_statusCode = new Integer(int_statusCode).toString();
			System.out.println("\n\nResponse code: " + str_statusCode);
	  		softAssertions.assertThat(str_statusCode).as("Response Code").isEqualTo(statusCode);
	  		softAssertions.assertAll();
	  		
	  		String jsonbody = body.asString();
			JsonPath jp = new JsonPath( jsonbody );
			String valueFN = jp.getString( "first_name" );
			String valueLN = jp.getString( "last_name" );
			String valueEM = jp.getString( "email" );
			String valueIP = jp.getString( "ip_address" );
			String valueLA = jp.getString( "latitude" );
			String valueLO = jp.getString( "longitude" );
			String valueCT = jp.getString( "city" );
			String valueID = jp.getString( "id" );
			//copy id and city in 2 d array to verify with city endpoint for all city
			arrayIDCityGroup[0][id-1] = valueID;
			arrayIDCityGroup[1][id-1] = valueCT;
	  		System.out.println("Id: " + id + " first_name: " + valueFN + " last_name: " + valueLN + " email: " + valueEM + " ip_address: " + valueIP + " latitude: " + valueLA + " longitude: " + valueLO);


		}
		
		/*for(int id = 0;id<totalUsers;id++)
		{
			System.out.println("id: " +arrayIDCityGroup[0][id] + " city: " +arrayIDCityGroup[1][id]);
		}
		*/
		


	}
	
	@Then("the user details fetched with endpoint {string} should match with details of allUsers endpoint")
	public void the_user_details_fetched_with_endpoint_should_match_with_details_of_allUsers_endpoint(String endpointName) {
		
	
		for(int id = 1;id<= totalUsers;id++)
		{
			String str_id = new Integer(id).toString();

			response = Endpoints.getUserDetailsById(endpointName,str_id); 
			body = response.getBody();
			String jsonbody = body.asString();
			JsonPath jp = new JsonPath( jsonbody );
			String valueFN = jp.getString( "first_name" );
			String valueLN = jp.getString( "last_name" );
			String valueEM = jp.getString( "email" );
			String valueIP = jp.getString( "ip_address" );
			String valueLA = jp.getString( "latitude" );
			String valueLO = jp.getString( "longitude" );
			for (Object o : usersByID)
	        {
	          JSONObject user = (JSONObject) o;
	          String strid = (String) user.get("id").toString();
	          if(strid.contentEquals(str_id))
	          {		
	        	  System.out.println("\nAssertions for id: " + str_id);
	        	  String strfirst_name = (String) user.get("first_name");
		          String strlast_name = (String) user.get("last_name");
		          String stremail = (String) user.get("email");
		          String strip_address = (String) user.get("ip_address");
		          String strlatitude = (String) user.get("latitude").toString();
		          String strlongitude = (String) user.get("longitude").toString();
		  		  softAssertions.assertThat(valueFN).as("first_name for id: "+str_id).isEqualTo(strfirst_name);
		  		  softAssertions.assertThat(valueLN).as("last_name for id: "+str_id).isEqualTo(strlast_name);
		  		  softAssertions.assertThat(valueEM).as("email for id: "+str_id).isEqualTo(stremail);
		  		  softAssertions.assertThat(valueIP).as("ip_address for id: "+str_id).isEqualTo(strip_address);
		  		  softAssertions.assertThat(valueLA).as("Latitude for id: "+str_id).isEqualTo(strlatitude);
		  		  softAssertions.assertThat(valueLO).as("Longitude for id: "+str_id).isEqualTo(strlongitude);

		  		  
		          
		  		  System.out.println("Details: Id: " + id + " first_name: " + strfirst_name + " last_name: " + strlast_name + " email: " + stremail + "\tip_address: " + strip_address + " latitude: " + strlatitude + " longitude: " + strlongitude);
		  		  break;
		  		  
	          }
	        }
			
			
		}
		
		
	
	softAssertions.assertAll();

		
	}
	
	@Given("a get request is made to fetch user details with endpoint {string} for each city with {string}")
	public void a_get_request_is_made_to_fetch_user_details_with_endpoint_for_each_city_with(String endpointName, String statusCode) throws FileNotFoundException, IOException, ParseException {
		

		for(int k = 0;k<totalUsers;k++)
		{
			
			String paramCity = arrayIDCityGroup[1][k];
			response = Endpoints.getUserDetailsByCity(endpointName,paramCity); 
			
			int int_statusCode = response.getStatusCode();
			String str_statusCode = new Integer(int_statusCode).toString();
			System.out.println("\n\n"+ paramCity+ " City Response code: " + str_statusCode);
	  		softAssertions.assertThat(str_statusCode).as("Response Code").isEqualTo(statusCode);
	  		softAssertions.assertAll();

		}
	}
	
	@Then("all the user details fetched with endpoint {string} should match with city details of user endpoint")
	public void all_the_user_details_fetched_with_endpoint_should_match_with_city_details_of_user_endpoint(String endpointName) {
		for(int k = 0;k<totalUsers;k++)
		{
			
			String paramCity = arrayIDCityGroup[1][k];
			response = Endpoints.getUserDetailsByCity(endpointName,paramCity); 
			body = response.getBody();
			
			  		
			String jsonbody = body.asString();
			JsonPath jp = new JsonPath( jsonbody );
			String valueId = jp.getString("id");
			System.out.println("Ids present for city: "+ paramCity+ " are: " + valueId);
			String newStr = valueId.replace("[","");
        	String newStr2 = newStr.replace("]","");
        	if(newStr2.contains(","))
        	{
        		String[] arrValueId = newStr2.split(",");
     	        int size = arrValueId.length;
     	     //for each response id do loop
    	        for(int y=0;y<size;y++)
    	        {	
    	        	String strIdResponseCity = arrValueId[y];
    	        	

    	        	//System.out.println("strResponseCity: "+strIdResponseCity);
    	        	//loop for all ids
    				for(int z=0;z<totalUsers;z++)
    				{
    					String strId = arrayIDCityGroup[0][z].toString();
    					if(strIdResponseCity.equals(strId))
    					{
    				  		String cityCompare = arrayIDCityGroup[1][z];
    						softAssertions.assertThat(paramCity).as("city for id: "+strId).isEqualTo(cityCompare);
    						//System.out.println(paramCity+ " City comparision success for id: "+strId);
    						break;
    					}
    					

    				}

    	        }
        	}
        	else
        	{
        		String strIdResponseCity = newStr2;
	        	

	        	//System.out.println("strResponseCity: "+strIdResponseCity);
	        	//loop for all ids
				for(int z=0;z<totalUsers;z++)
				{
					String strId = arrayIDCityGroup[0][z].toString();
					if(strIdResponseCity.equals(strId))
					{
				  		String cityCompare = arrayIDCityGroup[1][z];
						softAssertions.assertThat(paramCity).as("city for id: "+strId).isEqualTo(cityCompare);
						//System.out.println(paramCity+ " City comparision success for id: "+strId);
						break;
					}
					

				}
        	}
	       
	        

		}
		softAssertions.assertAll();

	}
}
