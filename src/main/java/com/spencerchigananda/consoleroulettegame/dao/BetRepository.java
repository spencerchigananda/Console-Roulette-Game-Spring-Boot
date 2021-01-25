package com.spencerchigananda.consoleroulettegame.dao;

import com.spencerchigananda.consoleroulettegame.models.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetRepository extends JpaRepository<Bet, Integer> {
    Bet findByPlayer(String player);
}
