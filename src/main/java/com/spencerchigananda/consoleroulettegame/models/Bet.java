package com.spencerchigananda.consoleroulettegame.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Bet {
    private Integer id;
    private String player;
    private String betPlaced;
    private BigDecimal amount;

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
    @Column(name = "player")
    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    @Basic
    @Column(name = "bet_placed")
    public String getBetPlaced() {
        return betPlaced;
    }

    public void setBetPlaced(String betPlaced) {
        this.betPlaced = betPlaced;
    }

    @Basic
    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bet bet = (Bet) o;
        return Objects.equals(id, bet.id) && Objects.equals(player, bet.player) && Objects.equals(betPlaced, bet.betPlaced) && Objects.equals(amount, bet.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, player, betPlaced, amount);
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id=" + id +
                ", player='" + player + '\'' +
                ", betPlaced='" + betPlaced + '\'' +
                ", amount=" + amount +
                '}';
    }
}
