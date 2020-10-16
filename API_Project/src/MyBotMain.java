// Author:     Ayush Gopisetty
// Course:     CS2336.502
// Date:       10/11/2020
// Assignment: CS 2336 Semester Project 1 - API
// Compiler:   Eclipse IDE for Java Developers 2020-06

import org.jibble.pircbot.*;

// this class will set up a bot
public class MyBotMain
{
	public static void main (String [] args) throws Exception
	{
		// now start our bot up
		MyBot bot = new MyBot();
		
		// enable debugging output
		bot.setVerbose(true);
		
		// connect to the IRC server
		bot.connect("irc.freenode.net");
		
		// join the #testingBotChannel channel
		bot.joinChannel("#testingBotChannel");
		 
		//this is the default message it will send when your pircbot first goes live 
	    bot.sendMessage("#testingBotChannel", "Hey! Enter any message and I’ll respond!");
	}
}