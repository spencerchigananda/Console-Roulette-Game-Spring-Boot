package com.spencerchigananda.consoleroulettegame.services;

import com.spencerchigananda.consoleroulettegame.dao.BetRepository;
import com.spencerchigananda.consoleroulettegame.models.Bet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BetServiceImpl implements BetService{

    private final BetRepository betRepository;

    @Autowired
    public BetServiceImpl(BetRepository betRepository) {
        this.betRepository = betRepository;
    }


    @Override
    public Bet add(Bet bet) {
        return betRepository.save(bet);
    }
}
