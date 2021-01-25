package com.spencerchigananda.consoleroulettegame.services;

import com.spencerchigananda.consoleroulettegame.models.Bet;
import com.spencerchigananda.consoleroulettegame.models.BetResult;
import org.beryx.textio.TextIO;

import java.util.List;

public interface BetResultService {
    BetResult findByBet(Bet bet);
    void checkBet(List<Bet> betsList, Integer ballNumber, TextIO textIO);
}
