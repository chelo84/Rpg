package rpg.itens;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import rpg.GUI.util.Mapper;

public class Itens {
    private ArrayList<Item> itens;
    
    public Itens(Item[] itens) {
        this.itens = new ArrayList<>();
        this.itens.addAll(Arrays.asList(itens));
    }
    
    public Itens() throws IOException {
        this.itens = new ArrayList<>();
        List<Item> p = Arrays.asList(Mapper.mapper.readValue(new File("elementos\\jsonfiles\\itens.json"), Item[].class));
        this.itens.addAll(p);
    }
    
    public boolean add(Item item) throws IOException {
        //Verifica se o nome é vazio, se for retorna false e não adiciona a lista de itens.
        if(item.getId().trim().isEmpty()) {
            return false;
        }
        
        //Verifica se a lista de itens já não contém um personagem com o mesmo nome.
        if(get(item.getId().trim()) == null) {
            this.itens.add(item);
            
            return true;
        }
        
        return false;
    }
    
    public boolean remove(String nome) {
        Item item = get(nome);
        
        if(item != null) {
            this.itens.remove(item);
            return true;
        } else {
            return false;
        }
    }
    
    public Item get(int i) {
        return this.itens.get(i);
    }
    
    public Item get(String id) {
        for(Item item : this.itens) {
            if(item.getId().equals(id)) {
                return item;
            }
        }
        
        return null;
    }
    
    public int size() {
        return this.itens.size();
    }    

    public void remove(int i) {
        this.itens.remove(i);
    }
}
