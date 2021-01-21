package com.spencerchigananda.consoleroulettegame;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
}
