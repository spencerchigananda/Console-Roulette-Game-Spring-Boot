package com.spencerchigananda.consoleroulettegame.services;

import com.spencerchigananda.consoleroulettegame.dao.BetResultRepository;
import com.spencerchigananda.consoleroulettegame.models.Bet;
import com.spencerchigananda.consoleroulettegame.models.BetResult;
import org.beryx.textio.TextIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class BetResultServiceImpl implements BetResultService{

    private final BetResultRepository betResultRepository;

    @Autowired
    public BetResultServiceImpl(BetResultRepository betResultRepository) {
        this.betResultRepository = betResultRepository;
    }


    @Override
    public BetResult findByBet(Bet bet) {
        BetResult betResult = betResultRepository.findByBet(bet);
        if (betResult == null){
            // TODO handle error
        }
        return betResult;
    }

    @Override
    public void checkBet(List<Bet> betsList, Integer ballNumber, TextIO textIO) {
        List<String> betResults = new ArrayList<>();
        String winnings = "";
        // Bet summary = playerName + \t + betPlaced + \t + outcome + \t + winnings
        String betSummary = "";
        // Iterate through list of bets
        for (Bet bet : betsList) {
            // Check if it successful
            if ((!bet.getBetPlaced().equalsIgnoreCase("EVEN")) && (!bet.getBetPlaced().equalsIgnoreCase("ODD")) && (Integer.parseInt(bet.getBetPlaced())) == ballNumber) {
                // Player bet on exact number
                // Multiply bet amount by 36
                winnings = String.valueOf((bet.getAmount().multiply(BigDecimal.valueOf(36))));
                betSummary = bet.getPlayer().concat("\t").concat(bet.getBetPlaced()).concat("\t").concat("WIN").concat("\t").concat(winnings);
                betResults.add(betSummary);
                // Write to player_summary file
                saveBetToFileAndDisplaySummary(bet.getPlayer(), winnings, bet.getAmount().toString(), textIO);
            } else if ((ballNumber % 2 == 0) && (bet.getBetPlaced().equalsIgnoreCase("EVEN"))) {
                // Ball number is even and bet placed is EVEN
                // Multiply bet amount by 2
                winnings = String.valueOf((bet.getAmount().multiply(BigDecimal.valueOf(2))));
                betSummary = bet.getPlayer().concat("\t").concat(bet.getBetPlaced()).concat("\t").concat("WIN").concat("\t").concat(winnings);
                betResults.add(betSummary);
                // Write to player_summary file
                saveBetToFileAndDisplaySummary(bet.getPlayer(), winnings, bet.getAmount().toString(), textIO);

            } else if ((ballNumber % 2 != 0) && (bet.getBetPlaced().equalsIgnoreCase("ODD"))) {
                // Ball number is odd and bet placed is ODD
                // Multiply bet amount by 2
                winnings = String.valueOf((bet.getAmount().multiply(BigDecimal.valueOf(2))));
                betSummary = bet.getPlayer().concat("\t").concat(bet.getBetPlaced()).concat("\t").concat("WIN").concat("\t").concat(winnings);
                betResults.add(betSummary);
                // Write to player_summary file
                saveBetToFileAndDisplaySummary(bet.getPlayer(), winnings, bet.getAmount().toString(), textIO);

            } else {
                // All other bets lose
                // Multiply bet amount by 0
                winnings = "0";
                betSummary = bet.getPlayer().concat("\t").concat(bet.getBetPlaced()).concat("\t").concat("LOSE").concat("\t").concat(winnings);
                betResults.add(betSummary);
                // Write to player_summary file
                saveBetToFileAndDisplaySummary(bet.getPlayer(), winnings, bet.getAmount().toString(), textIO);
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
