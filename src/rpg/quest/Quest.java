package rpg.quest;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.io.IOException;
import rpg.itens.Item;
import rpg.itens.Itens;

public class Quest {
    private Item item;
    private int itemQtd;
    private int xpRecompensa;
    
    public void setItem(String itemId) throws IOException {
        Itens itens = new Itens();
        
        this.item = itens.get(itemId);
    }
    
    public Item getItem() {
        return this.item;
    }
    
    @JsonSetter("quantidade")
    public void setItemQtd(int itemQtd) {
        this.itemQtd = itemQtd;
    }
    
    @JsonGetter("quantidade")
    public int getItemQtd() {
        return this.itemQtd;
    }
    
    @JsonSetter("xpRecompensa")
    public void setXpRecompensa(int xpRecompensa) {
        this.xpRecompensa = xpRecompensa;
    }
    
    @JsonGetter("xpRecompensa")
    public int getXpRecompensa() {
        return this.xpRecompensa;
    }
}
