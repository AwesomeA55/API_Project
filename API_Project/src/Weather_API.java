// Author:     Ayush Gopisetty
// Course:     CS2336.502
// Date:       10/11/2020
// Assignment: CS 2336 Semester Project 1 - API
// Compiler:   Eclipse IDE for Java Developers 2020-06

import com.google.gson.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class Weather_API
{
	public static void main (String [] args) throws Exception
	{
		// Scanner object will be created to read the user input
		Scanner input = new Scanner(System.in);
		
		// prompts the user to enter a city or zip
		// user will input city or zip
		System.out.print("Enter which city or zip do you want to find the weather for: ");
		String message = input.nextLine();
		
		// each of the words in the string message is split into different elements of a String array
		String[] array = message.split(" ");
		
		// sets boolean variable isZipCode to false
		boolean isZipCode = false;
		
		// determines whether the message contains the zip code or city
		// sets boolean variable to false if the message contains 
		for(int i = 0; i < array[0].length(); i++)
		{
			isZipCode = Character.isDigit(array[0].charAt(i));
			
			if(isZipCode == false)
			{
				break;
			}
		}
		
		// checks whether the zip code is contained in the message
		// if message contains zip code, then display the temperature of a zip code on a channel
		if(isZipCode)
		{
			// sets the string zipCode
			String zipCode = array[0].toString();
			
			// creates the URL object where the API endpoint for the Weather API that is passed in
			// creates an HttpURLConnection object
			// uses the HttpURLConnection to create a GET request
			URL url = new URL("http://api.openweathermap.org/data/2.5/weather?zip=" + zipCode + "&APPID=02e3acfce09c9da69c3dc4a536f498d4");	// creates the URL object where the API endpoint for the Weather API that is passed in
			HttpURLConnection con = (HttpURLConnection)url.openConnection();																// creates an HttpURLConnection object
			con.setRequestMethod("GET");																									// uses the HttpURLConnection to create a GET request
			
			// creates a BufferedReader to read the connection inputStream
			// creates a string inputLine
			// converts BufferedReader to String and stores the converted BufferedReader into the string inputLine
			// closes the BufferedReader object
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));	// creates a BufferedReader to read the connection inputStream
			String inputLine;																		// creates a string inputLine
			inputLine = in.readLine();																// converts BufferedReader to String and stores the converted BufferedReader into the string inputLine
			in.close();																				// closes the BufferedReader object
			
			// sets the weather by calling an function
			// converts temperature from Kelvin to Fahrenheit
			// displays the temperature of a zip code
			double kelvin = getTemperature(inputLine);													// sets the weather by calling an function
			double fahrenheit = Math.round((9.0 / 5)*(kelvin - 273) + 32);								// converts temperature from Kelvin to Fahrenheit
			System.out.println("Temperature in " + zipCode + " is " + fahrenheit + " in Fahhrenheit.");	// displays the temperature of a zip code
		}
		
		// zip code is not contained in the message
		// displays the temperature of a city on a channel
		else
		{
			// sets the string city;
			String city = "";
			
			// sets the string of a city if the city is one word
			if(array.length == 1)
			{
				city = array[0].toString();
			}
			
			// sets the string of a city if the city is more than one word
			if(array.length > 1)
			{
				for(int i = 0; i < array.length; i++)
				{
					if(i == array.length - 1)
					{
						city += array[i].toString();
					}
					else
					{
						city += array[i].toString() + " ";
					}
				}
			}
			
			// creates the URL object where the API endpoint for the Weather API that is passed in
			// creates an HttpURLConnection object
			// uses the HttpURLConnection to create a GET request
			URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=02e3acfce09c9da69c3dc4a536f498d4");	// creates the URL object where the API endpoint for the Weather API that is passed in
			HttpURLConnection con = (HttpURLConnection)url.openConnection();															// creates an HttpURLConnection object
			con.setRequestMethod("GET");																								// uses the HttpURLConnection to create a GET request
			
			// creates a BufferedReader to read the connection inputStream
			// creates a string inputLine
			// converts BufferedReader to String and stores the converted BufferedReader into the string inputLine
			// closes the BufferedReader object
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));	// creates a BufferedReader to read the connection inputStream
			String inputLine;																		// creates a string inputLine
			inputLine = in.readLine();																// converts BufferedReader to String and stores the converted BufferedReader into the string inputLine
			in.close();																				// closes the BufferedReader object
			
			// sets the weather by calling an function
			// converts temperature from Kelvin to Fahrenheit
			// displays the temperature of a city
			double kelvin = getTemperature(inputLine);												// sets the weather by calling an function
			double fahrenheit = Math.round((9.0 / 5)*(kelvin - 273) + 32);							// converts temperature from Kelvin to Fahrenheit
			System.out.println("Temperature in " + city + " is " + fahrenheit + " in Fahrenheit.");	// displays the temperature of a city
		}
		
	}
	
	// accepts the json as a paramenter and gets the temperature from the json
	// returns the temperature as a double
	public static double getTemperature(String result)
	{
		JsonElement element = new JsonParser().parse(result);
	    JsonObject  object = element.getAsJsonObject();
	    JsonObject main = object.getAsJsonObject("main");
	    double temp = main.get("temp").getAsDouble();
	    return temp;
	}
}