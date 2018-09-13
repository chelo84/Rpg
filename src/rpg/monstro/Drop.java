package rpg.monstro;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rpg.itens.Item;
import rpg.itens.Itens;

public class Drop {
    private Item item;
    private double chance;
    private int qtdMin;
    private int qtdMax;

    public Item getItem() {
        return item;
    }

    public void setItem(String itemId) {
        Itens itens = null;
        try {
            itens = new Itens();
        } catch (IOException ex) {
            Logger.getLogger(Drop.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Item item = itens.get(itemId);
        
        this.item = item;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public int getQtdMin() {
        return qtdMin;
    }

    public void setQtdMin(int qtdMin) {
        this.qtdMin = qtdMin;
    }

    public int getQtdMax() {
        return qtdMax;
    }

    public void setQtdMax(int qtdMax) {
        this.qtdMax = qtdMax;
    }
}
