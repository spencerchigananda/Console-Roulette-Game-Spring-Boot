package com.spencerchigananda.consoleroulettegame;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

@SpringBootApplication
public class ConsoleRouletteGameApplication implements CommandLineRunner {

	/*public static void main(String[] args) {
		SpringApplication.run(ConsoleRouletteGameApplication.class, args);
	}*/

	public static void main(String[] args) {
		new SpringApplicationBuilder(ConsoleRouletteGameApplication.class).headless(false).run(args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Implement Text IO
		TextIO textIO = TextIoFactory.getTextIO();

		// Read players from file
		Scanner scanner = new Scanner(new File("src/main/resources/players.txt"));

		// Load players from file
		List<String> players = new ArrayList<>();
		while (scanner.hasNextLine()){
			// Add players to list
			players.add(scanner.nextLine());
			//scanner.close();

		}

		// List to hold all the bets
		List<String> betsList = new ArrayList<>();

		// Display players
		textIO.getTextTerminal().printf("\nWelcome to Console Roulette v1.\n PLAYERS: ");
		for (String gamePlayer: players) {
			textIO.getTextTerminal().printf("\n %s", gamePlayer);

		}

		// Create player_summary file and initialize with default values
		initializePlayerSummary(players);

		// Schedule the timer for selecting random numbers every 30 seconds
		Timer timer = new Timer();
		timer.schedule(new SelectRandomNumber((c)->{
			System.out.println("Ball number is " + c);


			// Place a bet
			/*String bet = textIO.newStringInputReader().read("\n \n Place your bet: ");
			// Split bet string by spaces to get player name, bet placed, and amount
			String playerName = bet.split("\\s+")[0];
			String betPlaced = bet.split("\\s+")[1];
			String betAmount = bet.split("\\s+")[2];

			// Test print to see if bets properly split
			System.out.println("Player: ".concat(playerName).concat("\n Bet Placed: ").concat(betPlaced).concat("\n Bet amount: ").concat(betAmount));*/

			// Place bets in a list to allow multiple bets from same user
			List<String> bets = new ArrayList<>();
			bets = textIO.newStringInputReader().readList("\n\nPlace your bet (comma separated if many): ");
			System.out.println("PLAYER BETS>>>>>>> ".concat(bets.toString()));


			//betsList.add(bets);

			// Check bet and print ball number and winnings
			checkBet(bets, c, textIO);

			// Clear list after check bets
			bets.clear();

		}), 0, 30000);

	}

	private void initializePlayerSummary(List<String> players) {
		File playerSummaryFile = new File("src/main/resources/player_summary.txt");
		String initialSummary = "";
		try {
			if (playerSummaryFile.createNewFile()) {
				System.out.println("File created: " + playerSummaryFile.getName());
				// Write to summary with default values
				BufferedWriter writer = null;
				try {
					writer = new BufferedWriter(new FileWriter("src/main/resources/player_summary.txt"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					// Append to text file
					for (String gamePlayer: players) {
						initialSummary = gamePlayer
								.concat(",")
								.concat("0").concat(",")
								.concat("0").concat("\n");
						writer.write(initialSummary);

					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void checkBet(List<String> betsList, Integer ballNumber, TextIO textIO) {
		List<String> betResults = new ArrayList<>();
		String winnings = "";
		// Bet summary = playerName + \t + betPlaced + \t + outcome + \t + winnings
		String betSummary = "";
		// Iterate through list of bets
		for (String bet : betsList) {
			// Check if it successful
			if ((!bet.split("\\s+")[1].equalsIgnoreCase("EVEN")) && (!bet.split("\\s+")[1].equalsIgnoreCase("ODD")) && (Integer.parseInt(bet.split("\\s+")[1])) == ballNumber){
				// Player bet on exact number
				// Multiply bet amount by 36
				winnings = String.valueOf((Integer.parseInt(bet.split("\\s+")[2]) * 36));
				betSummary = bet.split("\\s+")[0].concat("\t").concat(bet.split("\\s+")[1]).concat("\t").concat("WIN").concat("\t").concat(winnings);
				betResults.add(betSummary);
				// Write to player_summary file
				saveBetToFileAndDisplaySummary(bet.split("\\s+")[0], winnings, bet.split("\\s+")[2], textIO);
			} else  if ((ballNumber % 2 == 0) && (bet.split("\\s+")[1].equalsIgnoreCase("EVEN"))){
				// Ball number is even and bet placed is EVEN
				// Multiply bet amount by 2
				winnings = String.valueOf((Integer.parseInt(bet.split("\\s+")[2]) * 2));
				betSummary = bet.split("\\s+")[0].concat("\t").concat(bet.split("\\s+")[1]).concat("\t").concat("WIN").concat("\t").concat(winnings);
				betResults.add(betSummary);
				// Write to player_summary file
				saveBetToFileAndDisplaySummary(bet.split("\\s+")[0], winnings, bet.split("\\s+")[2], textIO);

			} else if ((ballNumber % 2 != 0) && (bet.split("\\s+")[1].equalsIgnoreCase("ODD"))){
				// Ball number is odd and bet placed is ODD
				// Multiply bet amount by 2
				winnings = String.valueOf((Integer.parseInt(bet.split("\\s+")[2]) * 2));
				betSummary = bet.split("\\s+")[0].concat("\t").concat(bet.split("\\s+")[1]).concat("\t").concat("WIN").concat("\t").concat(winnings);
				betResults.add(betSummary);
				// Write to player_summary file
				saveBetToFileAndDisplaySummary(bet.split("\\s+")[0], winnings, bet.split("\\s+")[2], textIO);

			} else {
				// All other bets lose
				// Multiply bet amount by 0
				winnings = "0";
				betSummary = bet.split("\\s+")[0].concat("\t").concat(bet.split("\\s+")[1]).concat("\t").concat("LOSE").concat("\t").concat(winnings);
				betResults.add(betSummary);
				// Write to player_summary file
				saveBetToFileAndDisplaySummary(bet.split("\\s+")[0], winnings, bet.split("\\s+")[2], textIO);
			}

		}

		// Print to console the results
		String header1 = "\nNumber: ".concat(String.valueOf(ballNumber)).concat("\n");
		String header2 = "Player".concat("\t").concat("Bet").concat("\t").concat("Outcome").concat("\t").concat("Winnings").concat("\n");
		String header3 = "---".concat("\n");

		textIO.getTextTerminal().printf(header1.concat(header2).concat(header3));
		for (String result: betResults) {
			textIO.getTextTerminal().printf("\n".concat(result));

		}

	}

	private void saveBetToFileAndDisplaySummary(String playerName, String winnings, String betAmount, TextIO textIO) {
		String dataToWrite = "";
		dataToWrite = playerName.concat(",").concat(winnings).concat(",").concat(betAmount);
		List<String> summaryData = new ArrayList<>();

		// Read file to get previous values and update values
		// Read players from file
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("src/main/resources/player_summary.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Load players from file
		List<String> players = new ArrayList<>();
		while (scanner.hasNextLine()){
			// Add players to list
			players.add(scanner.nextLine());

		}

		for (String player: players) {
			// Get previous values and add new ones to them
			if (player.split(",")[0].equals(playerName)){
				String previousWinning = player.split(",")[1];
				String previousBetAmount = player.split(",")[2];
				dataToWrite = "\n".concat(playerName)
						.concat(",")
						.concat(String.valueOf(Integer.parseInt(previousWinning) + Integer.parseInt(winnings)))
						.concat(",")
						.concat(String.valueOf(Integer.parseInt(previousBetAmount) + Integer.parseInt(betAmount)));
				// Append to summary data
				summaryData.add(dataToWrite);
			}
		}
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("src/main/resources/player_summary.txt", false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			// Append to text file
			writer.write(dataToWrite);
			//writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Write to console Player Summary
		String header1 = "\n\nPlayer".concat("\t").concat("Total Win").concat("\t").concat("Total Bet").concat("\n");
		String header2 = "---".concat("\n");

		textIO.getTextTerminal().printf(header1.concat(header2));
		//System.out.println("SUMMARY: ".concat(summaryData.get(summaryData.size() - 1).toString()));
		for (String data: summaryData) {
			textIO.getTextTerminal().printf(""
					.concat(data.split(",")[0])
					.concat("\t")
					.concat(data.split(",")[1])
					.concat("\t")
					.concat(data.split(",")[2]));

		}
		textIO.getTextTerminal().printf("\n \n");
	}
}
