package com.spencerchigananda.consoleroulettegame.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "bet_result", schema = "console_roulette", catalog = "")
public class BetResult {
    private Integer id;
    private String outcome;
    private BigDecimal winnings;
    private Bet bet;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "outcome")
    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    @Basic
    @Column(name = "winnings")
    public BigDecimal getWinnings() {
        return winnings;
    }

    public void setWinnings(BigDecimal winnings) {
        this.winnings = winnings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BetResult betResult = (BetResult) o;
        return Objects.equals(id, betResult.id) && Objects.equals(outcome, betResult.outcome) && Objects.equals(winnings, betResult.winnings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, outcome, winnings);
    }

    @ManyToOne
    @JoinColumn(name = "bet_id", referencedColumnName = "id", nullable = false)
    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }
}
