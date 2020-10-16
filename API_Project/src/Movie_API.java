// Author:     Ayush Gopisetty
// Course:     CS2336.502
// Date:       10/11/2020
// Assignment: CS 2336 Semester Project 1 - API
// Compiler:   Eclipse IDE for Java Developers 2020-06

import com.google.gson.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class Movie_API
{
	public static void main (String [] args) throws Exception
	{
		// Scanner object will be created to read the user input
		Scanner input = new Scanner(System.in);
		
		// prompts the user to enter a movie
		// user will input a movie
		System.out.print("Enter a movie: ");	// prompts the user to enter a movie
		String message = input.nextLine();		// user will input a movie
		
		// each of the words in the string message is split into different elements of a String array
		String array[] = message.split(" ");
		
		// sets the string movie
		String movie = "";
		
		// sets the string movie and movieTitle if the string movie is one word
		if(array.length == 1)
		{
			movie = array[0].toString();
		}
		
		// sets the string movie and movieTitle if the string movie is more than one word
		if(array.length > 1)
		{
			for(int i = 0; i < array.length; i++)
			{
				if(i == array.length - 1)
				{
					movie += array[i].toString();
				}
				
				else
				{
					movie += array[i].toString() + "+";
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
		
		//	displays the information of a movie
		System.out.println("Here is the information for the movie " + message + ":\n");	// displays a message for the information of a movie
		System.out.println("Title: " + title);											// displays the title of a movie
		System.out.println("Year Released: " + yearReleased);							// displays the year a movie released
		System.out.println("MPAA Rating: " + MPAARating);								// displays the MPAA Rating of a movie
		System.out.println("Running Time: " + runningTime);								// displays the running time of a movie
		System.out.println("Actors: " + actors);										// displays the actors of a movie
		System.out.println("Director: " + director);									// displays the director of a movie	
		System.out.println("Description: " + description);								// displays the description of a movie
		System.out.println("IMDb Rating: " + IMDbRating);								// displays the IMDb rating of a movie
		System.out.println("Rotten Tomatoes Rating: " + RottenTomatoesRating);			// displays the Rotten Tomatoes Rating of a movie
		System.out.println("Metacritic Rating: " + MetacriticRating);					// displays the Metacritic Rating of a movie
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