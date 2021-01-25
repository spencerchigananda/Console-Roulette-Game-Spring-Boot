package com.spencerchigananda.consoleroulettegame.services;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService{

    @Override
    public void initializePlayerSummary(List<String> players) {
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
