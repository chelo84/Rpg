package rpg.GUI.util;

import java.io.IOException;
import rpg.itens.Itens;
import rpg.magia.Magias;
import rpg.monstro.Monstros;
import rpg.quest.Quests;

public class Listas {
    public static final Itens ITENSJOGO;
    public static final Monstros MONSTROSJOGO;
    public static final Quests QUESTSJOGO;
    public static final Magias MAGIASJOGO;
    
    static {
        try {
            ITENSJOGO = new Itens();
            MONSTROSJOGO = new Monstros();
            QUESTSJOGO = new Quests();
            MAGIASJOGO = new Magias();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
