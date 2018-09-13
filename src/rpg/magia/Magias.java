package rpg.magia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import rpg.GUI.util.Mapper;

public class Magias {
    private final ArrayList<Magia> magias;
    
    public Magias(Magia[] magias) {
        this.magias = new ArrayList<>();
        this.magias.addAll(Arrays.asList(magias));
    }
    
    public Magias() throws IOException {
        this.magias = new ArrayList<>();
        List<Magia> p = Arrays.asList(Mapper.mapper.readValue(new File("elementos\\jsonfiles\\magias.json"), Magia[].class));
        this.magias.addAll(p);
    }
    
    public boolean add(Magia magia) throws IOException {
        //Verifica se o id é vazio, se for retorna false e não adiciona a lista
        if(magia.getId().trim().isEmpty()) {
            return false;
        }
        
        //Verifica se a lista já não contém um personagem com o mesmo id
        if(get(magia.getId().trim()) == null) {
            this.magias.add(magia);
            
            return true;
        }
        
        return false;
    }
    
    public boolean remove(String magiaId) {
        Magia magia = get(magiaId);
        
        if(magia != null) {
            this.magias.remove(magia);
            return true;
        } else {
            return false;
        }
    }
    
    public Magia get(int i) {
        return this.magias.get(i);
    }
    
    public Magia get(String id) {
        for(Magia magia : this.magias) {
            if(magia.getId().equals(id)) {
                return magia;
            }
        }
        
        return null;
    }
    
    public int size() {
        return this.magias.size();
    }    

    public void remove(int i) {
        this.magias.remove(i);
    }
}
