// Author:     Ayush Gopisetty
// Course:     CS2336.502
// Date:       10/11/2020
// Assignment: CS 2336 Semester Project 1 - API
// Compiler:   Eclipse IDE for Java Developers 2020-06

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.jibble.pircbot.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// this class will have the user type messages into the IRC
// the bot will respond to the messages based on what is contained in the messages
public class MyBot extends PircBot
{
	// sets the name for the bot
	public MyBot()
	{
		this.setName("myApiTestingBot");
	}
	
	// checks the messages the user type on the IRC server
	// displays response based on what is contained in the message
	public void onMessage(String channel, String sender, String login, String hostname, String message)
	{
		// checks whether the message contains "time" while ignoring the case
		// displays current time if the message contains "time" while ignoring the case
		if(message.equalsIgnoreCase("time"))
		{
			String time = new java.util.Date().toString();
			sendMessage(channel, sender + ": The time is now " + time);
		}
		
		// checks whether the message contains "time" while ignoring the case
		// displays current time if the message contains "time" while ignoring the case
		if (message.contains("Hello")) 
		{
			//the user wants weather, so call the weather API you created in part 1
			//this is how you send a message back to the channel 
			sendMessage(channel, "Hey " + sender + "!");

		}
		
		// checks whether the message contains "Weather"
		// displays temperature of a city or zip code if the message contains "Weather" on a channel
		if (message.contains("Weather"))
		{
			try 
			{
				// each of the words in the string message is split into different elements of a String array
				String[] array = message.split(" ");
				
				// sets boolean variable isZipCode to false
				boolean isZipCode = false;
				
				// determines whether the message contains the zip code or city
				// sets boolean variable to false if the message contains 
				for(int i = 0; i < array[2].length(); i++)
				{
					isZipCode = Character.isDigit(array[2].charAt(i));
					
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
					String zipCode = array[2].toString();
					
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
					// displays the temperature of a zip code on a channel
					double kelvin = getTemperature(inputLine);														// sets the weather by calling an function
					double fahrenheit = Math.round((9.0 / 5)*(kelvin - 273) + 32);									// converts temperature from Kelvin to Fahrenheit
					sendMessage(channel, "Temperature in " + zipCode + " is " + fahrenheit + " in Fahrenheit.");	// displays the temperature of a zip code on a channel
				}
				
				// zip code is not contained in the message
				// displays the temperature of a city on a channel
				else
				{
					// sets the string city;
					String city = "";
					
					// sets the string of a city if the city is one word
					if(array.length == 3)
					{
						city = array[2].toString();
					}
					
					// sets the string of a city if the city is more than one word
					if(array.length > 3)
					{
						for(int i = 2; i < array.length; i++)
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
					// displays the temperature of a city on a channel
					double kelvin = getTemperature(inputLine);													// sets the weather by calling an function
					double fahrenheit = Math.round((9.0 / 5)*(kelvin - 273) + 32);								// converts temperature from Kelvin to Fahrenheit				
					sendMessage(channel, "Temperature in " + city + " is " + fahrenheit + " in Fahrenheit.");	// displays the temperature of a city on a channel
				}
			}
			
			// catches MALFORMEDURLException
			catch (MalformedURLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// catches ProtocolException
			catch (ProtocolException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			// catches IOException
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// checks whether the message contains "Movie"
		// displays information about a movie if the message contains "Movie"
		if(message.contains("Movie"))
		{
			try
			{
				// each of the words in the string message is split into different elements of a String array
				String[] array = message.split(" ");
				
				// sets the string movie and movieTitle
				String movie = "";		// sets the string movie
				String movieTitle = "";	// sets the string movieTitle
				
				// sets the string movie and movieTitle if the string movie and movieTitle are both one word
				if(array.length == 2)
				{
					movie = array[1].toString();
					movieTitle = array[1].toString();
				}
				
				// sets the string movie and movieTitle if the string movie and movieTitle are both more than one word
				if(array.length > 2)
				{
					for(int i = 1; i < array.length; i++)
					{
						if(i == array.length - 1)
						{
							movie += array[i].toString();
							movieTitle += array[i].toString();
						}
						
						else
						{
							movie += array[i].toString() + "+";
							movieTitle += array[i].toString() + " ";
						}
					}
				}
				
				// creates the URL object where the API endpoint for the Movie API that is passed in
				// creates an HttpURLConnection object
				// uses the HttpURLConnection to create a GET request
				URL url = new URL("http://www.omdbapi.com/?apikey=f2284700&t=" + movie);	// creates the URL object where the API endpoint for the Movie API that is passed in
				HttpURLConnection con = (HttpURLConnection)url.openConnection();			// creates an HttpURLConnection object
				con.setRequestMethod("GET");												// uses the HttpURLConnection to create a GET request
				
				// creates a BufferedReader to read the connection inputStream
				// creates a string inputLine
				// converts BufferedReader to String and stores the converted BufferedReader into the string inputLine
				// closes the BufferedReader object
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));	// creates a BufferedReader to read the connection inputStream
				String inputLine;																		// creates a string inputLine
				inputLine = in.readLine();																// converts BufferedReader to String and stores the converted BufferedReader into the string inputLine
				in.close();																				// closes the BufferedReader object
				
				// sets a string for each information of a movie by using calls to functions
				String title = getTitle(inputLine);									// sets the title of of a movie
				String yearReleased = getYearReleased(inputLine);					// sets the year released of a movie
				String MPAARating = getMPAARating(inputLine);						// sets the MPAA Rating of a movie
				String runningTime = getRunningTime(inputLine);						// sets the running time of a movie	
				String director = getDirector(inputLine);							// sets the director of a movie
				String actors = getActors(inputLine);								// sets the actors of a movie
				String description = getDescription(inputLine);						// sets the description of a movie
				String IMDbRating = getIMDbRating(inputLine);						// sets the IMDb rating of a movie
				String RottenTomatoesRating = getRottenTomatoesRating(inputLine);	// sets the Rotten Tomatoes Rating of a movie
				String MetacriticRating = getMetacriticRating(inputLine);			// sets the Metacritic rating of a movie
				
				// displays the information of a movie on a channel
				sendMessage(channel, "Here is the information for the movie " + movieTitle + ":\n");	// displays a message for the information of a movie on a channel
				sendMessage(channel, "Title: " + title);												// displays the title of a movie on a channel
				sendMessage(channel, "Year Released: " + yearReleased);									// displays the year a movie released on a channel
				sendMessage(channel, "MPAA Rating: " + MPAARating);										// displays the MPAA Rating of a movie on a channel
				sendMessage(channel, "Running Time: " + runningTime);									// displays the running time of a movie on a channel
				sendMessage(channel, "Director: " + director);											// displays the director of a movie on a channel
				sendMessage(channel, "Actors: " + actors);												// displays the actors of a movie on a channel
				sendMessage(channel, "Description: " + description);									// displays the description of a movie on a channel
				sendMessage(channel, "IMDb Rating: " + IMDbRating);										// displays the IMDb rating of a movie on a channel
				sendMessage(channel, "Rotten Tomatoes Rating: " + RottenTomatoesRating);				// displays the Rotten Tomatoes Rating of a movie on a channel
				sendMessage(channel, "Metacritic Rating: " + MetacriticRating);							// displays the Metacritic Rating of a movie on a channel
			}
			
			// catches MALFORMEDURLException
			catch (MalformedURLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			// catches ProtocolException
			catch (ProtocolException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			// catches IOException
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	// accepts the json as a parameter and gets the temperature from the json
	// returns the temperature as a double
	public static double getTemperature(String result)
	{
		JsonElement element = new JsonParser().parse(result);
	    JsonObject  object = element.getAsJsonObject();
	    JsonObject main = object.getAsJsonObject("main");
	    double temp = main.get("temp").getAsDouble();
	    return temp;
	}
	
	// accepts the json as a parameter and gets the movie title from the json
	// returns the movie title as a double
	public static String getTitle(String input)
	{
		JsonElement element = new JsonParser().parse(input);
		JsonObject  object = element.getAsJsonObject();
		String movieTitle = object.get("Title").getAsString();
		
		return movieTitle;
	}
	
	// accepts the json as a parameter and gets the year a movie released from the json
	// returns the year a movie released as a double
	public static String getYearReleased(String input)
	{
		JsonElement element = new JsonParser().parse(input);
		JsonObject  object = element.getAsJsonObject();
		String yearReleased = object.get("Year").getAsString();
		
		return yearReleased;
	}
	
	// accepts the json as a parameter and gets the MPAA rating of a movie from the json
	// returns the MPAA rating of a movie as a String
	public static String getMPAARating(String input)
	{
		JsonElement element = new JsonParser().parse(input);
		JsonObject  object = element.getAsJsonObject();
		String MPAARating = object.get("Rated").getAsString();
		
		return MPAARating;
	}
	
	// accepts the json as a parameter and gets the running time of a movie from the json
	// returns the running time of a movie as a String
	public static String getRunningTime(String input)
	{
		JsonElement element = new JsonParser().parse(input);
		JsonObject  object = element.getAsJsonObject();
		String runningTime = object.get("Runtime").getAsString();
		
		return runningTime;
	}
	
	// accepts the json as a parameter and gets the director of a movie from the json
	// returns the director of a movie as a String
	public static String getDirector(String input)
	{
		JsonElement element = new JsonParser().parse(input);
		JsonObject  object = element.getAsJsonObject();
		String director = object.get("Director").getAsString();
		
		return director;
	}
	
	// accepts the json as a parameter and gets the actors of a movie from the json
	// returns the actors of a movie as a String
	public static String getActors(String input)
	{
		JsonElement element = new JsonParser().parse(input);
		JsonObject  object = element.getAsJsonObject();
		String actors = object.get("Actors").getAsString();
		
		return actors;
	}
	
	// accepts the json as a parameter and gets the description of a movie from the json
	// returns the description of a movie as a String
	public static String getDescription(String input)
	{
		JsonElement element = new JsonParser().parse(input);
		JsonObject  object = element.getAsJsonObject();
		String description = object.get("Plot").getAsString();
		
		return description;
	}
	
	// accepts the json as a parameter and gets the IMDb rating of a movie from the json
	// returns the Imdb rating of a movie as a String
	public static String getIMDbRating(String input)
	{
		JsonElement element = new JsonParser().parse(input);
		JsonObject  object = element.getAsJsonObject();
		JsonArray  ratingsArray = object.getAsJsonArray("Ratings");
		JsonObject IMDbObject = ratingsArray.get(0).getAsJsonObject();
		String IMDBRating = IMDbObject.get("Value").getAsString();
		
		return IMDBRating;
	}
	
	// accepts the json as a parameter and gets the Rotten Tomatoes rating of a movie from the json
	// returns the Rotten Tomatoes rating of a movie as a String
	public static String getRottenTomatoesRating(String input)
	{
		JsonElement element = new JsonParser().parse(input);
		JsonObject  object = element.getAsJsonObject();
		JsonArray  ratingsArray = object.getAsJsonArray("Ratings");
		JsonObject RottenTomatoesObject = ratingsArray.get(1).getAsJsonObject();
		String RottenTomatoesRating = RottenTomatoesObject.get("Value").getAsString();
		
		return RottenTomatoesRating;
	}
	
	// accepts the json as a parameter and gets the Metacritic rating of a movie from the json
	// returns the Metacritic rating of a movie as a String
	public static String getMetacriticRating(String input)
	{
		JsonElement element = new JsonParser().parse(input);
		JsonObject  object = element.getAsJsonObject();
		JsonArray  ratingsArray = object.getAsJsonArray("Ratings");
		JsonObject MetacriticObject = ratingsArray.get(2).getAsJsonObject();
		String MetacriticRating = MetacriticObject.get("Value").getAsString();
		
		return MetacriticRating;
	}
}