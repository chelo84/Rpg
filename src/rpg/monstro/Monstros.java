package rpg.monstro;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import rpg.GUI.util.Mapper;

public class Monstros {
    ArrayList<Monstro> monstros = new ArrayList<>();
    
    public Monstros() throws IOException {
        List<Monstro> p = Arrays.asList(Mapper.mapper.readValue(new File("elementos\\jsonfiles\\monstros.json"), Monstro[].class));
        this.monstros.addAll(p);
    }
    
    public void add(Monstro monstro) {
        this.monstros.add(monstro);
    }
    
    public boolean remove(String id) {
        Monstro monstro = this.get(id);
        
        if(monstro != null) {
            this.monstros.remove(monstro);
            return true;
        } else {
            return false;
        }
    }
    
    public Monstro get(int i) {
        return this.monstros.get(i);
    }
    
    public Monstro get(String id) {
        for(Monstro monstro : this.monstros) {
            if(monstro.getId().equals(id)) {
                return monstro;
            }
        }
        
        return null;
    }
    
    public int size() {
        return this.monstros.size();
    }
}
