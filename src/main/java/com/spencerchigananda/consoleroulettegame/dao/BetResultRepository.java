package com.spencerchigananda.consoleroulettegame.dao;

import com.spencerchigananda.consoleroulettegame.models.Bet;
import com.spencerchigananda.consoleroulettegame.models.BetResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetResultRepository extends JpaRepository<BetResult, Integer> {
    BetResult findByBet(Bet bet);
}
