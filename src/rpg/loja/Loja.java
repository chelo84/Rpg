package rpg.loja;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static rpg.GUI.util.Listas.ITENSJOGO;
import rpg.itens.Item;
import rpg.itens.Itens;

public class Loja {
    private String[] itensId;
    private Itens equips;
    private Itens usaveis;
    private Itens magias;
    
    public Loja() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        this.itensId = mapper.readValue(new File("elementos\\jsonfiles\\loja.json"), String[].class);
        
        this.setTipos(this.itensId);
    }
    
    public void setTipos(String[] itensId) {
        ArrayList<Item> equipsList = new ArrayList<>();
        ArrayList<Item> usaveisList = new ArrayList<>();
        ArrayList<Item> magiasList = new ArrayList<>();
        
        for(String itemId : itensId) {
            Item item = ITENSJOGO.get(itemId);
            
            if(item.isEquipavel()) equipsList.add(item);
            if(item.getTipo().equals("usavel")) usaveisList.add(item);
            if(item.getTipo().equals("magia")) magiasList.add(item);
        }
        
        this.equips = new Itens(equipsList.toArray(new Item[equipsList.size()]));
        this.usaveis = new Itens(usaveisList.toArray(new Item[usaveisList.size()]));
        this.magias = new Itens(magiasList.toArray(new Item[magiasList.size()]));
    }
    
    public Itens getEquips() {
        return this.equips;
    }
    
    public Itens getUsaveis() {
        return this.usaveis;
    }
    
    public Itens getMagias() {
        return this.magias;
    }
}
