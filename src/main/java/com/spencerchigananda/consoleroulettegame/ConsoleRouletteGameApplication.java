package com.spencerchigananda.consoleroulettegame;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

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

	}
}
